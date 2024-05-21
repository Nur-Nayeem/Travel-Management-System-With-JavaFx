package com.example.pleasedo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class loginUserController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button loginBtn;
    @FXML
    private TextField userFiled;
    @FXML
    private PasswordField passFiled;
    @FXML
    private Hyperlink SignUpBtn;


    @FXML
    private ComboBox<String> cmboxUorA;

    private String UsrAdm;

    private static final String URL = "jdbc:mysql://localhost:3306/admin";
    private static final String USER = "root";
    private static final String PASSWORD = "";





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[]  listUser = {"User","Admin"};
        cmboxUorA.getItems().addAll(listUser);

        cmboxUorA.setOnAction(event -> {
            String data = cmboxUorA.getSelectionModel().getSelectedItem().toString();
            UsrAdm = data;
            System.out.println(UsrAdm);
        });

        loginBtn.setOnAction(event -> {

                if(userFiled.getText()!=null && passFiled.getText()!=null){

                    String nam = userFiled.getText();
                    String pass = passFiled.getText();


                    try {

                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "");
                        System.out.println("Connection");

                        Statement statement = connection.createStatement();

                        if(Objects.equals(UsrAdm,"Admin")){
                            ResultSet resultSet = statement.executeQuery("SELECT * FROM admins where name = '"+nam+"' AND password = '"+pass+"'");
                            if(resultSet.next()){
                                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + nam + "!");
                                try {
                                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminDashBoard.fxml")));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setX(130);
                                stage.setY(30);
                                stage.show();
                            }
                            else
                            {
                                System.out.println("login failed");
                            }

                        }else if(Objects.equals(UsrAdm,"User")){
                            ResultSet resultSet = statement.executeQuery("SELECT * FROM users where name = '"+nam+"' AND password = '"+pass+"'");
                            if(resultSet.next()){
                                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + nam + "!");

                                try {
                                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashBoard.fxml")));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setX(100);
                                stage.setY(30);
                                stage.show();

                            }
                            else
                            {
                                showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
                            }
                        }
                        else
                        {
                            showAlert(AlertType.ERROR, "Warning", "Select longin as");
                        }
                    }
                    catch (SQLException e) {
                        System.out.println("error");
                        showAlert(AlertType.ERROR, "Something Wrong", "Chack the connection");
                        e.printStackTrace(); // Print the stack trace for detailed error information
                    }


                }
                else{
                    showAlert(AlertType.ERROR, "Warning", "Please filled the text field");
                }
        });
        SignUpBtn.setOnAction(event -> {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Registration.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        });

    }
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
