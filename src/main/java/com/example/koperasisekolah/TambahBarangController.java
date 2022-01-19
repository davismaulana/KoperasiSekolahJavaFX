package com.example.koperasisekolah;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TambahBarangController  {

    @FXML
    private Button simpanBtn, tutupBtn;

    public void simpanBtnOnAction (ActionEvent event){
        Stage stage = (Stage) simpanBtn.getScene().getWindow();
        stage.close();
    }

    public void tutupBtnOnAction (ActionEvent event){
        Stage stage = (Stage) tutupBtn.getScene().getWindow();
        stage.close();
    }
}
