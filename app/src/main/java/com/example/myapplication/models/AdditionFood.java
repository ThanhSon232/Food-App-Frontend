package com.example.myapplication.models;

import java.util.List;

public class AdditionFood {
    Long id;
    String name;
    double price;
    int quantity;
    List<Image> imageURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Image> getImageURL() {
        return imageURL;
    }

    public void setImageURL(List<Image> imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "AdditionFood{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", imageURL=" + imageURL +
                '}';
    }

    public AdditionFood(Long id, String name, double price, int quantity, List<Image> imageURL) {
        id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }
}
