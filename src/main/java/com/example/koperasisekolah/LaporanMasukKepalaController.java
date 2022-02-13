package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LaporanMasukKepalaController implements Initializable {

    @FXML
    private VBox panelLaporan = null;

    @FXML
    private ImageView background;

    @FXML
    private Label pengeluaranLabel;

    @FXML
    private ImageView printIcon;


    @FXML
    private ImageView uang;

    @FXML
    private Button cetakBtn;


    PreparedStatement preparedStatement;
    ResultSet resultSetData;
    ResultSet resultSetPengeluaran;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File uangFile = new File("image/icons8-paper-money-96.png");
        Image uangImage = new Image(uangFile.toURI().toString());
        uang.setImage(uangImage);

        File printFile = new File("image/icons8-print-96.png");
        Image printImage = new Image(printFile.toURI().toString());
        printIcon.setImage(printImage);

        File backgroundFile = new File("image/background_dataBarang.png");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        background.setImage(backgroundImage);

        try {
            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM laporanmasuk");
            resultSetData = preparedStatement.executeQuery();


            while (resultSetData.next()){

                int id = resultSetData.getInt("id");
                String tanggal = resultSetData.getString("tanggal");
                String bulan = resultSetData.getString("bulan");
                String namaBarang = resultSetData.getString("namaBarang");
                int harga = resultSetData.getInt("harga");
                int kulak = resultSetData.getInt("kulak");
                int jumlahKulak = resultSetData.getInt("jumlahKulak");
                int pengeluaran = resultSetData.getInt("pengeluaran");

                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("ItemLaporanMasuk.fxml"));
                Parent root = (Parent) loader.load();
                ItemLaporanMasukController itemLaporanMasukController = loader.getController();
                itemLaporanMasukController.dataLaporan(id,tanggal,bulan,namaBarang,harga,kulak,jumlahKulak,pengeluaran);
                panelLaporan.getChildren().add(root);
            }

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(pengeluaran) AS totalpengeluaran FROM laporanmasuk");
            resultSetPengeluaran = preparedStatement.executeQuery();
            resultSetPengeluaran.next();
            pengeluaranLabel.setText("Rp. " + String.valueOf(resultSetPengeluaran.getInt("totalpengeluaran")));

            cetakBtn.setOnMouseClicked(event -> {
                JasperPrint jasperPrint;
                java.util.Map param = new HashMap();

                try {
                    jasperPrint = JasperFillManager.fillReport("report/LaporanMasuk.jasper", param,DBUtils.getConnect());

                    JasperViewer viewer = new JasperViewer(jasperPrint, false);
                    viewer.setTitle("Laporan Barang Masuk");
                    viewer.setVisible(true);
                } catch (JRException e) {
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
