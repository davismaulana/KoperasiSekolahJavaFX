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

public class LaporanKeluarKepalaController implements Initializable {

    @FXML
    private VBox panelLaporan = null;

    @FXML
    private ImageView background;

    @FXML
    private Button cetakBtn;


    @FXML
    private Label keuntunganLabel;

    @FXML
    private Label pendapatanLabel;

    @FXML
    private ImageView uang;

    @FXML
    private ImageView cetakIcon;

    @FXML
    private ImageView uang2;

    PreparedStatement preparedStatement;
    ResultSet resultSetData;
    ResultSet resultSetPendapatan;
    ResultSet resultSetKeuntungan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File uangFile = new File("image/icons8-paper-money-96.png");
        Image uangImage = new Image(uangFile.toURI().toString());
        uang.setImage(uangImage);

        File cetakFile = new File("image/icons8-print-96.png");
        Image cetakImage = new Image(cetakFile.toURI().toString());
        cetakIcon.setImage(cetakImage);

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

            cetakBtn.setOnMouseClicked(event -> {
                JasperPrint jasperPrint;
                java.util.Map param = new HashMap();

                try {
                    jasperPrint = JasperFillManager.fillReport("report/LaporanKeluar.jasper", param,DBUtils.getConnect());

                    JasperViewer viewer = new JasperViewer(jasperPrint, false);
                    viewer.setTitle("Laporan Barang Keluar");
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
