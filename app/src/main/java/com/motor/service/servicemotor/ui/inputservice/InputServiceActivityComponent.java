package com.motor.service.servicemotor.ui.inputservice;

import com.motor.service.servicemotor.base.annotation.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {
                InputServiceActivityModule.class
        }
)
public interface InputServiceActivityComponent {
    InputServiceActivity inject(InputServiceActivity activity);
}
