package com.example.norththompsonpizzarialandingactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderCustomizationDriver extends AppCompatActivity
        implements ToppingAdapter.ToppingSelectionListener {
    List<SauceDataModel> sauceList;
    List<MeatDataModel> meatList;
    ArrayList<MenuItemModel> selectedItems;
    List<VeggieDataModel> veggieList;
    Spinner sizeSpinner;
    RecyclerView recyclerView;
    Button continueBtn;
    TextView subtotalTextView;

    Intent intent = new Intent();

    Map<String, Double> sizePrices = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_customization_main);

        Intent intent = getIntent();
        selectedItems = (ArrayList<MenuItemModel>) intent.getSerializableExtra("selectedItems");
        if (selectedItems == null) {
            selectedItems = new ArrayList<>();
        }

        // Initializing UI components and data models
        setupUI();
        setupModelLists();
        setupRecyclerView();

        sizeSpinner.setOnItemSelectedListener(new SizeSelectionListener());
        continueBtn.setOnClickListener(this::onContinueBtnClicked);

    }

    private void setupUI() {
        continueBtn = findViewById(R.id.continueBtn);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        recyclerView = findViewById(R.id.recyclerView);
        subtotalTextView = findViewById(R.id.subtotalTextView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(adapter);
    }

    private void setupModelLists() {
        sauceList = new ArrayList<>();
        meatList = new ArrayList<>();
        veggieList = new ArrayList<>();

        sauceList.add(new SauceDataModel("Choose a sauce:", "Tomato", "Alfredo",
                0.00, 0.00));
        meatList.add(new MeatDataModel("Meat Choices", "Pepperoni", "Sausage", "Bacon",
                0.00, 0.00, 0.00));
        veggieList.add(new VeggieDataModel("Veggie Choices", "Tomatoes", "Peppers",
                "Mushrooms", "Onions", 0.00, 0.00, 0.00, 0.00));

        sizePrices.put("Small", 12.00);
        sizePrices.put("Medium", 14.00);
        sizePrices.put("Large", 18.00);
    }

    private void setupRecyclerView() {
        ToppingAdapter toppingAdapter = new ToppingAdapter(sauceList, meatList, veggieList, this);
        recyclerView.setAdapter(toppingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onToppingSelectionChanged() {
        updateSubtotal();
    }

    private void updateSubtotal() {
        String selectedItem = sizeSpinner.getSelectedItem().toString();
        String selectedSize = selectedItem.split(" \\$")[0];
        double selectedSizePrice = sizePrices.getOrDefault(selectedSize, 0.0);
        Log.d("SizePrice", "Selected Size: " + selectedSize + ", Price: " + selectedSizePrice);
        double subtotal = selectedSizePrice;
        double ingredientsPrice = 0.0;
        subtotal += sauceList.stream().filter(SauceDataModel::isSelected).mapToDouble(SauceDataModel::getPrice).sum();
        subtotal += ingredientsPrice;

        subtotalTextView.setText(String.format(Locale.US, "Subtotal: $%.2f", subtotal));
    }

    public CustomPizza createCustomPizza() {
        String selectedSize = sizeSpinner.getSelectedItem().toString();
        String selectedSauce = sauceList.stream()
                .filter(SauceDataModel::isSelected)
                .findFirst()
                .map(SauceDataModel::getSelectedSauce).
                orElse("");

        List<String> selectedMeats = meatList.stream()
                .flatMap(meat -> meat.getSelectedMeats().stream())
                .collect(Collectors.toList());

        List<String> selectedVeggies = veggieList.stream()
                .flatMap(veggie -> veggie.getSelectedVeggies().stream())
                .collect(Collectors.toList());

        return new CustomPizza(selectedSize, selectedSauce, selectedMeats, selectedVeggies);
    }

    public void onContinueBtnClicked(View view) {
        if (validatePizzaSelections()) {
            CustomPizza customPizza = createCustomPizza();
            MenuItemModel item = convertCustomPizzaToMenuItemModel(customPizza);

            ArrayList<MenuItemModel> selectedItems = getIntent().hasExtra("selectedItems") ?
                    (ArrayList<MenuItemModel>) getIntent().getSerializableExtra("selectedItems") :
                    new ArrayList<>();

          //  selectedItems.add(item);

            Intent intent = new Intent(OrderCustomizationDriver.this, Total.class);
            intent.putExtra("selectedItems", selectedItems);
            startActivity(intent);
        } else {
            Toast.makeText(OrderCustomizationDriver.this, "Please complete all selections",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private MenuItemModel convertCustomPizzaToMenuItemModel(CustomPizza customPizza) {
        String name = "Custom Pizza";
        double smlPrice = 12.00;
        double medPrice = 14.00;
        double lrgPrice = 18.00;
        int img = R.drawable.custom_pizza;
        int quantity = 1;
        String defaultSize = customPizza.getSize();


        return new MenuItemModel(img, name, smlPrice, medPrice, lrgPrice, quantity, defaultSize);
    }

    private double customPizzaPrice(CustomPizza customPizza, String size) {
        double price = sizePrices.getOrDefault(customPizza.getSize(), 0.0);
        return price;
    }

    // Ensure the customer makes the required choices before proceeding to checkout
    public boolean validatePizzaSelections() {
        boolean isSauceSelected = sauceList.stream().anyMatch(SauceDataModel::isSelected);
        if (!isSauceSelected) {
            Toast.makeText(this, "Please select a sauce", Toast.LENGTH_SHORT).show();
            return false;
        }

        // If check is passed
        return true;
    }

    private class SizeSelectionListener implements  AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            updateSubtotal();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}


