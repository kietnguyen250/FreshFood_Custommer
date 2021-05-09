package com.kietnt.foodbuyer.DAO;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kietnt.foodbuyer.Fragment.FoodFragment;
import com.kietnt.foodbuyer.Fragment.Fragment_Cart;
import com.kietnt.foodbuyer.Model.Cart;
import com.kietnt.foodbuyer.Model.Food;

import java.util.ArrayList;

import static com.kietnt.foodbuyer.Fragment.FoodFragment.foodsAdapter;

public class CartDAO {
    DatabaseReference mData;
    String key;
    private Context context;
    Fragment_Cart fragment_cart;

    public CartDAO(Context context){
        this.mData = FirebaseDatabase.getInstance().getReference("CartDAO");
        this.context = context;

    }

    public CartDAO(Context context, Fragment_Cart fragment_cart){
        this.mData = FirebaseDatabase.getInstance().getReference("CartDAO");
        this.context = context;
        this.fragment_cart = fragment_cart;

    }




    public ArrayList<Cart> getAllCart(final String email_buyer){
        final ArrayList<Cart> list = new ArrayList<Cart>();
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot data:snapshot.getChildren()){
                    if (data.child("email_buyer").getValue(String.class).equalsIgnoreCase(email_buyer)){
                        Cart item = data.getValue(Cart.class);

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
