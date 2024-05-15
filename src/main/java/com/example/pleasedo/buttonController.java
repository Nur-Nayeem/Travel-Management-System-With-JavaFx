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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class buttonController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button pkgBtn;

    @FXML
    private ImageView logOutbtn;

    @FXML
    private Button SeeMoreBd;

    @FXML
    private Button SeeMoreFrn;


    @FXML
    private GridPane gridPkghome;
    @FXML
    private GridPane gridPkghomeFrn;

    private List<City> cities;
    private List<City> citiesFrn;



    public void switchToPackage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Packages.fxml")));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToDashBoard(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashBoard.fxml")));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHotelDashboard(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hotelsInDashboard.fxml")));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToFrnPkg(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("foreignController.fxml")));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void LogOut(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginUser.fxml")));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cities = new ArrayList<>(getCities());
        citiesFrn = new ArrayList<>(getCitiesFrn());
        int column = 0;
        int row = 1;
        int columnFrn = 0;
        int rowFrn = 1;
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

                if(column == 4){
                    break;
                }
                gridPkghome.add(pane,column++,row);
                GridPane.setMargin(pane, new Insets(20));



            }


        }

        for (City city : citiesFrn) {
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

                if(columnFrn == 4){
                    break;
                }
                gridPkghomeFrn.add(pane,columnFrn++,rowFrn);
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

    private List<City> getCitiesFrn() {
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




//    public void switchToDashBoard(ActionEvent event) throws IOException{
//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashBoard.fxml")));
//        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }

}
