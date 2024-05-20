package com.example.pleasedo;

public class CitySeatCount {
    private String cityName;
    private int totalSeats;

    public CitySeatCount(String cityName, int totalSeats) {
        this.cityName = cityName;
        this.totalSeats = totalSeats;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}
