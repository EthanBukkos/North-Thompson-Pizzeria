package com.example.norththompsonpizzarialandingactivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.View_Holder> {

    Context context;

    // Array list to hold te image, name, and cost of each menu item
    ArrayList<MenuItemModel> menuArrayList;
    private boolean isQuantityPickerEnabled = true;
    private boolean isSizeSelectorEnabled = true;

    private MenuItemModel currentItem;


    // Create constructor for MenuAdapter
    MenuAdapter (Context context, ArrayList<MenuItemModel> menuArrayList) {

        this.context = context;
        this.menuArrayList = menuArrayList;

    }

    public void setQuantityPickerEnabled(boolean quantityPickerEnabled) {
        isQuantityPickerEnabled = quantityPickerEnabled;
        notifyDataSetChanged();
    }

    public void setSizeSelectorEnabled(boolean sizeSelectorEnabled) {
        isSizeSelectorEnabled = sizeSelectorEnabled;
        notifyDataSetChanged();
    }

    // Setter method for a placeHolder adapter to use in disabling the quantity pickers
    public void setCurrentItem(MenuItemModel currentItem) {
        this.currentItem = currentItem;
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.menu_row,
                parent, false);
        View_Holder viewHolder = new View_Holder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
        MenuItemModel menuItem = menuArrayList.get(position);

        // enable both selection items for quantity and size of pizza
        holder.quantityPicker.setEnabled(isQuantityPickerEnabled);
        holder.sizeSelector.setClickable(isSizeSelectorEnabled);

        // iterates over the radio button groups children and sets to true or false depending on
        // the Activity. For us, it is true on the menuSelect, and false on the subtotal(Total.class)
        for (int i = 0; i < holder.sizeSelector.getChildCount(); i++) {
            holder.sizeSelector.getChildAt(i).setEnabled(isSizeSelectorEnabled);
        }

        holder.image.setImageResource(menuArrayList.get(position).img);
        holder.menuItemName.setText(menuArrayList.get(position).name);
        holder.price.setText(String.format("$%.2f", menuItem.getPrice()));
        holder.quantityPicker.setMinValue(0);
        holder.quantityPicker.setMaxValue(10);
        holder.quantityPicker.setValue(menuItem.getQuantity());
        holder.sizeSelector.setOnCheckedChangeListener(null);
        holder.price.setText(String.format("$%.2f", menuItem.getTotalPrice()));

        // check which size is selected, default is none
        String selectedSize = menuItem.getSelectedSize();
        if (selectedSize != null) {
            switch (selectedSize) {
                case "Small":
                    holder.sizeSelector.check(R.id.pizzaSmlRadio);
                    break;
                case "Medium":
                    holder.sizeSelector.check(R.id.pizzaMedRadio);
                    break;
                case "Large":
                    holder.sizeSelector.check(R.id.pizzaLrgRadio);
                    break;
                default:
                    break;
            }
        } else {
            holder.sizeSelector.clearCheck();
        }

        // Quantity change listener
        holder.quantityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                menuItem.setQuantity(newVal);
                holder.price.setText(String.format("$%.2f", menuItem.getTotalPrice()));

            }
        });

        // Radio button selector, check for the view not having an ID, making sure the radio button
        // is valid. Null checks, and then adjusting the price of the menuItem based on size.
        holder.sizeSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != View.NO_ID) {
                    RadioButton radioButton = group.findViewById(checkedId);
                    if (radioButton != null) {
                        String size = (String) radioButton.getTag();
                        if (size != null) {
                            menuItem.setSelectedSize(size);
                            holder.price.setText(String.format("$%.2f", menuItem.getTotalPrice()));
                            Log.d("MenuAdapter", "Selected Size: " + menuItem.getSelectedSize());
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuArrayList.size();
    }

    // Custom View Holder class
    public class View_Holder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView menuItemName, price;
        NumberPicker quantityPicker;
        RadioGroup sizeSelector;

        // Create View_Holder constructor
        public View_Holder(@NonNull View itemView) {

            super(itemView);

            // Attach UI instance variables to UI components in menu_row.xml
            image = itemView.findViewById(R.id.menuImageView);
            menuItemName = itemView.findViewById(R.id.menuItemTextView);
            price = itemView.findViewById(R.id.pizzaPriceTextView);
            quantityPicker = itemView.findViewById(R.id.menuItemQuantityPicker);
            sizeSelector = itemView.findViewById(R.id.sizeSelector);
        }
    }

    // get the selected item objects and their quantities from the menu screen
    public ArrayList<MenuItemModel> getSelectedItems() {
        ArrayList<MenuItemModel> selectedItems = new ArrayList<>();
        for (MenuItemModel menuItem : menuArrayList) {
            if (menuItem.getQuantity() > 0) {
                selectedItems.add(menuItem);
            }
        }
        return selectedItems;
    }
}
