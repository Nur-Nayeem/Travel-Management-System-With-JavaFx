package com.example.pleasedo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DelatePackagesController implements Initializable {
    @FXML
    private Button deleteBtn;

    @FXML
    private Pane pkgImg;

    @FXML
    private ComboBox<String> pkgType;

    @FXML
    private ComboBox<String> selectPkg;

    @FXML
    private ImageView imageDpkg;

    @FXML
    private Label nmDpkg;

    private String pkgDELT;

    private String selectDltPkg;

    private List<City> city;


    private String URL = "jdbc:mysql://localhost:3306/admin";
    private String USER = "root";
    private String pppp = "";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[]  typePkg = {"Local","Foreign"};
        pkgType.getItems().addAll(typePkg);

        pkgType.setOnAction(event -> {
            String data = pkgType.getSelectionModel().getSelectedItem().toString();
            selectDltPkg = data;
            String type = "";
            if(selectDltPkg.equals("Local")){
                type = "city";
            } else if (selectDltPkg.equals("Foreign")) {
                type = "foreignCity";
            }
            else {
                System.out.println("error select");
            }
            System.out.println(selectDltPkg);
            try {
                Connection connection = DriverManager.getConnection(URL, USER, pppp);
                System.out.println("Connection");
                PreparedStatement statement = null;
                statement = connection.prepareStatement("SELECT name,image FROM "+type);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    selectPkg.getItems().add(resultSet.getString("name"));

                }

            }catch (SQLException e){
                System.out.println("error");
            }

        });

        selectPkg.setOnAction(event -> {
            String data = selectPkg.getSelectionModel().getSelectedItem().toString();
            pkgDELT = data;
            System.out.println(pkgDELT);

            try {
                Connection connection = DriverManager.getConnection(URL, USER, pppp);
                System.out.println("Connection");
                PreparedStatement statement = null;

                statement = connection.prepareStatement("SELECT image FROM allCity where name = ?");
                statement.setString(1, pkgDELT);


                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {

                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(resultSet.getString("image"))));
                    imageDpkg.setImage(image);
                    nmDpkg.setText(pkgDELT);

                }

            }catch (SQLException e){
                System.out.println("error");
            }

        });

        deleteBtn.setOnAction(event -> {

            String nameTrip = pkgDELT;


            if(nameTrip!=null && selectDltPkg!=null){

                try {
                    Connection connection = DriverManager.getConnection(URL, USER, pppp);
                    System.out.println("Connection");
                    String sql;
                    if (Objects.equals(selectDltPkg, "Local")) {
                        sql = "DELETE FROM city  WHERE name = ?";

                    } else if (Objects.equals(selectDltPkg, "Foreign")) {
                        sql = "DELETE FROM foreignCity  WHERE name = ?";
                    } else {
                        System.out.println("Error insertion");
                        return;
                    }

                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, nameTrip);


                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        sql = "DELETE FROM allCity  WHERE name = ?";

                        statement = connection.prepareStatement(sql);
                        statement.setString(1, nameTrip);

                        statement.executeUpdate();
                        showAlert(Alert.AlertType.INFORMATION, "Successfully Deleted", "Package Deleted Successfully");

                        System.out.println("Delete Successful.");
                    }
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }else {
                showAlert(Alert.AlertType.ERROR, "Deleting Failed", "Please select the package");

            }


        });

    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
