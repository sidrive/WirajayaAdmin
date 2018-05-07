package com.motor.service.servicemotor.data.model;

import android.support.annotation.NonNull;

/**
 * Created by ikun on 17/01/18.
 */

public class Motor {

    @NonNull
    private String idmotor;

    @NonNull
    private String userid;

    @NonNull
    private String jenis_service;

    @NonNull
    private String ket_service;

    @NonNull
    private int km_NextService;

    @NonNull
    private int km_now;

    @NonNull
    private int km_ratarata;

    @NonNull
    private Boolean motor_utama;

    @NonNull
    private String photo_url;

    @NonNull
    private Long tgl_nextService;

    @NonNull
    private Long tgl_service;

    @NonNull
    private String merk;

    @NonNull
    private String type;

    @NonNull
    private String seri;

    @NonNull
    private String plat;

    @NonNull
    private String tahun_buat;

    @NonNull
    private String no_rangka;

    @NonNull
    private Long tahun_pajak;

    public Motor(){}


    public Motor(@NonNull String idmotor, @NonNull String userid, @NonNull String jenis_service, @NonNull String ket_service, @NonNull int km_NextService, @NonNull int km_now, @NonNull int km_ratarata, @NonNull Boolean motor_utama, @NonNull String photo_url, @NonNull Long tgl_nextService, @NonNull Long tgl_service, @NonNull String merk, @NonNull String type, @NonNull String seri, @NonNull String plat, @NonNull String tahun_buat, @NonNull String no_rangka, @NonNull Long tahun_pajak) {
        this.idmotor = idmotor;
        this.userid = userid;
        this.jenis_service = jenis_service;
        this.ket_service = ket_service;
        this.km_NextService = km_NextService;
        this.km_now = km_now;
        this.km_ratarata = km_ratarata;
        this.motor_utama = motor_utama;
        this.photo_url = photo_url;
        this.tgl_nextService = tgl_nextService;
        this.tgl_service = tgl_service;
        this.merk = merk;
        this.type = type;
        this.seri = seri;
        this.plat = plat;
        this.tahun_buat = tahun_buat;
        this.no_rangka = no_rangka;
        this.tahun_pajak = tahun_pajak;
    }

    @NonNull
    public String getMerk() {
        return merk;
    }

    public void setMerk(@NonNull String merk) {
        this.merk = merk;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public String getSeri() {
        return seri;
    }

    public void setSeri(@NonNull String seri) {
        this.seri = seri;
    }

    @NonNull
    public String getPlat() {
        return plat;
    }

    public void setPlat(@NonNull String plat) {
        this.plat = plat;
    }

    @NonNull
    public String getTahun_buat() {
        return tahun_buat;
    }

    public void setTahun_buat(@NonNull String tahun_buat) {
        this.tahun_buat = tahun_buat;
    }

    @NonNull
    public String getNo_rangka() {
        return no_rangka;
    }

    public void setNo_rangka(@NonNull String no_rangka) {
        this.no_rangka = no_rangka;
    }

    @NonNull
    public String getIdmotor() {
        return idmotor;
    }

    public void setIdmotor(@NonNull String idmotor) {
        this.idmotor = idmotor;
    }

    @NonNull
    public String getUserid() {
        return userid;
    }

    public void setUserid(@NonNull String userid) {
        this.userid = userid;
    }

    @NonNull
    public Long getTahun_pajak() {
        return tahun_pajak;
    }

    public void setTahun_pajak(@NonNull Long tahun_pajak) {
        this.tahun_pajak = tahun_pajak;
    }

    @NonNull
    public String getJenis_service() {
        return jenis_service;
    }

    public void setJenis_service(@NonNull String jenis_service) {
        this.jenis_service = jenis_service;
    }

    @NonNull
    public String getKet_service() {
        return ket_service;
    }

    public void setKet_service(@NonNull String ket_service) {
        this.ket_service = ket_service;
    }

    @NonNull
    public int getKm_NextService() {
        return km_NextService;
    }

    public void setKm_NextService(@NonNull int km_NextService) {
        this.km_NextService = km_NextService;
    }

    @NonNull
    public int getKm_now() {
        return km_now;
    }

    public void setKm_now(@NonNull int km_now) {
        this.km_now = km_now;
    }

    @NonNull
    public int getKm_ratarata() {
        return km_ratarata;
    }

    public void setKm_ratarata(@NonNull int km_ratarata) {
        this.km_ratarata = km_ratarata;
    }

    @NonNull
    public Boolean getMotor_utama() {
        return motor_utama;
    }

    public void setMotor_utama(@NonNull Boolean motor_utama) {
        this.motor_utama = motor_utama;
    }

    @NonNull
    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(@NonNull String photo_url) {
        this.photo_url = photo_url;
    }

    @NonNull
    public Long getTgl_nextService() {
        return tgl_nextService;
    }

    public void setTgl_nextService(@NonNull Long tgl_nextService) {
        this.tgl_nextService = tgl_nextService;
    }

    @NonNull
    public Long getTgl_service() {
        return tgl_service;
    }

    public void setTgl_service(@NonNull Long tgl_service) {
        this.tgl_service = tgl_service;
    }

    @Override
    public String toString() {
        return "Motor{" +
                "idmotor='" + idmotor + '\'' +
                ", userid='" + userid + '\'' +
                ", jenis_service='" + jenis_service + '\'' +
                ", ket_service='" + ket_service + '\'' +
                ", km_NextService=" + km_NextService +
                ", km_now=" + km_now +
                ", km_ratarata=" + km_ratarata +
                ", motor_utama=" + motor_utama +
                ", photo_url='" + photo_url + '\'' +
                ", tgl_nextService=" + tgl_nextService +
                ", tgl_service=" + tgl_service +
                ", merk='" + merk + '\'' +
                ", type='" + type + '\'' +
                ", seri='" + seri + '\'' +
                ", plat='" + plat + '\'' +
                ", tahun_buat='" + tahun_buat + '\'' +
                ", no_rangka='" + no_rangka + '\'' +
                ", tahun_pajak='" + tahun_pajak + '\'' +
                '}';
    }
}
