package com.example.rezervacijadoktora;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
public class HelloController {
    public PasswordField passwordLabela;
    public TextField jmbgLabela;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void loginButtonClick(ActionEvent actionEvent) {
        try {
            String url = "jdbc:sqlite:bazaPodataka.db";
            Connection conn = DriverManager.getConnection(url);
            String jmbg = jmbgLabela.getText();
            String pass = passwordLabela.getText();
            Statement stmt = conn.createStatement();
            ResultSet rez = stmt.executeQuery("SELECT password, doktor FROM osoba WHERE jmbg = '" + jmbg+"'");
            if(rez.next()) {
                if(pass.equals(rez.getString(1))) {
                    if(rez.getInt(2) == 1) {
                        System.out.println("Prijava uspijesna");
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("doktorForma.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 762, 600);
                        Stage stage = new Stage();
                        stage.setTitle("Doktor");
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                    }else {
                        System.out.println("Prijava uspijesna");
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pacijentForma.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 894, 526);
                        Stage stage = new Stage();
                        stage.setTitle("Pacijent");
                        stage.setScene(scene);
                        stage.setResizable(false);
                        PacijentForma.initData(jmbgLabela.getText());
                        stage.show();
                    }
                }else {
                    Alert a = new Alert(Alert.AlertType.NONE);
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("Neispravna lozinka ili JMBG");
                    a.show();
                }

            }else {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Neispravna lozinka ili JMBG");
                a.show();
            }
            conn.close();
        } catch ( SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}