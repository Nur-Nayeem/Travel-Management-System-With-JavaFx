// CityController.java
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


public class CityController {

    @FXML
    private ImageView img;

    @FXML
    private Label days;

    @FXML
    public Label name;

    @FXML
    private Label price;

    public int d ;
    private CityDetails citiesDtls;



    public void Details(MouseEvent event) throws IOException, SQLException {
        d = 0;
        System.out.println(d);
        citiesDtls = getCitDtls();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PackageDetailsAndBooking.fxml"));
        Parent root = loader.load();
        CityDetailsBookingController cityDetailsBookingController = loader.getController();
        cityDetailsBookingController.setCityData(citiesDtls);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setData(City city){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(city.getImgSrc("/img/cox-bazar.png"))));
        img.setImage(image);
        name.setText(city.getName());
        days.setText(city.getNbDay()+"");
        price.setText(city.getPrice()+"");
    }

    public CityDetails getCitDtls() throws SQLException {


        CityDetails city = new CityDetails();
        city.setName(name.getText());
        System.out.println(img.getImage());
        String nm = name.getText();
        String img = "";
        String sql = "SELECT image FROM allCity WHERE name = ?";
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "");
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, nm);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String foundValue = resultSet.getString("image"); // Replace with actual column name
            img = foundValue;
            System.out.println("image found");
        } else {
            System.out.println("Value not found!");
        }
       // city.setImgSrc("/img/"+name.getText().toLowerCase()+".png");
        city.setImgSrc(img);
        city.setNbDay(days.getText());
        city.setPrice(price.getText());


        return city;
    }

}
