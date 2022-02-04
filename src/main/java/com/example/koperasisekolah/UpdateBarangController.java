package com.example.koperasisekolah;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private Button tutupBtn, simpan;

    PreparedStatement preparedStatement;


//    public void simpanBtnOnAction (String idData){
//
//
//        String namaBarang = namaBarangField.getText();
//        int jumlah = Integer.parseInt(jumlahField.getText());
//        int harga = Integer.parseInt(hargaField.getText());
//        int hargaKulak = Integer.parseInt(hargaKulakField.getText());
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
//        String tanggal = formatter.format(now);
//
//        try {
//            preparedStatement = DBUtils.getConnect().prepareStatement("UPDATE databarang SET namaBarang= ?, stok= ?, harga= ?, kulak= ?, tanggal= ? WHERE id= ?");
//            preparedStatement.setString(1,namaBarang);
//            preparedStatement.setInt(2, jumlah);
//            preparedStatement.setInt(3,harga);
//            preparedStatement.setInt(4,hargaKulak);
//            preparedStatement.setString(5,tanggal);
//            preparedStatement.setString(6,idData);
//            preparedStatement.executeUpdate();
//
//            Stage stage = (Stage) tutupBtn.getScene().getWindow();
//            stage.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void tutupBtnOnAction (ActionEvent event){
        Stage stage = (Stage) tutupBtn.getScene().getWindow();
        stage.close();
    }

    public void prepareData(int id, String namabarangData, int jumlahData, int hargaData, int kulakData){
        Alert a = new Alert(Alert.AlertType.NONE);
        System.out.println(id);
        namaBarangField.setText(namabarangData);
        jumlahField.setText(String.valueOf(jumlahData));
        hargaField.setText(String.valueOf(hargaData));
        hargaKulakField.setText(String.valueOf(kulakData));

        simpan.setOnMouseClicked(event -> {
            String namaBarang = namaBarangField.getText();
            int jumlah = Integer.parseInt(jumlahField.getText());
            int harga = Integer.parseInt(hargaField.getText());
            int hargaKulak = Integer.parseInt(hargaKulakField.getText());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
            String tanggal = formatter.format(now);

            try {
                preparedStatement = DBUtils.getConnect().prepareStatement("UPDATE databarang SET namaBarang= ?, stok= ?, harga= ?, kulak= ?, tanggal= ? WHERE id= ?");
                preparedStatement.setString(1,namaBarang);
                preparedStatement.setInt(2, jumlah);
                preparedStatement.setInt(3,harga);
                preparedStatement.setInt(4,hargaKulak);
                preparedStatement.setString(5,tanggal);
                preparedStatement.setInt(6,id);
                preparedStatement.executeUpdate();

                FXMLLoader fxmlLoader = new FXMLLoader(DBUtils.class.getResource("alert.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.requestFocus();
                stage.show();

                AlertController alertController = fxmlLoader.getController();
                alertController.judul.setText("Data berhasil diubah");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = (Stage) tutupBtn.getScene().getWindow();
            stage.close();

        });
    }

}
