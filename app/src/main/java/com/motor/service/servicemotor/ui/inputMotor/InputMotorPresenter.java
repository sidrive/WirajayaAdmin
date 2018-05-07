package com.motor.service.servicemotor.ui.inputMotor;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.motor.service.servicemotor.base.BasePresenter;
import com.motor.service.servicemotor.data.model.Category;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.remote.CategoryService;
import com.motor.service.servicemotor.data.remote.FirebaseImageService;
import com.motor.service.servicemotor.data.remote.MotorService;
import com.motor.service.servicemotor.data.remote.UserService;
import com.motor.service.servicemotor.data.remote.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikun on 11/01/18.
 */

public class InputMotorPresenter implements BasePresenter {
    InputMotorActivity activity;
    UserService userService;
    User user;
    CategoryService categoryService;
    FirebaseImageService firebaseImageService;
    Motor motor;

    public InputMotorPresenter(InputMotorActivity activity, UserService userService, User user, CategoryService categoryService, Motor motor, FirebaseImageService firebaseImageService){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.categoryService = categoryService;
        this.motor = motor;
        this.firebaseImageService = firebaseImageService;
    }
    @Override
    public void subscribe() {
        if (user != null){
            getCategories();
        }

    }

    @Override
    public void unsubscribe() {

    }

    public void getCategories(){
        categoryService.getMerk().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> listCategories = new ArrayList<Category>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    listCategories.add(category);
                }

                activity.initMerk(listCategories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getSubCategories(String id){
        categoryService.getType(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> listCategories = new ArrayList<Category>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    listCategories.add(category);
                }

                activity.initType(listCategories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getLevels(String id){
        categoryService.getSeri(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> listCategories = new ArrayList<Category>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    listCategories.add(category);
                }

                activity.initSeri(listCategories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void savemotor(Motor motor){
        Log.e("InputMotor","idmotor "+motor.getIdmotor());
        categoryService.saveMotor(motor).addOnCompleteListener(task -> activity.succesSaveMotor()).addOnFailureListener(e -> {
            activity.showLoading(false);
            Toast.makeText(activity, "Gagal menyimpan motor", Toast.LENGTH_SHORT).show();
        });
    }

    public void uploadAvatar(final Motor motor, byte[] data, final Uri uri){
        activity.showLoading(true);
        StorageReference avatarPartnerRef = firebaseImageService.getMotorImageRefOriginal(motor.getUserid(),motor.getIdmotor());

        UploadTask uploadTask = avatarPartnerRef.putFile(uri);
// Register observers to listen for when the download is done or if it fails


        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            System.out.print(exception);
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
            activity.successUploadImage(downloadUrl.toString());

        });
    }
}
