package com.example.pleasedo;

public class HotelDetails {
    private String name;
    private String imgSrc;
    private String location;
    public HotelDetails() {
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

    public void setLocation(String location){
        this.location = location;
    }


}