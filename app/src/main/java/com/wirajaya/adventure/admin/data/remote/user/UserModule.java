package com.wirajaya.adventure.admin.data.remote.user;

import com.wirajaya.adventure.admin.base.annotation.UserScope;
import com.wirajaya.adventure.admin.data.model.Motor;
import com.wirajaya.adventure.admin.data.remote.CategoryService;
import com.wirajaya.adventure.admin.data.remote.MotorService;
import com.wirajaya.adventure.admin.data.remote.model.User;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ikun on 28/12/17.
 */


@Module
public class UserModule {

    User user;

    public UserModule(User user) {
        this.user = user;
    }

    @Provides
    @UserScope
    User provideUser() {
        return user;
    }

    @Provides
    @UserScope
    CategoryService provideCategoryService(){
        return new CategoryService();
    }

   @Provides
    @UserScope
   Motor provideMotor(){
        return new Motor();
    }

/*    @UserScope
    @Provides
    LocationService locationService(){
        return new LocationService();
    }*/

}
