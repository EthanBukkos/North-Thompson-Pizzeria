package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class CustomerReviews extends AppCompatActivity {

    ImageButton logoBtn;
    RatingBar ratingBar;

    TextView review1,review2,review3,review4,review5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reviews);

        logoBtn = findViewById(R.id.logoBtn);
        ratingBar = findViewById(R.id.averageStars);
        review1 = findViewById(R.id.customerReviewTv1);
        review2 = findViewById(R.id.customerReviewTv2);
        review3 = findViewById(R.id.customerReviewTv3);
        review4 = findViewById(R.id.customerReviewTv4);
        review5 = findViewById(R.id.customerReviewTv5);

        logoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReviews.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}