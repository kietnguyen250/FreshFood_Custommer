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

import com.kietnt.foodbuyer.Model.DetailBill;
import com.kietnt.foodbuyer.R;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {
    Context context;
    public static ArrayList<DetailBill> list_detail;

    public DetailAdapter(Context context, ArrayList<DetailBill> list_detail){
        this.context = context;
        this.list_detail = list_detail;
    }
    @NonNull
    @Override
    public DetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =(LayoutInflater) ((AppCompatActivity)context).getLayoutInflater();

        View view = inflater.inflate(R.layout.item_cart, parent, false);
        DetailAdapter.MyViewHolder holder = new DetailAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.MyViewHolder holder, final int position) {
        DetailBill item = list_detail.get(position);
        holder.detail_nameFood.setText(item.getNameFood());
        holder.detail_price.setText(item.getPriceFood()+"");
        holder.detail_amount.setText(item.getAmount()+"");


    }

    @Override
    public int getItemCount() {
        return list_detail.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView detail_nameFood, detail_price, detail_amount;
        public MyViewHolder(final View view){
            super(view);
            detail_nameFood = view.findViewById(R.id.cart_nameFood);
            detail_price = view.findViewById(R.id.cart_price);
            detail_amount = view.findViewById(R.id.cart_amount);
            cardView = view.findViewById(R.id.item_cart);

        }
    }
}