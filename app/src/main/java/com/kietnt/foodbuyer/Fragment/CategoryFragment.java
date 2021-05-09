package com.kietnt.foodbuyer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kietnt.foodbuyer.Adapter.CategoryAdapter;
import com.kietnt.foodbuyer.DAO.CategoryDAO;
import com.kietnt.foodbuyer.Model.Category;
import com.kietnt.foodbuyer.R;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    // Biến toàn cục
    public static RecyclerView rcvCategory;

    CategoryDAO categoryDAO;
    ArrayList<Category> dataCategory;
    public static CategoryAdapter categoryAdapter;
    public static DatabaseReference mDataFood;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories,container,false);
        rcvCategory = view.findViewById(R.id.rcvCategory);

        // -----------------
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        rcvCategory.setLayoutManager(layoutManager);
        rcvCategory.hasFixedSize();
        rcvCategory.setHasFixedSize(true);
        // -----------------

        categoryDAO = new CategoryDAO(getContext());
        mDataFood = FirebaseDatabase.getInstance().getReference("TheLoai");
        dataCategory = new ArrayList<Category>();
        dataCategory = categoryDAO.getAllCategory();




        return view;
    }
}
