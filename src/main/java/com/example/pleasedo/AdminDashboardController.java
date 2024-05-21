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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button customersBtn;
    @FXML
    private Text totalpkg;

    @FXML
    private Text totalHtl;

    @FXML
    private Text totalBdPkg;

    @FXML
    private Text totalFrnPkg;

    @FXML
    private Text TotalBdHtl;

    @FXML
    private Text totalFrnHtl;




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

    @FXML
    private MenuItem updateHotelMnoBtn;

    @FXML
    private MenuItem deleteMnoPkg;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String s1 = "SELECT COUNT(*) AS row_count FROM allcity";
        String s2 = "SELECT COUNT(*) AS row_count FROM allhotel";
        String s3 = "SELECT COUNT(*) AS row_count FROM city";
        String s4 = "SELECT COUNT(*) AS row_count FROM foreigncity";
        String s5 = "SELECT COUNT(*) AS row_count FROM bdhotel";
        String s6 = "SELECT COUNT(*) AS row_count FROM foreignhotel";




        int cityCount;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            resultSet = statement.executeQuery(s1);

            if (resultSet.next()) {
                try {
                    cityCount = resultSet.getInt("row_count");
                    totalpkg.setText(String.valueOf(cityCount));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            resultSet = statement.executeQuery(s2);

            if (resultSet.next()) {
                try {
                    cityCount = resultSet.getInt("row_count");
                    totalHtl.setText(String.valueOf(cityCount));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }


            resultSet = statement.executeQuery(s3);

            if (resultSet.next()) {
                try {
                    cityCount = resultSet.getInt("row_count");
                    totalBdPkg.setText(String.valueOf(cityCount));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            resultSet = statement.executeQuery(s4);

            if (resultSet.next()) {
                try {
                    cityCount = resultSet.getInt("row_count");
                    totalFrnPkg.setText(String.valueOf(cityCount));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            resultSet = statement.executeQuery(s5);

            if (resultSet.next()) {
                try {
                    cityCount = resultSet.getInt("row_count");
                    TotalBdHtl.setText(String.valueOf(cityCount));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            resultSet = statement.executeQuery(s6);

            if (resultSet.next()) {
                try {
                    cityCount = resultSet.getInt("row_count");
                    totalFrnHtl.setText(String.valueOf(cityCount));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




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

        deleteMnoPkg.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("delatePackages.fxml"));

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

        updateHotelMnoBtn.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("updateHotels.fxml"));

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

        customersBtn.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Customer.fxml"));

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
