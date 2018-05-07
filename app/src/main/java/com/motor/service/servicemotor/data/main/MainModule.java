package com.motor.service.servicemotor.data.main;

import com.motor.service.servicemotor.base.annotation.MainScope;
import com.motor.service.servicemotor.ui.main.MainAct;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ikun on 05/01/18.
 */

@Module
public class MainModule {
    MainAct activity;

    public MainModule(MainAct activity){
        this.activity = activity;
    }

    @Provides
    @MainScope
    MainAct provideMainAct(){
        return activity;
    }
}
