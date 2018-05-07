package com.motor.service.servicemotor.data.model;

import android.support.annotation.NonNull;

public class Service {

    @NonNull
    private String idmotor;

    @NonNull
    private String idservice;

    @NonNull
    private String jenisService;

    @NonNull
    private String keterangan;

    @NonNull
    private int kmservice;

    @NonNull
    private String photo_url;

    @NonNull
    private Long tglService;

    public Service(){}

    public Service(@NonNull String idmotor, @NonNull String idservice, @NonNull String jenisService, @NonNull String keterangan, @NonNull int kmservice, @NonNull String photo_url, @NonNull Long tglService) {
        this.idmotor = idmotor;
        this.idservice = idservice;
        this.jenisService = jenisService;
        this.keterangan = keterangan;
        this.kmservice = kmservice;
        this.photo_url = photo_url;
        this.tglService = tglService;
    }

    @NonNull
    public String getIdmotor() {
        return idmotor;
    }

    public void setIdmotor(@NonNull String idmotor) {
        this.idmotor = idmotor;
    }

    @NonNull
    public String getIdservice() {
        return idservice;
    }

    public void setIdservice(@NonNull String idservice) {
        this.idservice = idservice;
    }

    @NonNull
    public String getJenisService() {
        return jenisService;
    }

    public void setJenisService(@NonNull String jenisService) {
        this.jenisService = jenisService;
    }

    @NonNull
    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(@NonNull String keterangan) {
        this.keterangan = keterangan;
    }

    @NonNull
    public int getKmservice() {
        return kmservice;
    }

    public void setKmservice(@NonNull int kmservice) {
        this.kmservice = kmservice;
    }

    @NonNull
    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(@NonNull String photo_url) {
        this.photo_url = photo_url;
    }

    @NonNull
    public Long getTglService() {
        return tglService;
    }

    public void setTglService(@NonNull Long tglService) {
        this.tglService = tglService;
    }

    @Override
    public String toString() {
        return "Service{" +
                "idmotor='" + idmotor + '\'' +
                ", idservice='" + idservice + '\'' +
                ", jenisService='" + jenisService + '\'' +
                ", keterangan='" + keterangan + '\'' +
                ", kmservice=" + kmservice +
                ", photo_url='" + photo_url + '\'' +
                ", tglService=" + tglService +
                '}';
    }
}
