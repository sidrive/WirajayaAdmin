package com.motor.service.servicemotor.data.remote.motor;

import com.motor.service.servicemotor.base.annotation.UserScope;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.model.Service;
import com.motor.service.servicemotor.data.remote.CategoryService;

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
