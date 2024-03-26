package com.ethanlogintest.order_customization;

import java.util.HashMap;
import java.util.Map;

public class SizeDataModel {
    private String small, medium, large;
    private double priceSmall, priceMedium, priceLarge;

    // SizeDataModel Constructor
    public SizeDataModel (
            String small,
            String medium,
            String large,
            double priceSmall,
            double priceMedium,
            double priceLarge) {

        this.small = small;
        this.medium = medium;
        this.large = large;
        this.priceSmall = priceSmall;
        this.priceMedium = priceMedium;
        this.priceLarge = priceLarge;
    }
    public double getPriceSmall() {
        return priceSmall;
    }
    public double getPriceMedium() {
        return priceMedium;
    }
    public double getPriceLarge() {
        return priceLarge;
    }
}
