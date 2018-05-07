package com.motor.service.servicemotor.ui.inputMotor;

import com.motor.service.servicemotor.base.annotation.ActivityScope;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.remote.CategoryService;
import com.motor.service.servicemotor.data.remote.FirebaseImageService;
import com.motor.service.servicemotor.data.remote.UserService;
import com.motor.service.servicemotor.data.remote.model.User;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ikun on 11/01/18.
 */
@Module
public class InputMotorModule {
    InputMotorActivity activity;

    public InputMotorModule(InputMotorActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    InputMotorActivity provideInputMotorActiviy(){return activity;}

    @ActivityScope
    @Provides
    InputMotorPresenter provideInputMotorPresenter(UserService userService, User user, CategoryService categoryService, Motor motor, FirebaseImageService firebaseImageService){
        return new InputMotorPresenter(activity,userService,user,categoryService,motor,firebaseImageService);
    }
}
