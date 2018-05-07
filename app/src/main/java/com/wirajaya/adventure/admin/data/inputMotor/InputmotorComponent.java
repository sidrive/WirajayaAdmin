package com.wirajaya.adventure.admin.data.inputMotor;

import com.wirajaya.adventure.admin.base.annotation.MainScope;

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
