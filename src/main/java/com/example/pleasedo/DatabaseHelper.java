package com.example.pleasedo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/admin";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static ObservableList<CitySeatCount> getCitySeatCounts() {

        ObservableList<CitySeatCount> citySeatCounts = FXCollections.observableArrayList();
        String query = "SELECT city_name, total_seats FROM Tour_seat_count WHERE total_seats > 0";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                citySeatCounts.add(new CitySeatCount(
                        resultSet.getString("city_name"),
                        resultSet.getInt("total_seats")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return citySeatCounts;
    }

    public static ObservableList<CustomerDetails> getCustomersDtls() {

        ObservableList<CustomerDetails> customerDetails = FXCollections.observableArrayList();
        String query = "SELECT name, location,seats, date FROM customer";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                customerDetails.add(new CustomerDetails(
                        resultSet.getString("name"),
                        resultSet.getString("location"),
                        resultSet.getInt("seats"),
                        resultSet.getString("date")

                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerDetails;
    }


    public static void delteCustomer(String cstName){
        String updateQuery = "DELETE FROM customer WHERE name = ?";

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, cstName);

            preparedStatement.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void resetCitySeats(String cityName) {

        String updateQuery = "UPDATE city SET number_of_seats = 30 WHERE name = ?";
        String updateQuery2 = "UPDATE allcity SET number_of_seats = 30 WHERE name = ?";
        String updateBusSeatQuery = "UPDATE Tour_seat_count SET total_seats = 0 WHERE city_name = ?";
        try {
             Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            PreparedStatement preparedStatement2 = connection.prepareStatement(updateQuery2);
            PreparedStatement preparedStatement3 = connection.prepareStatement(updateBusSeatQuery);



            preparedStatement.setString(1, cityName);
            preparedStatement2.setString(1, cityName);
            preparedStatement3.setString(1, cityName);


            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
            preparedStatement3.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
