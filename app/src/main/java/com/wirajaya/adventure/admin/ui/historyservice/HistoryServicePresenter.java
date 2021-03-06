package com.wirajaya.adventure.admin.ui.historyservice;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.wirajaya.adventure.admin.base.BasePresenter;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.data.model.Service;
import com.wirajaya.adventure.admin.data.remote.CategoryService;
import com.wirajaya.adventure.admin.data.remote.FirebaseImageService;
import com.wirajaya.adventure.admin.data.remote.UserService;

import java.util.ArrayList;
import java.util.List;

public class HistoryServicePresenter implements BasePresenter {
    HistoryServiceActivity activity;
    UserService userService;
    FirebaseImageService firebaseImageService;
    Barang barang;
    CategoryService categoryService;

    public HistoryServicePresenter(HistoryServiceActivity activity, UserService userService, Barang barang, FirebaseImageService firebaseImageService) {
        this.activity = activity;
        this.userService = userService;
        this.barang = barang;
        this.firebaseImageService = firebaseImageService;
        this.categoryService = categoryService;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void getService(Barang barang){
        userService.getService(barang.getIdmotor()).addValueEventListener(new ValueEventListener() {
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
