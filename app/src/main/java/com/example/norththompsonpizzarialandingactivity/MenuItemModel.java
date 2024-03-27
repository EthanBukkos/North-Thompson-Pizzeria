package com.example.norththompsonpizzarialandingactivity;

import java.io.Serializable;

public class MenuItemModel implements Serializable {

    int img;
    String name;
    double smlPrice, medPrice, lrgPrice;
    int quantity;

    public MenuItemModel(int img, String name, double smlPrice, double medPrice, double lrgPrice,
                         int quantity) {

        this.img = img;
        this.name = name;
        this.smlPrice = smlPrice;
        this.medPrice = medPrice;
        this.lrgPrice = lrgPrice;
        this.quantity = quantity;
    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
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

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}

