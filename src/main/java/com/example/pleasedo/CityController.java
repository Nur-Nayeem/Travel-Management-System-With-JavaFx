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
import java.util.Objects;


public class CityController {

    @FXML
    private ImageView img;
    @FXML
    private Label days;

    @FXML
    private Label name;

    @FXML
    private Label price;




    private Stage stage;
    private Scene scene;
    private Parent root;

    public void Details(MouseEvent event) throws IOException {
//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PackageDetailsAndBooking.fxml")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PackageDetailsAndBooking.fxml"));
        Parent root = loader.load();
        CityDetailsBookingController controller = loader.getController();
        controller.receiveData(name.getText(), days.getText(), price.getText(),img.getImage());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


//
    }

    public void setData(City city){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(city.getImgSrc("/img/cox.png"))));
        img.setImage(image);
        name.setText(city.getName());
        days.setText(city.getNbDay()+" Days");
        price.setText(city.getPrice()+" $");
    }


}