package com.motor.service.servicemotor.ui.editmotor;

import com.motor.service.servicemotor.base.annotation.ActivityScope;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.remote.CategoryService;
import com.motor.service.servicemotor.data.remote.FirebaseImageService;
import com.motor.service.servicemotor.data.remote.UserService;
import com.motor.service.servicemotor.data.remote.model.User;

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
