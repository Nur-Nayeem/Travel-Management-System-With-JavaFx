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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddHotelScreenController implements Initializable {



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
    private TextField hotelName;

    @FXML
    private ComboBox<String> hotelType;

    @FXML
    private Button imageLocationBtn;

    @FXML
    private TextField imgLocation;

    @FXML
    private TextField locationHotel;




    private String selectedImagePath;

    private List<Hotel> hotel;

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
        hotelType.getItems().addAll(typePkg);

        hotelType.setOnAction(event -> {
            String data = hotelType.getSelectionModel().getSelectedItem().toString();
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
                imgLocation.setText(relativePath);
                selectedImagePath = relativePath;
            }
        });

        addHotelBtn.setOnAction(event -> {

            // SQL query to insert data into table

            String location = locationHotel.getText();
            String name = hotelName.getText();
            int numVipRoom = Integer.parseInt(NumOfvipRoom.getText());
            int numEcoRoom = Integer.parseInt(NumOfeconomyRoom.getText());

            int priceVip = Integer.parseInt(VipPrice.getText());
            int priceEco = Integer.parseInt(economyPrice.getText());
           // int totalRoom = numVipRoom + numEcoRoom;
            String trimmedPath = selectedImagePath;

            String sql1 = "INSERT INTO bdhotel (location, name, num_of_eco_room,num_of_vip_room,eco_room_price,vip_room_price, image) VALUES (?, ?, ?, ?, ?, ?, ?)";

            String sql2 = "INSERT INTO foreignhotel (location, name, num_of_eco_room,num_of_vip_room,eco_room_price,vip_room_price, image) VALUES (?, ?, ?, ?, ?, ?, ?)";


            String sql3 = "INSERT INTO allhotel (location, name, num_of_eco_room,num_of_vip_room,eco_room_price,vip_room_price, image) VALUES (?, ?, ?, ?, ?, ?, ?)";





            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "");
                System.out.println("Connection");
                PreparedStatement statement = null,statement2=null;
                if(Objects.equals(selectAddCity,"Local")){
                    statement = connection.prepareStatement(sql1);
                } else if (Objects.equals(selectAddCity,"Foreign")) {
                    statement = connection.prepareStatement(sql2);
                }
                else {
                    System.out.println("Error insertion");
                }

                statement.setString(1, location);
                statement.setString(2, name);

                statement.setInt(3, numEcoRoom);
                statement.setInt(4, numVipRoom);
                statement.setInt(5, priceEco);
                statement.setInt(6, priceVip);
                statement.setString(7, trimmedPath);
                //statement.setInt(8, totalRoom);


                // Execute the SQL statement
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {

                    statement = connection.prepareStatement(sql3);

                    statement.setString(1, location);
                    statement.setString(2, name);

                    statement.setInt(3, numEcoRoom);
                    statement.setInt(4, numVipRoom);
                    statement.setInt(5, priceEco);
                    statement.setInt(6, priceVip);
                    statement.setString(7, trimmedPath);
                    statement.executeUpdate();
                    System.out.println("A new row has been inserted successfully.");

                }
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Location: " + location + "\nname: " + hotelName + "\nImage Location: " + trimmedPath );
            System.out.println(getClass().getResource("hotel.fxml"));
            insertExternalFxml(location,name,trimmedPath);

        });



    }

    private List<Hotel> getHotels(String location, String name ,String trimmedPath) {
        List<Hotel> ls = new ArrayList<>();

        Hotel hotel = new Hotel();
        hotel.setLocationName(location);
        hotel.setHotelName(name);
        hotel.setImgSrc(trimmedPath);
        ls.add(hotel);
        return ls;
    }

    private void insertExternalFxml(String location, String name ,String trimmedPath) {
        hotel = new ArrayList<>(getHotels(location,name,trimmedPath));



        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("hotel.fxml"));

        Pane pane = null;

        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        HotelController hotelcontroller = fxmlLoader.getController();
        if(hotelcontroller != null){
            System.out.println("Ok ");
            try{
                hotelcontroller.setData(hotel.getLast());
            }
            catch (Exception e){
                System.out.println("Error");
            }
            contentArea.getChildren().add(pane);
        }
    }
}
