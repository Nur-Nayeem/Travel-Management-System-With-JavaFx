module com.example.pleasedo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires mysql.connector.j;
    requires java.sql;

    opens com.example.pleasedo to javafx.fxml;
    exports com.example.pleasedo;
}