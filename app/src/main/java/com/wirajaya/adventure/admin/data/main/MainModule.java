package com.wirajaya.adventure.admin.data.main;

import com.wirajaya.adventure.admin.base.annotation.MainScope;
import com.wirajaya.adventure.admin.ui.main.MainAct;

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
