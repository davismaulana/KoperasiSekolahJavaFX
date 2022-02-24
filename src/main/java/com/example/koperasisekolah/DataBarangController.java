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
import javafx.scene.layout.AnchorPane;
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

public class DataBarangController implements Initializable {

    @FXML
    public VBox panelBarang = null;

    @FXML
    private ImageView tambahLogo, background;

    @FXML
    private TextField hargaField, jumlahField, kulakField, namaBarangField;

    @FXML
    private AnchorPane formPane;

    @FXML
    private Button simpanBtn, tutupBtn, tambahBarang;

    @FXML
    private Label judulLabel;


    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File tambahLogoFile = new File("image/icons8-add-48.png");
        Image tambahLogoImage = new Image(tambahLogoFile.toURI().toString());
        tambahLogo.setImage(tambahLogoImage);

        File backgroundFile = new File("image/background_dataBarang.png");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        background.setImage(backgroundImage);

        showData();

        formPane.setVisible(false);

        simpanBtn.setOnMouseClicked(event -> {

            String namaBarang = namaBarangField.getText();
            int jumlah = Integer.parseInt(jumlahField.getText());
            int harga = Integer.parseInt(hargaField.getText());
            int hargaKulak = Integer.parseInt(kulakField.getText());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
            String tanggal = formatter.format(now);

            int pengeluaran = hargaKulak * jumlah;

            LocalDateTime now1 = LocalDateTime.now();
            DateTimeFormatter bulanformatter = DateTimeFormatter.ofPattern("MMMM");
            String bulan = bulanformatter.format(now1);

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO databarang(namaBarang, stok, harga, kulak, totalKulak, tanggal) VALUES(?,?,?,?,?,?)");
                preparedStatement.setString(1,namaBarang);
                preparedStatement.setInt(2, jumlah);
                preparedStatement.setInt(3,harga);
                preparedStatement.setInt(4,hargaKulak);
                preparedStatement.setInt(5, jumlah);
                preparedStatement.setString(6,tanggal);
                preparedStatement.execute();

                preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO laporanmasuk(tanggal, bulan, namaBarang, harga, kulak, jumlahKulak, pengeluaran) VALUES(?,?,?,?,?,?,?)");
                preparedStatement.setString(1,tanggal);
                preparedStatement.setString(2,bulan);
                preparedStatement.setString(3, namaBarang);
                preparedStatement.setInt(4,harga);
                preparedStatement.setInt(5,hargaKulak);
                preparedStatement.setInt(6,jumlah);
                preparedStatement.setInt(7,pengeluaran);
                preparedStatement.execute();

                panelBarang.getChildren().clear();

                showData();

                formPane.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            namaBarangField.setText("");
            jumlahField.setText("");
            kulakField.setText("");
            hargaField.setText("");

        });

        tambahBarang.setOnMouseClicked(event -> {
            formPane.setVisible(true);
            judulLabel.setText("Tambah Data Barang");
        });

        tutupBtn.setOnMouseClicked(event -> {
            formPane.setVisible(false);
        });

    }

    public void showData() {
        Parent root = null;
        try {
            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM databarang");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("ItemBarang.fxml"));
                root = (Parent) loader.load();
                ItemBarangController itemBarangController = loader.getController();
                itemBarangController.setDataBarang(resultSet.getInt("id"), resultSet.getString("namaBarang"), resultSet.getInt("stok"), resultSet.getInt("harga"), resultSet.getInt("kulak"), resultSet.getString("tanggal"));
                panelBarang.getChildren().add(root);

                String idBarang = String.valueOf(resultSet.getInt("id"));
                String namaBarangData = resultSet.getString("namaBarang");
                String stokData = String.valueOf(resultSet.getInt("stok"));
                String hargaData = String.valueOf(resultSet.getInt("harga"));
                String hargaKulakData = String.valueOf(resultSet.getInt("kulak"));

                itemBarangController.deleteBtn.setOnMouseClicked(event -> {
                    try {
                        preparedStatement = DBUtils.getConnect().prepareStatement("DELETE FROM databarang WHERE id= ?");
                        preparedStatement.setString(1, idBarang);
                        preparedStatement.execute();


                        panelBarang.getChildren().clear();
                        showData();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                itemBarangController.editBtn.setOnMouseClicked(event -> {
                    formPane.setVisible(true);
                    judulLabel.setText("Ubah Data Barang");
                    namaBarangField.setText(namaBarangData);
                    jumlahField.setText(stokData);
                    hargaField.setText(hargaData);
                    kulakField.setText(hargaKulakData);

                    simpanBtn.setOnMouseClicked(event1 -> {
                        String namaBarang = namaBarangField.getText();
                        int jumlah = Integer.parseInt(jumlahField.getText());
                        int harga = Integer.parseInt(hargaField.getText());
                        int hargaKulak = Integer.parseInt(kulakField.getText());
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
                        String tanggal = formatter.format(now);

                        LocalDateTime now1 = LocalDateTime.now();
                        DateTimeFormatter bulanformatter = DateTimeFormatter.ofPattern("MMMM");
                        String bulan = bulanformatter.format(now1);

                        try {
                            preparedStatement = DBUtils.getConnect().prepareStatement("UPDATE databarang SET namaBarang= ?, stok= ?, harga= ?, kulak= ?, tanggal= ? WHERE id= ?");
                            preparedStatement.setString(1, namaBarang);
                            preparedStatement.setInt(2, jumlah);
                            preparedStatement.setInt(3, harga);
                            preparedStatement.setInt(4, hargaKulak);
                            preparedStatement.setString(5, tanggal);
                            preparedStatement.setInt(6, Integer.parseInt(idBarang));
                            preparedStatement.executeUpdate();


                            panelBarang.getChildren().clear();
                            showData();

                            formPane.setVisible(false);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                });
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
