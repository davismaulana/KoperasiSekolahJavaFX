package com.example.koperasisekolah;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    private ImageView buyCart, totalCart, soldCart, money, money2, background;

    @FXML
    private LineChart lineChart;

    @FXML
    private AnchorPane panelLineGrafik;

    @FXML
    private Label dashboard, totalBarangLabel, totalKulakLabel, totalTerjualLabel, keuntunganLabel, pendapatanLabel;

    @FXML
    private PieChart pieChart;

    PreparedStatement preparedStatement;
    ResultSet resultSetDataBarang;
    ResultSet resultSetDataKulak;
    ResultSet resultSetDataTerjual;
    ResultSet resultSetPendapatan;
    ResultSet resultSetPendapatanUpdate;
    ResultSet resultSetKeuntunganUpdate;
    ResultSet resultSetKeuntungan;
    ResultSet resultSetGrafik;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        File buyCartFile = new File("image/icons8-buy-96.png");
        Image buyCartImage = new Image(buyCartFile.toURI().toString());
        buyCart.setImage(buyCartImage);

        File totalCartFile = new File("image/icons8-buying-96.png");
        Image totalCartImage = new Image(totalCartFile.toURI().toString());
        totalCart.setImage(totalCartImage);

        File soldCartFile = new File("image/icons8-return-purchase-96.png");
        Image soldCartImage = new Image(soldCartFile.toURI().toString());
        soldCart.setImage(soldCartImage);

        File moneyFile = new File("image/icons8-paper-money-96.png");
        Image moneyImage = new Image(moneyFile.toURI().toString());
        money.setImage(moneyImage);

        File money1File = new File("image/icons8-paper-money-96.png");
        Image money1Image = new Image(money1File.toURI().toString());
        money2.setImage(money1Image);

        File backgroundFile = new File("image/wave-haikei.png");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        background.setImage(backgroundImage);

        dataBarang();
        datakeuangan();

    }

    public void dataBarang(){
        try {
            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(stok) AS totalbarang FROM databarang");
            resultSetDataBarang = preparedStatement.executeQuery();
            resultSetDataBarang.next();
            totalBarangLabel.setText(String.valueOf(resultSetDataBarang.getInt("totalbarang")));

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(totalKulak) AS totalkulak FROM databarang");
            resultSetDataKulak = preparedStatement.executeQuery();
            resultSetDataKulak.next();
            totalKulakLabel.setText(String.valueOf(resultSetDataKulak.getInt("totalkulak")));

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(terjual) AS totalterjual FROM laporankeluar");
            resultSetDataTerjual = preparedStatement.executeQuery();
            resultSetDataTerjual.next();
            totalTerjualLabel.setText(String.valueOf(resultSetDataTerjual.getInt("totalterjual")));

            int totalBarang = resultSetDataBarang.getInt("totalbarang");
            int totalKulak = resultSetDataKulak.getInt("totalkulak");
            int totalTerjual = resultSetDataTerjual.getInt("totalterjual");


            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                    new PieChart.Data("Total Barang", totalBarang),
                    new PieChart.Data("Total Barang Kulak", totalKulak),
                    new PieChart.Data("Total Barang Terjual", totalTerjual)
            );

            pieChart.setData(pieData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void datakeuangan(){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String tanggalNow = formatter.format(now);

        try {

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(pendapatan) AS totalpendapatan FROM laporankeluar");
            resultSetPendapatan = preparedStatement.executeQuery();
            resultSetPendapatan.next();
            pendapatanLabel.setText("Rp. " + String.valueOf(resultSetPendapatan.getInt("totalpendapatan")));

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(keuntungan) AS totalkeuntungan FROM laporankeluar");
            resultSetKeuntungan = preparedStatement.executeQuery();
            resultSetKeuntungan.next();
            keuntunganLabel.setText("Rp. " + String.valueOf(resultSetKeuntungan.getInt("totalkeuntungan")));

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(pendapatan) AS pendapatanupdate FROM laporankeluar WHERE tanggal=?");
            preparedStatement.setString(1, tanggalNow);
            resultSetPendapatanUpdate = preparedStatement.executeQuery();
            resultSetPendapatanUpdate.next();
            int totalPendapatanUpdate = resultSetPendapatanUpdate.getInt("pendapatanupdate");

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(keuntungan) AS keuntunganupdate FROM laporankeluar WHERE tanggal=?");
            preparedStatement.setString(1, tanggalNow);
            resultSetKeuntunganUpdate = preparedStatement.executeQuery();
            resultSetKeuntunganUpdate.next();
            int totalKeuntunganUpdate = resultSetKeuntunganUpdate.getInt("keuntunganupdate");

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM datagrafik");
            resultSetDataBarang = preparedStatement.executeQuery();

            if (!resultSetDataBarang.isBeforeFirst()){
                preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO datagrafik(tanggal, totalPendapatan, totalKeuntungan) VALUE (?,?,?)");
                preparedStatement.setString(1, tanggalNow);
                preparedStatement.setInt(2, totalPendapatanUpdate);
                preparedStatement.setInt(3, totalKeuntunganUpdate);
                preparedStatement.execute();
            }else{

                XYChart.Series series = new XYChart.Series();
                series.setName("Total pendapatan per hari");

                while (resultSetDataBarang.next()){
                    String tanggalData = resultSetDataBarang.getString("tanggal");
                    int pendapatanGrafik = resultSetDataBarang.getInt("totalPendapatan");
                    series.getData().add(new XYChart.Data(tanggalData, pendapatanGrafik));
                }
                lineChart.getData().add(series);
            }

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM datagrafik WHERE tanggal=?");
            preparedStatement.setString(1, tanggalNow);
            resultSetGrafik = preparedStatement.executeQuery();

            if (!resultSetGrafik.isBeforeFirst()){
                preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO datagrafik(tanggal, totalPendapatan, totalKeuntungan) VALUE (?,?,?)");
                preparedStatement.setString(1, tanggalNow);
                preparedStatement.setInt(2, totalPendapatanUpdate);
                preparedStatement.setInt(3, totalKeuntunganUpdate);
                preparedStatement.execute();
            }else {
                preparedStatement = DBUtils.getConnect().prepareStatement("UPDATE datagrafik SET totalPendapatan=?, totalKeuntungan=? WHERE tanggal=?");
                preparedStatement.setInt(1, totalPendapatanUpdate);
                preparedStatement.setInt(2, totalKeuntunganUpdate);
                preparedStatement.setString(3, tanggalNow);
                preparedStatement.execute();
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
