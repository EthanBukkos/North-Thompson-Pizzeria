package com.example.norththompsonpizzarialandingactivity;

import java.io.Serializable;

public class MenuItemModel implements Serializable {

    int img;
    String name;
    private String selectedSize, defaultSize;
    private double price;
    double smlPrice, medPrice, lrgPrice;
    int quantity;

    private boolean quantityPickerEnabled, sizeSelectorEnabled;

    public MenuItemModel(int img, String name, double smlPrice, double medPrice, double lrgPrice,
                         int quantity, String defaultSize) {

        this.img = img;
        this.name = name;
        this.smlPrice = smlPrice;
        this.medPrice = medPrice;
        this.lrgPrice = lrgPrice;
        this.quantity = quantity;
        this.selectedSize = defaultSize;

        // case to set the default size based on the price, and default price set to 0.00 for menu
        switch (defaultSize) {
            case "Small":
                this.price = smlPrice;
                break;
            case "Medium":
                this.price = medPrice;
                break;
            case "Large":
                this.price = lrgPrice;
                break;
            default:
                this.price = 0.00;
                break;
        }

    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    // return the price of just the selected size of pizza to call in discount activity
    public double getPrice() {
        switch (selectedSize) {
            case "Small":
                return smlPrice;
            case "Medium":
                return medPrice;
            case "Large":
                return lrgPrice;
            default:
                return 0.00;
        }
    }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSelectedSize() {
        return selectedSize;
    }

    public double getSmlPrice() {
        return smlPrice;
    }

    public double getMedPrice() {
        return medPrice;
    }

    public double getLrgPrice() {
        return lrgPrice;
    }

    // another check switch case on the pizza size, to adjust the price accordingly
    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;

        switch (selectedSize) {
            case "Small":
                price = smlPrice;
                break;
            case "Medium":
                price = medPrice;
                break;
            case "Large":
                price = lrgPrice;
                break;
            default:
                price = 0.00;
                break;
        }
    }

    // get the total price on the menu screen based on the case of the size price * quantity
    public double getTotalPrice() {
        switch (selectedSize) {
            case "Small":
                return smlPrice * quantity;
            case "Medium":
                return medPrice * quantity;
            case "Large":
                return lrgPrice * quantity;
            default:
                return 0.0;
        }
    }

    // methods added to be used to disable the radio buttons and numberpicker
    public boolean isQuantityPickerEnabled() {
        return quantityPickerEnabled;
    }

    public void setQuantityPickerEnabled(boolean quantityPickerEnabled) {
        this.quantityPickerEnabled = quantityPickerEnabled;
    }

    public boolean isSizeSelectorEnabled() {
        return sizeSelectorEnabled;
    }

    public void setSizeSelectorEnabled(boolean sizeSelectorEnabled) {
        this.sizeSelectorEnabled = sizeSelectorEnabled;
    }
}


