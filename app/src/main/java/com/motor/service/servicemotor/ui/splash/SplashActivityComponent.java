package com.motor.service.servicemotor.ui.splash;

import com.motor.service.servicemotor.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by ikun on 28/12/17.
 */


@ActivityScope
@Subcomponent(
        modules = {
                SplashActivityModule.class
        }
)
public interface SplashActivityComponent {
    SplashActivity inject(SplashActivity activity);
}