package com.wirajaya.adventure.admin.data.editMotor;

import com.wirajaya.adventure.admin.base.annotation.MainScope;

import dagger.Subcomponent;

@MainScope
@Subcomponent(
        modules = {
                EditMotorModule.class
        }
)
public interface EditMotorComponent {
}
