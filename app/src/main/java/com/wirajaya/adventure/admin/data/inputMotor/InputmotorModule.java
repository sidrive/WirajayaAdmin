package com.wirajaya.adventure.admin.data.inputMotor;

import com.wirajaya.adventure.admin.base.annotation.MainScope;
import com.wirajaya.adventure.admin.ui.inputBarang.InputBarangActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ikun on 11/01/18.
 */

@Module
public class InputmotorModule {
    InputBarangActivity activity;

    public InputmotorModule(InputBarangActivity activity){
        this.activity = activity;
    }

    @Provides
    @MainScope
    InputBarangActivity provideInputMotor(){
        return activity;
    }
}
