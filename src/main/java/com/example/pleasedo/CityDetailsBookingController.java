// CityDetailsBookingController.java
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CityDetailsBookingController implements Initializable {






    //customer details
    @FXML
    private TextField cstmrName;

    @FXML
    private TextField cstmrEmail;

    @FXML
    private TextField cstmrPhone;

    @FXML
    private DatePicker dateOfTour;

    @FXML
    private ComboBox<String> gendar;






    @FXML
    private Button bookPkg;

    @FXML
    private Button ClearBtn;



    //Package Details
    @FXML
    private Label titlaPkg;

    @FXML
    private Label numDays;

    @FXML
    private Label PricePkg;

    @FXML
    private Label totalPricePkg;

    @FXML
    private Text avleSeate;

    @FXML
    private ComboBox<Integer> numberOfSeatPkg;


    @FXML
    private ImageView detailsImgPkg;

    @FXML
    private Button bk;

    public String nm,locationTrp;


    private int seat,pkgprice;


    private CityController cityController; // Composition

    private String URL = "jdbc:mysql://localhost:3306/admin";
    private String USER = "root";
    private String PASS = "";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        nm = CityController.globalName;
        System.out.println(nm);

        String sql3 = "INSERT INTO customer (name,location,seats,pay,mobile,date) values ( ?,?,?,?,?,?)";

        Integer[]  numSeats = {1,2,3,4,5,6,7,8,9,10};
        String[] gendarSet = {"Male","Female"};
        gendar.getItems().addAll(gendarSet);

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





        ClearBtn.setOnAction(event -> {
            cstmrName.clear();
            cstmrEmail.clear();
            cstmrPhone.clear();
            dateOfTour.setValue(null);
            gendar.setValue(null);
            totalPricePkg.setText("");

        });

        bookPkg.setOnAction(event -> {
            if(cstmrName.getText() !=null && cstmrEmail.getText() !=null && cstmrPhone.getText() !=null && dateOfTour.getValue() !=null && gendar.getValue() !=null && numberOfSeatPkg.getValue() !=null ){

                int bookSeat=Integer.parseInt(numberOfSeatPkg.getValue()+"");
                int availSeat=Integer.parseInt(avleSeate.getText());

                try {
                    Connection connection = DriverManager.getConnection(URL,USER,PASS);
                    PreparedStatement preparedStatement = connection.prepareStatement(sql3);

                    preparedStatement.setString(1, cstmrName.getText());
                    preparedStatement.setString(2, nm);
                    preparedStatement.setInt(3, numberOfSeatPkg.getValue());
                    preparedStatement.setInt(4, pkgprice*seat);
                    preparedStatement.setString(5, cstmrPhone.getText());
                    preparedStatement.setString(6, String.valueOf(dateOfTour.getValue()));

                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected > 0){
                        System.out.println("A new row has been inserted successfully.");
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if(bookSeat<availSeat){

                    String sql = "Update allcity set number_of_seats = ? where name = ? ";
                    String sql1 = "Update city set number_of_seats = ? where name = ? ";
                    String sql2 = "Update foreigncity set number_of_seats = ? where name = ? ";
                    String SeatsUpdate = "Update tour_seat_count set total_seats = total_seats + ? where city_name = ? ";


                    try {Connection connection = DriverManager.getConnection(URL, USER, PASS);
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);


                        preparedStatement.setInt(1,availSeat - bookSeat);
                        preparedStatement.setString(2,titlaPkg.getText());



                        int rowsUpdated = preparedStatement.executeUpdate();


                        if (rowsUpdated > 0) {
                            System.out.println("Booking Done!");
                        }
                        avleSeate.setText(String.valueOf(availSeat - bookSeat));

                        try {
                            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                            PreparedStatement preparedStatement3 = connection.prepareStatement(SeatsUpdate);


                            preparedStatement1.setInt(1,availSeat - bookSeat);
                            preparedStatement1.setString(2,titlaPkg.getText());

                            preparedStatement2.setInt(1,availSeat - bookSeat);
                            preparedStatement2.setString(2,titlaPkg.getText());

                            preparedStatement3.setInt(1,bookSeat);
                            preparedStatement3.setString(2,titlaPkg.getText());

                            preparedStatement1.executeUpdate();
                            preparedStatement2.executeUpdate();
                            preparedStatement3.executeUpdate();

                        }catch (SQLException e)
                        {
                            System.out.println("Bus Is full manage a new Bus");
                        }



                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    System.out.println("Connecting problem");
                }



            }
            else{
                System.out.println("Text fields are empty");
            }
        });


    }


    private List<String> fetchDataFromDatabase(String lc) {
        List<String> data = new ArrayList<>();
        String tripLcn = "";
        String query = "SELECT name FROM allcity where location = ?";
        try {Connection connection = DriverManager.getConnection(URL, USER, PASS);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,lc);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data.add(resultSet.getString("name"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void switchToPackage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Packages.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(130); // Set X position
        stage.setY(20);
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
        avleSeate.setText(String.valueOf(cityDetails.getSeatAbailable()));


    }

}
