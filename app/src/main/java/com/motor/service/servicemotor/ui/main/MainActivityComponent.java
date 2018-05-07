package com.motor.service.servicemotor.ui.main;

import com.motor.service.servicemotor.base.annotation.ActivityScope;


import dagger.Subcomponent;

/**
 * Created by ikun on 02/01/18.
 */

@ActivityScope
@Subcomponent(
        modules = {
                MainActivityModule.class
        }
)
public interface MainActivityComponent {
    MainAct inject(MainAct activity);
}
