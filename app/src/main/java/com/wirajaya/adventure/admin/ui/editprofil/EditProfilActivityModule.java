package com.wirajaya.adventure.admin.ui.editprofil;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;
import com.wirajaya.adventure.admin.data.remote.FirebaseImageService;
import com.wirajaya.adventure.admin.data.remote.FirebaseUserService;
import com.wirajaya.adventure.admin.data.remote.UserService;
import com.wirajaya.adventure.admin.ui.login.LoginActivity;

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
