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

    public static void changeScene(ActionEvent event, String fxmlFile, String username){
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

//    public static void changeLabelLogin(String Message){
//        Parent root = null;
//        try {
//            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("login.fxml"));
//            root = loader.load();
//            loginController loginController = loader.getController();
//            loginController.setMessageLabel(Message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager. getConnection("jdbc:mysql://localhost:3306/koperasisekolah_db", "root","");
            preparedStatement = connection.prepareStatement("SELECT password FROM admin WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();



            if (!resultSet.isBeforeFirst()){
                System.out.println("Admin not found in the database!");

            }else{
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)){
                        changeScene(event, "Koperasi.fxml", username);

                    }else {
                        System.out.println("Password did not match!");

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null){
                try{
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
