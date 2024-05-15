package com.example.pleasedo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdatePackagesController implements Initializable {

    @FXML
    private ComboBox<String> PackageType;

    @FXML
    private ComboBox<String> selectLocation;

    @FXML
    private Button addPkgManue;

    @FXML
    private Button deletePkgBtn;

    @FXML
    private Button updatePkgBtn;

    @FXML
    private TextField daysTrip;

    @FXML
    private Button imageLocationBtn;

    @FXML
    private TextField imgLocation;

    @FXML
    private TextField priceTrip; // Improved variable name


    @FXML
    private VBox contentArea;

    @FXML
    private Button ClearBtn;



    @FXML
    private Button updateBtn;


    private String SelectTripName;

    private String selectedImagePath;

    private List<City> city;

    private String selectAddCity;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String URL = "jdbc:mysql://localhost:3306/admin";
    private String USER = "root";
    private String pppp = "";

    @FXML
    void switchToAdmin(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminDashBoard.fxml")));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[]  typePkg = {"Local","Foreign"};
        PackageType.getItems().addAll(typePkg);

        PackageType.setOnAction(event -> {
            String data = PackageType.getSelectionModel().getSelectedItem().toString();
            selectAddCity = data;
            String type = "";
            if(selectAddCity.equals("Local")){
                type = "city";
            } else if (selectAddCity.equals("Foreign")) {
                type = "foreignCity";
            }
            else {
                System.out.println("error select");
            }
            System.out.println(selectAddCity);
            try {
                Connection connection = DriverManager.getConnection(URL, USER, pppp);
                System.out.println("Connection");
                PreparedStatement statement = null;
                statement = connection.prepareStatement("SELECT name FROM "+type);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    selectLocation.getItems().add(resultSet.getString("name"));
                }

            }catch (SQLException e){
                System.out.println("error");
            }
        });




        imageLocationBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image");
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                String absolutePath = selectedFile.getAbsolutePath();
                String relativePath = absolutePath.replace("\\", "/").replaceFirst(".*/img/", "/img/");
                imgLocation.setText(relativePath);
                selectedImagePath = relativePath;
            }
        });

        updateBtn.setOnAction(event -> {

            // SQL query to insert data into table

            String nameTrip = String.valueOf(selectLocation.getValue());
            int priceTripValue = Integer.parseInt(priceTrip.getText());
            int daysTrp = Integer.parseInt(daysTrip.getText());
            String trimmedPath = selectedImagePath;

            String sql1 = "UPDATE city SET days = "+daysTrp+", price = "+priceTripValue+", image = "+trimmedPath+" where name = "+nameTrip;

            String sql2 = "UPDATE foreignCity SET days = "+daysTrp+", price = "+priceTripValue+", image = "+trimmedPath+" where name = "+nameTrip;

            String sql3="UPDATE allCity SET days = "+daysTrp+", price = "+priceTripValue+" , image = "+trimmedPath+" where name = "+nameTrip;




            try {
                Connection connection = DriverManager.getConnection(URL, USER, pppp);
                System.out.println("Connection");
                String sql;
                if (Objects.equals(selectAddCity, "Local")) {
                    sql = "UPDATE  city  SET days = ?, price = ?, image = ? WHERE name = ?";
                } else if (Objects.equals(selectAddCity, "Foreign")) {
                    sql = "UPDATE foreignCity SET days = ?, price = ?, image = ? WHERE name = ?";
                } else {
                    System.out.println("Error insertion");
                    return;
                }

                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, daysTrp);
                statement.setInt(2, priceTripValue);
                statement.setString(3, trimmedPath);
                statement.setString(4, nameTrip);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    sql = "UPDATE allCity SET days = ?, price = ?, image = ? WHERE name = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, daysTrp);
                    statement.setInt(2, priceTripValue);
                    statement.setString(3, trimmedPath);
                    statement.setString(4, nameTrip);
                    statement.executeUpdate();
                    System.out.println("Update Successful.");
                }
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Trip Name: " + nameTrip + "\nPrice: " + priceTripValue + "\nImage Location: " + trimmedPath + "\nDay's: " + daysTrp);
            System.out.println(getClass().getResource("City.fxml"));
            insertExternalFxml(nameTrip,priceTripValue,daysTrp,trimmedPath);

        });

        ClearBtn.setOnAction(event -> {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addPackages.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
        addPkgManue.setOnAction(event -> {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addPackages.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });





    }

    private List<City> getCities(String nameTrip,int priceTripValue,int daysTrp,String trimmedPath) {
        List<City> ls = new ArrayList<>();

        City city = new City();
        city.setName(nameTrip);
        city.setImgSrc(trimmedPath);
        city.setNbDay(daysTrp);
        city.setPrice(priceTripValue);
        ls.add(city);
        return ls;
    }

    private void insertExternalFxml(String nameTrip,int priceTripValue,int daysTrp,String trimmedPath) {
        city = new ArrayList<>(getCities(nameTrip,priceTripValue,daysTrp,trimmedPath));



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
                cityController.setData(city.getLast());
            }
            catch (Exception e){
                System.out.println("Error");
            }
            contentArea.getChildren().add(pane);
        }
    }
}
