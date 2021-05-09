package com.kietnt.foodbuyer.DAO;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kietnt.foodbuyer.Fragment.FoodFragment;
import com.kietnt.foodbuyer.Model.Food;

import java.util.ArrayList;

import static com.kietnt.foodbuyer.Fragment.FoodFragment.foodsAdapter;

public class FoodDAO {
    DatabaseReference mData;
    String key;
    private Context context;
    FoodFragment foodFragment;

    public FoodDAO(Context context){
        this.mData = FirebaseDatabase.getInstance().getReference("Food");
        this.context = context;

    }

    public FoodDAO(Context context, FoodFragment foodFragment){
        this.mData = FirebaseDatabase.getInstance().getReference("Food");
        this.context = context;
        this.foodFragment = foodFragment;

    }




    public ArrayList<Food> getAllFood(final String categories_id){
        final ArrayList<Food> list = new ArrayList<Food>();
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot data:snapshot.getChildren()){
                    if (data.child("categories_id").getValue(String.class).equalsIgnoreCase(categories_id)){
                        Food item = data.getValue(Food.class);

                        list.add(item);
                    }

                }
                foodsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//            mData.addValueEventListener(listener);
        return list;
    }

}
