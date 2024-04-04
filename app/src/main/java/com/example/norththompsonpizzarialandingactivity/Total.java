package com.example.norththompsonpizzarialandingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Total extends AppCompatActivity {
    RecyclerView recyclerView;
    Button returnToMenuBtn, checkoutBtn;

    TextView subtotalTv, taxTv, totalTv;

    MenuAdapter menuAdapter;
    Intent intent;

    private final double TAX_AMOUNT = 0.13; // constant to hold current tax value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        recyclerView = findViewById(R.id.recycler_view);
        returnToMenuBtn = findViewById(R.id.return_to_menu_button);
        checkoutBtn = findViewById(R.id.continue_to_checkout_button);
        subtotalTv = findViewById(R.id.subtotal_text);
        taxTv = findViewById(R.id.tax_text);
        totalTv = findViewById(R.id.total_text);

        // display the objects from the menu screen in this recyclerview, reuse menuAdapter
        ArrayList<MenuItemModel> selectedItems = (ArrayList<MenuItemModel>) getIntent().getSerializableExtra("selectedItems");
        menuAdapter = new MenuAdapter(this, selectedItems);

        // make sure the selection isnt empty
        if (selectedItems != null && !selectedItems.isEmpty()) {
            menuAdapter.setCurrentItem(selectedItems.get(0));
        } else {
            selectedItems = new ArrayList<>();
        }

        recyclerView.setAdapter(menuAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // calculations to figure out each line total and set the values in their textViews
        double subtotal = calculateSubtotal(selectedItems);
        double tax = TAX_AMOUNT * subtotal;
        double total = subtotal + tax;

        subtotalTv.setText(String.format("Subtotal: $%.2f", subtotal));
        taxTv.setText(String.format("Tax (13%%): $%.2f", tax));
        totalTv.setText(String.format("Total: $%.2f", total));

        // set these to disabled so the user cant adjust the quantity or size on this screen.
        menuAdapter.setQuantityPickerEnabled(false);
        menuAdapter.setSizeSelectorEnabled(false);

        returnToMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Total.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MenuItemModel> selectedItems = menuAdapter.getSelectedItems();
                intent = new Intent(Total.this, CheckoutActivity.class);
                intent.putExtra("totalCost", total);
                intent.putExtra("subtotal",subtotal);
                intent.putExtra("tax", tax);
                intent.putExtra("selectedItems",selectedItems);
                startActivity(intent);
            }
        });

    }
    // calculate the subtotal amount based on quantities and pizza size
    private double calculateSubtotal(ArrayList<MenuItemModel> selectedItems) {
        double subtotal = 0.0;
        for (MenuItemModel item : selectedItems) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }
}
