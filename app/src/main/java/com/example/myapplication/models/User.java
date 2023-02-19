package com.example.myapplication.models;

import java.util.List;

public class User {
    private Long Id;
    private String fullName;
    private String password;
    private String email;
    private List<String> imageURL;
    private boolean isSeller;

    public User(String fullName, String password, String email, boolean isSeller) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.isSeller = isSeller;
    }

    public User(String fullName, String password, String email, List<String> imageURL, boolean isSeller) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.imageURL = imageURL;
        this.isSeller = isSeller;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getImageURL() {
        return imageURL;
    }

    public void setImageURL(List<String> imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", isSeller=" + isSeller +
                '}';
    }
}
