package com.example.pleasedo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class bdhotelsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button backBtn;

    @FXML
    private Button bdbtn;

    @FXML
    private Button frnbtn;

    @FXML
    private GridPane hotelsGrid;

    private List<Hotel> hotels;

    public void switchToDashBoard(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashBoard.fxml")));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToBdHotel(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("bdhotels.fxml")));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToFrnHotel(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("foreignHotels.fxml")));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        hotels = new ArrayList<>(getHotels());
        int column = 0;
        int row = 1;
        for (Hotel hotel : hotels) {
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
                    hotelController.setData(hotel);
                }
                catch (Exception e){
                    System.out.println("Error");
                }

                if(column == 3){
                    column = 0;
                    ++row;
                }
                hotelsGrid.add(pane,column++,row);
                GridPane.setMargin(pane, new Insets(20));



            }


        }

    }

    private List<Hotel> getHotels() {
        List<Hotel> ls = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bdhotel");
            while (resultSet.next()) {
                // Assuming a table with two columns: id and name
                String name = resultSet.getString("name");
                String imdr = resultSet.getString("image");
                String location = resultSet.getString("location");

                Hotel hotel = new Hotel();
                hotel.setHotelName(name);
                hotel.setImgSrc(imdr);
                hotel.setLocationName(location);

                ls.add(hotel);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return ls;

    }

}
