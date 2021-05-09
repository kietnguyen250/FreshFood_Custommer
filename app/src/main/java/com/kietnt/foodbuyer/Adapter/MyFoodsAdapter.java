package com.kietnt.foodbuyer.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kietnt.foodbuyer.DAO.FoodDAO;
import com.kietnt.foodbuyer.Model.Cart;
import com.kietnt.foodbuyer.Model.Food;
import com.kietnt.foodbuyer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.kietnt.foodbuyer.Fragment.FoodFragment.counter_fab;

public class MyFoodsAdapter extends RecyclerView.Adapter<MyFoodsAdapter.MyViewHolder> {
    Context context;
    ArrayList<Food> list_food;
    FoodDAO foodDAO;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference mDataCart;
    String key = "";

    public MyFoodsAdapter(Context context, ArrayList<Food> list_food){
        this.context = context;
        this.list_food = list_food;
    }
    @NonNull
    @Override
    public MyFoodsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDataCart = FirebaseDatabase.getInstance().getReference("Cart");

        LayoutInflater inflater =(LayoutInflater) ((AppCompatActivity)context).getLayoutInflater();

        View view = inflater.inflate(R.layout.item_food, parent, false);
        MyFoodsAdapter.MyViewHolder holder = new MyFoodsAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyFoodsAdapter.MyViewHolder holder, final int position) {
        Food item = list_food.get(position);
        Picasso.get().load(item.getFoodImage()).into(holder.img_food);
        holder.tv_name_food.setText(item.getFoodName());
        holder.tv_count_food.setText("Count: "+item.getSoLuong());
        holder.tv_price_food.setText(item.getGia()+" VNĐ");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        holder.tv_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder dialog1 = new androidx.appcompat.app.AlertDialog.Builder(context);
                LayoutInflater inflater1 =((AppCompatActivity)context).getLayoutInflater();
                View dialogView1 = inflater1.inflate(R.layout.dialog_add_cart, null);
                dialog1.setView(dialogView1);

                final ImageView iv_food = dialogView1.findViewById(R.id.iv_food);
                final ElegantNumberButton number_button = dialogView1.findViewById(R.id.number_button);
                final EditText edtNameFood = dialogView1.findViewById(R.id.edtNameFood);
                final EditText edtPriceFood = dialogView1.findViewById(R.id.edtPriceFood);

                Button btnAddCart = dialogView1.findViewById(R.id.btnAddCart);

                Picasso.get().load(item.getFoodImage()).into(iv_food);
                edtNameFood.setText(item.getFoodName());
                edtPriceFood.setText(item.getGia()+"");
                number_button.setNumber("1");

                number_button.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edtPriceFood.setText((item.getGia() * Integer.parseInt(number_button.getNumber()))+"");
                    }
                });


                final AlertDialog alertDialog1 = dialog1.create();
                alertDialog1.show();
                SharedPreferences pref = context.getSharedPreferences("buyer.dat",MODE_PRIVATE);
                String strEmail = pref.getString("Email","");

                btnAddCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Food_id = item.getFood_id();
                        String Food_Name = edtNameFood.getText().toString();
                        int Amount = Integer.parseInt(number_button.getNumber());
                        double Price = Double.parseDouble(edtPriceFood.getText().toString());
                        Cart cart = new Cart();
                        cart.setId_food(Food_id);
                        cart.setNameFood(Food_Name);
                        cart.setPriceFood(Price);
                        cart.setAmount(Amount);
                        cart.setEmail_buyer(strEmail);

                        key = mDataCart.push().getKey();

                        mDataCart.child(key).setValue(cart);

                        Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        counter_fab.increase();
                        alertDialog1.cancel();
                    }
                });


            }
        });


    }

    @Override
    public int getItemCount() {
        return list_food.size();
    }

    //Tạo hàm OnItemClickListener ********
    private static MyFoodsAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(MyFoodsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    //*****************

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tv_name_food, tv_price_food, tv_count_food, tv_add_cart;
        ImageView img_food;
        public MyViewHolder(final View view){
            super(view);
            tv_name_food = view.findViewById(R.id.tvFoodName);
            tv_price_food = view.findViewById(R.id.tvFoodPice);
            tv_count_food = view.findViewById(R.id.tvFoodCount);
            cardView = view.findViewById(R.id.item_food);
            img_food = view.findViewById(R.id.img_food);
            tv_add_cart = view.findViewById(R.id.tvAddCart);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
}
