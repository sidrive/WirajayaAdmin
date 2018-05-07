package com.wirajaya.adventure.admin.data.remote.motor;

import com.wirajaya.adventure.admin.base.annotation.UserScope;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.data.model.Service;
import com.wirajaya.adventure.admin.data.remote.CategoryService;

import dagger.Module;
import dagger.Provides;

@Module
public class MotorModule {
    Barang barang;

    public MotorModule(Barang barang) {
        this.barang = barang;
    }

    @Provides
    @UserScope
    Barang provideMotor() {
        return barang;
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
//    Barang provideMotor(){
//        return new Barang();
//    }
}
