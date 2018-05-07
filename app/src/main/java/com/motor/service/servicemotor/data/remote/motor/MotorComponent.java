package com.motor.service.servicemotor.data.remote.motor;

import com.motor.service.servicemotor.base.annotation.UserScope;
import com.motor.service.servicemotor.ui.historyservice.HistoryServiceActivityComponent;
import com.motor.service.servicemotor.ui.historyservice.HistoryServiceActivityModule;
import com.motor.service.servicemotor.ui.editmotor.EditMotorActivityComponent;
import com.motor.service.servicemotor.ui.editmotor.EditMotorActivityModule;
import com.motor.service.servicemotor.ui.inputservice.InputServiceActivityComponent;
import com.motor.service.servicemotor.ui.inputservice.InputServiceActivityModule;

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
