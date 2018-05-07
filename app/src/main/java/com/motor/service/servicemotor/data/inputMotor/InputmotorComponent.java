package com.motor.service.servicemotor.data.inputMotor;

import com.motor.service.servicemotor.base.annotation.MainScope;

import dagger.Subcomponent;

/**
 * Created by ikun on 11/01/18.
 */

@MainScope
@Subcomponent(
        modules = {
                InputmotorModule.class
        }
)
public interface InputmotorComponent {
}
