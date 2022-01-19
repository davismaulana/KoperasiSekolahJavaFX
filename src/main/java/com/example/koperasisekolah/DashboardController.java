package com.example.koperasisekolah;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Label time, date, usernameLabel;
    private volatile boolean stop = false;

    @FXML
    private ImageView buyCart, totalCart, soldCart;

    @FXML
    private LineChart lineChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Timenow();
        Day();

        File buyCartFile = new File("image/icons8-buy-96.png");
        Image buyCartImage = new Image(buyCartFile.toURI().toString());
        buyCart.setImage(buyCartImage);

        File totalCartFile = new File("image/icons8-buying-96.png");
        Image totalCartImage = new Image(totalCartFile.toURI().toString());
        totalCart.setImage(totalCartImage);

        File soldCartFile = new File("image/icons8-return-purchase-96.png");
        Image soldCartImage = new Image(soldCartFile.toURI().toString());
        soldCart.setImage(soldCartImage);

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

    }

    private void Timenow(){
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            while (!stop){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(()->{
                    time.setText(timenow);
                });
            }
        });
        thread.start();
    }

    private void Day(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        String datenow = sdf.format(new Date());
        date.setText(datenow);
    }

    public void setUserInformation(String username){
        usernameLabel.setText(username);
    }
}
