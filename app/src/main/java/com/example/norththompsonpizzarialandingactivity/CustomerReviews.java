package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerReviews extends AppCompatActivity {

    ImageButton logoBtn;
    TextView review2, review3, review4, review5, rating;
    List<String> displayedReviews = new ArrayList<>();
    public static List<String> reviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reviews);

        logoBtn = findViewById(R.id.logoBtn);
        review2 = findViewById(R.id.customerReviewTv2);
        review3 = findViewById(R.id.customerReviewTv3);
        review4 = findViewById(R.id.customerReviewTv4);
        review5 = findViewById(R.id.customerReviewTv5);
        rating = findViewById(R.id.averageRating);

        reviews.add("Best Pizza in Town! \n-John");
        reviews.add("Great Service \n-Stacy");
        reviews.add("Slow Delivery... \n-MacGruber");
        reviews.add("Great Prices! \n-Cecilia");
        reviews.add("Amazing Staff \n-Monica");
        reviews.add("Delivery drvier was so nice and food was great! \n-Regina");
        reviews.add("The pizza is incredible, I cant wait to go again! \n-Richard");
        reviews.add("Almost as good as McDonalds Pizza \n-Ronald");

        displayReviews(review2);
        displayReviews(review3);
        displayReviews(review4);
        displayReviews(review5);

        float ratings = getIntent().getFloatExtra("averageRating",0.0f);
        String formatRating = String.format("%.1f", ratings);
        rating.setText("Average Rating: " + formatRating);

        logoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReviews.this, Navigation_Main.class);
                startActivity(intent);

            }
        });
    }
        // method to show the reviews since It's repeated multiple times in the on create
        private void displayReviews (TextView textView){
            String randomReview = getRandomReview();
            textView.setText(randomReview);
            displayedReviews.add(randomReview);
        }

        // Randomly retrieve reviews to display in the textview and check to make sure they arent a duplicate
        private String getRandomReview (){
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