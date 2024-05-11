// CityDetailsBookingController.java
package com.example.pleasedo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
    private Button bk;


    private int seat,pkgprice;
    @FXML
    private ComboBox<Integer> numberOfSeatPkg;

    private String data="bhjbm";

    private CityController cityController; // Composition

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Integer[]  numSeats = {1,2,3,4,5,6,7,8,9,10};
        numberOfSeatPkg.getItems().addAll(numSeats);
        numberOfSeatPkg.setOnAction(event -> {
            seat = numberOfSeatPkg.getSelectionModel().getSelectedItem();
            try {
                pkgprice = Integer.valueOf(PricePkg.getText());
                totalPricePkg.setText((pkgprice*seat)+"");
            } catch (NumberFormatException e) {
                System.out.println("Error: Label text is not a valid integer.");
            }
        });

    }
    public int seatCount(){
        return seat;
    }

    public void switchToPackage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Packages.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setCityData(CityDetails cityDetails){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(cityDetails.getImgSrc("/img/cox-bazar.png"))));
        detailsImgPkg.setImage(image);
        titlaPkg.setText(cityDetails.getName());
        numDays.setText(cityDetails.getNbDay());
        PricePkg.setText(cityDetails.getPrice());

    }

}
