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

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    private ImageView buyCart, totalCart, soldCart, money, money2, background;

    @FXML
    private LineChart lineChart;

    @FXML
    private Label dashboard;

    @FXML
    private PieChart pieChart;


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

        XYChart.Series series = new XYChart.Series();
        series.setName("Total pendapatan per bulan");

        series.getData().add(new XYChart.Data("Januari", 1000000));
        series.getData().add(new XYChart.Data("Februari", 2300000));
        series.getData().add(new XYChart.Data("Maret", 3000000));
        series.getData().add(new XYChart.Data("April", 5000000));
        series.getData().add(new XYChart.Data("Mei", 1000000));
        series.getData().add(new XYChart.Data("Juni", 5000000));
        series.getData().add(new XYChart.Data("Juli", 6000000));
        series.getData().add(new XYChart.Data("Agustus", 2000000));
        series.getData().add(new XYChart.Data("September", 3000000));
        series.getData().add(new XYChart.Data("Oktober", 4000000));
        series.getData().add(new XYChart.Data("November", 5000000));
        series.getData().add(new XYChart.Data("Desember", 7000000));

        lineChart.getData().add(series);

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Total Barang", 40),
                new PieChart.Data("Total Barang Kulak", 70),
                new PieChart.Data("Total Barang Terjual", 40)
        );

        pieChart.setData(pieData);
    }
//    private void dateNow(){
//        Thread thread = new Thread(()->{
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD MMMM YYYY");
//            while (!stop){
//                try {
//                    Thread.sleep(1000);
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//                final String timenow = simpleDateFormat.format(new Date());
//                Platform.runLater(()->{
//                    dashboard.setText(timenow);
//                });
//            }
//        });
//        thread.start();
//    }

}
