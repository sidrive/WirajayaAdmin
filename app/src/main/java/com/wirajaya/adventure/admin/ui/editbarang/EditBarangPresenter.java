package com.wirajaya.adventure.admin.ui.editbarang;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wirajaya.adventure.admin.base.BasePresenter;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.data.remote.CategoryService;
import com.wirajaya.adventure.admin.data.remote.FirebaseImageService;
import com.wirajaya.adventure.admin.data.remote.UserService;
import com.wirajaya.adventure.admin.data.remote.model.User;

import static io.fabric.sdk.android.Fabric.TAG;

public class EditBarangPresenter implements BasePresenter {
    EditBarangActivity activity;
    UserService userService;
    User user;
    CategoryService categoryService;
    FirebaseImageService firebaseImageService;
    Barang barang;

    public EditBarangPresenter(EditBarangActivity activity, UserService userService, Barang barang, FirebaseImageService firebaseImageService){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.categoryService = categoryService;
        this.barang = barang;
        this.firebaseImageService = firebaseImageService;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void updateBarang(final Barang barang){
        Log.e(TAG, "updateBarang: "+barang );
        userService.updateBarang(barang).addOnCompleteListener(
                task -> activity.successUpdateBarang(barang));
    }

    public void uploadAvatar(final Barang barang, byte[] data, final Uri uri){
        StorageReference avatarPartnerRef = firebaseImageService.getBarangImageRefOriginal(barang.getKategoriBarang(), barang.getIdbarang(), barang.getNamaBarang());

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
