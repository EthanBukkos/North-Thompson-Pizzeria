package com.example.norththompsonpizzarialandingactivity;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ConfirmationActivity extends AppCompatActivity {

    Intent intent;

    TextView subTot, total, tax, tip;

    RecyclerView recyclerView;
    MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        subTot = findViewById(R.id.subtotal_text);
        total = findViewById(R.id.total_text);
        tax = findViewById(R.id.tax_text);
        tip = findViewById(R.id.tipText);
        recyclerView = findViewById(R.id.confirmationRecyclerView);

        Button returnToMenuButton = findViewById(R.id.return_to_menu_button);
        ArrayList<MenuItemModel> selectedItems = (ArrayList<MenuItemModel>) getIntent().getSerializableExtra("selectedItems");
         menuAdapter = new MenuAdapter(this, selectedItems);

        // make sure the selection isnt empty
        if (!selectedItems.isEmpty()) {
            menuAdapter.setCurrentItem(selectedItems.get(0));
        }

        double totalAmount = getIntent().getDoubleExtra("total",0.0);
        double taxAmount = getIntent().getDoubleExtra("tax",0.0);
        double tipAmount = getIntent().getDoubleExtra("tipAmount", 0.0);
        double subtotal = getIntent().getDoubleExtra("subtotal", 0.0);

        subTot.setText("Subtotal: $" + String.format("%.2f", subtotal));
        tax.setText("Tax (13%): $" + String.format("%.2f", taxAmount));
        tip.setText("Tip: $" + String.format("%.2f", tipAmount));
        total.setText("Total: $" + String.format("%.2f", totalAmount));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(menuAdapter);

        // set these to disabled so the user cant adjust the quantity or size on this screen.
        menuAdapter.setQuantityPickerEnabled(false);
        menuAdapter.setSizeSelectorEnabled(false);


        returnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ConfirmationActivity.this, Navigation_Main.class);
                startActivity(intent);

            }
        });

    }
}
