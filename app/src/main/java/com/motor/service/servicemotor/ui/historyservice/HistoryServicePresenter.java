package com.motor.service.servicemotor.ui.historyservice;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.motor.service.servicemotor.base.BasePresenter;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.model.Service;
import com.motor.service.servicemotor.data.remote.CategoryService;
import com.motor.service.servicemotor.data.remote.FirebaseImageService;
import com.motor.service.servicemotor.data.remote.UserService;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.GraphRequest.TAG;

public class HistoryServicePresenter implements BasePresenter {
    HistoryServiceActivity activity;
    UserService userService;
    FirebaseImageService firebaseImageService;
    Motor motor;
    CategoryService categoryService;

    public HistoryServicePresenter(HistoryServiceActivity activity, UserService userService, Motor motor, FirebaseImageService firebaseImageService) {
        this.activity = activity;
        this.userService = userService;
        this.motor = motor;
        this.firebaseImageService = firebaseImageService;
        this.categoryService = categoryService;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void getService(Motor motor){
        userService.getService(motor.getIdmotor()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Service> listService = new ArrayList<Service>();
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    Log.e("MainPresenter", "onDataChange: " + dataSnapshot.getChildren());
                    listService.add(service);
//                    Log.e("MainPresenter", "onDataChange: " + listMotor);
                }

                activity.initListService(listService);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }

}
