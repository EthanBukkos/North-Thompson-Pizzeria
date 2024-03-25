package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerReviews extends AppCompatActivity {

    ImageButton logoBtn;
    TextView review2, review3, review4, review5;
    List<String> displayedReviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reviews);

        logoBtn = findViewById(R.id.logoBtn);
        review2 = findViewById(R.id.customerReviewTv2);
        review3 = findViewById(R.id.customerReviewTv3);
        review4 = findViewById(R.id.customerReviewTv4);
        review5 = findViewById(R.id.customerReviewTv5);

        String[] reviews = getResources().getStringArray(R.array.reviews);

        displayReviews(review2, reviews);
        displayReviews(review3, reviews);
        displayReviews(review4, reviews);
        displayReviews(review5, reviews);


        logoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReviews.this, Navigation_Main.class);
                startActivity(intent);

            }
        });

    }

        private void displayReviews (TextView textView, String[]reviews){
            String randomReview = getRandomReview(reviews);
            textView.setText(randomReview);
            displayedReviews.add(randomReview);
        }

        private String getRandomReview (String[]reviews){

            List<String> allReviews = new ArrayList<>();
            for (String review : reviews) {
                if (!displayedReviews.contains(review)) {
                    allReviews.add(review);
                }
            }
            Random random = new Random();
            int index = random.nextInt(allReviews.size());
            return allReviews.get(index);
        }
    }