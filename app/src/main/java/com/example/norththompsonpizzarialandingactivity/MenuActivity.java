package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

public class MenuActivity extends AppCompatActivity {

    // Initialize array list of type MenuItemModel
    ArrayList<MenuItemModel> menuArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        RecyclerView recyclerView = findViewById(R.id.menuRecView);

        menuArrayList.add(new MenuItemModel(R.drawable.custom_pizza,"Customize Pizza",
                12.00, 14.00, 18.00, 0));

        menuArrayList.add(new MenuItemModel(R.drawable.cheese_pizza, "Cheese Pizza",
                12.00, 14.00, 18.00, 0));

        menuArrayList.add(new MenuItemModel(R.drawable.pepperoni_pizza, "Pepperoni Pizza",
                12.00, 14.00, 18.00, 0));

        menuArrayList.add(new MenuItemModel(R.drawable.hawaiian_pizza, "Hawaiian Pizza",
                12.00, 14.00, 18.00, 0));

        menuArrayList.add(new MenuItemModel(R.drawable.meat_lovers_pizza, "Meat Lovers Pizza",
                12.00, 14.00, 18.00, 0));

        menuArrayList.add(new MenuItemModel(R.drawable.supreme_pizza, "Supreme Pizza",
                12.00, 14.00, 18.00, 0));

        menuArrayList.add(new MenuItemModel(R.drawable.veggie_pizza, "Veggie Pizza",
                12.00, 14.00, 18.00, 0));

        menuArrayList.add(new MenuItemModel(R.drawable.bbq_chicken_pizza, "BBQ Chicken Pizza",
                12.00, 14.00, 18.00, 0));

        // Instance of the MenuAdapter
        MenuAdapter menuAdapter = new MenuAdapter(this, menuArrayList);

        recyclerView.setAdapter(menuAdapter);

        // Grid layout
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}