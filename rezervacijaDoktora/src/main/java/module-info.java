module com.example.rezervacijadoktora {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.rezervacijadoktora to javafx.fxml;
    exports com.example.rezervacijadoktora;
}