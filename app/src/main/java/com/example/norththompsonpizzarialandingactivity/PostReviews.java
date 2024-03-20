package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class PostReviews extends AppCompatActivity {

    Button backBtn, viewReviewBtn;

    EditText postReviewEt;

    RatingBar ratingBar;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_reviews);

        backBtn = findViewById(R.id.reviewBackBtn);
        viewReviewBtn = findViewById(R.id.viewReviewBtn);
        postReviewEt = findViewById(R.id.reviewEt);
        ratingBar = findViewById(R.id.reviewBar);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(PostReviews.this,ContactUs.class);
                startActivity(intent);
            }
        });

        viewReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(PostReviews.this, CustomerReviews.class);
                startActivity(intent);
            }
        });


    }
}