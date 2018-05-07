package com.motor.service.servicemotor.ui.historyservice;

import com.motor.service.servicemotor.base.annotation.ActivityScope;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.remote.FirebaseImageService;
import com.motor.service.servicemotor.data.remote.UserService;

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
