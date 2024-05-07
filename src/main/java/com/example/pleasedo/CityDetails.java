package com.example.pleasedo;

import javafx.scene.image.Image;

public class CityDetails {
    private String name;
    private String days;
    private String price;
    private Image image;

    public CityDetails(String name, String days, String price, Image image) {
        this.name = name;
        this.days = days;
        this.price = price;
        this.image = image;
    }


    // Getters for all fields
    // You can also add setters if needed
}