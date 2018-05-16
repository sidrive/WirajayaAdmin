package com.wirajaya.adventure.admin.data.model;

import android.support.annotation.NonNull;

/**
 * Created by ikun on 17/01/18.
 */

public class Barang {

    @NonNull
    private String idbarang;

    @NonNull
    private String namaBarang;

    @NonNull
    private String merkBarang;

    @NonNull
    private String kategoriBarang;

    @NonNull
    private String keteranganBarang;

    @NonNull
    private int stokBarang;

    @NonNull
    private int hargaBarang;

    @NonNull
    private Long updateTerakhir;

    private String Photo_url;

    //end of model barang



    public Barang(){}


    public Barang(@NonNull String idbarang, @NonNull String namaBarang, @NonNull String merkBarang, @NonNull String kategoriBarang, @NonNull String keteranganBarang, @NonNull int stokBarang, @NonNull int hargaBarang, @NonNull Long updateTerakhir, String photo_url) {
        this.idbarang = idbarang;
        this.namaBarang = namaBarang;
        this.merkBarang = merkBarang;
        this.kategoriBarang = kategoriBarang;
        this.keteranganBarang = keteranganBarang;
        this.stokBarang = stokBarang;
        this.hargaBarang = hargaBarang;
        this.updateTerakhir = updateTerakhir;
        Photo_url = photo_url;
    }



    //start of getter & setter model barang

    @NonNull
    public String getIdbarang() {
        return idbarang;
    }

    public void setIdbarang(@NonNull String idbarang) {
        this.idbarang = idbarang;
    }

    @NonNull
    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(@NonNull String namaBarang) {
        this.namaBarang = namaBarang;
    }

    @NonNull
    public String getMerkBarang() {
        return merkBarang;
    }

    public void setMerkBarang(@NonNull String merkBarang) {
        this.merkBarang = merkBarang;
    }

    @NonNull
    public String getKategoriBarang() {
        return kategoriBarang;
    }

    public void setKategoriBarang(@NonNull String kategoriBarang) {
        this.kategoriBarang = kategoriBarang;
    }

    @NonNull
    public String getKeteranganBarang() {
        return keteranganBarang;
    }

    public void setKeteranganBarang(@NonNull String keteranganBarang) {
        this.keteranganBarang = keteranganBarang;
    }

    @NonNull
    public int getStokBarang() {
        return stokBarang;
    }

    public void setStokBarang(@NonNull int stokBarang) {
        this.stokBarang = stokBarang;
    }

    @NonNull
    public int getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(@NonNull int hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    @NonNull
    public Long getUpdateTerakhir() {
        return updateTerakhir;
    }

    public void setUpdateTerakhir(@NonNull Long updateTerakhir) {
        this.updateTerakhir = updateTerakhir;
    }

    public String getPhoto_url() {
        return Photo_url;
    }

    public void setPhoto_url(String photo_url) {
        Photo_url = photo_url;
    }

    @Override
    public String toString() {
        return "Barang{" +
                "idbarang='" + idbarang + '\'' +
                ", namaBarang='" + namaBarang + '\'' +
                ", merkBarang='" + merkBarang + '\'' +
                ", kategoriBarang='" + kategoriBarang + '\'' +
                ", keteranganBarang='" + keteranganBarang + '\'' +
                ", stokBarang=" + stokBarang +
                ", hargaBarang=" + hargaBarang +
                ", updateTerakhir=" + updateTerakhir +
                ", Photo_url='" + Photo_url + '\'' +
                '}';
    }
}
