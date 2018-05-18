package com.wirajaya.adventure.admin.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.google.firebase.iid.FirebaseInstanceId;
import com.wirajaya.adventure.admin.R;
import com.wirajaya.adventure.admin.base.BaseActivity;
import com.wirajaya.adventure.admin.base.BaseApplication;
import com.wirajaya.adventure.admin.data.adapter.AdapterProfileUser;
import com.wirajaya.adventure.admin.data.adapter.AdapterStatusMotor;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.data.remote.model.User;
import com.wirajaya.adventure.admin.ui.editprofil.EditProfilActivity;
import com.wirajaya.adventure.admin.ui.inputBarang.InputBarangActivity;
import com.wirajaya.adventure.admin.ui.mainfragment.AccFragment;
import com.wirajaya.adventure.admin.ui.mainfragment.CarierFragment;
import com.wirajaya.adventure.admin.ui.mainfragment.TendaFragment;
import com.wirajaya.adventure.admin.ui.mainfragment.TendaPramukaFragment;

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

public class MainAct extends BaseActivity implements CarierFragment.OnFragmentInteractionListener,
        TendaFragment.OnFragmentInteractionListener,
        TendaPramukaFragment.OnFragmentInteractionListener,
        AccFragment.OnFragmentInteractionListener {

    private static final int RC_LOC_PERM = 1001;

    @Bind(R.id.txtnama_user)
    TextView txtnama;

    @Bind(R.id.img_avatar)
    ImageView imgAvatar;

    @Bind(R.id.listmotor)
    RecyclerView lsmotor;

    @Bind(R.id.listprofile)
    RecyclerView lsprofile;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Bind(R.id.frameFragment)
    FrameLayout frame;


    @Inject
    MainPresenter presenter;

    @Inject
    User user;

    @Inject
    Barang barang;

    TendaFragment tendaFragment = new TendaFragment();

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

        showLoading(false);

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this,config);

        String token = FirebaseInstanceId.getInstance().getToken();
        presenter.updateFCMToken(user.getUid(),token);

        txtnama.setText(user.getFull_name());

        initProfilePhoto();
        initRecycleView();
        initMotor();
        initUser();
        initPager();

        initFragment(new TendaFragment());
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

    @OnClick(R.id.lnInputBarang)
    public void inputBarang(){
        InputBarangActivity.startWithUser(this, user);
    }

    @OnClick(R.id.ivProfile)
    public void showProfile(){
        EditProfilActivity.startWithUser(this,user,true);
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

    public void initListMotor(List<Barang> listBarang){
        adapterStatusMotor = new AdapterStatusMotor((ArrayList<Barang>) listBarang,this, this);
//        adapterStatusMotor.UpdateMotor(listBarang);
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

    public void updateKM(Barang barang){
        presenter.updateMotor(barang);
    }

    public void succesSaveMotor() {
        showLoading(false);
        String title = "Barang disimpan";
        String desc = "Update data barang Berhasil";
        int icon = R.drawable.ic_alarm_on;
        showAlertDialog(title, desc, icon);
    }

    public void showLoading(boolean b) {
        if(b){
            viewProgress.setVisibility(View.VISIBLE);
        }else {
            viewProgress.setVisibility(View.GONE);
        }
        Log.e("MainAct", "showLoading: "+b );
    }

    private void showAlertDialog(String title, String desc, int icon) {
        final Intent intent = new Intent(this, MainAct.class);
        intent.putExtra("barang", "barang");
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

    private void initFragment(Fragment classFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameFragment, classFragment);
        transaction.commit();

    }



    @OnClick(R.id.lnTendaDoom)
    public void showTendaDoom(){
        initFragment(new TendaFragment());
    }

    @OnClick(R.id.lnCarrier)
    public void showCarrier(){
        initFragment(new CarierFragment());
    }

    @OnClick(R.id.lnAcc)
    public void showAcc(){
        initFragment(new AccFragment());
    }

    @OnClick(R.id.lnTendaPramuka)
    public void showTendaPramuka(){
        initFragment(new TendaPramukaFragment());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
