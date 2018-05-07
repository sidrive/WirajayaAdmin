package com.wirajaya.adventure.admin.data.remote.user;

import com.wirajaya.adventure.admin.base.annotation.UserScope;
import com.wirajaya.adventure.admin.data.editMotor.EditMotorComponent;
import com.wirajaya.adventure.admin.data.editMotor.EditMotorModule;
import com.wirajaya.adventure.admin.data.inputMotor.InputmotorComponent;
import com.wirajaya.adventure.admin.data.inputMotor.InputmotorModule;
import com.wirajaya.adventure.admin.data.main.MainComponent;
import com.wirajaya.adventure.admin.data.main.MainModule;
import com.wirajaya.adventure.admin.ui.editmotor.EditMotorActivityComponent;
import com.wirajaya.adventure.admin.ui.editmotor.EditMotorActivityModule;
import com.wirajaya.adventure.admin.ui.inputMotor.InputMotorComponent;
import com.wirajaya.adventure.admin.ui.inputMotor.InputMotorModule;
import com.wirajaya.adventure.admin.ui.editprofil.EditProfilActivityComponent;
import com.wirajaya.adventure.admin.ui.editprofil.EditProfilActivityModule;
import com.wirajaya.adventure.admin.ui.main.MainActivityComponent;
import com.wirajaya.adventure.admin.ui.main.MainActivityModule;

import dagger.Subcomponent;

/**
 * Created by ikun on 28/12/17.
 */

@UserScope
@Subcomponent(
        modules = {
                UserModule.class
        }
)
public interface UserComponent {

//    IntroActivityComponent plus(IntroActivityModule activityModule);
//
    MainActivityComponent plus(MainActivityModule activityModule);
    InputMotorComponent plus(InputMotorModule activityModule);
    EditMotorActivityComponent plus(EditMotorActivityModule activityModule);
//
//    InputmotorComponent plus(InputmotorModule mainModule);
//
    EditProfilActivityComponent plus(EditProfilActivityModule activityModule);

    MainComponent plus(MainModule mainModule);

    InputmotorComponent plus(InputmotorModule inputMotorModule);

    EditMotorComponent plus(EditMotorModule editMotorModule);
//
//    SettingActivityComponent plus(SettingActivityModule activityModule);
//
//    BriefActivityComponent plus(BriefActivityModule activityModule);
//
//    SkillActivityComponent plus(SkillActivityModule activityModule);
//
//    PrestasiActivityComponent plus(PrestasiActivityModule activityModule);
//
//    PengalamanActivityComponent plus(PengalamanActivityModule activityModule);
//
//    SkillComponent plus(SkillModule module);
//
//    LocationComponent plus(LocationModule locationModule);
//
//    AddLocationActivityComponent plus(AddLocationActivityModule activityModule);
//
//    PaymentDetailActivityComponent plus(PaymentDetailActivityModule activityModule);
//
//    VerificationActivityComponent plus(VerificationActivityModule activityModule);
//
//    ReviewsActivityComponent plus(ReviewsActivityModule activityModule);
//
//    MapActivityComponent plus(MapActivityModule mapActivityModule);
//    WalletActivityComponent plus (WalletActivityModule walletActivityModule);
}
