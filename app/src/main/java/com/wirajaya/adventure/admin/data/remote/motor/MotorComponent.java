package com.wirajaya.adventure.admin.data.remote.motor;

import com.wirajaya.adventure.admin.base.annotation.UserScope;
import com.wirajaya.adventure.admin.ui.historyservice.HistoryServiceActivityComponent;
import com.wirajaya.adventure.admin.ui.historyservice.HistoryServiceActivityModule;
import com.wirajaya.adventure.admin.ui.editmotor.EditMotorActivityComponent;
import com.wirajaya.adventure.admin.ui.editmotor.EditMotorActivityModule;
import com.wirajaya.adventure.admin.ui.inputservice.InputServiceActivityComponent;
import com.wirajaya.adventure.admin.ui.inputservice.InputServiceActivityModule;

import dagger.Subcomponent;

@UserScope
@Subcomponent(
        modules = {
                MotorModule.class
        }
)
public interface MotorComponent {
    EditMotorActivityComponent plus(EditMotorActivityModule editMotorActivityModule);

    HistoryServiceActivityComponent plus(HistoryServiceActivityModule historyServiceActivityModule);

    InputServiceActivityComponent plus(InputServiceActivityModule inputServiceActivityModule);
}
