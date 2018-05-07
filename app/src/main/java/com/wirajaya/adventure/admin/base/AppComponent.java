package com.wirajaya.adventure.admin.base;

import com.wirajaya.adventure.admin.data.remote.firebase.FirebaseModule;
import com.wirajaya.adventure.admin.data.remote.motor.MotorComponent;
import com.wirajaya.adventure.admin.data.remote.motor.MotorModule;
import com.wirajaya.adventure.admin.data.remote.network.NetworkModule;
import com.wirajaya.adventure.admin.data.remote.user.UserComponent;
import com.wirajaya.adventure.admin.data.remote.user.UserModule;
import com.wirajaya.adventure.admin.ui.login.LoginActivityComponent;
import com.wirajaya.adventure.admin.ui.login.LoginActivityModule;
import com.wirajaya.adventure.admin.ui.main.MainActivityComponent;
import com.wirajaya.adventure.admin.ui.main.MainActivityModule;
import com.wirajaya.adventure.admin.ui.splash.SplashActivityComponent;
import com.wirajaya.adventure.admin.ui.splash.SplashActivityModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by ikun on 28/12/17.
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                FirebaseModule.class,
                NetworkModule.class
        }
)

public interface AppComponent {

        SplashActivityComponent plus(SplashActivityModule activityModule);

        LoginActivityComponent plus(LoginActivityModule activityModule);

//        MainActivityComponent plus(MainActivityModule activityModule);

        UserComponent plus(UserModule userModule);
        MotorComponent plus(MotorModule motorModule);

        Retrofit retrofit();
}
