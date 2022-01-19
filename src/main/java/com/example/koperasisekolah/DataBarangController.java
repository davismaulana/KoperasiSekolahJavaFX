package com.example.koperasisekolah;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DataBarangController implements Initializable {

    @FXML
    private VBox panelBarang = null;

    @FXML
    private Button tambahBarang;

    @FXML
    private ImageView tambahLogo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Node [] nodes = new Node[10];
        for (int i = 0; i<nodes.length; i++){
            try {
                nodes [i] = FXMLLoader.load(getClass().getResource("ItemBarang.fxml"));
                panelBarang.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File tambahLogoFile = new File("image/icons8-add-48.png");
        Image tambahLogoImage = new Image(tambahLogoFile.toURI().toString());
        tambahLogo.setImage(tambahLogoImage);
    }

    public void tambahBarangOnAction (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FormTambahBarang.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
