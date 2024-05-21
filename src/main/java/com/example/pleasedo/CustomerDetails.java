package com.example.pleasedo;

public class CustomerDetails {
    private String cstomerName;
    private String tripName;
    private int totalSeats;
    private String dateTrip;


    public CustomerDetails(String cstomerName, String tripName,int totalSeats,String dateTrip) {

        this.cstomerName = cstomerName;
        this.tripName = tripName;
        this.dateTrip = dateTrip;
        this.totalSeats = totalSeats;
    }

    public String getCstomerName() {
        return cstomerName;
    }

    public void setCstomerName(String cstomerName) {
        this.cstomerName = cstomerName;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
    public  String getTripName(){
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setDateTrip(String dateTrip) {
        this.dateTrip = dateTrip;
    }
    public String getDateTrip(){
        return dateTrip;
    }
}
