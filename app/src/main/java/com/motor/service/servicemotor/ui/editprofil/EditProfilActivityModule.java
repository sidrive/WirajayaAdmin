package com.motor.service.servicemotor.ui.editprofil;

import com.motor.service.servicemotor.base.annotation.ActivityScope;
import com.motor.service.servicemotor.data.remote.FirebaseImageService;
import com.motor.service.servicemotor.data.remote.FirebaseUserService;
import com.motor.service.servicemotor.data.remote.UserService;
import com.motor.service.servicemotor.ui.login.LoginActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ikun on 29/12/17.
 */

@Module
public class EditProfilActivityModule {
    EditProfilActivity activity;

    public EditProfilActivityModule(EditProfilActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    EditProfilActivity provideEditProfilActivity(){return activity;}

    @ActivityScope
    @Provides
    EditProfilPresenter provideEditProfilPresenter(UserService userService, FirebaseUserService firebaseUserService, FirebaseImageService firebaseImageService){
        return new EditProfilPresenter(activity, userService, firebaseUserService, firebaseImageService);
    }
}
