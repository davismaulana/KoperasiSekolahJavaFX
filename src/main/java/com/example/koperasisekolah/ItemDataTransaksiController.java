package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemDataTransaksiController implements Initializable {

    @FXML
    public ImageView hapusImg;

    @FXML
    private Label hargaLabel;

    @FXML
    private Label jumlahLabel;

    @FXML
    private Label namaBarangLabel;

    @FXML
    private Label totalLabel;

    @FXML
    public Button hapusBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File imgHapusFile = new File("image/icons8-close-96.png");
        Image hapusImage = new Image(imgHapusFile.toURI().toString());
        hapusImg.setImage(hapusImage);
    }

    public void setDataTransaksi(String namaBarang, int jumlah, int harga, int total){
        namaBarangLabel.setText(namaBarang);
        jumlahLabel.setText(String.valueOf(jumlah));
        hargaLabel.setText(String.valueOf(harga));
        totalLabel.setText(String.valueOf(total));
    }
}
