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

public class TransaksiController implements Initializable {

    @FXML
    private VBox panelTransaksi, panelDataTransaksi;

    @FXML
    private ImageView backgroundTransaksi;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File backgroundFile = new File("image/background_transaksi.png");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        backgroundTransaksi.setImage(backgroundImage);

        Node[] nodes = new Node[10];
        for (int i = 0; i<nodes.length; i++){
            try {
                nodes [i] = FXMLLoader.load(getClass().getResource("ItemTransaksi.fxml"));
                panelTransaksi.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Node[] nodes1 = new Node[10];
        for (int i = 0; i<nodes1.length; i++){
            try {
                nodes1 [i] = FXMLLoader.load(getClass().getResource("ItemDataTransaksi.fxml"));
                panelDataTransaksi.getChildren().add(nodes1[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
