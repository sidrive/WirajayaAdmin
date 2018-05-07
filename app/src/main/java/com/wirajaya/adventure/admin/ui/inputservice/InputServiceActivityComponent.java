package com.wirajaya.adventure.admin.ui.inputservice;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;

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
