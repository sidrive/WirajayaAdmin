package com.wirajaya.adventure.admin.data.editMotor;

import com.wirajaya.adventure.admin.base.annotation.MainScope;
import com.wirajaya.adventure.admin.ui.editbarang.EditBarangActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class EditMotorModule {
    EditBarangActivity activity;

    public EditMotorModule(EditBarangActivity activity){
        this.activity = activity;
    }

    @Provides
    @MainScope
    EditBarangActivity provideEditMotor(){return activity;}
}
