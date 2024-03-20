package com.example.norththompsonpizzarialandingactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        holder.image.setImageResource(menuArrayList.get(position).img);
        holder.text.setText(menuArrayList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return menuArrayList.size();
    }

    // Custom View Holder class
    public class View_Holder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView text;

        // Create View_Holder constructor
        public View_Holder(@NonNull View itemView) {

            super(itemView);

            // Attach UI instance variables to UI components in menu_row.xml
            image = itemView.findViewById(R.id.menuImageView);
            text = itemView.findViewById(R.id.menuItemTextView);
        }
    }
}
