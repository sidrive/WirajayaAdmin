package com.wirajaya.adventure.admin.ui.editmotor;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;
import com.wirajaya.adventure.admin.data.model.Motor;
import com.wirajaya.adventure.admin.data.remote.CategoryService;
import com.wirajaya.adventure.admin.data.remote.FirebaseImageService;
import com.wirajaya.adventure.admin.data.remote.UserService;
import com.wirajaya.adventure.admin.data.remote.model.User;

import dagger.Module;
import dagger.Provides;

@Module
public class EditMotorActivityModule {
    EditMotorActivity activity;

    public EditMotorActivityModule(EditMotorActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    EditMotorActivity provideEditMotorActivity(){return activity;}

    @ActivityScope
    @Provides
    EditMotorPresenter provideEditMotorPresenter(UserService userService, Motor motor, FirebaseImageService firebaseImageService){
        return new EditMotorPresenter(activity,userService,motor,firebaseImageService);
    }
}