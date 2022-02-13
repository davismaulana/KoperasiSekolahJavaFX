package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ItemLaporanKeluarController {
    @FXML
    private Label bulanLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label keuntunganLabel;

    @FXML
    private Label namaBarangLabel;

    @FXML
    private Label pendapatanLabel;

    @FXML
    private Label tanggalLabel;

    @FXML
    private Label terjualLabel;


    public void dataLaporan(int id, String tanggal, String bulan,String namaBarang, int terjual, int pendapatan, int keuntungan){
        idLabel.setText(String.valueOf(id));
        tanggalLabel.setText(tanggal);
        bulanLabel.setText(bulan);
        namaBarangLabel.setText(namaBarang);
        terjualLabel.setText(String.valueOf(terjual));
        pendapatanLabel.setText(String.valueOf(pendapatan));
        keuntunganLabel.setText(String.valueOf(keuntungan));
    }
}
