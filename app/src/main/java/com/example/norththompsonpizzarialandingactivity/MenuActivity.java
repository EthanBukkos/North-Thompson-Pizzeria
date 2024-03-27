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

import java.util.ArrayList;
import java.util.Collections;

public class MenuActivity extends AppCompatActivity {

    // Initialize array list of type MenuItemModel
    ArrayList<MenuItemModel> menuArrayList = new ArrayList<>();

    Button backBtn, signInSignOutBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        RecyclerView recyclerView = findViewById(R.id.menuRecView);
        backBtn = findViewById(R.id.menuBackBtn);
        signInSignOutBtn = findViewById(R.id.menuSignInBtn);
        mAuth = FirebaseAuth.getInstance();

        updateButtonOnUserStatus();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent = new Intent(MenuActivity.this, Navigation_Main.class);
                startActivity(intent);
            }
        });

        signInSignOutBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                Intent intent = new Intent();
                intent = new Intent(MenuActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

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

    private void updateButtonOnUserStatus() {
        if (mAuth.getCurrentUser() != null) {
            signInSignOutBtn.setText("Sign Out");
            signInSignOutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // User sign out
                    mAuth.signOut();

                    signInSignOutBtn.setText("Sign In");

                    updateButtonOnUserStatus();
                }
            });
        } else {
            signInSignOutBtn.setText("Sign In");
            signInSignOutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Sign out successful.",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MenuActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            });
        }
    }


}