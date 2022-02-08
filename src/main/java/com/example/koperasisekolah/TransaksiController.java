package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TransaksiController implements Initializable {

    @FXML
    private VBox panelTransaksi, panelDataTransaksi;

    @FXML
    private ImageView backgroundTransaksi, searchLogo, refreshImg;

    @FXML
    private Button searchBtn, tambahBtn, bayarBtn, refreshBtn;

    @FXML
    private TextField searchField, jumlahField, bayarField, totalHargaField;

    @FXML
    public Label stokLabel, alertBayarLabel, kembalianLabel, alertStokLabel;


    PreparedStatement preparedStatement;
    ResultSet resultSetBarang;
    ResultSet resultSetInformation;
    ResultSet resultSetTransaksi;
    ResultSet resultSetSum;
    ResultSet resultSetHapus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File backgroundFile = new File("image/background_transaksi.png");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        backgroundTransaksi.setImage(backgroundImage);

        File searchFile = new File("image/icons8-search-100.png");
        Image searchImage = new Image(searchFile.toURI().toString());
        searchLogo.setImage(searchImage);

        File refreshFile = new File("image/icons8-available-updates-96.png");
        Image refreshImage = new Image(refreshFile.toURI().toString());
        refreshImg.setImage(refreshImage);

        searchBtn.setOnMouseClicked(event -> {
            panelTransaksi.getChildren().clear();
            String searchKey =  searchField.getText();
            jumlahField.setText("");
            stokLabel.setText("");

            try {
                preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM databarang WHERE namaBarang LIKE ?");
                preparedStatement.setString(1, "%" + searchKey + "%");
                resultSetBarang = preparedStatement.executeQuery();

                while (resultSetBarang.next()){

                    String namaBarang = resultSetBarang.getString("namaBarang");
                    int harga = resultSetBarang.getInt("harga");
                    int kulak = resultSetBarang.getInt("kulak");
                    int jumlahKulak = resultSetBarang.getInt("stok");

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
                    String tanggal = formatter.format(now);

                    LocalDateTime now1 = LocalDateTime.now();
                    DateTimeFormatter bulanformatter = DateTimeFormatter.ofPattern("MMMM");
                    String bulan = bulanformatter.format(now1);

                    FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("ItemTransaksi.fxml"));
                    Parent root = (Parent) loader.load();
                    ItemTransaksiController itemTransaksiController = loader.getController();
                    itemTransaksiController.searchData(resultSetBarang.getString("namaBarang"));
                    panelTransaksi.getChildren().add(root);
                    int idBarang = resultSetBarang.getInt("id");

                    itemTransaksiController.dataButton.setOnMouseClicked(event1 -> {
                        stokLabel.setText(String.valueOf(jumlahKulak));
                        panelTransaksi.getChildren().clear();
                        jumlahField.setText("");

                        try {
                            FXMLLoader loader1 = new FXMLLoader(DBUtils.class.getResource("ItemTransaksi.fxml"));
                            Parent root1 = null;
                            root1 = (Parent) loader1.load();
                            ItemTransaksiController itemTransaksiController1 = loader1.getController();
                            itemTransaksiController1.searchData(namaBarang);
                            panelTransaksi.getChildren().add(root1);

                            tambahBtn.setOnMouseClicked(event2 -> {
                                int jumlah = Integer.parseInt(jumlahField.getText());
                                int pendapatan = jumlah * harga;
                                int jumlahKeuntungan = jumlah * kulak;
                                int keuntungan = pendapatan - jumlahKeuntungan;

                                totalHargaField.setText("");
                                bayarField.setText("");
                                kembalianLabel.setText("");

                                try {
                                    preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM information WHERE namaBarang=?");
                                    preparedStatement.setString(1, namaBarang);
                                    resultSetInformation = preparedStatement.executeQuery();

                                    resultSetInformation.next();

                                    int totalTerjual = resultSetInformation.getInt("totalTerjual");
                                    int totalPendapatan = resultSetInformation.getInt("totalPendapatan");
                                    int totalKeuntungan = resultSetInformation.getInt("totalKeuntungan");

                                    if (jumlahKulak >= jumlah){

                                        preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO preparetransaksi(namaBarang, jumlah, harga, kulak, total) VALUES (?,?,?,?,?)");
                                        preparedStatement.setString(1, namaBarang);
                                        preparedStatement.setInt(2, jumlah);
                                        preparedStatement.setInt(3, harga);
                                        preparedStatement.setInt(4, kulak);
                                        preparedStatement.setInt(5, jumlah * harga);
                                        preparedStatement.execute();

                                        preparedStatement = DBUtils.getConnect().prepareStatement("UPDATE databarang SET stok=? WHERE id=?");
                                        preparedStatement.setInt(1, jumlahKulak - jumlah);
                                        preparedStatement.setInt(2, idBarang);
                                        preparedStatement.executeUpdate();

                                        preparedStatement = DBUtils.getConnect().prepareStatement("UPDATE information SET totalBarang=?, totalTerjual=?, totalPendapatan=?, totalKeuntungan=?, tanggal=?, bulan=? WHERE namaBarang=?");
                                        preparedStatement.setInt(1, jumlahKulak - jumlah);
                                        preparedStatement.setInt(2, totalTerjual + jumlah);
                                        preparedStatement.setInt(3, totalPendapatan + pendapatan);
                                        preparedStatement.setInt(4, totalKeuntungan + keuntungan);
                                        preparedStatement.setString(5, tanggal);
                                        preparedStatement.setString(6, bulan);
                                        preparedStatement.setString(7, namaBarang);
                                        preparedStatement.executeUpdate();

                                        preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(total) AS totalharga FROM preparetransaksi");
                                        resultSetSum = preparedStatement.executeQuery();

                                        resultSetSum.next();
                                        totalHargaField.setText(String.valueOf(resultSetSum.getInt("totalharga")));

                                        panelDataTransaksi.getChildren().clear();
                                        showDataTransaksi();

                                    }else {
                                        alertStokLabel.setText("Jumlah stok tidak cukup");
                                    }
                                }catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


//    public void totalBayar(){
//
//        try {
//            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM preparetransaksi");
//            resultSetAll = preparedStatement.executeQuery();
//
//            while (resultSetAll.next()){
//                while (resultSetSum.next()){
//                    int harga = resultSetAll.getInt("harga");
//                    int total = resultSetSum.getInt("totalharga");
//                    totalHargaField.setText(String.valueOf(total));
//                }
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void showDataTransaksi(){
        try {
            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM preparetransaksi");
            resultSetTransaksi = preparedStatement.executeQuery();

            while (resultSetTransaksi.next()){
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("ItemDataTransaksi.fxml"));
                Parent root = (Parent) loader.load();
                ItemDataTransaksiController itemDataTransaksiController = loader.getController();
                itemDataTransaksiController.setDataTransaksi(resultSetTransaksi.getString("namaBarang"), resultSetTransaksi.getInt("jumlah"), resultSetTransaksi.getInt("harga"), resultSetTransaksi.getInt("total"));
                panelDataTransaksi.getChildren().add(root);

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
                String tanggal = formatter.format(now);

                LocalDateTime now1 = LocalDateTime.now();
                DateTimeFormatter bulanformatter = DateTimeFormatter.ofPattern("MMMM");
                String bulan = bulanformatter.format(now1);

                int harga = resultSetTransaksi.getInt("harga");
                int idBarang = resultSetTransaksi.getInt("id");
                String namaBarang = resultSetTransaksi.getString("namaBarang");
                int kulak = resultSetTransaksi.getInt("kulak");
                int jumlah = resultSetTransaksi.getInt("jumlah");
                int pendapatan = jumlah * harga;
                int jumlahKeuntungan = jumlah * kulak;
                int keuntungan = pendapatan - jumlahKeuntungan;

                itemDataTransaksiController.hapusBtn.setOnMouseClicked(event -> {

                    try {
                        preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM information WHERE namaBarang=?");
                        preparedStatement.setString(1, namaBarang);
                        resultSetInformation = preparedStatement.executeQuery();
                        resultSetInformation.next();

                        int totalBarang = resultSetInformation.getInt("totalBarang");


                        int totalTerjual = resultSetInformation.getInt("totalTerjual");
                        int totalPendapatan = resultSetInformation.getInt("totalPendapatan");
                        int totalKeuntungan = resultSetInformation.getInt("totalKeuntungan");

                        preparedStatement = DBUtils.getConnect().prepareStatement("DELETE FROM preparetransaksi WHERE id= ?");
                        preparedStatement.setInt(1, idBarang);
                        preparedStatement.execute();

                        preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(total) AS totalharga FROM preparetransaksi");
                        resultSetSum = preparedStatement.executeQuery();
                        resultSetSum.next();
                        totalHargaField.setText(String.valueOf(resultSetSum.getInt("totalharga")));


                        preparedStatement = DBUtils.getConnect().prepareStatement("UPDATE databarang SET stok=? WHERE namaBarang=?");
                        preparedStatement.setInt(1, totalBarang + jumlah);
                        preparedStatement.setString(2, namaBarang);
                        preparedStatement.executeUpdate();

                        preparedStatement = DBUtils.getConnect().prepareStatement("UPDATE information SET totalBarang=?, totalTerjual=?, totalPendapatan=?, totalKeuntungan=?, tanggal=?, bulan=? WHERE namaBarang=?");
                        preparedStatement.setInt(1, totalBarang + jumlah);
                        preparedStatement.setInt(2, totalTerjual - jumlah);
                        preparedStatement.setInt(3, totalPendapatan - pendapatan);
                        preparedStatement.setInt(4, totalKeuntungan - keuntungan);
                        preparedStatement.setString(5, tanggal);
                        preparedStatement.setString(6, bulan);
                        preparedStatement.setString(7, namaBarang);
                        preparedStatement.executeUpdate();

                        panelDataTransaksi.getChildren().clear();
                        showDataTransaksi();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                });

                bayarBtn.setOnMouseClicked(event2 -> {

                    try {
                        preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(total) AS totalharga FROM preparetransaksi");
                        resultSetSum = preparedStatement.executeQuery();

                        resultSetSum.next();

                        int totalHarga = resultSetSum.getInt("totalharga");
                        int bayar = Integer.parseInt(bayarField.getText());

                        if (totalHarga > bayar){
                            alertBayarLabel.setText("");
                            alertBayarLabel.setText("Pembayaran tidak cukup");
                        }else{
                            alertBayarLabel.setText("");
                            int kembalian = bayar - totalHarga;

                            kembalianLabel.setText(String.valueOf(kembalian));

                            preparedStatement = DBUtils.getConnect().prepareStatement("TRUNCATE TABLE preparetransaksi");
                            preparedStatement.execute();

                        }
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                refreshBtn.setOnMouseClicked(event -> {

                    totalHargaField.setText("");
                    kembalianLabel.setText("");
                    bayarField.setText("");
                    jumlahField.setText("");
                    stokLabel.setText("");
                    panelDataTransaksi.getChildren().clear();
                    panelTransaksi.getChildren().clear();

                });

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
