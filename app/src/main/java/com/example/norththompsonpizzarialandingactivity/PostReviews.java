package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

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

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(PostReviews.this, String.valueOf(rating), Toast.LENGTH_SHORT).show();
            }
        });


    }
}