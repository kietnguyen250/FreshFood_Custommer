package com.kietnt.foodbuyer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kietnt.foodbuyer.Adapter.DetailAdapter;
import com.kietnt.foodbuyer.Model.DetailBill;
import com.kietnt.foodbuyer.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailFragment  extends Fragment {
    public static RecyclerView rcv_detail;
    TextView tvSum;
    ImageView iv_back;
    ImageView iv_AddDetail;
    DatabaseReference mDataDetail;

    double thanhTien;

    DetailAdapter detailAdapter;
    ArrayList<DetailBill> list_detail;
    String id_cart = "";
    String key = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        initView(root);
        mDataDetail= FirebaseDatabase.getInstance().getReference("Detail");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
//                fragment_food.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
            }
        });
        //lấy email người dùng từ bill
        Bundle getdata =getArguments();
        final String bill_email_buyer = getdata.getString("bill_email_buyer");
        final String id_bill = getdata.getString("id_bill");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcv_detail.setLayoutManager(layoutManager);
        rcv_detail.hasFixedSize();
        rcv_detail.setHasFixedSize(false);

        list_detail = new ArrayList<DetailBill>();

        mDataDetail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_detail.clear();
                for (DataSnapshot data:snapshot.getChildren()){

                    //so sánh email để lấy đúng giỏ hàng của khách hàng
                    if (data.child("bill_id").getValue(String.class).equalsIgnoreCase(id_bill)){
                        DetailBill item = data.getValue(DetailBill.class);

                        list_detail.add(item);

                        thanhTien = thanhTien+item.getPriceFood();

                        // Đặt kiểu số-----------------
                        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        decimalFormat.applyPattern("#,###,###,###");
                        tvSum.setText(decimalFormat.format(thanhTien) + " Đ");
                        //------------------------------

                    }

                }
                detailAdapter = new DetailAdapter(getContext(),list_detail);
                rcv_detail.setAdapter(detailAdapter);
                detailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

    private void initView(View root) {
        rcv_detail = root.findViewById(R.id.rcv_detail);
        iv_back = root.findViewById(R.id.iv_back);
        tvSum = root.findViewById(R.id.tvSum);
    }
}