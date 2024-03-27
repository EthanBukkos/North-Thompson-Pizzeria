package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import java.util.ArrayList;

public class PostReviews extends AppCompatActivity {

    Button backBtn, viewReviewBtn, submitReview;

    EditText postReviewEt;

    RatingBar ratingBar;

    Intent intent;

    ArrayList<Float> ratingList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_reviews);

        backBtn = findViewById(R.id.reviewBackBtn);
        viewReviewBtn = findViewById(R.id.viewReviewBtn);
        postReviewEt = findViewById(R.id.reviewEt);
        ratingBar = findViewById(R.id.reviewBar);
        submitReview = findViewById(R.id.submitBtn);

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

        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewText = postReviewEt.getText().toString();
                float ratingValue = ratingBar.getRating();

                // Check to make  sure the user inputs something before they submit a review and provides a toast if they dont
                if (reviewText.isEmpty() || ratingValue == 0.0f) {
                    Toast.makeText(PostReviews.this, "Please provide a rating and a review", Toast.LENGTH_SHORT).show();
                    return;
                }
                CustomerReviews.reviews.add(reviewText);
                ratingList.add(ratingValue);

                // calculate the average for the ratings in the rating list
                float totalRating = 0;
                for (float rating : ratingList) {
                    totalRating += rating;
                }

                // find the average to display
                float averageRating = totalRating / ratingList.size();

                // reset both views to default values so another review can be submitted
                postReviewEt.setText("");
                ratingBar.setRating(0);

                // send the values through intents to CustomerReviews.class
                Intent intent = new Intent(PostReviews.this, CustomerReviews.class);
                intent.putExtra("newReview", reviewText);
                intent.putExtra("newRating", ratingValue);
                intent.putExtra("averageRating", averageRating);

                startActivity(intent);
            }
        });
    }

}