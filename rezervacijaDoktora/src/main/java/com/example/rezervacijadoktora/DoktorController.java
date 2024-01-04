package com.example.rezervacijadoktora;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.sql.*;

public class DoktorController {
    public TextField mjesecText;
    public TextField danText;
    public TextField satiText;
    public TextField minuteText;
    public TextField opisText;
    public TextArea textNalaz;
    public TextField jmbgNalan;
    public ListView l1;
    public ListView l2;
    public ListView l3;
    public Button rezervacijeClick;
    public ListView l4;
    public static String jmbgPamti = null;
    public static void initData(String text) {
        jmbgPamti = text;
    }

    public void kreirajClick(ActionEvent actionEvent) {
        try {
            String url = "jdbc:sqlite:bazaPodataka.db";
            Connection conn = DriverManager.getConnection(url);
            Statement stm = conn.createStatement();
            String mjesec = mjesecText.getText();
            String dan = danText.getText();
            String sat = satiText.getText();
            String minute = minuteText.getText();
            String opis = opisText.getText();
            stm.execute("INSERT INTO termini(dan,mjesec,minute,sati,opis_pregleda,rezervisano) VALUES ("+dan+","+mjesec+","+minute+","+sat+",'"+opis+"',"+"0)");
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("TERMIN USPIJESNO KREIRAN");
            a.show();
            mjesecText.setText("");danText.setText("");satiText.setText("");minuteText.setText("");opisText.setText("");
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void nalazClick(ActionEvent actionEvent) {
        try {
            String jmbg = jmbgNalan.getText();
            String nalaz = textNalaz.getText();
            String url = "jdbc:sqlite:bazaPodataka.db";
            Connection conn = DriverManager.getConnection(url);
            Statement st = conn.createStatement();
            st.execute("INSERT  INTO nalaz(jmbg_pacijenta, text_nalaza) VALUES('"+jmbg+"' , '"+nalaz+"')");
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("NALAZ USPIJESNO KREIRAN");
            a.show();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void nalaziClick(ActionEvent actionEvent) {

        try {
            String url = "jdbc:sqlite:bazaPodataka.db";
            Connection conn = null;
            conn = DriverManager.getConnection(url);
            Statement stm = conn.createStatement();
            ResultSet rez = stm.executeQuery("SELECT jmbg_pacijenta, text_nalaza FROM nalaz");
            l1.getItems().clear();
            l2.getItems().clear();
            while(rez.next()) {
                l1.getItems().add(rez.getString(1));
                l2.getItems().add(rez.getString(2));
            }
            System.out.println(jmbgPamti);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void rezervacijeKC(ActionEvent actionEvent) {

        try {
            String url = "jdbc:sqlite:bazaPodataka.db";
            Connection conn = null;
            conn = DriverManager.getConnection(url);
            Statement stm = conn.createStatement();
            ResultSet rez = stm.executeQuery("SELECT r.jmbg, t.dan, t.mjesec, t.sati, t.minute FROM rezervacije r JOIN termini t ON t.id_termina = r.id_termina");
            l3.getItems().clear();
            l4.getItems().clear();
            while(rez.next()) {
                l3.getItems().add(rez.getString(1));
                l4.getItems().add(rez.getString(2)+"."+rez.getString(3)+ " "+rez.getString(4)+":"+rez.getString(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
