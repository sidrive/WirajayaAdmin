package com.motor.service.servicemotor.ui.inputservice;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.motor.service.servicemotor.base.BasePresenter;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.model.Service;
import com.motor.service.servicemotor.data.remote.CategoryService;
import com.motor.service.servicemotor.data.remote.FirebaseImageService;
import com.motor.service.servicemotor.data.remote.UserService;

public class InputServicePresenter implements BasePresenter {
    InputServiceActivity activity;
    UserService userService;
    FirebaseImageService firebaseImageService;
    Motor motor;
    CategoryService categoryService;
    Service service;

    public InputServicePresenter(InputServiceActivity activity,UserService userService,CategoryService categoryService, Motor motor, Service service, FirebaseImageService firebaseImageService){
        this.activity = activity;
        this.userService = userService;
        this.motor = motor;
        this.service = service;
        this.firebaseImageService = firebaseImageService;
        this.categoryService = categoryService;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void uploadAvatar(final Motor motor,final Service service, byte[] data, final Uri uri){
        activity.showLoading(true);
        StorageReference avatarPartnerRef = firebaseImageService.getServiceImageRefOriginal(motor.getUserid(),motor.getIdmotor(),service.getIdservice());

        UploadTask uploadTask = avatarPartnerRef.putFile(uri);
// Register observers to listen for when the download is done or if it fails

        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            System.out.print(exception);
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
            activity.successUploadImage(downloadUrl.toString(),motor);

        });
    }

    public void updateMotor(Motor motor,Service service){
        Log.e("InputMotor","idmotor "+motor.getIdmotor());
        categoryService.saveMotor(motor).addOnCompleteListener(task -> activity.succesSaveMotor(service)).addOnFailureListener(e -> {
            activity.showLoading(false);
            Toast.makeText(activity, "Gagal menyimpan motor", Toast.LENGTH_SHORT).show();
        });
    }

    public void saveService(Service service){
        categoryService.saveService(service).addOnCompleteListener(task -> activity.succesSaveService()).addOnFailureListener(e -> {
            activity.showLoading(false);
            Toast.makeText(activity, "Gagal menyimpan service", Toast.LENGTH_SHORT).show();
        });
    }
}
