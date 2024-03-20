package com.example.norththompsonpizzarialandingactivity;

public class MenuItemModel {

    int img;
    String name;
    double smlPrice, medPrice, lrgPrice;

    public MenuItemModel(int img, String name, double smlPrice, double medPrice, double lrgPrice) {

        this.img = img;
        this.name = name;
        this.smlPrice = smlPrice;
        this.medPrice = medPrice;
        this.lrgPrice = lrgPrice;
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
}

