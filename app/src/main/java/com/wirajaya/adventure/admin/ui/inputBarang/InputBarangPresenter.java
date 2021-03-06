package com.wirajaya.adventure.admin.ui.inputBarang;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wirajaya.adventure.admin.base.BasePresenter;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.data.model.Category;
import com.wirajaya.adventure.admin.data.remote.CategoryService;
import com.wirajaya.adventure.admin.data.remote.FirebaseImageService;
import com.wirajaya.adventure.admin.data.remote.UserService;
import com.wirajaya.adventure.admin.data.remote.model.User;

import java.util.ArrayList;
import java.util.List;

import static io.fabric.sdk.android.Fabric.TAG;

/**
 * Created by ikun on 11/01/18.
 */

public class InputBarangPresenter implements BasePresenter {
    InputBarangActivity activity;
    UserService userService;
    User user;
    CategoryService categoryService;
    FirebaseImageService firebaseImageService;
    Barang barang;

    public InputBarangPresenter(InputBarangActivity activity, UserService userService, User user, CategoryService categoryService, Barang barang, FirebaseImageService firebaseImageService){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.categoryService = categoryService;
        this.barang = barang;
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

                Log.e(TAG, "onDataChange: "+listCategories );

                activity.initKategori(listCategories);
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

    public void savebarang(Barang barang){
        Log.e("InputBarang","idbarang "+ barang);
        categoryService.saveBarang(barang).addOnCompleteListener(task -> activity.succesSaveBarang()).addOnFailureListener(e -> {
            activity.showLoading(false);
            Toast.makeText(activity, "Gagal menyimpan barang", Toast.LENGTH_SHORT).show();
        });
    }

    public void uploadAvatar(final Barang barang, byte[] data, final Uri uri){
        activity.showLoading(true);
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
