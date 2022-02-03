package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
    private ImageView tambahLogo, background, refreshLogo;

    @FXML
    private TextField hargaField, jumlahField, kulakField, namaBarangField;

    @FXML
    private AnchorPane formPane;

    @FXML
    private Button simpanBtn, tutupBtn, tambahBarang, refresh;


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

        File refreshFile = new File("image/icons8-available-updates-96.png");
        Image refreshImage = new Image(refreshFile.toURI().toString());
        refreshLogo.setImage(refreshImage);

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

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO databarang(namaBarang, stok, harga, kulak, tanggal) VALUES(?,?,?,?,?)");
                preparedStatement.setString(1,namaBarang);
                preparedStatement.setInt(2, jumlah);
                preparedStatement.setInt(3,harga);
                preparedStatement.setInt(4,hargaKulak);
                preparedStatement.setString(5,tanggal);
                preparedStatement.executeUpdate();

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
        });

        tutupBtn.setOnMouseClicked(event -> {
            formPane.setVisible(false);
        });

        refresh.setOnMouseClicked(event -> {
            panelBarang.getChildren().clear();
            showData();
        });

    }

    public void showData(){
        Parent root = null;
        try {
            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM databarang");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("ItemBarang.fxml"));
                root = (Parent) loader.load();
                ItemBarangController itemBarangController = loader.getController();
                itemBarangController.setDataBarang(resultSet.getInt("id"), resultSet.getString("namaBarang"), resultSet.getInt("stok"), resultSet.getInt("harga"), resultSet.getInt("kulak"), resultSet.getString("tanggal"));
                panelBarang.getChildren().add(root);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
