package com.example.pleasedo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {




    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private VBox ScrollPaneWhite;

    @FXML
    private Pane newPanes;

    @FXML
    private Button frnbtn;

    @FXML
    private Button backBtn;

    @FXML
    private GridPane citiesGrid;
    private List<City> cities;



    public void switchToPackage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Packages.fxml")));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        citiesGrid.getChildren().clear();
        frnbtn.setOnAction(event -> {
            citiesGrid.getChildren().clear();
            cities = new ArrayList<>(getForeignCities());
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

                    if(column == 3){
                        column = 0;
                        ++row;
                    }
                    citiesGrid.add(pane,column++,row);
                    GridPane.setMargin(pane, new Insets(20));

                }


            }

        });

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

                if(column == 3){
                    column = 0;
                    ++row;
                }
                citiesGrid.add(pane,column++,row);
                GridPane.setMargin(pane, new Insets(20));



            }


        }
        backBtn.setOnAction(event -> {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashBoard.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });


    }


    private List<City> getCities() {
        List<City> ls = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM City");
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

    private List<City> getForeignCities() {
        List<City> ls = new ArrayList<>();


        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM foreignCity");
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

    public void DetailsPkg() throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("PackageDetailsAndBooking.fxml"));

        Pane pane = null;


        try {
            pane = fxmlLoader.load();
            newPanes.getChildren().clear();
            newPanes.getChildren().add(pane);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }

    public void fornPkgData(){
        citiesGrid.getChildren().clear();
        cities = new ArrayList<>(getForeignCities());
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

                if(column == 3){
                    column = 0;
                    ++row;
                }
                citiesGrid.add(pane,column++,row);
                GridPane.setMargin(pane, new Insets(20));

            }


        }

    }




    }