package com.motor.service.servicemotor.ui.editprofil;

import android.net.Uri;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.motor.service.servicemotor.base.BasePresenter;
import com.motor.service.servicemotor.data.remote.FirebaseImageService;
import com.motor.service.servicemotor.data.remote.FirebaseUserService;
import com.motor.service.servicemotor.data.remote.UserService;
import com.motor.service.servicemotor.data.remote.model.User;

/**
 * Created by ikun on 29/12/17.
 */

public class EditProfilPresenter implements BasePresenter {

    EditProfilActivity activity;
    UserService userService;
    FirebaseImageService firebaseImageService;
    FirebaseUserService firebaseUserService;

    public EditProfilPresenter(EditProfilActivity activity, UserService userService, FirebaseUserService firebaseUserService, FirebaseImageService firebaseImageService){
        this.activity = activity;
        this.userService = userService;
        this.firebaseImageService = firebaseImageService;
        this.firebaseUserService = firebaseUserService;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void updateProfile(final User user){
        userService.updateUser(user).addOnCompleteListener(
                task -> activity.successUpdateProfile(user));
    }

//    public void getPayment(String uid){
//        userService.getUserPayment(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                PartnerPayment partnerPayment = dataSnapshot.getValue(PartnerPayment.class);
//                if (partnerPayment != null) activity.initPayment(partnerPayment);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }
//
//    public void updatePayment(PartnerPayment partnerPayment){
//        userService.updateUserPayment(partnerPayment);
//    }

    public void uploadAvatar(final User user, byte[] data, final Uri uri){
        StorageReference avatarPartnerRef = firebaseImageService.getUserImageRefOriginal(user.getUid());

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
