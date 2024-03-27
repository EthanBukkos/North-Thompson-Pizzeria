package com.example.norththompsonpizzarialandingactivity;

import java.util.ArrayList;
import java.util.List;

public class VeggieDataModel {

    private String veggies, tomatoes, peppers, mushrooms, onions;
    private double tomatoPrice, pepperPrice, mushroomPrice, onionPrice;
    private boolean isSelected = false;
    private boolean tomatoesSelected = false,
            peppersSelected = false,
            mushroomsSelected = false,
            onionsSelected = false;
    public VeggieDataModel(
            String veggies,
            String tomatoes,
            String peppers,
            String mushrooms,
            String onions,
            double tomatoPrice,
            double pepperPrice,
            double mushroomPrice,
            double onionPrice) {

        this.veggies = veggies;
        this.tomatoes = tomatoes;
        this.peppers = peppers;
        this.mushrooms = mushrooms;
        this.onions = onions;
        this.tomatoPrice = tomatoPrice;
        this.pepperPrice = pepperPrice;
        this.mushroomPrice = mushroomPrice;
        this.onionPrice = onionPrice;
    }

    public String getVeggies() {
        return veggies;
    }

    public String getTomatoes() {
        return tomatoes;
    }

    public String getPeppers() {
        return peppers;
    }

    public String getMushrooms() {
        return mushrooms;
    }

    public String getOnions() {
        return onions;
    }

    public double getTomatoPrice() {
        return tomatoPrice;
    }
    public double getPepperPrice() {
        return pepperPrice;
    }
    public double getMushroomPrice() {
        return mushroomPrice;
    }
    public double getOnionPrice() {
        return onionPrice;
    }

    public boolean isTomatoesSelected() {
        return tomatoesSelected;
    }

    public boolean isPeppersSelected() {
        return peppersSelected;
    }

    public boolean isOnionsSelected() {
        return onionsSelected;
    }

    public boolean isMushroomsSelected() {
        return mushroomsSelected;
    }

    public void setTomatoesSelected(boolean tomatoesSelected) {
        this.tomatoesSelected = tomatoesSelected;
    }

    public void setPeppersSelected(boolean peppersSelected) {
        this.peppersSelected = peppersSelected;
    }

    public void setOnionsSelected(boolean onionsSelected) {
        this.onionsSelected = onionsSelected;
    }

    public void setMushroomsSelected(boolean mushroomsSelected) {
        this.mushroomsSelected = mushroomsSelected;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<String> getSelectedVeggies() {
        List<String> selectedVeggies = new ArrayList<>();
        if (tomatoesSelected) selectedVeggies.add("Tomatoes");
        if (peppersSelected) selectedVeggies.add("Peppers");
        if (mushroomsSelected) selectedVeggies.add("Mushrooms");
        if (peppersSelected) selectedVeggies.add("Onions");
        return selectedVeggies;
    }
}
