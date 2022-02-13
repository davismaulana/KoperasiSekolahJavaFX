package com.example.koperasisekolah;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ItemLaporanMasukController {
    @FXML
    private Label bulanLabel;

    @FXML
    private Label hargaLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label jumlahKulakLabel;

    @FXML
    private Label kulakLabel;

    @FXML
    private Label namaBarangLabel;

    @FXML
    private Label pengeluaranLabel;

    @FXML
    private Label tanggalLabel;

    public void dataLaporan(int id, String tanggal, String bulan,String namaBarang, int harga, int kulak, int jumlahKulak, int pengeluaran){
        idLabel.setText(String.valueOf(id));
        tanggalLabel.setText(tanggal);
        bulanLabel.setText(bulan);
        namaBarangLabel.setText(namaBarang);
        hargaLabel.setText(String.valueOf(harga));
        kulakLabel.setText(String.valueOf(kulak));
        jumlahKulakLabel.setText(String.valueOf(jumlahKulak));
        pengeluaranLabel.setText(String.valueOf(pengeluaran));
    }
}
