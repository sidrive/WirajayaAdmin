package com.wirajaya.adventure.admin.ui.historyservice;

import com.wirajaya.adventure.admin.base.annotation.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {
                HistoryServiceActivityModule.class
        }
)

public interface HistoryServiceActivityComponent {
    HistoryServiceActivity inject(HistoryServiceActivity activity);
}
