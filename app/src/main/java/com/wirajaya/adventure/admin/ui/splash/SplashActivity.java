package com.wirajaya.adventure.admin.ui.splash;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.wirajaya.adventure.admin.MainActivity;
import com.wirajaya.adventure.admin.R;
import com.wirajaya.adventure.admin.base.BaseActivity;
import com.wirajaya.adventure.admin.base.BaseApplication;
import com.wirajaya.adventure.admin.data.remote.model.User;
import com.wirajaya.adventure.admin.ui.editprofil.EditProfilActivity;
import com.wirajaya.adventure.admin.ui.login.LoginActivity;
import com.wirajaya.adventure.admin.ui.main.MainAct;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by ikun on 28/12/17.
 */

public class SplashActivity extends BaseActivity {
    @Inject
    SplashPresenter presenter;

    @BindView(R.id.imageView3)
    ImageView imageView;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /*overridePendingTransition(R.anim.fadein,R.anim.fadeout);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    public void showLoginActivity() {
        Intent a=new Intent(this,LoginActivity.class);
        startActivity(a);
        finish();

    }

    public void showMainActivity(User user){
            MainAct.startWithUser(this, user);
            finish();
    }

    public void showRegisterActivity(User user){
        EditProfilActivity.startWithUser(this, user, true);
        finish();
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new SplashActivityModule(this))
                .inject(this);
    }


}
