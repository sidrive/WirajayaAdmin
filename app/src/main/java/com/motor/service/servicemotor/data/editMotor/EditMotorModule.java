package com.motor.service.servicemotor.data.editMotor;

import com.motor.service.servicemotor.base.annotation.MainScope;
import com.motor.service.servicemotor.ui.editmotor.EditMotorActivity;

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
