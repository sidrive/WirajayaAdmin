package com.wirajaya.adventure.admin.ui.historyservice;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;
import com.wirajaya.adventure.admin.data.model.Motor;
import com.wirajaya.adventure.admin.data.remote.FirebaseImageService;
import com.wirajaya.adventure.admin.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

@Module
public class HistoryServiceActivityModule {
    HistoryServiceActivity activity;

    public HistoryServiceActivityModule(HistoryServiceActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    HistoryServiceActivity provideHistoryServiceActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    HistoryServicePresenter provideHistoryServicePresenter(UserService userService, Motor motor, FirebaseImageService firebaseImageService){
        return new HistoryServicePresenter(activity,userService,motor,firebaseImageService);
    }
}
