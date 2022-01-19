package com.example.koperasisekolah;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
                DBUtils.logInUser(event, usernameTextField.getText(), passwordField.getText());
            }
        });

    }

    public void loginButtonOnAction(ActionEvent event) {
//        if (usernameTextField.getText().isBlank() && passwordField.getText().isBlank() == false) {
//            validateLogin();
//        } else {
//            loginMessageLabel.setText("Tolong isi username dan password anda");
//        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Koperasi.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            root.setOnMousePressed(mouseEvent -> {
                x = mouseEvent.getSceneX();
                y = mouseEvent.getSceneY();
            });

            root.setOnMouseDragged(mouseEvent -> {
                stage.setX(mouseEvent.getSceneX() - x);
                stage.setY(mouseEvent.getSceneY() - y);
            });

            stage.setResizable(false);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stageclose = (Stage) loginButton.getScene().getWindow();
        stageclose.close();

    }


    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
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
