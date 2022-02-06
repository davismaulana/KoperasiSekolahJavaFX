package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertController implements Initializable {

    @FXML
    private Button okButton;

    @FXML
    public Label judul;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        okButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        });
    }
}
