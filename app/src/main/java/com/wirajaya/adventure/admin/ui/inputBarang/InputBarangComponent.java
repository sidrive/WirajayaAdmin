package com.wirajaya.adventure.admin.ui.inputBarang;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by ikun on 11/01/18.
 */

@ActivityScope
@Subcomponent(
        modules = {
                InputBarangModule.class
        }
)

public interface InputBarangComponent {
    InputBarangActivity inject(InputBarangActivity activity);
}
