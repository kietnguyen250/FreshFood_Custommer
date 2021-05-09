package com.kietnt.foodbuyer.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kietnt.foodbuyer.LoginActivity;
import com.kietnt.foodbuyer.Model.Buyer;
import com.kietnt.foodbuyer.R;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    TextView tv_hoTen, tv_gioiTinh, tv_namSinh, tv_usn, tv_sdt, tv_mail;
    DatabaseReference mData;
    Button btn_changepass, btn_signout;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container,false);

        mData = FirebaseDatabase.getInstance().getReference("Buyer");
        initView(view);

        SharedPreferences pref = ((AppCompatActivity) getActivity()).getSharedPreferences("buyer.dat",MODE_PRIVATE);
        String strEmail = pref.getString("Email","");
        String strPass = pref.getString("password","");

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String sdt = data.getKey();
                    Buyer buyer = data.getValue(Buyer.class);
                    String email = buyer.getEmail();
                    String address = buyer.getAddress();
                    String name = buyer.getHoTen();
                    String gioiTinh = buyer.getGioiTinh();
                    String ngaySinh = buyer.getNamSinh();

                    if ((email).equals(strEmail)){

                        tv_usn.setText(address);
                        tv_sdt.setText("(+84) "+sdt);
                        tv_mail.setText(email);
                        tv_hoTen.setText(name);
                        tv_gioiTinh.setText(gioiTinh);
                        tv_namSinh.setText(ngaySinh);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getContext(), LoginActivity.class));

                }
            }
        };

        //change pass
        btn_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1 =getLayoutInflater();
                View dialogView1 = inflater1.inflate(R.layout.dialog_change_password, null);
                dialog1.setView(dialogView1);

                final EditText edt_email_changepass = dialogView1.findViewById(R.id.edt_email_changepass);
                final EditText edt_name_changepass = dialogView1.findViewById(R.id.edt_name_changepass);
                final EditText edt_oldpass = dialogView1.findViewById(R.id.edt_oldpass);
                final EditText edt_newpass = dialogView1.findViewById(R.id.edt_newpass);
                final EditText edt_confirmpass = dialogView1.findViewById(R.id.edt_confirmpass);

                Button btn_change = dialogView1.findViewById(R.id.btn_change);
                Button btn_huy = dialogView1.findViewById(R.id.btn_huy);

                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()){
                            Buyer buyer = data.getValue(Buyer.class);
                            String email = buyer.getEmail();
                            String address = buyer.getAddress();
                            if ((email).equals(strEmail)){

                                edt_name_changepass.setText(address);
                                edt_email_changepass.setText(email);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                final AlertDialog alertDialog1 = dialog1.create();
                alertDialog1.show();

                btn_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (edt_oldpass.getText().length()==0 || edt_newpass.getText().length() == 0 || edt_confirmpass.getText().length() == 0) {
                            Toast.makeText(getActivity(), "Bạn phải nhập đầy đủ thông tin!",
                                    Toast.LENGTH_SHORT).show();

                        }else{
                            String mk_moi = edt_newpass.getText().toString();
                            String re_mk_moi = edt_confirmpass.getText().toString();
                            if (!mk_moi.equals(re_mk_moi)) {
                                Toast.makeText(getActivity(), "Mật khẩu không trùng khớp!",
                                        Toast.LENGTH_SHORT).show();

                            }
                            else if (!(edt_oldpass.getText().toString().trim()).equals(strPass)){
                                Toast.makeText(getActivity(), "Nhập sai mật khẩu!",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (user != null && !edt_newpass.getText().toString().trim().equals("")) {
                                    if (edt_newpass.getText().toString().trim().length() < 6) {
                                        edt_newpass.setError("Mật khẩu quá ngắn!");
                                    } else {
                                        //change pass in Authentication
                                        user.updatePassword(edt_newpass.getText().toString().trim())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            //change pass in realtime-database
                                                            mData.addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    for (DataSnapshot data : snapshot.getChildren()) {
                                                                        String sdt = data.getKey();
                                                                        Buyer buyer = data.getValue(Buyer.class);
                                                                        String email = buyer.getEmail();
                                                                        String name = buyer.getAddress();
                                                                        String gioiTinh = buyer.getGioiTinh();
                                                                        String hoTen = buyer.getHoTen();
                                                                        String namSinh = buyer.getNamSinh();


                                                                        String Password = edt_newpass.getText().toString();

                                                                        if ((email).equals(strEmail)) {
                                                                            buyer = new Buyer();
                                                                            buyer.setAddress(name);
                                                                            buyer.setEmail(email);
                                                                            buyer.setHoTen(hoTen);
                                                                            buyer.setGioiTinh(gioiTinh);
                                                                            buyer.setNamSinh(namSinh);
                                                                            buyer.setPassword(Password);
                                                                            mData.child(sdt).setValue(buyer)
                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {
                                                                                        }
                                                                                    });

                                                                        }

                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                            alertDialog1.cancel();
                                                            Toast.makeText(getContext(), "Đổi mật khẩu thành công, vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
                                                            signOut();
                                                        } else {
                                                            Toast.makeText(getContext(), "Đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });
                                    }
                                } else if (edt_newpass.getText().toString().trim().equals("")) {
                                    edt_newpass.setError("Enter password");
                                }
                            }
                        }

                    }
                });
                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog1.cancel();

                    }
                });
            }
        });

        //Đăng xuất
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                edt_password.clearComposingText();
//                chk_save.setChecked(false);
                SharedPreferences preferences = getActivity().getSharedPreferences("custommer.dat", MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences.edit();

                editor.clear();

                editor.commit();
                signOut();
                Toast.makeText(getActivity(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    //đăng xuất tài khoản
    public void signOut() {
        auth.signOut();
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    private void initView(View view){
        tv_usn = view.findViewById(R.id.tv_usn);
        tv_sdt = view.findViewById(R.id.tv_sdt);
        tv_mail = view.findViewById(R.id.tv_mail);
        tv_hoTen = view.findViewById(R.id.tv_hoTen);
        tv_gioiTinh = view.findViewById(R.id.tv_gioiTinh);
        tv_namSinh = view.findViewById(R.id.tv_namSinh);
        btn_signout = view.findViewById(R.id.btn_signout);
        btn_changepass = view.findViewById(R.id.btn_changepass);

    }
}
