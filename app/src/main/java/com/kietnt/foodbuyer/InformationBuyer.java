package com.kietnt.foodbuyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InformationBuyer extends AppCompatActivity {

    EditText edt_username, edt_address, edt_phone_buyer;
    TextView tv_ngaySinh;
    Spinner sp_male;
    Button btn_create;
    ImageView iv_back;
    private DatabaseReference mData_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_information_buyer);
        mData_ = FirebaseDatabase.getInstance().getReference("Buyer");

        initView();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_ngaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NgaySinh();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(InformationBuyer.this,
                R.array.male_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_male.setAdapter(adapter);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = edt_username.getText().toString().trim();
                String Address = edt_address.getText().toString().trim();
                String NgaySinh = tv_ngaySinh.getText().toString();
                String GioiTinh = sp_male.getSelectedItem().toString();
                String Phone = edt_phone_buyer.getText().toString();

                if (Username.isEmpty() && Address.isEmpty() && Phone.isEmpty() && NgaySinh.isEmpty()){
                    Toast.makeText(InformationBuyer.this, "Vui lòng nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                }
                else {
                    mData_.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.child(Phone).exists()){
                                Intent intent = new Intent(InformationBuyer.this,SignUpActivity.class);
                                Bundle b = new Bundle();
                                b.putString("USERNAME", Address);
                                b.putString("NAME", Username);
                                b.putString("GIOITINH", GioiTinh);
                                b.putString("NGAYSINH", NgaySinh);
                                b.putString("PHONE", Phone);
                                intent.putExtras(b);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
                            }
                            else {
                                Toast.makeText(InformationBuyer.this, "Số điện thoại này đã tồn tại !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }


            }
        });

    }

    private void NgaySinh(){
        Date today =new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        final int months = calendar.get(Calendar.MONTH);
        final int years = calendar.get(Calendar.YEAR);

        final Calendar calendar1 = Calendar.getInstance();
        int date = calendar1.get(Calendar.DAY_OF_MONTH);
        int month = calendar1.get(Calendar.MONTH);
        int year = calendar1.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(InformationBuyer.this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int i, int i1, int i2) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                calendar1.set(i,i1,i2);
                tv_ngaySinh.setText(simpleDateFormat.format(calendar1.getTime()));
            }
        },years, months,dayOfWeek);
        datePickerDialog.show();
    }

    public void initView(){

        edt_username = findViewById(R.id.edt_username);
        edt_address = findViewById(R.id.edt_address);
        btn_create = findViewById(R.id.btn_create);
        edt_phone_buyer = findViewById(R.id.edt_phone_buyer);
        tv_ngaySinh = findViewById(R.id.tv_ngaySinh);
        sp_male = findViewById(R.id.sp_male);
        iv_back = findViewById(R.id.iv_back);

    }
}