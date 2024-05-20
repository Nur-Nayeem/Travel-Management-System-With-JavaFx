package com.example.pleasedo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CitySeatCountController {

    @FXML
    private TableView<CitySeatCount> citySeatCountTable;

    @FXML
    private TableColumn<CitySeatCount, String> cityNameColumn;

    @FXML
    private TableColumn<CitySeatCount, Integer> totalSeatsColumn;

    @FXML
    private Button handleCheckout;

    private ObservableList<CitySeatCount> citySeatCounts;


    @FXML
    public void initialize() {
        cityNameColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        totalSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));

        citySeatCounts = DatabaseHelper.getCitySeatCounts();
        citySeatCountTable.setItems(citySeatCounts);

//        citySeatCountTable.setItems(DatabaseHelper.getCitySeatCounts());

        handleCheckout.setOnAction(event -> {
            CitySeatCount selectedCity = citySeatCountTable.getSelectionModel().getSelectedItem();
            if (selectedCity != null) {
                DatabaseHelper.resetCitySeats(selectedCity.getCityName());
                refreshTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a city to checkout.", ButtonType.OK);
                alert.showAndWait();
            }

        });


    }

    private void refreshTable() {
        citySeatCounts = DatabaseHelper.getCitySeatCounts();
        citySeatCountTable.setItems(citySeatCounts);
    }

    private void resetNumberOfSeats(String cityName) {
        DatabaseHelper.resetCitySeats(cityName);
    }

}
