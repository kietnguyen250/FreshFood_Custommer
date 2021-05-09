package com.kietnt.foodbuyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;


public class FinishSignUp extends AppCompatActivity{

    private static final String TAG = "";
    PinView pinFromUser;
    ImageView iv_back;
    private String verificationId;
    TextView tv_sdt_;
    Button btn_finish;
    ProgressBar progressBar;
    private PhoneAuthProvider.ForceResendingToken mResendToken;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_finish_signup);

        mAuth = FirebaseAuth.getInstance();


        initView();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {

//            String _userName = b.getString("USERNAME");
//            String _fullName = b.getString("NAME");
//            String _gioiTinh = b.getString("GIOITINH");
//            String _ngaySinh = b.getString("NGAYSINH");
//            String _phone = b.getString("PHONE");
              String _eMail = b.getString("EMAIL");
              String _pass = b.getString("PASS");

              btn_finish.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent intent = new Intent(FinishSignUp.this, LoginActivity.class);
                      b.putString("EMAIL", _eMail);
                      b.putString("PASS", _pass);
                      intent.putExtras(b);
                      startActivity(intent);

                      overridePendingTransition(R.anim.slide_fr_left, R.anim.slide_fr_right);
                  }
              });

//              Toast.makeText(VerifyOTP.this, _phoneNo + " - " + _fullName + " - " + _gioiTinh + " - " + _ngaySinh + " - " + _eMail, Toast.LENGTH_SHORT).show();

//            tv_sdt_.setText(_phoneNo);
//
//            sendVerificationCode(_phoneNo);
            // Force reCAPTCHA flow
        }

    }


//    public void xacNhanOTP(View view){
//        String code = pinFromUser.getText().toString();
//        if (code.isEmpty() || code.length() < 6) {
//
//            pinFromUser.setError("Enter code...");
//            pinFromUser.requestFocus();
//            return;
//        }
//        sendVerificationCode(code);
//    }
//    private void sendVerificationCode(String mobile) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                mobile,
//                60,
//                TimeUnit.SECONDS,
//                VerifyOTP.this,
//                mCallbacks);
//    }


//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//            //Getting the code sent by SMS
//            String code = phoneAuthCredential.getSmsCode();
//
//            //sometime the code is not detected automatically
//            //in this case the code will be null
//            //so user has to manually enter the code
//            if (code != null) {
//                pinFromUser.setText(code);
//                //verifying the code
//                verifyVerificationCode(code);
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//            Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            verificationId = s;
//            mResendToken = forceResendingToken;
//        }
//    };
//
//    private void verifyVerificationCode(String otp) {
//        //creating the credential
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
//        //signing the user
//        signInWithPhoneAuthCredential(credential);
//    }
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(VerifyOTP.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            //verification successful we will start the profile activity
//                            Intent intent = new Intent(VerifyOTP.this, MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                        } else {
//                            //verification unsuccessful.. display an error message
//                            String message = "Somthing is wrong, we will fix it soon...";
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                message = "Invalid code entered...";
//                            }
//                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
//                            snackbar.setAction("Dismiss", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                }
//                            });
//                            snackbar.show();
//                        }
//                    }
//                });
//    }
    public void initView(){
//        pinFromUser = findViewById(R.id.pin_view);
//        tv_sdt_ = findViewById(R.id.tv_sdt_);
        iv_back = findViewById(R.id.iv_back);
        btn_finish = findViewById(R.id.btn_finish);
//        progressBar = findViewById(R.id.progress_bar);
    }
}