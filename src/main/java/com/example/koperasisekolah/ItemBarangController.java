package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemBarangController implements Initializable {

    @FXML
    private ImageView delete;

    @FXML
    private ImageView edit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File editFile = new File("image/icons8-edit-property-48.png");
        Image editImage = new Image(editFile.toURI().toString());
        edit.setImage(editImage);

        File deleteFile = new File("image/icons8-delete-48.png");
        Image deleteImage = new Image(deleteFile.toURI().toString());
        delete.setImage(deleteImage);
    }
}
