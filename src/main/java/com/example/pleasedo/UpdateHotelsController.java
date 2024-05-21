package com.example.pleasedo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateHotelsController implements Initializable {

    @FXML
    private Button ClearBtn;

    @FXML
    private TextField NumOfeconomyRoom;

    @FXML
    private TextField NumOfvipRoom;

    @FXML
    private TextField VipPrice;

    @FXML
    private Button addHotelBtn;

    @FXML
    private VBox contentArea;

    @FXML
    private TextField economyPrice;

    @FXML
    private Button updateBtn;


    @FXML
    private ComboBox<String> hotelType;

    @FXML
    private Button imageLocationBtn;

    @FXML
    private TextField imgLocation;

    @FXML
    private TextField locationHotel;

    @FXML
    private ComboBox<String> selectHotel;

    private String selectedImagePath;

    private String selectUpHotel;

    private List<Hotel> hotel;


    private String URL = "jdbc:mysql://localhost:3306/admin";
    private String USER = "root";
    private String pppp = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[]  typePkg = {"Local","Foreign"};
        hotelType.getItems().addAll(typePkg);

        hotelType.setOnAction(event -> {
            String data = hotelType.getSelectionModel().getSelectedItem().toString();
            selectUpHotel = data;
            String type = "";
            if(selectUpHotel.equals("Local")){
                type = "bdhotel";
            } else if (selectUpHotel.equals("Foreign")) {
                type = "foreignhotel";
            }
            else {
                System.out.println("error select");
            }
            System.out.println(selectUpHotel);
            try {
                Connection connection = DriverManager.getConnection(URL, USER, pppp);
                System.out.println("Connection");
                PreparedStatement statement = null;
                statement = connection.prepareStatement("SELECT name FROM "+type);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    selectHotel.getItems().add(resultSet.getString("name"));
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
            System.out.println(selectedImagePath);
        });

        updateBtn.setOnAction(event -> {

            if(selectHotel.getValue()!=null && NumOfvipRoom.getText()!=null && VipPrice.getText()!=null && NumOfeconomyRoom.getText()!=null && economyPrice.getText()!=null && locationHotel.getText()!=null){

                String nameTrip = String.valueOf(selectHotel.getValue());
                int VipRoom = Integer.parseInt(NumOfvipRoom.getText());
                int priceVip = Integer.parseInt(VipPrice.getText());//VipPrice
                int EcoRoom = Integer.parseInt(NumOfeconomyRoom.getText());
                int priceeco = Integer.parseInt(economyPrice.getText());
                String location = String.valueOf(locationHotel.getText());
                String trimmedPath = selectedImagePath;


                try {
                    Connection connection = DriverManager.getConnection(URL, USER, pppp);
                    System.out.println("Connection");
                    String sql;
                    if (Objects.equals(selectUpHotel, "Local")) {
                        sql = "UPDATE  bdhotel  SET location = ?, num_of_vip_room = ?, num_of_eco_room = ?, vip_room_price = ?, eco_room_price = ?, image = ?  WHERE name = ?";

                    } else if (Objects.equals(selectUpHotel, "Foreign")) {
                        sql = "UPDATE foreignhotel SET location = ?, num_of_vip_room = ?, num_of_eco_room = ?, vip_room_price = ?, eco_room_price = ?, image = ?  WHERE name = ?";
                    } else {
                        System.out.println("Error insertion");
                        return;
                    }

                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, location);
                    statement.setInt(2,VipRoom);
                    statement.setInt(3, EcoRoom);
                    statement.setInt(4, priceVip);
                    statement.setInt(5, priceeco);
                    statement.setString(6, trimmedPath);
                    statement.setString(7, nameTrip);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        sql = "UPDATE allhotel SET location = ?, num_of_vip_room = ?, num_of_eco_room = ?, vip_room_price = ?, eco_room_price = ?, image = ?  WHERE name = ?";

                        statement = connection.prepareStatement(sql);
                        statement.setString(1, location);
                        statement.setInt(2,VipRoom);
                        statement.setInt(3, EcoRoom);
                        statement.setInt(4, priceVip);
                        statement.setInt(5, priceeco);
                        statement.setString(6, trimmedPath);
                        statement.setString(7, nameTrip);
                        statement.executeUpdate();
                        showAlert(Alert.AlertType.INFORMATION, "Update Hotel", "successfully updateed hotel");

                        System.out.println("Update Successful.");
                    }
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
                insertExternalFxml(nameTrip,location,trimmedPath);


            }else {
                showAlert(Alert.AlertType.ERROR, "Updating Failed", "Please filled the input fileds");

            }



        });


    }

    private List<Hotel> getHotels(String nameTrip, String location,String trimmedPath) {
        List<Hotel> ls = new ArrayList<>();

        Hotel hotel = new Hotel();
        hotel.setHotelName(nameTrip);
        hotel.setImgSrc(trimmedPath);
        hotel.setLocationName(location);
        ls.add(hotel);
        return ls;
    }



    private void insertExternalFxml(String nameTrip,String location,String trimmedPath) {
        hotel = new ArrayList<>(getHotels(nameTrip,location,trimmedPath));



        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("hotel.fxml"));

        Pane pane = null;

        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        HotelController hotelController = fxmlLoader.getController();
        if(hotelController != null){
            System.out.println("Ok ");
            try{
                hotelController.setData(hotel.getLast());
            }
            catch (Exception e){
                System.out.println("Error");
            }
            contentArea.getChildren().add(pane);
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
