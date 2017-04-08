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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

import static id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme.PersonalActivity.pos;

public class TargetActivity extends AppCompatActivity {
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
    private EditText etTarget, etDue, etNotes;
    private RadioGroup rgPriority;
    private ImageButton ivTgl;
    private Button btSave;

    private FirebaseDatabase mDB;
    private DatabaseReference mDBtarget, mDBtargetUser;
    private Long jumlahData;
    private Integer currentPostId;
    private String dbCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        setTitle("Input Target");

        etTarget = (EditText) findViewById(R.id.editTextTarget);
        etDue = (EditText) findViewById(R.id.editTextDue);
        etNotes = (EditText) findViewById(R.id.editTextNote);
        rgPriority = (RadioGroup) findViewById(R.id.radioGroupPriority);
        ivTgl = (ImageButton) findViewById(R.id.imageButtonTgl);
        ivTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });
        btSave = (Button) findViewById(R.id.buttonSaveTarget);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fabtarget);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("POS", pos);
                if(pos == "per"){
                    Intent k = new Intent(TargetActivity.this, TargetActivity.class);
                    startActivity(k);
                }
            }
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();

        dbCurrentUser = mAuth.getCurrentUser().getUid();

        mDBtarget = mDB.getReference().child("target");
        mDBtargetUser = mDBtarget.child(dbCurrentUser);
        mDBtargetUser.addValueEventListener(new ValueEventListener() {
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
        String target = etTarget.getText().toString();
        String due = etDue.getText().toString();
        String note = etNotes.getText().toString();
        // get selected radio button from radioGroup
        int selectedId = rgPriority.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        String priority = radioButton.getText().toString();

        if (TextUtils.isEmpty(target) || TextUtils.isEmpty(due) || TextUtils.isEmpty(note) || TextUtils.isEmpty(priority)) {
            return;
        }

        Log.d("Input", target);
        Log.d("Input", due);
        Log.d("Input", note);
        Log.d("Input", priority);

        // Database Reference for note
        DatabaseReference dbtarget = mDBtargetUser.child(currentPostId.toString());

        dbtarget.child("target").setValue(target);
        dbtarget.child("due").setValue(due);
        dbtarget.child("priority").setValue(priority);
        dbtarget.child("note").setValue(note);


        Toast.makeText(getApplicationContext(), "Target saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateDate() {
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),
                dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }
}
