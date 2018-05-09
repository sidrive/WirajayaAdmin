package com.wirajaya.adventure.admin.ui.main;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.wirajaya.adventure.admin.base.BasePresenter;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.data.remote.CategoryService;
import com.wirajaya.adventure.admin.data.remote.UserService;
import com.wirajaya.adventure.admin.data.remote.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikun on 02/01/18.
 */

public class MainPresenter implements BasePresenter {
    MainAct activity;
    UserService userService;
    User user;
    CategoryService categoryService;

    public MainPresenter(MainAct activity, UserService userService, User user, CategoryService categoryService){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.categoryService = categoryService;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }

    public void updateFCMToken(String uid, String token){
        userService.updateUserToken(uid, token);
    }

    public void getMotor(User user){
        categoryService.getMotor(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Barang> listBarang = new ArrayList<Barang>();
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Barang barang = postSnapshot.getValue(Barang.class);
                    Log.e("MainPresenter", "onDataChange: " + dataSnapshot.getChildren());
                    listBarang.add(barang);
//                    Log.e("MainPresenter", "onDataChange: " + listBarang);
                }

                activity.initListMotor(listBarang);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getUser(User user){
        userService.getUser(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> listUser = new ArrayList<User>();
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    User user = postSnapshot.getValue(User.class);
                    User user = dataSnapshot.getValue(User.class);
                    Log.e("MainPresenter", "onDataChange: " + user);
                    listUser.add(user);
//                    Log.e("MainPresenter", "onDataChange: " + listMotor);
//                }

                activity.initListUser(listUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateMotor(Barang barang){
        categoryService.saveBarang(barang).addOnCompleteListener(task -> activity.succesSaveMotor()).addOnFailureListener(e -> {
            activity.showLoading(false);
            Toast.makeText(activity, "Gagal menyimpan barang", Toast.LENGTH_SHORT).show();
        });

    }
}
