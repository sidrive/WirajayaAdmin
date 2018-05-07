package com.wirajaya.adventure.admin.ui.editmotor;

import android.net.Uri;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wirajaya.adventure.admin.base.BasePresenter;
import com.wirajaya.adventure.admin.data.model.Motor;
import com.wirajaya.adventure.admin.data.remote.CategoryService;
import com.wirajaya.adventure.admin.data.remote.FirebaseImageService;
import com.wirajaya.adventure.admin.data.remote.UserService;
import com.wirajaya.adventure.admin.data.remote.model.User;

public class EditMotorPresenter implements BasePresenter {
    EditMotorActivity activity;
    UserService userService;
    User user;
    CategoryService categoryService;
    FirebaseImageService firebaseImageService;
    Motor motor;

    public EditMotorPresenter(EditMotorActivity activity,UserService userService, Motor motor, FirebaseImageService firebaseImageService){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.categoryService = categoryService;
        this.motor = motor;
        this.firebaseImageService = firebaseImageService;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void updateMotor(final Motor motor){
        userService.updateMotor(motor).addOnCompleteListener(
                task -> activity.successUpdateMotor(motor));
    }

    public void uploadAvatar(final Motor motor, byte[] data, final Uri uri){
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
