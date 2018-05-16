package com.wirajaya.adventure.admin.ui.inputservice;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wirajaya.adventure.admin.base.BasePresenter;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.data.model.Service;
import com.wirajaya.adventure.admin.data.remote.CategoryService;
import com.wirajaya.adventure.admin.data.remote.FirebaseImageService;
import com.wirajaya.adventure.admin.data.remote.UserService;

public class InputServicePresenter implements BasePresenter {
    InputServiceActivity activity;
    UserService userService;
    FirebaseImageService firebaseImageService;
    Barang barang;
    CategoryService categoryService;
    Service service;

    public InputServicePresenter(InputServiceActivity activity, UserService userService, CategoryService categoryService, Barang barang, Service service, FirebaseImageService firebaseImageService){
        this.activity = activity;
        this.userService = userService;
        this.barang = barang;
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

    public void uploadAvatar(final Barang barang, final Service service, byte[] data, final Uri uri){
//        activity.showLoading(true);
////        StorageReference avatarPartnerRef = firebaseImageService.getServiceImageRefOriginal(barang.getUserid(), barang.getIdmotor(),service.getIdservice());
//
//        UploadTask uploadTask = avatarPartnerRef.putFile(uri);
//// Register observers to listen for when the download is done or if it fails
//
//        uploadTask.addOnFailureListener(exception -> {
//            // Handle unsuccessful uploads
//            System.out.print(exception);
//        }).addOnSuccessListener(taskSnapshot -> {
//            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//            Uri downloadUrl = taskSnapshot.getDownloadUrl();
//            activity.successUploadImage(downloadUrl.toString(), barang);
//
//        });
    }

    public void updateMotor(Barang barang, Service service){
//        Log.e("InputMotor","idmotor "+ barang.getIdmotor());
        categoryService.saveBarang(barang).addOnCompleteListener(task -> activity.succesSaveMotor(service)).addOnFailureListener(e -> {
            activity.showLoading(false);
            Toast.makeText(activity, "Gagal menyimpan barang", Toast.LENGTH_SHORT).show();
        });
    }

    public void saveService(Service service){
        categoryService.saveService(service).addOnCompleteListener(task -> activity.succesSaveService()).addOnFailureListener(e -> {
            activity.showLoading(false);
            Toast.makeText(activity, "Gagal menyimpan service", Toast.LENGTH_SHORT).show();
        });
    }
}
