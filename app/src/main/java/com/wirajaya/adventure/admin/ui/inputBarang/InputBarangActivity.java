package com.wirajaya.adventure.admin.ui.inputBarang;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wirajaya.adventure.admin.R;
import com.wirajaya.adventure.admin.base.BaseActivity;
import com.wirajaya.adventure.admin.base.BaseApplication;
import com.wirajaya.adventure.admin.data.model.Category;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.data.remote.model.User;
import com.wirajaya.adventure.admin.ui.dialog.DialogUploadOption;
import com.wirajaya.adventure.admin.ui.main.MainAct;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by ikun on 11/01/18.
 */

public class InputBarangActivity extends BaseActivity implements DialogUploadOption.OnDialogUploadOptionClickListener, EasyPermissions.PermissionCallbacks {

    private static final String TAG = "DetailBarangActivity";
    public static final int REQUST_CODE_CAMERA = 1002;
    public static final int REQUST_CODE_GALLERY = 1001;
    private static final int RC_CAMERA_PERM = 205;
    public static Uri mCapturedImageURI;

    @BindString(R.string.error_field_required)
    String strErrRequired;

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
    Barang barang;

    @Inject
    InputBarangPresenter presenter;

    @Inject
    User user;

    CharSequence[] kategori;
    CharSequence[] keterangan;
    CharSequence[] seri;

    List<Category> listKategori;
    List<Category> listKeterangan;
    List<Category> listSeri;

    int kategoriVal;
    int keteranganVal;
    int seriVal;

    String kategoriID;
    String keteranganID;
    String seriID;

    Calendar myCalendar;

    byte[] imgSmall;
    Uri imgOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_barang);
        ButterKnife.bind(this);

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
        BaseApplication.get(this).getUserComponent()
                .plus(new InputBarangModule(this))
                .inject(this);
        BaseApplication.get(this).createInputMotorComponent(this);
    }

    public static void startWithUser(Activity activity, final User user) {
        Intent intent = new Intent(activity, InputBarangActivity.class);

        BaseApplication.get(activity).createUserComponent(user);
        activity.startActivity(intent);
    }

    @OnClick(R.id.ln_kate)
    void showCategoryKategori(){
        showKate();
    }

    @OnClick(R.id.ln_ket)
    void showCategoryKeterangan(){
        showKete();
    }

//    @OnClick(R.id.ln_seri)
//    void showCategoryVarian(){
//        showSeri();
//    }

    private void showKate(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
        alert.setTitle("Pilih Kategori Barang");
        alert.setSingleChoiceItems(kategori, kategoriVal, (dialog, which) -> {
            handleSelectCategoryKategori(which);
//            changeLogo();
            dialog.dismiss();
        });
        alert.setIcon(R.drawable.ic_2000px_motorbike_svg);
        alert.show();

    }

