package com.example.myapplication.models;

import java.util.List;

public class Product {
    String id;
    String name;
    Type category;
    int quantity;
    double rateRatio;
    int numRate;
    String description;
    List<Image> image;
    String restaurantName;
    int price;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", quantity=" + quantity +
                ", rateRatio=" + rateRatio +
                ", numRate=" + numRate +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", restaurantName='" + restaurantName + '\'' +
                ", price=" + price +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getCategory() {
        return category;
    }

    public void setCategory(Type category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Product(String id, String name, Type category, int quantity, double rateRatio, int numRate, String description, List<Image> image, String restaurantName, int price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.rateRatio = rateRatio;
        this.numRate = numRate;
        this.description = description;
        this.image = image;
        this.restaurantName = restaurantName;
        this.price = price;
    }
}
