package com.wirajaya.adventure.admin.ui.main;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;


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
