package com.example.pleasedo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CityDetailsBookingController implements Initializable {

    @FXML
    private ImageView detailsImgPkg;

    @FXML
    private Button addCartBtn;

    @FXML
    private Button bookPkg;

    @FXML
    private Label titlaPkg;

    @FXML
    private Label numDays;

    @FXML
    private Label PricePkg;

    @FXML
    private Label totalPricePkg;

    @FXML
    private Button genarateTotalPricePkg;

    public int seat;
    @FXML
    private ComboBox<Integer> numberOfSeatPkg;

    private int total;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Integer[]  numSeats = {1,2,3,4,5,6,7,8,9,10};
        numberOfSeatPkg.getItems().addAll(numSeats);
        numberOfSeatPkg.setOnAction(event -> {
             seat = numberOfSeatPkg.getSelectionModel().getSelectedItem();
            System.out.println(seat);
        });

    }

    public void receiveData(String name,String days, String price,Image img){

        titlaPkg.setText(name);
        numDays.setText(days);
        PricePkg.setText(price);
        totalPricePkg.setText(price);
        detailsImgPkg.setImage(img);


    }

}
