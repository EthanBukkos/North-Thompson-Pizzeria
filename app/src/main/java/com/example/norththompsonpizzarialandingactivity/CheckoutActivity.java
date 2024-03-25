package com.example.total_page;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        final SeekBar tipSeekBar = findViewById(R.id.tip_slider);
        final TextView tipPercentageTextView = findViewById(R.id.tip_percentage_display);
        final TextView totalWithTipTextView = findViewById(R.id.total_with_tip);

        tipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                tipPercentageTextView.setText("Tip Percentage: " + progress + "%");


                double subtotal = calculateSubtotal(); // Implement your method to calculate subtotal
                double tipAmount = (progress / 100.0) * subtotal;
                double totalWithTip = subtotal + tipAmount;
                totalWithTipTextView.setText("Total (with Tip): $" + totalWithTip);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        Button returnToMenuButton = findViewById(R.id.return_to_menu_button);
        Button cancelOrderButton = findViewById(R.id.cancel_order_button);
        Button checkoutButton = findViewById(R.id.checkout_button);


        returnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CheckoutActivity.this, ConfirmationActivity.class);
                startActivity(intent);
            }
        });
    }

    private double calculateSubtotal() {

        return 0.0;
    }
}
