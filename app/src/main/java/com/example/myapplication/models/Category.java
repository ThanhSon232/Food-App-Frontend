package com.example.myapplication.models;

public class Category {
    private int resource;
    private String title;
    private boolean isSelected;

    public Category(int resource, String title, boolean isSelected) {
        this.resource = resource;
        this.title = title;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
