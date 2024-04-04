package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.type.TimeOfDay;

import java.util.ArrayList;
import java.util.Collections;

public class MenuActivity extends AppCompatActivity {

    // Initialize array list of type MenuItemModel
    ArrayList<MenuItemModel> menuArrayList = new ArrayList<>();

    Button backBtn, signInSignOutBtn, submitOrderBtn;
    FirebaseAuth mAuth;
    MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        RecyclerView recyclerView = findViewById(R.id.menuRecView);
        backBtn = findViewById(R.id.menuBackBtn);
        signInSignOutBtn = findViewById(R.id.menuSignInBtn);
        mAuth = FirebaseAuth.getInstance();
        submitOrderBtn = findViewById(R.id.submitOrderBtn);

        // Initialize the MenuAdapter
       menuAdapter = new MenuAdapter(this, menuArrayList);

        recyclerView.setAdapter(menuAdapter);

        // Grid layout
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        updateButtonOnUserStatus();

        submitOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent = new Intent(MenuActivity.this, Navigation_Main.class);
                startActivity(intent);
            }
        });

        signInSignOutBtn.setOnClickListener(v -> {
            if (mAuth.getCurrentUser() != null) {
                mAuth.signOut();
                Toast.makeText(getApplicationContext(), "Sign out successful.",
                        Toast.LENGTH_SHORT).show();
                updateButtonOnUserStatus();
            } else {
                Intent intent = new Intent(MenuActivity.this, SignInActivity.class);
                startActivity(intent);
            }
                });

        // Click listener if 'Customize Pizza' is selected from the menu

        menuAdapter.setOnQuantityChangeListener(new MenuAdapter.onQuantityChangeListener() {
            @Override
            public void onQuantityChanged(MenuItemModel item, int newQuantity)  {


            }
        });

        menuArrayList.add(new MenuItemModel(R.drawable.custom_pizza,"Customize Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.cheese_pizza, "Cheese Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.pepperoni_pizza, "Pepperoni Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.hawaiian_pizza, "Hawaiian Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.meat_lovers_pizza, "Meat Lovers Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.supreme_pizza, "Supreme Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.veggie_pizza, "Veggie Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.bbq_chicken_pizza, "BBQ Chicken Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

    }

    private void updateButtonOnUserStatus() {
        if (mAuth.getCurrentUser() != null) {
            signInSignOutBtn.setText("Sign Out");
        } else {
            signInSignOutBtn.setText("Sign In");
        }
    }

    // submitOrder method to add the objects and their quantity and send through an intent
    private void submitOrder() {
        boolean isCustomPizzaSelected = false;
        boolean itemSelected = false;
        ArrayList<MenuItemModel> selectedItems = menuAdapter.getSelectedItems();

        for (MenuItemModel item : selectedItems) {
            if (item.getQuantity() > 0) {
                itemSelected = true;
                if ("Customize Pizza".equals(item.getName())) {
                    isCustomPizzaSelected = true;
                    break;
                }
            }
        }

        Intent intent;
        if (!itemSelected) {
            Toast.makeText(this,"Please select an Item before Submitting your Order!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (isCustomPizzaSelected) {
           intent = new Intent(MenuActivity.this, OrderCustomizationDriver.class);
        } else {
           intent = new Intent(MenuActivity.this, Total.class);
        }

        intent.putExtra("selectedItems", selectedItems);
        startActivity(intent);

        Toast.makeText(this,"Selection added.", Toast.LENGTH_SHORT).show();
    }


}