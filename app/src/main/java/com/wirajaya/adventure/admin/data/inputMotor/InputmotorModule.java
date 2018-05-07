package com.wirajaya.adventure.admin.data.inputMotor;

import com.wirajaya.adventure.admin.base.annotation.MainScope;
import com.wirajaya.adventure.admin.ui.inputMotor.InputMotorActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ikun on 11/01/18.
 */

@Module
public class InputmotorModule {
    InputMotorActivity activity;

    public InputmotorModule(InputMotorActivity activity){
        this.activity = activity;
    }

    @Provides
    @MainScope
    InputMotorActivity provideInputMotor(){
        return activity;
    }
}
