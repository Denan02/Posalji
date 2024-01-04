package com.example.rezervacijadoktora;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PacijentForma {

    public TableView tabela;
    public TableColumn id_termina;
    public TableColumn dan;
    public TableColumn mjesec;
    public TableColumn sati;
    public TableColumn minute;
    public ListView lista1;
    public ListView lista2;
    public ListView lista3;
    public ListView lista4;
    public TextField idTerm;
    public static String jmbgPamti = null;
    public ListView nalazL1;
    public ListView terminL1;
    public ListView terminL2;
    public ListView terminL3;

    public static void initData(String text) {
        jmbgPamti = text;
    }

    public void ucitajClick(ActionEvent actionEvent) {
        try {
            String url = "jdbc:sqlite:bazaPodataka.db";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rez = stmt.executeQuery("SELECT id_termina, dan, mjesec, sati, minute, opis_pregleda FROM termini WHERE rezervisano = 0");
            lista1.getItems().clear();
            lista2.getItems().clear();
            lista3.getItems().clear();
            lista4.getItems().clear();
            while(rez.next()) {
                lista1.getItems().add(rez.getString(1));
                lista2.getItems().add(rez.getString(2)+"."+rez.getString(3));
                lista3.getItems().add(rez.getString(4)+":"+rez.getString(5));
                lista4.getItems().add(rez.getString(6));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rezervisiClick(ActionEvent actionEvent) {
        try {
            String url = "jdbc:sqlite:bazaPodataka.db";
            Connection conn = DriverManager.getConnection(url);
            Statement stm = conn.createStatement();
            stm.execute("UPDATE termini SET rezervisano = 1 WHERE id_termina = "+ idTerm.getText()+" AND rezervisano = 0");
            stm.execute("INSERT INTO rezervacije(id_termina,jmbg) VALUES(" + idTerm.getText()+","+jmbgPamti+")");
            ResultSet rez = stm.executeQuery("SELECT id_termina, dan, mjesec, sati, minute, opis_pregleda FROM termini WHERE rezervisano = 0");
            lista1.getItems().clear();
            lista2.getItems().clear();
            lista3.getItems().clear();
            lista4.getItems().clear();
            while(rez.next()) {
                lista1.getItems().add(rez.getString(1));
                lista2.getItems().add(rez.getString(2)+"."+rez.getString(3));
                lista3.getItems().add(rez.getString(4)+":"+rez.getString(5));
                lista4.getItems().add(rez.getString(6));
            }
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("USPIJESNO STE REZERVISALI");
            a.show();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void nalaziClick(ActionEvent actionEvent) {
        try {
            String url = "jdbc:sqlite:bazaPodataka.db";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = null;
            stmt = conn.createStatement();
            ResultSet rez = stmt.executeQuery("SELECT text_nalaza FROM nalaz WHERE jmbg_pacijenta = " + jmbgPamti);
            nalazL1.getItems().clear();
            while(rez.next()) {
                nalazL1.getItems().add(rez.getString(1));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rezervacijeClick(ActionEvent actionEvent) {
        try {
            String url = "jdbc:sqlite:bazaPodataka.db";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = null;
            stmt = conn.createStatement();
            ResultSet rez = stmt.executeQuery("SELECT t.dan,t.mjesec, t.sati, t.minute, t.opis_pregleda FROM rezervacije r JOIN termini t ON t.id_termina = r.id_termina WHERE r.jmbg = " + jmbgPamti);
            terminL1.getItems().clear();
            terminL2.getItems().clear();
            terminL3.getItems().clear();
            while(rez.next()) {
                terminL1.getItems().add(rez.getString(1)+"."+rez.getString(2));
                terminL2.getItems().add(rez.getString(3)+":"+rez.getString(4));
                terminL3.getItems().add(rez.getString(5));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}