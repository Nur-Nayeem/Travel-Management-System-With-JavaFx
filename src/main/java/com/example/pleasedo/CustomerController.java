package com.example.pleasedo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;



public class CustomerController implements Initializable {

    @FXML
    private TableView<CustomerDetails> customerDetailsTble;

    @FXML
    private TableColumn<CustomerDetails, String> CustomerName;

    @FXML
    private Button DeleteBtn;

    @FXML
    private TableColumn<CustomerDetails, String> TripNameColumn;

    @FXML
    private TableColumn<CustomerDetails, Integer> bookedSeatColumn;



    @FXML
    private TableColumn<CustomerDetails, String> dateColumn;

    private ObservableList<CustomerDetails> customerDetailsOb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CustomerName.setCellValueFactory(new PropertyValueFactory<>("cstomerName"));
        TripNameColumn.setCellValueFactory(new PropertyValueFactory<>("tripName"));
        bookedSeatColumn.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTrip"));


        customerDetailsOb = DatabaseHelper.getCustomersDtls();
        customerDetailsTble.setItems(customerDetailsOb);

        DeleteBtn.setOnAction(event -> {
            CustomerDetails selectName = customerDetailsTble.getSelectionModel().getSelectedItem();
            if (selectName != null) {
                DatabaseHelper.delteCustomer(selectName.getCstomerName());
                refreshTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a city to checkout.", ButtonType.OK);
                alert.showAndWait();
            }

        });

    }
    private void refreshTable() {
        customerDetailsOb = DatabaseHelper.getCustomersDtls();
        customerDetailsTble.setItems(customerDetailsOb);
    }

}
