package com.wirajaya.adventure.admin.data.main;

import com.wirajaya.adventure.admin.base.annotation.MainScope;

import dagger.Subcomponent;

/**
 * Created by ikun on 05/01/18.
 */
@MainScope
@Subcomponent(
        modules = {
                MainModule.class
        }
)

public interface MainComponent {
}
