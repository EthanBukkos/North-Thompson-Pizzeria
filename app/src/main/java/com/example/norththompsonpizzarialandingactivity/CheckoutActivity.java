package com.example.norththompsonpizzarialandingactivity;

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

    TextView totalTv;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        double totalAmount = getIntent().getDoubleExtra("totalCost",0.0);


        final SeekBar tipSeekBar = findViewById(R.id.tip_slider);
        final TextView tipPercentageTextView = findViewById(R.id.tip_percentage_display);
        final TextView totalWithTipTextView = findViewById(R.id.total_with_tip);

        // set default to 0 on seekbar to match the default value of 0 on textView
        tipSeekBar.setProgress(0);

        // setDefault value's for the textViews
        tipPercentageTextView.setText("Tip Percentage: 0%");
        totalWithTipTextView.setText("Total (with Tip): $" + totalAmount);

        tipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                           double tipAmount = (progress / 100.0) * totalAmount;
                double totalWithTip = totalAmount + tipAmount;
                tipPercentageTextView.setText("Tip Percentage: " + progress + "%");
                totalWithTipTextView.setText(String.format("Total (with Tip): $%.2f", totalWithTip));
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
                Intent intent = new Intent();
                intent = new Intent(CheckoutActivity.this,Navigation_Main.class);
                startActivity(intent);

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

}
