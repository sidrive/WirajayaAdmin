package com.wirajaya.adventure.admin.ui.editbarang;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {
                EditBarangActivityModule.class
        }
)
public interface EditBarangActivityComponent {
    EditBarangActivity inject(EditBarangActivity activity);
}
