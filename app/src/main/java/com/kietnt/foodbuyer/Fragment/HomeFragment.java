package com.kietnt.foodbuyer.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.kietnt.foodbuyer.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    CardView cvViewVeget, cvViewMeat, cvViewDrink, cvViewAll;
    ImageView iv_bill;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container,false);
        ImageSlider sliderVoucher = view.findViewById(R.id.imgVoucherSlide);

        anhXa(view);

//        iv_bill.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences pref = getActivity().getSharedPreferences("custommer.dat",MODE_PRIVATE);
//                String strEmail = pref.getString("Email","");
//
//            }
//        });

        cvViewVeget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("categories_id", "Rau Củ");
                FoodFragment fragment_food = new FoodFragment();
                fragment_food.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment_food).commit();
            }
        });

        cvViewMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("categories_id", "Thịt");
                FoodFragment fragment_food = new FoodFragment();
                fragment_food.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment_food).commit();
            }
        });

        cvViewDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("categories_id", "Thức Uống");
                FoodFragment fragment_food = new FoodFragment();
                fragment_food.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment_food).commit();
            }
        });

        cvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle args = new Bundle();
//                args.putString("categories_id", "Thức uống");
                CategoryFragment cagories_fragment = new CategoryFragment();
//                fragment_food.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.container, cagories_fragment).commit();
            }
        });

        // SLider
        List<SlideModel> slideModelVoucher = new ArrayList<>();
        slideModelVoucher.add(new SlideModel(R.drawable.voucher6,"", ScaleTypes.CENTER_CROP));
        slideModelVoucher.add(new SlideModel(R.drawable.voucher,"", ScaleTypes.CENTER_CROP));
        slideModelVoucher.add(new SlideModel(R.drawable.voucher3,"", ScaleTypes.CENTER_CROP));
        slideModelVoucher.add(new SlideModel(R.drawable.voucher4,"", ScaleTypes.CENTER_CROP));
        slideModelVoucher.add(new SlideModel(R.drawable.voucher2,"", ScaleTypes.CENTER_CROP));
        slideModelVoucher.add(new SlideModel(R.drawable.voucher1,"", ScaleTypes.CENTER_CROP));
        sliderVoucher.setImageList(slideModelVoucher,ScaleTypes.CENTER_CROP);


        return view;
    }

    private void anhXa(View view){
        cvViewVeget = view.findViewById(R.id.cvViewVeget);
        cvViewMeat = view.findViewById(R.id.cvViewMeat);
        cvViewDrink = view.findViewById(R.id.cvViewDrink);
        cvViewAll = view.findViewById(R.id.cvViewAll);

    }
}