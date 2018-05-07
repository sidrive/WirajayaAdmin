package com.wirajaya.adventure.admin.data.remote.firebase;

import android.app.Application;

import com.wirajaya.adventure.admin.data.remote.FirebaseImageService;
import com.wirajaya.adventure.admin.data.remote.FirebaseUserService;
import com.wirajaya.adventure.admin.data.remote.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ikun on 28/12/17.
 */

@Module
public class FirebaseModule {
    @Provides
    @Singleton
    public FirebaseUserService provideFirebaseUserService(Application application) {
        return new FirebaseUserService(application);
    }

    @Provides
    @Singleton
    public UserService provideUserService(Application application) {
        return new UserService(application);
    }

    @Provides
    @Singleton
    public FirebaseImageService provideFirebaseImageService(Application application) {
        return new FirebaseImageService(application);
    }
}
