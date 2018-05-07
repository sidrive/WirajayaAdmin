package com.wirajaya.adventure.admin.ui.editprofil;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by ikun on 29/12/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                EditProfilActivityModule.class
        }
)
public interface EditProfilActivityComponent {
    EditProfilActivity inject(EditProfilActivity activity);
}
