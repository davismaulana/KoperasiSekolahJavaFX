package com.example.koperasisekolah;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class UpdateBarangController implements Initializable {

    @FXML
    private TextField hargaField;

    @FXML
    private TextField hargaKulakField;

    @FXML
    private TextField jumlahField;

    @FXML
    private TextField namaBarangField;

    @FXML
    private Button simpanBtn;

    @FXML
    private Label judul;

    @FXML
    private Button tutupBtn;

    PreparedStatement preparedStatement;

    public void simpanBtnOnAction (ActionEvent event){
//        String namaBarang = namaBarangField.getText();
//        int jumlah = Integer.parseInt(jumlahField.getText());
//        int harga = Integer.parseInt(hargaField.getText());
//        int hargaKulak = Integer.parseInt(hargaKulakField.getText());
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD/MM/YYYY");
//        String tanggal = formatter.format(now);
//
//        try {
//            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("DataBarang.fxml"));
//            loader.load();
//            DataBarangController dataBarangController = loader.getController();
//            dataBarangController.inputDataBarang(namaBarang,jumlah,harga,hargaKulak,tanggal);
//            Stage stage = (Stage) tutupBtn.getScene().getWindow();
//            stage.close();
//
//        } catch (IOException | SQLException e) {
//            e.printStackTrace();
//        }


    }

    public void tutupBtnOnAction (ActionEvent event){
        Stage stage = (Stage) tutupBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
