package com.example.koperasisekolah;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KoperasiController implements Initializable {

    @FXML
    private ImageView menu, dashboard, dataBarang, logout, laporan, transaksi, close;

    @FXML
    private AnchorPane pane1, pane2;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button logout_btn, closeBtn;

    @FXML
    private Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        File menuFile = new File("image/icons8-menu-96.png");
        Image menuImage = new Image(menuFile.toURI().toString());
        menu.setImage(menuImage);

        File closeFile = new File("image/icons8-close-96.png");
        Image closeImage = new Image(closeFile.toURI().toString());
        close.setImage(closeImage);

        File dashboardFile = new File("image/icons8-dashboard-layout-96.png");
        Image dashboardImage = new Image(dashboardFile.toURI().toString());
        dashboard.setImage(dashboardImage);

        File dataBarangFile = new File("image/icons8-new-product-96.png");
        Image dataBarangImage = new Image(dataBarangFile.toURI().toString());
        dataBarang.setImage(dataBarangImage);

        File laporanFile = new File("image/icons8-note-64.png");
        Image laporanImage = new Image(laporanFile.toURI().toString());
        laporan.setImage(laporanImage);

        File transaksiFile = new File("image/icons8-calculator-100.png");
        Image transaksiImage = new Image(transaksiFile.toURI().toString());
        transaksi.setImage(transaksiImage);

        File logoutFile = new File("image/icons8-logout-96.png");
        Image logoutImage = new Image(logoutFile.toURI().toString());
        logout.setImage(logoutImage);

        pane1.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5),pane2);
        translateTransition.setByX(-600);
        translateTransition.play();


//        menu.setOnMouseClicked(event -> {
//            pane1.setVisible(true);
//
//            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
//            fadeTransition1.setFromValue(1);
//            fadeTransition1.setToValue(0.15);
//            fadeTransition1.play();
//
//            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5),pane2);
//            translateTransition1.setByX(+600);
//            translateTransition1.play();
//        });

        pane1.setOnMouseClicked(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });

        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException e) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null, e);
        }

        logout_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "login.fxml", null);
            }
        });
    }

    public void closeActionBtn(ActionEvent event){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void showMenu(ActionEvent event) {
        pane1.setVisible(true);

        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition1.setFromValue(1);
        fadeTransition1.setToValue(0.15);
        fadeTransition1.play();

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5),pane2);
        translateTransition1.setByX(+600);
        translateTransition1.play();
    }

    public void dashboardPane(ActionEvent event) throws Exception{
        Parent fxml = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void laporanPane(ActionEvent event) throws Exception{
        Parent fxml = FXMLLoader.load(getClass().getResource("Laporan.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void dataBarangPane(ActionEvent event) throws Exception{
        Parent fxml = FXMLLoader.load(getClass().getResource("DataBarang.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void transaksiPane(ActionEvent event) throws Exception{
        Parent fxml = FXMLLoader.load(getClass().getResource("Transaksi.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void setUserInformation(String username){
        usernameLabel.setText(username);
    }

}

