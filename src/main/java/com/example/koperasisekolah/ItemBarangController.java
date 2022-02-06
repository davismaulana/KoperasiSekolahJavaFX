package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class ItemBarangController implements Initializable {

    @FXML
    private ImageView delete;

    @FXML
    private Label idLabel, namaBarangLabel, hargaLabel, kulakLabel, tanggalLabel, stokLabel;

    @FXML
    private ImageView edit;

    @FXML
    public Button deleteBtn, editBtn;

    PreparedStatement preparedStatement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File editFile = new File("image/icons8-edit-property-48.png");
        Image editImage = new Image(editFile.toURI().toString());
        edit.setImage(editImage);

        File deleteFile = new File("image/icons8-delete-48.png");
        Image deleteImage = new Image(deleteFile.toURI().toString());
        delete.setImage(deleteImage);
    }

    public void setDataBarang(int id, String namaBarang, int stok, int harga, int kulak, String tanggal){
        idLabel.setText(String.valueOf(id));
        namaBarangLabel.setText(namaBarang);
        stokLabel.setText(String.valueOf(stok));
        hargaLabel.setText(String.valueOf(harga));
        kulakLabel.setText(String.valueOf(kulak));
        tanggalLabel.setText(tanggal);

//        editBtn.setOnMouseClicked(event -> {
//            Parent root = null;
//
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(DBUtils.class.getResource("FormUpdateBarang.fxml"));
//                root = (Parent) fxmlLoader.load();
//                Stage stage = new Stage();
//                stage.setScene(new Scene(root));
//                stage.setResizable(false);
//                stage.initStyle(StageStyle.UNDECORATED);
//                stage.requestFocus();
//                stage.show();
//                UpdateBarangController updateBarangController = fxmlLoader.getController();
//                updateBarangController.prepareData(id, namaBarang, stok, harga, kulak);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        });
//    }

//    public void deleteOnAction(int id){
//        String idBarang = String.valueOf(id);
//        try {
//            preparedStatement = DBUtils.getConnect().prepareStatement("DELETE FROM databarang WHERE id= ?");
//            preparedStatement.setString(1, idBarang);
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