//    private void changeLogo(){
//        if((txtmerk.getText().toString()).equalsIgnoreCase("HONDA")){
//            imglogo.setImageResource(R.drawable.ic_logo_honda);
//        }if((txtmerk.getText().toString()).equalsIgnoreCase("YAMAHA")){
//            imglogo.setImageResource(R.drawable.ic_logo_yamaha);
//        }if((txtmerk.getText().toString()).equalsIgnoreCase("SUZUKI")){
//            imglogo.setImageResource(R.drawable.ic_logo_suzuki);
//        }if((txtmerk.getText().toString()).equalsIgnoreCase("KAWASAKI")){
//            imglogo.setImageResource(R.drawable.ic_logo_kawasaki);
//        }
//    }

    private void handleSelectCategoryKategori(int pos){
        kategoriVal = pos;
        String kategoris = kategori[pos].toString();
        txtkategori.setText(kategoris);
        presenter.getSubCategories(listKategori.get(pos).getId());
    }

    private void showKete() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
        alert.setTitle("Pilih Keterangan Barang");
        alert.setSingleChoiceItems(keterangan, keteranganVal, (dialog, which) -> {
            handleSelectSubCategoryKet(which);
            dialog.dismiss();

        });
        alert.setIcon(R.drawable.ic_2000px_motorbike_svg);
        alert.show();
    }

    private void handleSelectSubCategoryKet(int pos){
        keteranganVal = pos;
        String keterangans = keterangan[pos].toString();
        txtket.setText(keterangans);

        kategoriID = listKeterangan.get(pos).getId();
//        presenter.getLevels(listKeterangan.get(pos).getSeri());
    }

    private void showSeri() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
        alert.setTitle("Pilih Seri");
        alert.setSingleChoiceItems(seri, seriVal, (dialog, which) -> {
            handleSelectLevelSeri(which);
            dialog.dismiss();

        });
        alert.setIcon(R.drawable.ic_2000px_motorbike_svg);
        alert.show();
    }

    private void handleSelectLevelSeri(int pos){
        seriVal = pos;
        String level = seri[pos].toString();
        txtseri.setText(level);
        seriID = listSeri.get(pos).getId();
    }

    private void init(String id){
        for (int i=0;i<listKategori.size();i++){
            String catId = listKategori.get(i).getId();
            if (id.equals(catId)){
                handleSelectCategoryKategori(i);
            }
        }
    }

    public void initKategori(List<Category> listKategori){
        this.listKategori = listKategori;
        kategori = new CharSequence[listKategori.size()];
        for (int i=0;i<listKategori.size();i++){
            kategori[i] = listKategori.get(i).getName();
        }

        if (seriID != null){
            init(seriID);
        }

    }


    public void initType(List<Category> listType){
        this.listKeterangan = listType;
        keterangan = new CharSequence[listType.size()];
        for (int i=0;i<listType.size();i++){
            keterangan[i] = listType.get(i).getName();
        }

    }

    public void initSeri(List<Category> listSeri){
        this.listSeri = listSeri;
        seri = new CharSequence[listSeri.size()];
        for (int i=0;i<listSeri.size();i++){
            seri[i] = listSeri.get(i).getName();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }



    @OnClick(R.id.btn_simpan)
    void saveMotor(){
//        showLoading(true);
        validate();
    }


    public void validate(){
        txtkategori.setError(null);
        txtket.setError(null);
        txtseri.setError(null);
        namaBrg.setError(null);
        txtMerk.setError(null);
        txtStok.setError(null);
        txtHarga.setError(null);


        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(txtkategori.getText().toString()) && txtkategori.getText().toString().equals("Kategori")){
            txtkategori.setError(strErrRequired);
            focusView = txtkategori;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtket.getText().toString()) && txtket.getText().toString().equals("Keterangan")){
            txtket.setError(strErrRequired);
            focusView = txtket;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtseri.getText().toString()) && txtseri.getText().toString().equals("Varian")){
            txtseri.setError(strErrRequired);
            focusView = txtseri;
            cancel = true;
        }
        if(TextUtils.isEmpty(namaBrg.getText().toString()) && namaBrg.getText().toString().equals("Nama Barang")){
            namaBrg.setError(strErrRequired);
            focusView = namaBrg;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtMerk.getText().toString()) && txtMerk.getText().toString().equals("Merk Barang")){
            txtMerk.setError(strErrRequired);
            focusView = txtMerk;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtStok.getText().toString()) && txtStok.getText().toString().equals("Jumlah Barang")){
            txtMerk.setError(strErrRequired);
            focusView = txtHarga;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtHarga.getText().toString()) && txtHarga.getText().toString().equals("Harga Barang")){
            txtHarga.setError(strErrRequired);
            focusView = txtHarga;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        }else {

            Random rand = new Random();
            int id = rand.nextInt(999);
            String id1 = "";
            if (txtkategori.getText().equals("Tenda Doom")){
                id1 = "DM";
            }
            if (txtkategori.getText().equals("Tenda Pramuka")){
                id1 = "PM";
            }
            if (txtkategori.getText().equals("Carrier")){
                id1 = "CR";
            }
            if (txtkategori.getText().equals("Accesories")){
                id1 = "AC";
            }

            barang.setIdbarang("WR"+id1+id);
            barang.setKategoriBarang(String.valueOf(txtkategori.getText()));
            barang.setKeteranganBarang(String.valueOf(txtket.getText()));
            barang.setNamaBarang(String.valueOf(namaBrg.getText()));
            barang.setMerkBarang(String.valueOf(txtMerk.getText()));
            barang.setStokBarang(Integer.valueOf(txtStok.getText().toString()));
            barang.setHargaBarang(Integer.valueOf(txtHarga.getText().toString()));
            barang.setUpdateTerakhir(System.currentTimeMillis());


            if (imgOriginal != null) {
                presenter.uploadAvatar(barang, imgSmall, imgOriginal);

            } else {
                presenter.savebarang(barang);
                Log.e(TAG, "validate: "+barang.getKategoriBarang());
            }

        }

    }

    public void succesSaveBarang() {
        showLoading(false);
        String title = "Barang disimpan";
        String desc = "Kami sedang melakukan update data barang";
        int icon = R.drawable.ic_alarm_on;
        showAlertDialog(title, desc, icon);
    }

    void showLoading(boolean b) {
        if(b){
            viewProgress.setVisibility(View.VISIBLE);
        }else {
            viewProgress.setVisibility(View.GONE);
        }
        Log.e(TAG, "showLoading: "+b );
    }

    private void showAlertDialog(String title, String desc, int icon) {
        final Intent intent = new Intent(this, MainAct.class);
        intent.putExtra("barang", "barang");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(desc)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    // continue with delete
                    dialog.dismiss();
                    startActivity(intent);

                })
                .setIcon(icon)
                .show();
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
        presenter.savebarang(barang);
    }

    public void successUpdateMotor(Barang barang) {
        showLoading(false);
        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
        BaseApplication.get(this).createMotorComponent(barang);
        finish();

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




}
