package com.example.pleasedo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class HotelController {
    @FXML
    private Label hotelName;

    @FXML
    private ImageView img;

    @FXML
    private Label locationName;
    private HotelDetails hotelDetails1;
    public static String globalName;

    public void Details(MouseEvent event) throws IOException, SQLException {


        hotelDetails1 = gethotelDetails();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hotelDetails.fxml"));
        Parent root = loader.load();
        hotelDetailsController hotelDetailsController1 = loader.getController();
        hotelDetailsController1.setHotelData(hotelDetails1);


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(350); // Set X position
        stage.setY(180);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void setData(Hotel hotel){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(hotel.getImgSrc("/img/5 Star Hotel.png"))));
        img.setImage(image);
        hotelName.setText(hotel.getHotelName());
        locationName.setText(hotel.getLocationName());

    }
    public HotelDetails gethotelDetails() throws SQLException {


        HotelDetails hotel = new HotelDetails();
        hotel.setName(hotelName.getText());
        globalName = hotelName.getText();
        System.out.println(img.getImage());
        String nm = hotelName.getText();
        String img = "";
        String sql = "SELECT image FROM allhotel WHERE name = ?";
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "");
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, nm);
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.setString(1, nm);

        if (resultSet.next()) {
            String foundValue = resultSet.getString("image"); // Replace with actual column name
            img = foundValue;
            System.out.println("image found");
        } else {
            System.out.println("Value not found!");
        }
        hotel.setImgSrc(img);
        hotel.setName(hotelName.getText());


        return hotel;
    }



}
