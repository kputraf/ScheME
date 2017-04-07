package id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private TextView tvGoLogin;
    private EditText etUsername, etEmail, etPassword;
    private Button btRegister;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mDBuser;

    private String username, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        mDBuser = mDB.getReference().child("user_data");

        etUsername = (EditText) findViewById(R.id.editTextRegUsername);
        etEmail = (EditText) findViewById(R.id.editTextRegEmail);
        etPassword = (EditText) findViewById(R.id.editTextRegPassword);
        btRegister = (Button) findViewById(R.id.buttonRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });
    }

    private boolean regTrue() {
        username = etUsername.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Username harus diisi");
            return false;
        }
        if (username.length() <= 5) {
            etUsername.setError("Username minimal 6 char");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email harus diisi");
            return false;
        }
        if (!email.matches(emailPattern)) {
            etEmail.setError("Email harus sesuai format");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password harus diisi");
            return false;
        }
        if (password.length() <= 7) {
            etPassword.setError("Password minimal 8 char.");
            return false;
        }
        return true;
    }

    private void doRegister() {
        if (regTrue()) {
            btRegister.setText("Silahkan Tunggu");
            btRegister.setEnabled(false);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            String UID = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUser = mDBuser.child(UID);
                            currentUser.child("username").setValue(username);
                            currentUser.child("email").setValue(email);

                            Date date = new Date();
                            String time = DateFormat.getDateTimeInstance().format(date);

                            currentUser.child("date_created").setValue(time);
                            btRegister.setEnabled(true);

                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Berhasil")
                                    .setMessage("Register berhasil")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            etUsername.setText("");
                                            etEmail.setText("");
                                            etPassword.setText("");

                                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    }).show();
                            mAuth.signOut();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    btRegister.setText("Register");
                    btRegister.setEnabled(true);

                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Gagal")
                            .setMessage("Registrasi gagal " + e.getMessage())
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            });
        }
    }
}
