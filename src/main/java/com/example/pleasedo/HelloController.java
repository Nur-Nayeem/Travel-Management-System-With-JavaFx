package com.example.pleasedo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {



    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private GridPane citiesGrid;
    private List<City> cities;


    public void switchToDashBoard(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashBoard.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPackage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Packages.fxml")));

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

        City city = new City();
        city.setName("Shylate");
        city.setImgSrc("/img/Shylate.png");
        city.setNbDay(3);
        city.setPrice(2000);
        ls.add(city);

        city = new City();
        city.setName("Cox-Bazar");
        city.setImgSrc("/img/cox.png");
        city.setNbDay(4);
        city.setPrice(6000);
        ls.add(city);

        city = new City();
        city.setName("Bandarban");
        city.setImgSrc("/img/bandarban.png");
        city.setNbDay(5);
        city.setPrice(2500);
        ls.add(city);

        city = new City();
        city.setName("Rangamati");
        city.setImgSrc("/img/rangamati.png");
        city.setNbDay(5);
        city.setPrice(2500);
        ls.add(city);

        city = new City();
        city.setName("Saint Martin");
        city.setImgSrc("/img/saintmartin.png");
        city.setNbDay(5);
        city.setPrice(2500);

        ls.add(city);


        return ls;

    }



}