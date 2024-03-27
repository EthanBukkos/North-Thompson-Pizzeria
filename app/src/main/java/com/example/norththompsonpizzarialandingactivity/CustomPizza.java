package com.example.norththompsonpizzarialandingactivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomPizza implements Serializable {

    private String size, sauce;

    double smallPrice;
    double medPrice;



    double largePrice;
    List<String> meats;
    List<String> veggies;

    public CustomPizza(
            String size,
            String sauce,
            List<String> meats,
            List<String> veggies) {

        this.size = size;
        this.sauce = sauce;
        this.meats = new ArrayList<>(meats);
        this.veggies = new ArrayList<>(veggies);
    }

    public String getSize() {
        return size;
    }

    public String getSauce() {
        return sauce;
    }

    public List<String> getMeats() {
        return meats;
    }

    public List<String> getVeggies() {
        return veggies;
    }

    public double getSmallPrice() { return smallPrice; }

    public double getMedPrice() { return medPrice; }

    public double getLargePrice() { return largePrice; }

    @Override
    public String toString() {

        return "CustomPizza{" +
                "size = " + size + '\'' +
                ", sauce = " + sauce + '\'' +
                ", meats = " + meats + '\'' +
                ", veggies = " + veggies +
                "}";
    }
}
