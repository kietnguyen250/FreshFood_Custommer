package com.kietnt.foodbuyer.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andremion.counterfab.CounterFab;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kietnt.foodbuyer.Adapter.CartAdapter;
import com.kietnt.foodbuyer.Adapter.MyFoodsAdapter;
import com.kietnt.foodbuyer.BottomSheet.BottomSheetBillAdd;
import com.kietnt.foodbuyer.DAO.CartDAO;
import com.kietnt.foodbuyer.DAO.FoodDAO;
import com.kietnt.foodbuyer.Model.Cart;
import com.kietnt.foodbuyer.Model.Food;
import com.kietnt.foodbuyer.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.kietnt.foodbuyer.Fragment.FoodFragment.foodsAdapter;

public class Fragment_Cart extends Fragment {
    public static RecyclerView rcv_cart;
    TextView tvSum;
    ImageView iv_back;
    CartDAO cartDAO;
    DatabaseReference mDataCart;
    public static CartAdapter cartAdapter;
    ArrayList<Cart> ds_cart;
    double thanhTien;
    Button btnOrder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        mDataCart= FirebaseDatabase.getInstance().getReference("Cart");

        btnOrder = root.findViewById(R.id.btnOrder);
        rcv_cart = root.findViewById(R.id.rcv_cart);
        tvSum = root.findViewById(R.id.tvSum);
        iv_back = root.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
//                fragment_food.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcv_cart.setLayoutManager(layoutManager);
        rcv_cart.hasFixedSize();
        rcv_cart.setHasFixedSize(false);

        cartDAO = new CartDAO(getContext());
        ds_cart = new ArrayList<Cart>();

        SharedPreferences pref = getActivity().getSharedPreferences("buyer.dat",MODE_PRIVATE);
        String strEmail = pref.getString("Email","");

        mDataCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ds_cart.clear();
                for (DataSnapshot data:snapshot.getChildren()){
                    if (data.child("email_buyer").getValue(String.class).equalsIgnoreCase(strEmail)){
                        Cart item = data.getValue(Cart.class);

                        ds_cart.add(item);

                        thanhTien = thanhTien+item.getPriceFood();

                        // Đặt kiểu số-----------------
                        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        decimalFormat.applyPattern("#,###,###,###");
                        tvSum.setText(decimalFormat.format(thanhTien) + " Đ");
                        //------------------------------
                    }

                }
                cartAdapter = new CartAdapter(getContext(),ds_cart);
                rcv_cart.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvSum.equals("0")){
                    Toast.makeText(getContext(), "Không có gì trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle args = new Bundle();
                    args.putString("email_buyer", strEmail);
                    BottomSheetBillAdd bottomSheetBillAdd = new BottomSheetBillAdd();
                    bottomSheetBillAdd.setArguments(args);
                    bottomSheetBillAdd.show(getFragmentManager(), bottomSheetBillAdd.getTag());
                }
            }
        });

        return root;
    }
}
