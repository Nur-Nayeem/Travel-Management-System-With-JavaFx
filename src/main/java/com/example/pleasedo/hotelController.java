package com.example.pleasedo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class hotelController {
    @FXML
    private Label hotelName;

    @FXML
    private ImageView img;

    @FXML
    private Label locationName;

    public void setData(Hotel hotel){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(hotel.getImgSrc("/img/cox-bazar.png"))));
        img.setImage(image);
        hotelName.setText(hotel.getHotelName());
        locationName.setText(hotel.getLocationName());

    }



}
