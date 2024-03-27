package com.example.norththompsonpizzarialandingactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.View_Holder> {

    Context context;

    // Array list to hold te image, name, and cost of each menu item
    ArrayList<MenuItemModel> menuArrayList;

    // Create constructor for MenuAdapter
    MenuAdapter (Context context, ArrayList<MenuItemModel> menuArrayList) {

        this.context = context;
        this.menuArrayList = menuArrayList;
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

        holder.image.setImageResource(menuArrayList.get(position).img);
        holder.menuItemName.setText(menuArrayList.get(position).name);
        holder.price.setText(String.format("$%.2f", menuItem.getSmlPrice()));
        holder.quantityPicker.setMinValue(0);
        holder.quantityPicker.setMaxValue(10);
        holder.quantityPicker.setValue(menuItem.getQuantity());

        // Quantity change listener
        holder.quantityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                menuItem.setQuantity(newVal);
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

        // Create View_Holder constructor
        public View_Holder(@NonNull View itemView) {

            super(itemView);

            // Attach UI instance variables to UI components in menu_row.xml
            image = itemView.findViewById(R.id.menuImageView);
            menuItemName = itemView.findViewById(R.id.menuItemTextView);
            price = itemView.findViewById(R.id.pizzaPriceTextView);
            quantityPicker = itemView.findViewById(R.id.menuItemQuantityPicker);
        }
    }
}
