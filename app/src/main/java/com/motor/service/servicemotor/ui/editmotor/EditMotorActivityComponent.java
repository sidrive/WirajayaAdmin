package com.motor.service.servicemotor.ui.editmotor;

import com.motor.service.servicemotor.base.annotation.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {
                EditMotorActivityModule.class
        }
)
public interface EditMotorActivityComponent {
    EditMotorActivity inject(EditMotorActivity activity);
}
