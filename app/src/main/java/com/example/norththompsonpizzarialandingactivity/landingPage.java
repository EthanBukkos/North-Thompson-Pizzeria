package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class landingPage extends AppCompatActivity {
    private static int SPLASH_SCREEN_DELAY = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        // use a runnable to load to the splash screen and delay by the constant amount, 6 seconds
        // before loading to the main navigation page
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(landingPage.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_SCREEN_DELAY);
    }
}