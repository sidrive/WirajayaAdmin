package com.motor.service.servicemotor.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.midi.MidiOutputPort;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.iid.FirebaseInstanceId;
import com.motor.service.servicemotor.R;
import com.motor.service.servicemotor.base.BaseActivity;
import com.motor.service.servicemotor.base.BaseApplication;
import com.motor.service.servicemotor.data.adapter.AdapterProfileUser;
import com.motor.service.servicemotor.data.adapter.AdapterStatusMotor;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.remote.model.User;
import com.motor.service.servicemotor.ui.editprofil.EditProfilActivity;
import com.motor.service.servicemotor.ui.inputMotor.InputMotorActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by ikun on 02/01/18.
 */

public class MainAct extends BaseActivity {

    private static final int RC_LOC_PERM = 1001;

    @Bind(R.id.txtnama_user)
    TextView txtnama;

    @Bind(R.id.img_avatar)
    ImageView imgAvatar;

    @Bind(R.id.listmotor)
    RecyclerView lsmotor;

    @Bind(R.id.listprofile)
    RecyclerView lsprofile;


    @Inject
    MainPresenter presenter;

    @Inject
    User user;

    @Inject
    Motor motor;

    private AdapterStatusMotor adapterStatusMotor;
    private AdapterProfileUser adapterProfileUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver(tokenReceiver,
                new IntentFilter("tokenReceiver"));
        ButterKnife.bind(this);

        locationTask();

        String token = FirebaseInstanceId.getInstance().getToken();
        presenter.updateFCMToken(user.getUid(),token);

        txtnama.setText(user.getFull_name());

        initProfilePhoto();
        initRecycleView();
        initMotor();
        initUser();
        initPager();
    }
    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getUserComponent()
                .plus(new MainActivityModule(this))
                .inject(this);
        BaseApplication.get(this).createMainComponent(this);
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

    public static void startWithUser(Activity activity, final User user) {
        Intent intent = new Intent(activity, MainAct.class);

        BaseApplication.get(activity).createUserComponent(user);
        activity.startActivity(intent);
    }

    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra("token");
            if(token != null)
            {
                presenter.updateFCMToken(user.getUid(),token);
            }
        }
    };

    @OnClick(R.id.button2)
    public void test(){
        InputMotorActivity.startWithUser(this, user);
    }

    @OnClick(R.id.button3)
    public void editProfile(){
        EditProfilActivity.startWithUser(this, user,true);
    }

    public void initPager(){
    }



    private void initRecycleView() {

        lsprofile.setHasFixedSize(true);
        lsprofile.addItemDecoration(
                new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        lsprofile.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lsprofile.setNestedScrollingEnabled(false);

        lsmotor.setHasFixedSize(true);
        lsmotor.addItemDecoration(
                new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        lsmotor.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lsmotor.setNestedScrollingEnabled(false);
    }

    public void initMotor(){
    presenter.getMotor(user);
    }

    public void initListMotor(List<Motor> listMotor){
        adapterStatusMotor = new AdapterStatusMotor((ArrayList<Motor>) listMotor,this, this);
//        adapterStatusMotor.UpdateMotor(listMotor);
        lsmotor.setAdapter(adapterStatusMotor);
    }

    public void initUser(){
        presenter.getUser(user);
    }

    public void initListUser(List<User> listUser){
        adapterProfileUser = new AdapterProfileUser((ArrayList<User>) listUser,this, this);
//        adapterStatusMotor.UpdateMotor(listMotor);
        lsprofile.setAdapter(adapterProfileUser);
    }



    public void initProfilePhoto(){
        if (user.getPhoto_url() != null) {
            if (!user.getPhoto_url().equalsIgnoreCase("NOT")){
                Glide.with(this)
                        .load(user.getPhoto_url()).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e("IMAGE_EXCEPTION", "Exception " + e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.d("smtime img's not loaded",  "n dis tex's not di");
                        return false;
                    }
                });
                        /*.placeholder(R.color.colorSoft)
                        .dontAnimate()
                        .into(imgAvatar);*/

                Glide.with(this)
                        .load(user.getPhoto_url())
                        .placeholder(R.color.colorSoft)
                        .dontAnimate()
                        .into(imgAvatar);
            }
        }
    }

    public void updateKM(Motor motor){
        presenter.updateMotor(motor);
    }

    public void succesSaveMotor() {
        showLoading(false);
        String title = "Motor disimpan";
        String desc = "Kami sedang melakukan update data motor";
        int icon = R.drawable.ic_alarm_on;
        showAlertDialog(title, desc, icon);
    }

    void showLoading(boolean b) {
    }

    private void showAlertDialog(String title, String desc, int icon) {
        final Intent intent = new Intent(this, MainAct.class);
        intent.putExtra("motor", "motor");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(desc)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    // continue with delete
                    dialog.dismiss();
                    startActivity(intent);

                })
                .setIcon(icon)
                .show();
    }

    @AfterPermissionGranted(RC_LOC_PERM)
    public void locationTask() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Have permission, do the thing!
//            onLaunchCamera();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.ijin_lokasi),
                    RC_LOC_PERM, perms);
        }
    }
}
