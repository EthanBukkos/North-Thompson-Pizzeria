package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class Navigation_Main extends AppCompatActivity {

    Button contactBtn, registerBtn, menuBtn, dealBtn, locationBtn;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);

        contactBtn = findViewById(R.id.contactBtn);
        registerBtn = findViewById(R.id.signInBtn);
        menuBtn = findViewById(R.id.menuBtn);
        dealBtn = findViewById(R.id.dealBtn);
        locationBtn = findViewById(R.id.locationBtn);

        // contact page
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Navigation_Main.this, ContactUs.class);
                startActivity(intent);
            }
        });

        // Deal of the week page
        dealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Navigation_Main.this, DealOfTheWeek.class);
                startActivity(intent);
            }
        });

        // Menu page
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Navigation_Main.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        // registration once I have activity
       /* registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
            }
        });*/

        // To location page
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Navigation_Main.this, LocationList.class);
                startActivity(intent);
            }
        });

    }
}








