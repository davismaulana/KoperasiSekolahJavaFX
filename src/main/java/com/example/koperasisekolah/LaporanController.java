package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LaporanController implements Initializable {

    @FXML
    private VBox panelLaporan = null;

    @FXML
    private ImageView hapusRiwayat, tanggal, uang, uang2, background;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File hapusRiwayatFile = new File("image/icons8-delete-48.png");
        Image hapusRiwayatImage = new Image(hapusRiwayatFile.toURI().toString());
        hapusRiwayat.setImage(hapusRiwayatImage);

        File tanggalFile = new File("image/icons8-calendar-100.png");
        Image tanggalImage = new Image(tanggalFile.toURI().toString());
        tanggal.setImage(tanggalImage);

        File uangFile = new File("image/icons8-paper-money-96.png");
        Image uangImage = new Image(uangFile.toURI().toString());
        uang.setImage(uangImage);

        File uang2File = new File("image/icons8-paper-money-96.png");
        Image uang2Image = new Image(uang2File.toURI().toString());
        uang2.setImage(uang2Image);

        File backgroundFile = new File("image/background_dataBarang.png");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        background.setImage(backgroundImage);

        Node[] nodes = new Node[10];
        for (int i = 0; i<nodes.length; i++){
            try {
                nodes [i] = FXMLLoader.load(getClass().getResource("ItemLaporan.fxml"));
                panelLaporan.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
