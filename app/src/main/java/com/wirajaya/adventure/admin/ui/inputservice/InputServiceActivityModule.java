package com.wirajaya.adventure.admin.ui.inputservice;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;
import com.wirajaya.adventure.admin.data.model.Motor;
import com.wirajaya.adventure.admin.data.model.Service;
import com.wirajaya.adventure.admin.data.remote.CategoryService;
import com.wirajaya.adventure.admin.data.remote.FirebaseImageService;
import com.wirajaya.adventure.admin.data.remote.UserService;

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
