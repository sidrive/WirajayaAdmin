package com.motor.service.servicemotor.ui.historyservice;

import com.motor.service.servicemotor.base.annotation.ActivityScope;

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
