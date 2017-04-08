package id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

import id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme.model.Personal;

public class PersonalActivity extends AppCompatActivity {
    Calendar dateTime = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            DateFormat df_date = DateFormat.getDateInstance();
            etDue.setText(df_date.format(dateTime.getTime()));
        }
    };
    private EditText etActivity, etDue, etNote;
    private Spinner spnCat;
    private ImageButton ibDate;
    private Button btSave;

    private FirebaseDatabase mDB;
    private DatabaseReference mDBpersonal, mDBpersonalUser;
    private Long jumlahData;
    private Integer currentPostId;
    private String dbCurrentUser;
    public static String pos = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        setTitle("Input Personal");
        Log.d("POS", pos);

        etActivity = (EditText) findViewById(R.id.editTextActivity);
        etDue = (EditText) findViewById(R.id.editTextDueAct);
        etNote = (EditText) findViewById(R.id.editTextNoteAct);
        spnCat = (Spinner) findViewById(R.id.spinnerCat);
        ibDate = (ImageButton) findViewById(R.id.imageButtonTglAct);
        ibDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });
        btSave = (Button) findViewById(R.id.buttonSaveAct);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fabpersonal);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("POS", pos);
                if(pos == "per"){
                    Intent k = new Intent(PersonalActivity.this, PersonalActivity.class);
                    startActivity(k);
                }
            }
                                });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();

        dbCurrentUser = mAuth.getCurrentUser().getUid();

        mDBpersonal = mDB.getReference().child("personal");
        mDBpersonalUser = mDBpersonal.child(dbCurrentUser);
        mDBpersonalUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jumlahData = dataSnapshot.getChildrenCount() - 1;
                currentPostId = jumlahData.intValue() + 1;
                String strValue = jumlahData.toString();
                Boolean isDataNotExist = false;
                while (isDataNotExist == false) {
                    Boolean cleanCheck = dataSnapshot.child(currentPostId.toString()).exists();
                    if (cleanCheck) {
                        Log.d("FirebaseCounter", "Child with ID " + currentPostId.toString() + " already Exists! +1");
                        currentPostId += 1;
                    } else {
                        Log.d("FirebaseCounter", "Child with ID " + currentPostId.toString() + " Available to Use!");
                        isDataNotExist = true;
                    }
                }
                Log.d("FirebaseCounter", strValue);
                Log.d("FirebaseCounter", "Next Post Should be : " + currentPostId.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FirebaseError", databaseError.getMessage());
            }
        });
    }

    private void save() {
        String activity = etActivity.getText().toString();
        String due = etDue.getText().toString();
        String note = etNote.getText().toString();
        String cat = spnCat.getSelectedItem().toString();

        if (TextUtils.isEmpty(activity) || TextUtils.isEmpty(due) || TextUtils.isEmpty(note) || TextUtils.isEmpty(cat)) {
            return;
        }

        Log.d("Input", activity);
        Log.d("Input", due);
        Log.d("Input", note);
        Log.d("Input", cat);

        // Database Reference for note
        DatabaseReference dbtarget = mDBpersonalUser.child(currentPostId.toString());

        dbtarget.child("activity").setValue(activity);
        dbtarget.child("due").setValue(due);
        dbtarget.child("category").setValue(cat);
        dbtarget.child("note").setValue(note);


        Toast.makeText(getApplicationContext(), "Personal saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateDate() {
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),
                dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }
}
