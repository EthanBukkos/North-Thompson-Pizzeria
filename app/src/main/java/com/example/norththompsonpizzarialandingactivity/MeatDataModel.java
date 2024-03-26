package com.ethanlogintest.order_customization;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MeatDataModel {

    private String meat, pepperoni, sausage, bacon;
    private double pepperoniPrice, sausagePrice, baconPrice;
    private boolean isSelected = false;
    private boolean pepperoniSelected = false,
            sausageSelected = false,
            baconSelected = false;

    public MeatDataModel(
            String meat,
            String pepperoni,
            String sausage,
            String bacon,
            double pepperoniPrice,
            double sausagePrice,
            double baconPrice) {

        this.meat = meat;
        this.pepperoni = pepperoni;
        this.sausage = sausage;
        this.bacon = bacon;
        this.pepperoniPrice = pepperoniPrice;
        this.sausagePrice = sausagePrice;
        this.baconPrice = baconPrice;
    }

    public String getMeat() {
        return meat;
    }

    public String getPepperoni() {
        return pepperoni;
    }

    public String getSausage() {
        return sausage;
    }

    public String getBacon() {
        return bacon;
    }

    public double getPepperoniPrice() {
        return pepperoniPrice;
    }

    public double getSausagePrice() {
        return sausagePrice;
    }

    public double getBaconPrice() {
        return baconPrice;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isPepperoniSelected() {
        return pepperoniSelected;
    }

    public boolean isSausageSelected() {
        return sausageSelected;
    }

    public boolean isBaconSelected() {
        return baconSelected;
    }

    public void setPepperoniSelected(boolean pepperoniSelected) {
        this.pepperoniSelected = pepperoniSelected;
        Log.d("MeatSelection", "Pepperoni selected " + pepperoniSelected);
    }

    public void setSausageSelected(boolean sausageSelected) {
        this.sausageSelected = sausageSelected;
        Log.d("MeatSelection", "Sausage selected " + sausageSelected);

    }

    public void setBaconSelected(boolean baconSelected) {
        this.baconSelected = baconSelected;
        Log.d("MeatSelection", "Bacon selected " + baconSelected);
    }

    public List<String> getSelectedMeats() {

        List<String> selectedMeats = new ArrayList<>();
        if (isPepperoniSelected()) {
            selectedMeats.add(getPepperoni());
        }
        if (isSausageSelected()) {
            selectedMeats.add(getSausage());
        }
        if (isBaconSelected()) {
            selectedMeats.add(getBacon());
        }
        return selectedMeats;
    }
}

