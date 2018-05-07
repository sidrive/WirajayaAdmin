package com.wirajaya.adventure.admin.data.editMotor;

import com.wirajaya.adventure.admin.base.annotation.MainScope;
import com.wirajaya.adventure.admin.ui.editmotor.EditMotorActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class EditMotorModule {
    EditMotorActivity activity;

    public EditMotorModule(EditMotorActivity activity){
        this.activity = activity;
    }

    @Provides
    @MainScope
    EditMotorActivity provideEditMotor(){return activity;}
}
