package com.motor.service.servicemotor.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ikun on 12/01/18.
 */

public class Category {

    @NonNull
    String id;
    @Nullable
    String name;
    @NonNull
    String seri;
    @NonNull
    int icon;

    public Category(){

    }

    public Category(String id, String name,String seri, int icon) {
        this.id = id;
        this.name = name;
        this.seri = seri;
        this.icon = icon;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @NonNull
    public String getSeri() {
        return seri;
    }

    public void setSeri(@NonNull String seri) {
        this.seri = seri;
    }

    @NonNull
    public int getIcon() {
        return icon;
    }

    public void setIcon(@NonNull int icon) {
        this.icon = icon;
    }
}
