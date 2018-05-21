package com.wirajaya.adventure.admin.ui.editbarang;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wirajaya.adventure.admin.R;
import com.wirajaya.adventure.admin.base.BaseActivity;
import com.wirajaya.adventure.admin.base.BaseApplication;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.ui.historyservice.HistoryServiceActivity;
import com.wirajaya.adventure.admin.ui.dialog.DialogUploadOption;
import com.wirajaya.adventure.admin.ui.dialog.DialogUploadOption.OnDialogUploadOptionClickListener;
import com.wirajaya.adventure.admin.ui.inputservice.InputServiceActivity;
import com.wirajaya.adventure.admin.utils.DateFormater;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks;

public class EditBarangActivity extends BaseActivity implements OnDialogUploadOptionClickListener, PermissionCallbacks {

    private static final String TAG = "DetailMotorActivity";
    public static final int REQUST_CODE_CAMERA = 1002;
    public static final int REQUST_CODE_GALLERY = 1001;
    private static final int RC_CAMERA_PERM = 205;
    public static Uri mCapturedImageURI;

    @BindView(R.id.viewPrrogress)
    LinearLayout viewProgress;

    @BindView(R.id.txtKategori)
    TextView txtkategori;

    @BindView(R.id.txtKeterangan)
    TextView txtket;

    @BindView(R.id.txtSeri)
    TextView txtseri;

    @BindView(R.id.imglogo)
    ImageView imglogo;

    @BindView(R.id.imageView2)
    ImageView imgAvatar;

    @BindView(R.id.txt_namaBrg)
    TextView namaBrg;

    @BindView(R.id.txt_merk)
    TextView txtMerk;

    @BindView(R.id.txt_harga)
    TextView txtHarga;

    @BindView(R.id.txt_stok)
    TextView txtStok;

    @Inject
    EditBarangPresenter presenter;

    @Inject
    Barang barang;

    byte[] imgSmall;
    Uri imgOriginal;

    Calendar myCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_barang);
        ButterKnife.bind(this);

        initBarang();

        myCalendar = Calendar.getInstance();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        if (mCapturedImageURI != null) {
            savedInstanceState.putString("mUriKey", mCapturedImageURI.toString());
        }

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        mCapturedImageURI = Uri.parse(savedInstanceState.getString("mUriKey"));
        Log.d("Restore state", "mCapturedImageURI = " + mCapturedImageURI);
    }



    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getMotorComponent()
                .plus(new EditBarangActivityModule(this))
                .inject(this);
//        BaseApplication.get(this).createEditMotorComponent(this);
    }


    public static void startWithBarang(Activity activity, final Barang barang) {
        Intent intent = new Intent(activity, EditBarangActivity.class);

        BaseApplication.get(activity).createMotorComponent(barang);
        activity.startActivity(intent);
    }

    public void initBarang(){
        if(barang.getKategoriBarang() != null){
            txtkategori.setText(barang.getKategoriBarang().toString());
        }
        if(barang.getKeteranganBarang() != null){
            txtket.setText(barang.getKeteranganBarang().toString());
        }
        if(barang.getNamaBarang() != null){
            namaBrg.setText(barang.getNamaBarang().toString());
        }
        if(barang.getMerkBarang() != null){
            txtMerk.setText(barang.getMerkBarang().toString());
        }
        if(String.valueOf(barang.getStokBarang()) != null){
            txtStok.setText(String.valueOf(barang.getStokBarang()));
        }

        if(String.valueOf(barang.getHargaBarang()) != null){
            txtHarga.setText(String.valueOf(barang.getHargaBarang()));
        }
        if(barang.getPhoto_url() != null){
            if (!barang.getPhoto_url().equals("NOT")) {
                Glide.with(this)
                        .load(barang.getPhoto_url())
                        .placeholder(R.color.colorSoft)
                        .dontAnimate()
                        .into(imgAvatar);
            }
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUST_CODE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = mCapturedImageURI;
                handleCrop(uri);
            }
        }

        if (requestCode == REQUST_CODE_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                handleCrop(uri);

            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = result.getUri();
                imgOriginal = uri;

                try {
                    Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imgAvatar.setImageBitmap(bitmap2);
                    encodeBitmapAndSaveToFirebase(bitmap2);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                error.printStackTrace();
            }
        }
    }

    @OnClick(R.id.btnaddPhoto)
    void showDialogUploadOption() {
        DialogUploadOption dialogUploadOption = new DialogUploadOption(this);
        dialogUploadOption.show();
    }

    @OnClick(R.id.btn_simpan)
    void simpanBarang(){
        validate();
    }


    @Override
    public void onGalleryClicked(Dialog dialog) {
        galeryTask();
        dialog.dismiss();
    }

    @Override
    public void onCameraClicked(Dialog dialog) {
        cameraTask();
        dialog.dismiss();
    }


    private void handleCrop(Uri uri) {
        CropImage.activity(uri)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(500, 500)
                .start(this);
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        imgSmall = baos.toByteArray();

    }

    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

    public void onLaunchCamera() {
        Intent intent;
        String fileName = nextSessionId();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        mCapturedImageURI = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, REQUST_CODE_CAMERA);
        }
    }

    public void onLaunchGallery() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "content://media/internal/images/media"));
        intent.setAction(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");
        startActivityForResult(intent, REQUST_CODE_GALLERY);
    }

    public void successUploadImage(String url) {
        if (url != null) {
            barang.setPhoto_url(url);
        }
        presenter.updateBarang(barang);
    }

    public void successUpdateBarang(Barang barang) {
        showLoading(false);
            Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
            BaseApplication.get(this).createMotorComponent(barang);
            finish();
            showLoading(false);

    }

    public void showLoading(boolean show) {
        if (show) {
            viewProgress.setVisibility(View.VISIBLE);
        } else {
            viewProgress.setVisibility(View.GONE);
        }

    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            onLaunchCamera();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.ijin_camera),
                    RC_CAMERA_PERM, perms);
        }
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void galeryTask() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            onLaunchGallery();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.ijin_camera),
                    RC_CAMERA_PERM, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    public void validate(){

        if(namaBrg.getText() != null){
            barang.setNamaBarang(namaBrg.getText().toString());
        }
        if(txtMerk.getText() != null){
            barang.setMerkBarang(txtMerk.getText().toString());
        }
        if(txtStok.getText() != null){
            barang.setStokBarang(Integer.valueOf(txtStok.getText().toString()));
        }
        if(txtHarga.getText() != null){
            barang.setHargaBarang(Integer.valueOf(txtHarga.getText().toString()));
        }
        barang.setUpdateTerakhir(System.currentTimeMillis());
        if (imgOriginal != null) {
            presenter.uploadAvatar(barang, imgSmall, imgOriginal);
        } else {
            presenter.updateBarang(barang);
        }

        showLoading(true);

    }

    @OnClick(R.id.fabHistoryService)
    void showHistory(){
        HistoryServiceActivity.startWithMotor(this, barang);
    }

    @OnClick(R.id.fabService)
    void addService(){
        InputServiceActivity.startWithMotor(this, barang);
    }
}
