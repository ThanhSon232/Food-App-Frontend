package com.example.myapplication.models;

import java.util.List;

public class Restaurant {
    private List<Image> imageURL;
    private double rateRatio;
    private int numRate;
    private boolean isVerified;
    private List<Type> types;
    private boolean isFreeShipping;
    private String name;


    public Restaurant(List<Image> imageURL, double rateRatio, int numRate, boolean isVerified, List<Type> types, boolean isFreeShipping, String name) {
        this.imageURL = imageURL;
        this.rateRatio = rateRatio;
        this.numRate = numRate;
        this.isVerified = isVerified;
        this.types = types;
        this.isFreeShipping = isFreeShipping;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "imageURL=" + imageURL +
                ", rateRatio=" + rateRatio +
                ", numRate=" + numRate +
                ", isVerified=" + isVerified +
                ", types=" + types +
                ", isFreeShipping=" + isFreeShipping +
                ", name='" + name + '\'' +
                '}';
    }

    public List<Image> getImageURL() {
        return imageURL;
    }

    public void setImageURL(List<Image> imageURL) {
        this.imageURL = imageURL;
    }

    public double getRateRatio() {
        return rateRatio;
    }

    public void setRateRatio(double rateRatio) {
        this.rateRatio = rateRatio;
    }

    public int getNumRate() {
        return numRate;
    }

    public void setNumRate(int numRate) {
        this.numRate = numRate;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public boolean isFreeShipping() {
        return isFreeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        isFreeShipping = freeShipping;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
