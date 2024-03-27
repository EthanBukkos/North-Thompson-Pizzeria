package com.example.norththompsonpizzarialandingactivity;

import java.util.HashMap;
import java.util.Map;

public class SauceDataModel {

    private String sauce, tomatoSauce, alfredoSauce;
    private double tomatoSaucePrice, alfredoSaucePrice;
    private boolean isSelected = false;
    private String selectedSauce;

    // Data model1 constructor
    public SauceDataModel(
            String sauce,
            String tomatoSauce,
            String alfredoSauce,
            double tomatoSaucePrice,
            double alfredoSaucePrice) {

        this.sauce = sauce;
        this.tomatoSauce = tomatoSauce;
        this.alfredoSauce = alfredoSauce;
        this.tomatoSaucePrice = tomatoSaucePrice;
        this.alfredoSaucePrice = alfredoSaucePrice;
    }

    public String getSauce() {
        return sauce;
    }
    public String getTomatoSauce() {
        return tomatoSauce;
    }
    public String getAlfredoSauce() {
        return alfredoSauce;
    }

    public boolean isSelected() {
        return selectedSauce != null && !selectedSauce.isEmpty();
    }
    public void setSelected(String selectedSauce) {
        this.selectedSauce = selectedSauce;
    }
    public String getSelectedSauce() {
       return selectedSauce;
    }
    public double getPrice() {
        if ("Tomato".equals(selectedSauce)) {
            return tomatoSaucePrice;
        } else if ("Alfredo".equals(selectedSauce)) {
            return alfredoSaucePrice;
        }
        return 0;
    }
}