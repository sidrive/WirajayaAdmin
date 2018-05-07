package com.wirajaya.adventure.admin.data.remote.motor;

import com.wirajaya.adventure.admin.base.annotation.UserScope;
import com.wirajaya.adventure.admin.data.model.Motor;
import com.wirajaya.adventure.admin.data.model.Service;
import com.wirajaya.adventure.admin.data.remote.CategoryService;

import dagger.Module;
import dagger.Provides;

@Module
public class MotorModule {
    Motor motor;

    public MotorModule(Motor motor) {
        this.motor = motor;
    }

    @Provides
    @UserScope
    Motor provideMotor() {
        return motor;
    }

    @Provides
    @UserScope
    Service provideService(){
        return new Service();
    }

    @Provides
    @UserScope
    CategoryService provideCategoryService(){
        return new CategoryService();
    }

//
//
//    @Provides
//    @UserScope
//    Motor provideMotor(){
//        return new Motor();
//    }
}
