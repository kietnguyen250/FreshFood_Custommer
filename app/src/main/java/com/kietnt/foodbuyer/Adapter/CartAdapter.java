package com.kietnt.foodbuyer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kietnt.foodbuyer.Model.Cart;
import com.kietnt.foodbuyer.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    public static ArrayList<Cart> list_cart;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference mDataCart;
    String key = "";

    public CartAdapter(Context context, ArrayList<Cart> list_cart){
        this.context = context;
        this.list_cart = list_cart;
    }
    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDataCart = FirebaseDatabase.getInstance().getReference("Cart");

        LayoutInflater inflater =(LayoutInflater) ((AppCompatActivity)context).getLayoutInflater();

        View view = inflater.inflate(R.layout.item_cart, parent, false);
        CartAdapter.MyViewHolder holder = new CartAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, final int position) {
        Cart item = list_cart.get(position);
        holder.cart_nameFood.setText(item.getNameFood());
        holder.cart_price.setText(item.getPriceFood()+"");
        holder.cart_amount.setText(item.getAmount()+"");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

    }

    @Override
    public int getItemCount() {
        return list_cart.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView cart_nameFood, cart_price, cart_amount;
        public MyViewHolder(final View view){
            super(view);
            cart_nameFood = view.findViewById(R.id.cart_nameFood);
            cart_price = view.findViewById(R.id.cart_price);
            cart_amount = view.findViewById(R.id.cart_amount);
            cardView = view.findViewById(R.id.item_cart);



        }
    }
}

