package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemDataTransaksiController implements Initializable {

    @FXML
    private ImageView hapusImg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File imgHapusFile = new File("image/icons8-close-96.png");
        Image hapusImage = new Image(imgHapusFile.toURI().toString());
        hapusImg.setImage(hapusImage);
    }
}
