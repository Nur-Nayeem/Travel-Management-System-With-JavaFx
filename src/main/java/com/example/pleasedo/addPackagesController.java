package com.example.pleasedo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class addPackagesController implements Initializable {

    @FXML
    private TextField seatsField;

    @FXML
    private ComboBox<String> PackageType;

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
    private TextField TripLocation;

    @FXML
    private VBox contentArea;

    @FXML
    private Button ClearBtn;




    private String selectedImagePath;

    private List<City> city;

    private String selectAddCity;

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
        String[]  typePkg = {"Local","Foreign"};
        PackageType.getItems().addAll(typePkg);

        PackageType.setOnAction(event -> {
            String data = PackageType.getSelectionModel().getSelectedItem().toString();
            selectAddCity = data;
            System.out.println(selectAddCity);
        });

        imageLocationBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image");
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                String absolutePath = selectedFile.getAbsolutePath();
                String relativePath = absolutePath.replace("\\", "/").replaceFirst(".*/img/", "/img/");
                System.out.println(relativePath);
                imgLocation.setText(relativePath);
                selectedImagePath = relativePath;
            }
        });

        addPackageBtn.setOnAction(event -> {

            // SQL query to insert data into table
            if(tripTo.getText()!=null && priceTrip.getText()!=null && daysTrip.getText()!=null && imgLocation.getText()!=null && seatsField.getText()!=null && TripLocation.getText()!=null){

                String nameTrip = tripTo.getText();
                int priceTripValue = Integer.parseInt(priceTrip.getText());
                int daysTrp = Integer.parseInt(daysTrip.getText());
                String trimmedPath = selectedImagePath;
                int seatsTrip = Integer.parseInt(seatsField.getText());
                String LcName = TripLocation.getText();

                String sql1 = "INSERT INTO City (name, days, price, image,number_of_seats,triplocation) VALUES (?, ?, ?, ?,?,?)";

                String sql2 = "INSERT INTO foreignCity (name, days, price, image,number_of_seats,triplocation) VALUES (?, ?, ?, ?,?,?)";

                String CountSeatUpdate = "INSERT INTO tour_seat_count (city_name,total_seats) VALUES (?, ?)";

                String sql3="";




                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "");
                    System.out.println("Connection");
                    PreparedStatement statement = null;
                    if(Objects.equals(selectAddCity,"Local")){
                        statement = connection.prepareStatement(sql1);
                        sql3 = "INSERT INTO allCity (name, days, price, image,number_of_seats,triplocation) values (?, ?, ?, ?, ?,?)";
                    } else if (Objects.equals(selectAddCity,"Foreign")) {
                        statement = connection.prepareStatement(sql2);
                        sql3 = "INSERT INTO allCity (name, days, price, image,number_of_seats,triplocation) values (?, ?, ?, ?, ?,?)";
                    }
                    else {
                        System.out.println("Error insertion");
                    }


                    // Set values for parameters

                    statement.setString(1, nameTrip);
                    statement.setInt(2, daysTrp);
                    statement.setInt(3, priceTripValue);
                    statement.setString(4, trimmedPath);
                    statement.setInt(5, seatsTrip);
                    statement.setString(6, LcName);





                    // Execute the SQL statement
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        statement = connection.prepareStatement(sql3);
                        statement.setString(1, nameTrip);
                        statement.setInt(2, daysTrp);
                        statement.setInt(3, priceTripValue);
                        statement.setString(4, trimmedPath);
                        statement.setInt(5, seatsTrip);
                        statement.setString(6, LcName);
                        statement.executeUpdate();

                        statement = connection.prepareStatement(CountSeatUpdate);
                        statement.setString(1, nameTrip);
                        statement.setInt(2, 0);
                        statement.executeUpdate();
                        showAlert(Alert.AlertType.INFORMATION, "Add Packages", "successfully added Packages");

                        System.out.println("A new row has been inserted successfully.");

                    }
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
                System.out.println("Trip Name: " + nameTrip + "\nPrice: " + priceTripValue + "\nImage Location: " + trimmedPath + "\nDay's: " + daysTrp);
                System.out.println(getClass().getResource("City.fxml"));
                insertExternalFxml(nameTrip,priceTripValue,daysTrp,trimmedPath);

            }else {
                showAlert(Alert.AlertType.ERROR, "Adding Failed", "Please filled the input fileds");

            }



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
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
