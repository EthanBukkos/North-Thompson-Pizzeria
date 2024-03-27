package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ContactUs extends AppCompatActivity {

    Button phoneBtn, emailBtn, reviewBtn, backBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        phoneBtn = findViewById(R.id.phoneBtn);
        emailBtn = findViewById(R.id.emailBtn);
        reviewBtn = findViewById(R.id.reviewBtn);
        backBtn = findViewById(R.id.backBtn);

        // intent to bring up dial pad and parse the dummy phone number
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:8002222222"));
                startActivity(intent);
            }
        });

        // return to main activity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ContactUs.this, Navigation_Main.class);
                startActivity(intent);
            }
        });

        // intent to move to the review page
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ContactUs.this, PostReviews.class);
                startActivity(intent);
            }
        });

        // intent to call the email app defaulted on the device to send an email and then uses a
        // dummy email address as the extra
        emailBtn.setOnClickListener(new View.OnClickListener() {
            String emailAddress = "NorthThompsonPizzaria@gmail.com";
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
                startActivity(Intent.createChooser(intent, "Choose an email client : "));
            }
        });
    }
}