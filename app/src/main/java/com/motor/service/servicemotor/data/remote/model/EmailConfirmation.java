package com.motor.service.servicemotor.data.remote.model;

import android.support.annotation.NonNull;

/**
 * Created by ikun on 22/12/17.
 */

public class EmailConfirmation {
    @NonNull
    String uid;
    @NonNull
    String email;
    @NonNull
    String name;

    public EmailConfirmation(){

    }

    public EmailConfirmation(String uid, String email, String name){
        this.uid = uid;
        this.email = email;
        this.name = name;
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
