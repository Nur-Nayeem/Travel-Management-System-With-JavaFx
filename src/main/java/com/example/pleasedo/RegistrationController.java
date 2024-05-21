package com.example.pleasedo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Button RegistationBtn;

    @FXML
    private TextField password;

    @FXML
    private TextField userName;

    @FXML
    private Hyperlink loginPagebtn;

    private static final String URL = "jdbc:mysql://localhost:3306/admin";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RegistationBtn.setOnAction(event -> {

            if(userName.getText()!=null && password.getText()!=null){

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "");
                    System.out.println("Connection");
                    PreparedStatement statement = null;
                    String sql = "INSERT INTO users (name,password) values (?, ?)";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1,userName.getText());
                    statement.setString(2,password.getText());
                    int rowsInserted = statement.executeUpdate();
                    if(rowsInserted>0){
                        showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Thank You, " + userName.getText() + "!");

                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Connection");

            }
            else {
                System.out.println("Please fill the input boxes");
            }
        });

        loginPagebtn.setOnAction(event -> {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginUser.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
