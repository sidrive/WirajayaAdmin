package com.wirajaya.adventure.admin.ui.inputMotor;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.data.remote.CategoryService;
import com.wirajaya.adventure.admin.data.remote.FirebaseImageService;
import com.wirajaya.adventure.admin.data.remote.UserService;
import com.wirajaya.adventure.admin.data.remote.model.User;

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
    InputMotorPresenter provideInputMotorPresenter(UserService userService, User user, CategoryService categoryService, Barang barang, FirebaseImageService firebaseImageService){
        return new InputMotorPresenter(activity,userService,user,categoryService, barang,firebaseImageService);
    }
}
