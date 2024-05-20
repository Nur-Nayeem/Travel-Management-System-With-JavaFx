package com.example.pleasedo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button adminDashBtn;

    @FXML
    private Button viewAllPackages;

    @FXML
    private Button TourBuses;

    @FXML
    private Pane TableOfBusses;

    @FXML
    private MenuButton menuePackages;

    @FXML
    private MenuButton HotelManage;

    @FXML
    private MenuItem addPackages;

    @FXML
    private MenuItem updatePkg;

    @FXML
    private MenuItem addHotelBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        adminDashBtn.setOnAction(event -> {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminDashBoard.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });



        TourBuses.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("CitySeatCountTable.fxml"));

            Pane pane = null;


            try {
                pane = fxmlLoader.load();
                TableOfBusses.getChildren().clear();
                TableOfBusses.getChildren().add(pane);
            } catch (IOException e) {

                throw new RuntimeException(e);
            }


        });
        addPackages.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addPackages.fxml"));

            Pane pane = null;

            try {
                pane = fxmlLoader.load();
                TableOfBusses.getChildren().clear();
                TableOfBusses.getChildren().add(pane);
            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        });
        updatePkg.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("updatePackages.fxml"));

            Pane pane = null;


            try {
                pane = fxmlLoader.load();
                TableOfBusses.getChildren().clear();
                TableOfBusses.getChildren().add(pane);
            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        });

        addHotelBtn.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addHotelScreen.fxml"));

            Pane pane = null;


            try {
                pane = fxmlLoader.load();
                TableOfBusses.getChildren().clear();
                TableOfBusses.getChildren().add(pane);
            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        });


        menuePackages.setOnAction(event -> {
            MenuItem menuItem = (MenuItem) event.getSource();
            String selectedOption = menuItem.getText();
            // Do something with the selected option
            System.out.println("Selected: " + selectedOption);
        });
        HotelManage.setOnAction(event -> {
            MenuItem menuItem = (MenuItem) event.getSource();
            String selectedOption = menuItem.getText();
            // Do something with the selected option
            System.out.println("Selected: " + selectedOption);
        });

        viewAllPackages.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("viewPkgFromAdmin.fxml"));

            Pane pane = null;

            try {
                pane = fxmlLoader.load();
                TableOfBusses.getChildren().clear();
                TableOfBusses.getChildren().add(pane);
            } catch (IOException e) {

                throw new RuntimeException(e);
            }

        });
    }

    public void LogOut(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginUser.fxml")));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
