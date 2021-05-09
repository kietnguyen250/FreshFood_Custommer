package com.kietnt.foodbuyer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andremion.counterfab.CounterFab;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kietnt.foodbuyer.Adapter.MyFoodsAdapter;
import com.kietnt.foodbuyer.DAO.FoodDAO;
import com.kietnt.foodbuyer.Model.Cart;
import com.kietnt.foodbuyer.Model.Food;
import com.kietnt.foodbuyer.R;

import java.util.ArrayList;

import static com.kietnt.foodbuyer.Adapter.CartAdapter.list_cart;

public class FoodFragment extends Fragment {
    public static RecyclerView rcv_food;
    TextView tv_tenLoai;
    ImageView iv_back;
    FoodDAO foodDAO;
     public static CounterFab counter_fab;
    public static DatabaseReference mDataFood;
    public static MyFoodsAdapter foodsAdapter;
    ArrayList<Food> ds_food;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.food_fragment, container, false);
        mDataFood= FirebaseDatabase.getInstance().getReference("Food");

        rcv_food = root.findViewById(R.id.rcv_food);
        tv_tenLoai = root.findViewById(R.id.tv_tenLoai);
        iv_back = root.findViewById(R.id.iv_back);
        counter_fab = root.findViewById(R.id.counter_fab);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
//                fragment_food.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
            }
        });

        counter_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Cart fragment_cart = new Fragment_Cart();
//                fragment_food.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment_cart).commit();
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rcv_food.setLayoutManager(layoutManager);
        rcv_food.hasFixedSize();
        rcv_food.setHasFixedSize(false);

        foodDAO = new FoodDAO(getContext());

        ds_food = new ArrayList<Food>();
        Bundle getdata =getArguments();
        final String categories_id = getdata.getString("categories_id");
        tv_tenLoai.setText(categories_id);
        ds_food = foodDAO.getAllFood(categories_id);

        foodsAdapter = new MyFoodsAdapter(getActivity(), ds_food);
        rcv_food.setAdapter(foodsAdapter);
        foodsAdapter.notifyDataSetChanged();

        return root;
    }
}
