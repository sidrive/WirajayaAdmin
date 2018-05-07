package com.wirajaya.adventure.admin.ui.editmotor;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;

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
