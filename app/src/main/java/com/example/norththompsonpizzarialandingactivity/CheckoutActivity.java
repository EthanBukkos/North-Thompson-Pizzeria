package com.example.norththompsonpizzarialandingactivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class CheckoutActivity extends AppCompatActivity {

    TextView totalTv,totalBefTip, tipAmt;
    RadioGroup radioGroup;
    Intent intent;

    double totalWithTip = 0.0;
    double tipAmount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        ArrayList<MenuItemModel> selectedItems = (ArrayList<MenuItemModel>) getIntent().getSerializableExtra("selectedItems");

        double totalAmount = getIntent().getDoubleExtra("totalCost",0.0);
        double taxAmt = getIntent().getDoubleExtra("tax",0.0);
        double subtotal = getIntent().getDoubleExtra("subtotal",0.0);


        radioGroup = findViewById(R.id.radioGroup);
        final SeekBar tipSeekBar = findViewById(R.id.tip_slider);
        final TextView tipPercentageTextView = findViewById(R.id.tip_percentage_display);
        final TextView totalWithTipTextView = findViewById(R.id.total_with_tip);
        totalBefTip = findViewById(R.id.totalBeforeTip);
        tipAmt = findViewById(R.id.tipAmount);

        tipAmt.setText("Tip Amount: $0.00");

        totalBefTip.setText(String.format("Total: $%.2f",totalAmount));


        // set default to 0 on seekbar to match the default value of 0 on textView
        tipSeekBar.setProgress(0);

        // setDefault value's for the textViews
        tipPercentageTextView.setText("Tip Percentage: 0%");
        totalWithTipTextView.setText("Total (with Tip): $" + totalAmount);

        tipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                tipAmount = (progress / 100.0) * totalAmount;
                totalWithTip = totalAmount + tipAmount;
                tipPercentageTextView.setText("Tip Percentage: " + progress + "%");
                tipAmt.setText(String.format("Tip Amount: $%.2f", tipAmount));
                totalWithTipTextView.setText(String.format("Total (with Tip): $%.2f", totalWithTip));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        Button returnToMenuButton = findViewById(R.id.return_to_menu_button);
        Button checkoutButton = findViewById(R.id.checkout_button);


        returnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent = new Intent(CheckoutActivity.this,Navigation_Main.class);
                startActivity(intent);

            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(CheckoutActivity.this, "Please select pickup or delivery.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton deliveryOption = findViewById(radioGroup.getCheckedRadioButtonId());
                String deliveryMethod = deliveryOption.getText().toString();

                Intent intent = new Intent(CheckoutActivity.this, InformationConfirmation.class);
                intent.putExtra("selectedItems",selectedItems);
                intent.putExtra("tax", taxAmt);
                intent.putExtra("total",totalWithTip);
                intent.putExtra("tipAmount",tipAmount);
                intent.putExtra("subtotal", subtotal);
                intent.putExtra("deliveryMethod", deliveryMethod);
                startActivity(intent);
            }
        });
    }

}
