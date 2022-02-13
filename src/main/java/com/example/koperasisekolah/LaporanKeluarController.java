package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LaporanKeluarController implements Initializable {

    @FXML
    private VBox panelLaporan = null;

    @FXML
    private ImageView background;

    @FXML
    private Button hapusData;

    @FXML
    private ImageView hapusRiwayat;

    @FXML
    private DatePicker kalender;

    @FXML
    private Label keuntunganLabel;

    @FXML
    private Label pendapatanLabel;

    @FXML
    private ImageView tanggal;

    @FXML
    private ImageView uang;

    @FXML
    private ImageView uang2;

    PreparedStatement preparedStatement;
    ResultSet resultSetData;
    ResultSet resultSetPendapatan;
    ResultSet resultSetKeuntungan;

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

        try {
            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM laporankeluar");
            resultSetData = preparedStatement.executeQuery();


            while (resultSetData.next()) {

                int id = resultSetData.getInt("id");
                String tanggal = resultSetData.getString("tanggal");
                String bulan = resultSetData.getString("bulan");
                String namaBarang = resultSetData.getString("namaBarang");
                int terjual = resultSetData.getInt("terjual");
                int pendapatan = resultSetData.getInt("pendapatan");
                int keuntungan = resultSetData.getInt("keuntungan");

                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("ItemLaporanKeluar.fxml"));
                Parent root = (Parent) loader.load();
                ItemLaporanKeluarController itemLaporanKeluarController = loader.getController();
                itemLaporanKeluarController.dataLaporan(id, tanggal, bulan, namaBarang, terjual, pendapatan, keuntungan);
                panelLaporan.getChildren().add(root);
            }

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(pendapatan) AS totalpendapatan FROM laporankeluar");
            resultSetPendapatan = preparedStatement.executeQuery();
            resultSetPendapatan.next();
            pendapatanLabel.setText("Rp. " + String.valueOf(resultSetPendapatan.getInt("totalpendapatan")));

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(keuntungan) AS totalkeuntungan FROM laporankeluar");
            resultSetKeuntungan = preparedStatement.executeQuery();
            resultSetKeuntungan.next();
            keuntunganLabel.setText("Rp. " + String.valueOf(resultSetKeuntungan.getInt("totalkeuntungan")));

            hapusData.setOnMouseClicked(event -> {
                try {
                    preparedStatement = DBUtils.getConnect().prepareStatement("DELETE FROM laporankeluar");
                    preparedStatement.execute();

                    preparedStatement = DBUtils.getConnect().prepareStatement("DELETE FROM datagrafik");
                    preparedStatement.execute();

                    panelLaporan.getChildren().clear();

                    keuntunganLabel.setText("Rp. 0");
                    pendapatanLabel.setText("Rp. 0");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
