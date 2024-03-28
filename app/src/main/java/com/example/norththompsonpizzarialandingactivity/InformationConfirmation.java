package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class InformationConfirmation extends AppCompatActivity {

    EditText firstName, lastName, phoneNum, address, ccNum, ccExpiry, cvc;
    Button continueToCheckoutBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_confirmation);

        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        phoneNum = findViewById(R.id.phoneConfirmEditText);
        address = findViewById(R.id.addressConfirmEditText);
        ccNum = findViewById(R.id.ccNumEditText);
        ccExpiry = findViewById(R.id.ccExpiryEditText);
        cvc = findViewById(R.id.cvcEditText);
        continueToCheckoutBtn = findViewById(R.id.contCheckoutBtn);

        ArrayList<MenuItemModel> selectedItems = (ArrayList<MenuItemModel>) getIntent().getSerializableExtra("selectedItems");
        double totalAmount = getIntent().getDoubleExtra("total",0.0);
        double taxAmount = getIntent().getDoubleExtra("tax",0.0);
        double tipAmount = getIntent().getDoubleExtra("tipAmount", 0.0);
        double subtotal = getIntent().getDoubleExtra("subtotal", 0.0);

        continueToCheckoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFilledFields()) {
                    Intent toCheckoutIntent = new Intent(InformationConfirmation.this, ConfirmationActivity.class);
                    toCheckoutIntent.putExtra("selectedItems",selectedItems);
                    toCheckoutIntent.putExtra("tax", taxAmount);
                    toCheckoutIntent.putExtra("total",totalAmount);
                    toCheckoutIntent.putExtra("tipAmount",tipAmount);
                    toCheckoutIntent.putExtra("subtotal", subtotal);
                    startActivity(toCheckoutIntent);
                } else {
                    Toast.makeText(InformationConfirmation.this,"Please fill all fields",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean checkFilledFields() {
        return !firstName.getText().toString().trim().isEmpty() &&
                !lastName.getText().toString().trim().isEmpty() &&
                !phoneNum.getText().toString().trim().isEmpty() &&
                !address.getText().toString().trim().isEmpty() &&
                !ccNum.getText().toString().trim().isEmpty() &&
                !ccExpiry.getText().toString().trim().isEmpty() &&
                !cvc.getText().toString().trim().isEmpty();
    }
}