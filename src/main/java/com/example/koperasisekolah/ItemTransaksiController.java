package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemTransaksiController implements Initializable {

    @FXML
    public Button dataButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void searchData(String namaBarang){
        dataButton.setText(namaBarang);
    }
}
