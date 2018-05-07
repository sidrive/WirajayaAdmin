package com.motor.service.servicemotor.data.editMotor;

import com.motor.service.servicemotor.base.annotation.MainScope;

import dagger.Subcomponent;

@MainScope
@Subcomponent(
        modules = {
                EditMotorModule.class
        }
)
public interface EditMotorComponent {
}
