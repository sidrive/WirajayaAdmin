package com.motor.service.servicemotor.ui.historyservice;

import android.app.Activity;
import android.app.Presentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.motor.service.servicemotor.R;
import com.motor.service.servicemotor.base.BaseActivity;
import com.motor.service.servicemotor.base.BaseApplication;
import com.motor.service.servicemotor.data.adapter.AdapterService;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.model.Service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryServiceActivity extends BaseActivity {


    private static final String TAG = "HistoryService";

    @Bind(R.id.listservice)
    RecyclerView lsservice;

    @Bind(R.id.imgAvatar)
    ImageView imgAvatar;

    @Inject
    Motor motor;

    @Inject
    HistoryServicePresenter presenter;

    private AdapterService adapterService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_service_activity);
        ButterKnife.bind(this);

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this,config);

        initRecycleView();
        initService();
        initMotorPhoto();

    }


    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getMotorComponent()
                .plus(new HistoryServiceActivityModule(this))
                .inject(this);
    }

    public static void startWithMotor(Activity activity, final Motor motor) {
        Intent intent = new Intent(activity, HistoryServiceActivity.class);

        BaseApplication.get(activity).createMotorComponent(motor);
        activity.startActivity(intent);
    }


    private void initRecycleView() {
        lsservice.setHasFixedSize(true);
        lsservice.addItemDecoration(
                new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        lsservice.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lsservice.setNestedScrollingEnabled(false);
    }

    public void initService(){
        presenter.getService(motor);
    }

    public void initListService(List<Service> listService){
        Log.e(TAG, "initListService: "+listService );
        adapterService = new AdapterService((ArrayList<Service>) listService,this, this);
////        adapterStatusMotor.UpdateMotor(listMotor);
        lsservice.setAdapter(adapterService);
    }

    public void initMotorPhoto(){
        if (motor.getPhoto_url() != null) {
            if (!motor.getPhoto_url().equalsIgnoreCase("NOT")){
                Glide.with(this)
                        .load(motor.getPhoto_url()).listener(new RequestListener<String, GlideDrawable>() {
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
                        .load(motor.getPhoto_url())
                        .placeholder(R.color.colorSoft)
                        .dontAnimate()
                        .into(imgAvatar);
            }
        }
    }
}
