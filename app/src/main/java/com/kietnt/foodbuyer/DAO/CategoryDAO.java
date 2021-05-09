package com.kietnt.foodbuyer.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kietnt.foodbuyer.Adapter.CategoryAdapter;
import com.kietnt.foodbuyer.Model.Category;

import java.util.ArrayList;

import static com.kietnt.foodbuyer.Fragment.CategoryFragment.rcvCategory;

public class CategoryDAO {
    // Write a message to the database
    DatabaseReference mData;
    private Context context;
    String key;
    CategoryAdapter adapter;


    public CategoryDAO( Context context) {
        //Tạo bảng thể loại
        this.mData = FirebaseDatabase.getInstance().getReference("TheLoai");
        this.context = context;
    }
    // Read all list Category
    public ArrayList<Category> getAllCategory(){
        final ArrayList<Category> dataCategory = new ArrayList<Category>();
        // Hàm đọc dữ liệu liên tục, add vào data
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataCategory.clear(); // Xóa data cũ đi trước khi lấy value
                // Đếm all children trong bảng
                for (DataSnapshot data:snapshot.getChildren()){
                    // Gọi model ra getValue để lấy value và add vào array
                    Category item = data.getValue(Category.class);
                    dataCategory.add(item);

                    adapter = new CategoryAdapter(context,dataCategory);
                    rcvCategory.setAdapter(adapter);

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mData.addValueEventListener(listener);
        return dataCategory;
    }


}
