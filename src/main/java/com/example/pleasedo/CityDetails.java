package com.example.pleasedo;

public class CityDetails {
    private String name;
    private String imgSrc;
    private String nbDay;
    private String price;
    private String TotalPrice;


    public CityDetails() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc(String s) {
        return this.imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getNbDay() {
        return this.nbDay;
    }

    public void setNbDay(String nbDay) {
        this.nbDay = nbDay;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return this.TotalPrice;
    }

    public void setTotalPrice(String TotalPrice) {
        this.TotalPrice = TotalPrice;
    }
}
