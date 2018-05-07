package com.motor.service.servicemotor.ui.inputMotor;

import com.motor.service.servicemotor.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by ikun on 11/01/18.
 */

@ActivityScope
@Subcomponent(
        modules = {
                InputMotorModule.class
        }
)

public interface InputMotorComponent {
    InputMotorActivity inject(InputMotorActivity activity);
}
