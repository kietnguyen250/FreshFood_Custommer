package com.kietnt.foodbuyer.BottomSheet;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kietnt.foodbuyer.Adapter.CartAdapter;
import com.kietnt.foodbuyer.Model.Bill;
import com.kietnt.foodbuyer.Model.Buyer;
import com.kietnt.foodbuyer.Model.Cart;
import com.kietnt.foodbuyer.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class BottomSheetBillAdd extends BottomSheetDialogFragment {
    Button  btnAddBill;
    DatabaseReference mDataBill;
    DatabaseReference mDataBuyer;
    ArrayList<Bill> ds_bill;
    String edbillID = "";
    ArrayList<Buyer> list_buyer;

    EditText edEmail_buyer, edt_date, edt_diachi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_add_bill, container, false);
        mDataBill = FirebaseDatabase.getInstance().getReference("Bill");
        mDataBuyer = FirebaseDatabase.getInstance().getReference("Buyer");



        initView(view);
        //set today cho hóa đơn-----
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //--------------------------
        Bundle bundle = getArguments();
        String email = bundle.getString("email_buyer");


        //set toDay===================
        edt_date.setText(currentDate);
        //============================

        mDataBuyer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren()){

                        Buyer item = data.getValue(Buyer.class);
                        String email_ = item.getEmail();
                        String diachi = item.getAddress();
                        if (email_.equals(email)){
                            edEmail_buyer.setText(email_);
                            edt_diachi.setText(diachi);
                        }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Đọc email
                String email_buyer = edEmail_buyer.getText().toString().trim();
                //Đọc date++++++++++++++++++++++++++++++++++++++++
                String date = edt_date.getText().toString().trim();
                //++++++++++++++++++++++++++++++++++++++++++++++++

                String adress = edt_diachi.getText().toString().trim();

                Bill bill = new Bill();
                bill.setEmail_buyer(email_buyer);
                bill.setDate(date);
                bill.setAddress_buyer(adress);
                edbillID = mDataBill.push().getKey();
                mDataBill.child(edbillID).setValue(bill);

                dismiss();
//                SharedPreferences preferences = ((AppCompatActivity)getActivity()).getSharedPreferences("bill.dat", MODE_PRIVATE);
//                SharedPreferences.Editor editor= preferences.edit();
//
//                editor.putString("bill_id", bill_id);
//                editor.putString("Date", date);
//
//
//                editor.commit();
                dismiss();
                Toast.makeText(getContext(), "Thêm hóa đơn thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void initView(View view){
        edt_date = view.findViewById(R.id.edt_date);
        btnAddBill = view.findViewById(R.id.btnAddBill);
        edEmail_buyer = view.findViewById(R.id.edEmail_buyer);
        edt_diachi = view.findViewById(R.id.edt_diachi);
    }


}