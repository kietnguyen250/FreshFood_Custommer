package com.kietnt.foodbuyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout txt_mail, txt_password;
    public static EditText edt_mail_login, edt_password_login;
    Button btn_login;
    TextView btn_signup;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    public static CheckBox chk_save;
    DatabaseReference mDataBuyer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        mDataBuyer = FirebaseDatabase.getInstance().getReference("Buyer");
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        initView();
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {

            String _eMail = b.getString("EMAIL");
            String _pass = b.getString("PASS");
            edt_mail_login.setText(_eMail);
            edt_password_login.setText(_pass);
        }
        loadData();

//        mData_staff = FirebaseDatabase.getInstance().getReference("Staff");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                login();

            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, InformationBuyer.class);
                startActivity(intent);
            }
        });

    }
    private void login() {
        String email = edt_mail_login.getText().toString().trim();
        String password = edt_password_login.getText().toString().trim();

        if (email.isEmpty()){
            edt_mail_login.setError("Email is required");
            edt_mail_login.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edt_mail_login.setError("Please provide valid email");
            return;
        }
        if (password.isEmpty()){
            edt_password_login.setError("Password is required");
            edt_password_login.requestFocus();
            return;
        }
        if (password.length()<6){
            edt_password_login.setError("Min password length should be 6 characters");
            edt_password_login.requestFocus();
            return;
        }

        mDataBuyer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){

                    if (data.child("email").getValue(String.class).equals(email)){
                        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        boolean check = chk_save.isChecked();
        SaveTT(email, password, check);
    }

    public void initView(){


        btn_login = findViewById(R.id.btn_login);
        edt_mail_login = findViewById(R.id.edt_mail_login);
        edt_password_login = findViewById(R.id.edt_password_login);
        btn_signup = findViewById(R.id.btn_signup);
        chk_save = findViewById(R.id.chk_save);

    }

    private void SaveTT(String email, String pwd, boolean check){
        SharedPreferences preferences = getSharedPreferences("buyer.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        if (check){
            editor.putString("Email", email);
            editor.putString("password", pwd);
            editor.putBoolean("check", check);
        }else {
            editor.clear();
        }
        editor.commit();

    }
    private void loadData(){
        SharedPreferences pref =getSharedPreferences("buyer.dat", MODE_PRIVATE);
        boolean check = pref.getBoolean("check", false);
        if (check){
            edt_mail_login.setText(pref.getString("Email", ""));
            edt_password_login.setText(pref.getString("password", ""));
            chk_save.setChecked(check);
        }
    }

    public boolean validate() {
        boolean valid = false;

        String Email = edt_mail_login.getText().toString();
        String Password = edt_password_login.getText().toString();


        if (Email.isEmpty()) {
            valid = false;
            edt_mail_login.setError("Vui lòng nhập email!");
        }
        else if (Password.isEmpty()){
            valid = false;
            edt_password_login.setError("Vui lòng nhập mật khẩu!");
        }
        else {
            if (Password.length() >= 5) {
                valid = true;
                edt_password_login.setError(null);
            } else {
                valid = false;
                edt_password_login.setError("Mật khẩu quá ngắn!");
            }
        }

        return valid;
    }
}