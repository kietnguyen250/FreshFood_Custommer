package com.kietnt.foodbuyer.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kietnt.foodbuyer.DAO.CategoryDAO;
import com.kietnt.foodbuyer.Fragment.FoodFragment;
import com.kietnt.foodbuyer.Model.Category;
import com.kietnt.foodbuyer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<Category> dataCategory;
    //Lấy ảnh
    FirebaseStorage firebaseStorage;
    CategoryDAO categoryDAO;

    public CategoryAdapter(Context context, ArrayList<Category> dataCategory) {
        this.context = context;
        this.dataCategory = dataCategory;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_category,parent,false);
        CategoryAdapter.MyViewHolder holder = new CategoryAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        Category item = dataCategory.get(position);
        Picasso.get().load(item.getHinhLoai()).into(holder.ivCategoryImage);
        holder.tvCategoryName.setText(item.getTenLoai());
        firebaseStorage = FirebaseStorage.getInstance();

        holder.cvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("categories_id", dataCategory.get(position).getTenLoai());
                FoodFragment foodFragment = new FoodFragment();
                foodFragment.setArguments(bundle);
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.container,foodFragment).commit();

            }
        });

    }


    @Override
    public int getItemCount() {
        return dataCategory.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cvCategory;
        TextView tvCategoryName;
        ImageView ivCategoryImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cvCategory = itemView.findViewById(R.id.itemCategory);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            ivCategoryImage = itemView.findViewById(R.id.ivCategoryImage);



        }
    }
}
