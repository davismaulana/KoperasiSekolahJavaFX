package com.example.koperasisekolah;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class DBUtils {

    private static Connection connection;

    public static Connection getConnect(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/koperasisekolah_db", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void changeSceneAdmin(ActionEvent event, String fxmlFile, String username){
        Parent root = null;

        if (username != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = (Parent) loader.load();
                KoperasiController koperasiController = loader.getController();
                koperasiController.setUserInformation(username);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = new Stage();
        stage.close();

    }

    public static void changeSceneKepala(ActionEvent event, String fxmlFile, String username){
        Parent root = null;

        if (username != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = (Parent) loader.load();
                KepalaKoperasiController kepalaKoperasiController = loader.getController();
                kepalaKoperasiController.setUserInformation(username);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = new Stage();
        stage.close();

    }

}
