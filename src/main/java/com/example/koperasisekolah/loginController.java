package com.example.koperasisekolah;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    double x,y = 0;

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private ImageView brandingImageView;

    @FXML
    private ImageView lockIcon;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("image/20220110_223634.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File lockFile = new File("image/icons8-lock-90.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockIcon.setImage(lockImage);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;
                try{
                    preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM datauser WHERE username = ?");
                    preparedStatement.setString(1, usernameTextField.getText());
                    resultSet = preparedStatement.executeQuery();



                    if (!resultSet.isBeforeFirst()){
                        setMessageLabel("Username belum terdaftar");

                    }else{
                        while (resultSet.next()){
                            String retrievedPassword = resultSet.getString("password");
                            String dataBagian = resultSet.getString("bagian");
                            if (retrievedPassword.equals(passwordField.getText())){
                                if (dataBagian.equals("admin")){
                                    DBUtils.changeSceneAdmin(event, "Koperasi.fxml", usernameTextField.getText());
                                    Stage stage = (Stage) loginButton.getScene().getWindow();
                                    stage.close();
                                }else if (dataBagian.equals("kepalaKoperasi")){
                                    DBUtils.changeSceneKepala(event, "KepalaKoperasi.fxml", usernameTextField.getText());
                                    Stage stage = (Stage) loginButton.getScene().getWindow();
                                    stage.close();
                                }else {
                                    setMessageLabel("Anda siapa?");
                                }

                            }else {
                                setMessageLabel("Password salah");

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
                    if (DBUtils.getConnect() != null){
                        try {
                            DBUtils.getConnect().close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setMessageLabel(String Message){
        loginMessageLabel.setText(Message);
    }


//    public void loginButtonOnAction(ActionEvent event) throws IOException {
//        loginMessageLabel.setText("Login Error. Coba login ulang");
//        Parent root = FXMLLoader.load(getClass().getResource("Koperasi.fxml"));
//        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }

}
