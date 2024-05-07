package com.example.pleasedo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class addPackagesController implements Initializable {

    @FXML
    private Button addPackageBtn;

    @FXML
    private TextField daysTrip;

    @FXML
    private Button imageLocationBtn;

    @FXML
    private TextField imgLocation;

    @FXML
    private TextField priceTrip; // Improved variable name

    @FXML
    private TextField tripTo;

    @FXML
    private VBox contentArea;

    private String selectedImagePath;

    private List<City> city;

    private Stage stage;
    private Scene scene;
    private Parent root;

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

        addPackageBtn.setOnAction(event -> {

            String nameTrip = tripTo.getText();
            int priceTripValue = Integer.parseInt(priceTrip.getText());
            int daysTrp = Integer.parseInt(daysTrip.getText());
            String trimmedPath = selectedImagePath;
            System.out.println("Trip Name: " + nameTrip + "\nPrice: " + priceTripValue + "\nImage Location: " + trimmedPath + "\nDay's: " + daysTrp);
            System.out.println(getClass().getResource("City.fxml"));
            insertExternalFxml(nameTrip,priceTripValue,daysTrp,trimmedPath);

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
