package com.motor.service.servicemotor.ui.inputservice;

import com.motor.service.servicemotor.base.annotation.ActivityScope;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.model.Service;
import com.motor.service.servicemotor.data.remote.CategoryService;
import com.motor.service.servicemotor.data.remote.FirebaseImageService;
import com.motor.service.servicemotor.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

@Module
public class InputServiceActivityModule {
    InputServiceActivity activity;

    public InputServiceActivityModule(InputServiceActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    InputServiceActivity provideInputServiceActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    InputServicePresenter provideInputServicePresenter(UserService userService, CategoryService categoryService, Motor motor, Service service, FirebaseImageService firebaseImageService){
        return new InputServicePresenter(activity,userService,categoryService,motor,service,firebaseImageService);
    }
}
