package com.example.pleasedo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewPkgFromAdminController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private GridPane citiesGrid;

    private List<City> cities;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        cities = new ArrayList<>(getCities());
        int column = 0;
        int row = 1;
        for (City city : cities) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("City.fxml"));

            Pane pane = null;

            try {
                pane = fxmlLoader.load();
            } catch (IOException e) {

                throw new RuntimeException(e);
            }

            CityController cityController = fxmlLoader.getController();
            if(cityController != null){
                System.out.println("Ok ");
                try{
                    cityController.setData(city);
                }
                catch (Exception e){
                    System.out.println("Error");
                }

                if(column == 2){
                    column = 0;
                    ++row;
                }
                citiesGrid.add(pane,column++,row);
                GridPane.setMargin(pane, new Insets(20));



            }


        }


    }



    private List<City> getCities() {
        List<City> ls = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM allCity");
            while (resultSet.next()) {
                // Assuming a table with two columns: id and name
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String imdr = resultSet.getString("image");
                int d = resultSet.getInt("days");
                int prc = resultSet.getInt("price");

                City city = new City();
                city.setName(name);
                city.setImgSrc(imdr);
                city.setNbDay(d);
                city.setPrice(prc);
                ls.add(city);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return ls;

    }
}
