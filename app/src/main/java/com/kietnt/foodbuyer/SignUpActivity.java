package com.kietnt.foodbuyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.kietnt.foodbuyer.Model.Buyer;

public class SignUpActivity extends AppCompatActivity {

    public static EditText edt_email, edt_pass, edt_repass;
    ImageView iv_back;
    CountryCodePicker countryCodePicker;
    DatabaseReference mData_user;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mData_user = FirebaseDatabase.getInstance().getReference("Buyer");

        //ánh xạ
        initView();


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    //set onClick button
    public void signUp(View view){
        DangKy();
    }

    private void DangKy(){

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            String _userName = b.getString("USERNAME");
            String _fullName = b.getString("NAME");
            String _gioiTinh = b.getString("GIOITINH");
            String _ngaySinh = b.getString("NGAYSINH");
            String _phone = b.getString("PHONE");
            String _pass = edt_pass.getText().toString();


            String Email = edt_email.getText().toString().trim();
            if (validate()){

                if (!validate()){
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập đúng định dạng!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.createUserWithEmailAndPassword(Email, _pass)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mData_user.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Buyer buyer = new Buyer(_userName, Email, _fullName, _gioiTinh, _ngaySinh, _pass);
                                                mData_user.child(_phone).setValue(buyer);

                                                Intent intent = new Intent(getApplicationContext(), FinishSignUp.class);


                                                b.putString("USERNAME", _userName);
                                                b.putString("NAME", _fullName);
                                                b.putString("GIOITINH", _gioiTinh);
                                                b.putString("NGAYSINH", _ngaySinh);
                                                b.putString("EMAIL", Email);
                                                b.putString("PHONE", _phone);
                                                b.putString("PASS", _pass);

                                                intent.putExtras(b);
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                        Toast.makeText(SignUpActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Lỗi đăng kí, vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                } //
            }
        }



    }


    public boolean validate() {
        boolean valid = false;

        String Phone = edt_email.getText().toString();
        String Password = edt_pass.getText().toString();
        String RePass = edt_repass.getText().toString();


        if (Phone.isEmpty()) {
            valid = false;
            edt_email.setError("Vui lòng nhập email!");
        }

        else if (Password.isEmpty()){
            valid = false;
            edt_pass.setError("Vui lòng nhập mật khẩu!");
        }
        else if (!RePass.equalsIgnoreCase(Password)){
            valid = false;
            edt_repass.setError("Vui lòng nhập đúng mật khẩu!");
        }
        else {
            if (Password.length() >= 5) {
                valid = true;
                edt_pass.setError(null);
            } else {
                valid = false;
                edt_pass.setError("Mật khẩu quá ngắn!");
            }
        }

        return valid;
    }

    public void initView(){
        edt_email = findViewById(R.id.edt_email);
        edt_repass = findViewById(R.id.edt_repass);
        edt_pass = findViewById(R.id.edt_pass);
        iv_back = findViewById(R.id.iv_back);
//        countryCodePicker = findViewById(R.id.country_code_picker);
    }

}