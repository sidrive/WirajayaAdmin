package com.motor.service.servicemotor.data.main;

import com.motor.service.servicemotor.base.annotation.MainScope;

import dagger.Subcomponent;

/**
 * Created by ikun on 05/01/18.
 */
@MainScope
@Subcomponent(
        modules = {
                MainModule.class
        }
)

public interface MainComponent {
}
