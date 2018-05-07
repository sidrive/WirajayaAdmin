package com.motor.service.servicemotor.base;

import com.motor.service.servicemotor.data.remote.firebase.FirebaseModule;
import com.motor.service.servicemotor.data.remote.motor.MotorComponent;
import com.motor.service.servicemotor.data.remote.motor.MotorModule;
import com.motor.service.servicemotor.data.remote.network.NetworkModule;
import com.motor.service.servicemotor.data.remote.user.UserComponent;
import com.motor.service.servicemotor.data.remote.user.UserModule;
import com.motor.service.servicemotor.ui.login.LoginActivityComponent;
import com.motor.service.servicemotor.ui.login.LoginActivityModule;
import com.motor.service.servicemotor.ui.main.MainActivityComponent;
import com.motor.service.servicemotor.ui.main.MainActivityModule;
import com.motor.service.servicemotor.ui.splash.SplashActivityComponent;
import com.motor.service.servicemotor.ui.splash.SplashActivityModule;

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
