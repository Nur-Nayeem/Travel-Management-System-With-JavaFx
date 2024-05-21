package com.example.pleasedo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class hotelDetailsController implements Initializable {

    @FXML
    private Button ClearBtn;

    @FXML
    private Button bk;

    @FXML
    private Button bookPkg;

    @FXML
    private TextField cstmrEmail;

    @FXML
    private TextField cstmrName;

    @FXML
    private TextField cstmrPhone;

    @FXML
    private DatePicker dateOfTour;

    @FXML
    private ImageView detailsImgPkg;

    @FXML
    private ComboBox<String> gendar;

    @FXML
    private ComboBox<Integer> numberOfSeatPkg;

    @FXML
    private ComboBox<String> roomTypes;

    @FXML
    private Text price;

    @FXML
    private Label titlaPkg;

    @FXML
    private Label totalPricePkg;

    @FXML
    void switchToPackage(ActionEvent event) {}

    private int seat,pkgprice;
    private String pkgType,HtlName,nm;

    private String URL = "jdbc:mysql://localhost:3306/admin";
    private String USER = "root";
    private String PASS = "";



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        bk.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hotelsInDashboard.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(130); // Set X position
            stage.setY(20);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });


        nm = HotelController.globalName;
        String qry = "select vip_room_price from allhotel where name = ?";
        String qry2 = "select eco_room_price from allhotel where name = ?";
        String sql3 = "INSERT INTO hotelCstmr (name,seats,pay,mobile,typeOfRoom,date,Hotelname) values ( ?,?,?,?,?,?,?)";

        Integer[]  numSeats = {1,2,3,4,5,6,7,8,9,10};
        String[] genderSet = {"Male","Female"};
        String[] typeOfRoom = {"VIP","Economy"};
        gendar.getItems().addAll(genderSet);
        roomTypes.getItems().addAll(typeOfRoom);



        try {
            Connection connection = DriverManager.getConnection(URL,USER,PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(qry2);

            preparedStatement.setString(1, nm);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                pkgprice = resultSet.getInt("eco_room_price");
                price.setText(String.valueOf(pkgprice));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        numberOfSeatPkg.getItems().addAll(numSeats);
        numberOfSeatPkg.setOnAction(event -> {
            seat = numberOfSeatPkg.getSelectionModel().getSelectedItem();

        });


        roomTypes.setOnAction(event -> {
            pkgType = roomTypes.getSelectionModel().getSelectedItem();
            try {
               if(Objects.equals(pkgType, "VIP")){

                   try {
                       Connection connection = DriverManager.getConnection(URL,USER,PASS);
                       PreparedStatement preparedStatement = connection.prepareStatement(qry);

                       preparedStatement.setString(1, nm);

                       ResultSet resultSet = preparedStatement.executeQuery();
                       if (resultSet.next()) {
                           pkgprice = resultSet.getInt("vip_room_price");
                           price.setText(String.valueOf(pkgprice));
                       }

                   } catch (SQLException e) {
                       throw new RuntimeException(e);
                   }
                   totalPricePkg.setText((pkgprice*seat)+"");
               }
               else{
                   try {
                       Connection connection = DriverManager.getConnection(URL,USER,PASS);
                       PreparedStatement preparedStatement = connection.prepareStatement(qry2);

                       preparedStatement.setString(1, nm);

                       ResultSet resultSet = preparedStatement.executeQuery();
                       if (resultSet.next()) {
                           pkgprice = resultSet.getInt("eco_room_price");
                           price.setText(String.valueOf(pkgprice));

                       }

                   } catch (SQLException e) {
                       throw new RuntimeException(e);
                   }
                   totalPricePkg.setText((pkgprice*seat)+"");
               }
            } catch (NumberFormatException e) {
                System.out.println("Error: Label text is not a valid integer.");
            }
        });


        bookPkg.setOnAction(event -> {
            if(cstmrName.getText() !=null && cstmrEmail.getText() !=null && cstmrPhone.getText() !=null && dateOfTour.getValue() !=null && gendar.getValue() !=null && numberOfSeatPkg.getValue() !=null ){

                int bookSeat=Integer.parseInt(numberOfSeatPkg.getValue()+"");

                try {
                    Connection connection = DriverManager.getConnection(URL,USER,PASS);
                    PreparedStatement preparedStatement = connection.prepareStatement(sql3);
                    //name,seats,pay,mobile,typeOfRoom,date,hotelname
                    preparedStatement.setString(1, cstmrName.getText());
                    preparedStatement.setInt(2, seat);
                    preparedStatement.setInt(3, Integer.parseInt(totalPricePkg.getText()));
                    preparedStatement.setString(4, cstmrPhone.getText());
                    preparedStatement.setString(5, pkgType);
                    preparedStatement.setString(6, String.valueOf(dateOfTour.getValue()));
                    preparedStatement.setString(7, nm);

                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected > 0){
                        showAlert(Alert.AlertType.INFORMATION, "Successfully Booked", "Hotel Booked Successful");

                        System.out.println("A new row has been inserted successfully.");
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                showAlert(Alert.AlertType.ERROR, "Booking Failed", "Please filled the input fileds");

                System.out.println("Text fields are empty");
            }
        });




    }
    public void setHotelData(HotelDetails hoteldetails){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(hoteldetails.getImgSrc("/img/cox-bazar.png"))));
        detailsImgPkg.setImage(image);
        titlaPkg.setText(hoteldetails.getName());

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




}
