package com.wirajaya.adventure.admin.ui.editbarang;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.data.remote.FirebaseImageService;
import com.wirajaya.adventure.admin.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

@Module
public class EditBarangActivityModule {
    EditBarangActivity activity;

    public EditBarangActivityModule(EditBarangActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    EditBarangActivity provideEditMotorActivity(){return activity;}

    @ActivityScope
    @Provides
    EditBarangPresenter provideEditMotorPresenter(UserService userService, Barang barang, FirebaseImageService firebaseImageService){
        return new EditBarangPresenter(activity,userService, barang,firebaseImageService);
    }
}
