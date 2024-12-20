package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class DealOfTheWeek extends AppCompatActivity {

    Button backBtn;

    ArrayList<MenuItemModel> menuArrayList = new ArrayList<>();
    ImageView pizzaImage;
    TextView nameTV, discountTV, smallCostTV, mediumCostTV, largeCostTV;
    double DEAL_DISCOUNT; // constant to hold the value set in the discount TV of the UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_of_the_week);

        pizzaImage = findViewById(R.id.dealPizzaImage);
        nameTV = findViewById(R.id.dealPizzaNameTV);
        discountTV = findViewById(R.id.discountTV);
        smallCostTV = findViewById(R.id.dealSmallTV);
        mediumCostTV = findViewById(R.id.dealMedTV);
        largeCostTV = findViewById(R.id.dealLargeTV);
        backBtn = findViewById(R.id.dealBackBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DealOfTheWeek.this, Navigation_Main.class);
                startActivity(intent);
            }
        });

        // Parse the double from the discount amount on the UI to use as a constant for further changes
        // Handle exceptions that may occur for empty string, if none calculate, if there is set Constant to 0.0
        String discount = discountTV.getText().toString().trim();
        if (!discount.isEmpty())
        {
            try {
                DEAL_DISCOUNT = Double.parseDouble(discount);
                DEAL_DISCOUNT = DEAL_DISCOUNT / 100.0;
            } catch (NumberFormatException e) {
                DEAL_DISCOUNT = 0.0;
            }
        } else {
            DEAL_DISCOUNT = 0.0;
        }

        // ArrayList of Menu Items to pull and use for random selections in call to randomDeal()

        menuArrayList.add(new MenuItemModel(R.drawable.custom_pizza,"Customize Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.cheese_pizza, "Cheese Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.pepperoni_pizza, "Pepperoni Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.hawaiian_pizza, "Hawaiian Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.meat_lovers_pizza, "Meat Lovers Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.supreme_pizza, "Supreme Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.veggie_pizza, "Veggie Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        menuArrayList.add(new MenuItemModel(R.drawable.bbq_chicken_pizza, "BBQ Chicken Pizza",
                12.00, 14.00, 18.00, 0,"Small"));

        randomDeal();
    }

    // Random method to import Random class, calculate the discounted cost based on discount % on UI
    // and set the text to two decimal places. Use Getters from Model class to provide information.
    // Page will randomize everytime we leave and return to it.
    private void randomDeal() {
        Random random = new Random();
        int randomIndex = random.nextInt(menuArrayList.size());

        MenuItemModel randomMenuItem = menuArrayList.get(randomIndex);

        pizzaImage.setImageResource(randomMenuItem.getImg());
        nameTV.setText(randomMenuItem.getName());
        double smallDiscountPrice = randomMenuItem.getSmlPrice() * (1 - DEAL_DISCOUNT);
        double medDiscountPrice = randomMenuItem.getMedPrice() * (1 - DEAL_DISCOUNT);
        double lrgDiscountPrice = randomMenuItem.getLrgPrice() * (1 - DEAL_DISCOUNT);

        smallCostTV.setText("Small: $" + String.format("%.2f" , smallDiscountPrice));
        mediumCostTV.setText("Medium: $" + String.format("%.2f" , medDiscountPrice));
        largeCostTV.setText("Large: $" + String.format("%.2f" , lrgDiscountPrice));
        }
}