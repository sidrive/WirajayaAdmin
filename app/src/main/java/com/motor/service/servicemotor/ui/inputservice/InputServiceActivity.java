package com.motor.service.servicemotor.ui.inputservice;

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
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.motor.service.servicemotor.R;
import com.motor.service.servicemotor.base.BaseActivity;
import com.motor.service.servicemotor.base.BaseApplication;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.model.Service;
import com.motor.service.servicemotor.ui.dialog.DialogUploadOption;
import com.motor.service.servicemotor.ui.main.MainAct;
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

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class InputServiceActivity extends BaseActivity implements DialogUploadOption.OnDialogUploadOptionClickListener, EasyPermissions.PermissionCallbacks{

    private static final String TAG = "InputService";
    public static final int REQUST_CODE_CAMERA = 1002;
    public static final int REQUST_CODE_GALLERY = 1001;
    private static final int RC_CAMERA_PERM = 205;
    public static Uri mCapturedImageURI;

    CharSequence[] charJenService;
    CharSequence[] charKetService;
    int serviceVal = 4;
    int ketVal = 3;

    Calendar myCalender;

    @BindString(R.string.error_field_required)
    String strErrRequired;

    @Bind(R.id.rdNotaBaru)
    RadioButton rdNotaBaru;

    @Bind(R.id.rdNotaLama)
    RadioButton rdNotaLama;

    @Bind(R.id.txtMotor)
    TextView txtMotor;

    @Bind(R.id.input_jenis_service)
    EditText txtJenisService;

    @Bind(R.id.input_ketservice)
    EditText txtKetService;

    @Bind(R.id.layout_jenis_service)
    TextInputLayout lnJenisService;

    @Bind(R.id.layout_service)
    TextInputLayout lnService;

    @Bind(R.id.lnPart)
    LinearLayout lnPart;

    @Bind(R.id.txt_part)
    EditText txtPart;

    @Bind(R.id.txt_kmservice)
    EditText txtKmService;

    @Bind(R.id.btn_tglservice)
    Button btnTglService;

    @Bind(R.id.imageView2)
    ImageView imgAvatar;

    @Bind(R.id.imgNota)
    ImageView imgNota;


    @Inject
    Motor motor;

    @Inject
    Service service;

    @Inject
    InputServicePresenter presenter;

    byte[] imgSmall;
    Uri imgOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_service);
        ButterKnife.bind(this);
        Log.e(TAG, "onCreate: "+service );

        myCalender = Calendar.getInstance();
        txtMotor.setText(motor.getSeri().toString().toUpperCase()+" "+motor.getPlat().toString().toUpperCase());
        charJenService = getResources().getStringArray(R.array.list_jenisservice);
        charKetService = getResources().getStringArray(R.array.list_ketservice);

        initPicMotor();
    }

    private void initPicMotor() {
        if(motor.getPhoto_url() != null){
            if (!motor.getPhoto_url().equals("NOT")) {
                Glide.with(this)
                        .load(motor.getPhoto_url())
                        .placeholder(R.color.colorSoft)
                        .dontAnimate()
                        .into(imgAvatar);
            }
        }
    }


    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getMotorComponent()
                .plus(new InputServiceActivityModule(this))
                .inject(this);
    }

    public static void startWithMotor(Activity activity, final Motor motor) {
        Intent intent = new Intent(activity, InputServiceActivity.class);

        BaseApplication.get(activity).createMotorComponent(motor);
        activity.startActivity(intent);
    }

    @OnClick(R.id.input_jenis_service)
    void initJenisService(){
        showJenisService();
    }

    @OnClick(R.id.input_ketservice)
    void initKetService(){
        showKetService();
    }

    private void showJenisService() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Jenis Service");
        alert.setSingleChoiceItems(charJenService, serviceVal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String whichIs = charJenService[which].toString();

                txtJenisService.setText(whichIs);
                serviceVal = which;
                if(whichIs.equals("Service & Ganti Oli")){
                    disableKetService(true,1);
                }
                else if(whichIs.equals("Ganti Oli")){
                    disableKetService(true,2);
                }else{
                    disableKetService(false,0);
                }

                dialog.dismiss();

            }
        });
        alert.show();
    }

    private void disableKetService(Boolean bool,int serv) {
        if(bool){
            if(serv == 1){
                lnService.setVisibility(View.GONE);
                lnPart.setVisibility(View.GONE);
                txtKetService.setText("Paket");
                txtPart.setText("Service");
            }
            else if(serv == 2){
                lnService.setVisibility(View.GONE);
                lnPart.setVisibility(View.GONE);
                txtKetService.setText("Ganti");
                txtPart.setText("Oli");
            }

        }else{
            lnService.setVisibility(View.VISIBLE);
            lnPart.setVisibility(View.VISIBLE);
        }

    }

    private void showKetService() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Keterangan Service");
        alert.setSingleChoiceItems(charKetService, ketVal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String whichIs = charKetService[which].toString();

                txtKetService.setText(whichIs);
                ketVal = which;

                dialog.dismiss();

            }
        });
        alert.show();
    }

    @OnClick(R.id.btn_tglservice)
    void initTglService(){
        showDialogDate();
    }

    private void showDialogDate() {
        btnTglService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(InputServiceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        myCalender.set(Calendar.MONTH, month);
                        myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        myCalender.set(Calendar.YEAR,year);

                        String formatTanggal = "dd MMMM y";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        btnTglService.setText(sdf.format(myCalender.getTime()));
                    }
                },
                        myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH),
                        myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    @OnClick(R.id.btn_simpan)
    void saveService(){

        if(rdNotaBaru.isChecked()){
            validate();
        }
        if(rdNotaLama.isChecked()){
            validateLama();
        }

    }

    private void validate() {
        txtJenisService.setError(null);
        txtKetService.setError(null);
        txtKmService.setError(null);
        txtPart.setError(null);
        btnTglService.setError(null);

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(txtJenisService.getText().toString()) || txtJenisService.getText().toString().equals("Pilih Jenis Service")){
            txtJenisService.setError(strErrRequired);
            focusView = txtJenisService;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtKetService.getText().toString()) || txtKetService.getText().toString().equals("Pilih Keterangan Service")){
            txtKetService.setError(strErrRequired);
            focusView = txtKetService;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtPart.getText().toString())){
            txtPart.setError(strErrRequired);
            focusView = txtPart;
            cancel = true;
        }
        if(TextUtils.isEmpty(btnTglService.getText().toString()) || btnTglService.getText().toString().equals("Tanggal Service")){
            btnTglService.setError(strErrRequired);
            focusView = btnTglService;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtKmService.getText().toString())){
            txtKmService.setError(strErrRequired);
            focusView = txtKmService;
            cancel = true;
        }
        if(cancel){
            focusView.requestFocus();
        }else{

            Random rand = new Random();
            String oid = Integer.toString(rand.nextInt(99999));

            service.setIdmotor(motor.getIdmotor().toString());
            service.setIdservice(motor.getIdmotor().toString()+String.valueOf(oid));
            service.setJenisService(txtJenisService.getText().toString());
            service.setKeterangan(txtKetService.getText().toString()+" "+txtPart.getText().toString());
            service.setTglService(myCalender.getTimeInMillis());
            service.setKmservice(Integer.valueOf(txtKmService.getText().toString()));

            motor.setKm_now(Integer.valueOf(txtKmService.getText().toString()));
            motor.setTgl_service(myCalender.getTimeInMillis());

            if(txtJenisService.getText().toString().equals("Service & Ganti Oli")){
                motor.setKm_NextService(Integer.valueOf(txtKmService.getText().toString())+2500);

                if (imgOriginal != null) {
                    presenter.uploadAvatar(motor,service, imgSmall, imgOriginal);
                    Log.e(TAG, "validate: "+motor.getKm_NextService());

                } else {
                    presenter.updateMotor(motor,service);
                    Log.e(TAG, "validate: "+motor.getKm_NextService());
                }

            }if(txtJenisService.getText().toString().equals("Ganti Oli")){
                motor.setKm_NextService(Integer.valueOf(txtKmService.getText().toString())+2500);

                if (imgOriginal != null) {
                    presenter.uploadAvatar(motor,service, imgSmall, imgOriginal);
                    Log.e(TAG, "validate: "+motor.getKm_NextService());

                } else {
                    presenter.updateMotor(motor,service);
                    Log.e(TAG, "validate: "+motor.getKm_NextService());
                }

            }else{

                if (imgOriginal != null) {
                    presenter.uploadAvatar(motor,service, imgSmall, imgOriginal);

                } else {
                    presenter.updateMotor(motor,service);
                }
            }

        }
    }

    private void validateLama() {
        txtJenisService.setError(null);
        txtKetService.setError(null);
        txtKmService.setError(null);
        txtPart.setError(null);
        btnTglService.setError(null);

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(txtJenisService.getText().toString()) || txtJenisService.getText().toString().equals("Pilih Jenis Service")){
            txtJenisService.setError(strErrRequired);
            focusView = txtJenisService;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtKetService.getText().toString()) || txtKetService.getText().toString().equals("Pilih Keterangan Service")){
            txtKetService.setError(strErrRequired);
            focusView = txtKetService;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtPart.getText().toString())){
            txtPart.setError(strErrRequired);
            focusView = txtPart;
            cancel = true;
        }
        if(TextUtils.isEmpty(btnTglService.getText().toString()) || btnTglService.getText().toString().equals("Tanggal Service")){
            btnTglService.setError(strErrRequired);
            focusView = btnTglService;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtKmService.getText().toString())){
            txtKmService.setError(strErrRequired);
            focusView = txtKmService;
            cancel = true;
        }
        if(cancel){
            focusView.requestFocus();
        }else{

            Random rand = new Random();
            String oid = Integer.toString(rand.nextInt(99999));

            service.setIdmotor(motor.getIdmotor().toString());
            service.setIdservice(motor.getIdmotor().toString()+String.valueOf(oid));
            service.setJenisService(txtJenisService.getText().toString());
            service.setKeterangan(txtKetService.getText().toString()+" "+txtPart.getText().toString());
            service.setTglService(myCalender.getTimeInMillis());
            service.setKmservice(Integer.valueOf(txtKmService.getText().toString()));



            if(txtJenisService.getText().toString().equals("Service & Ganti Oli")){

                if (imgOriginal != null) {
                    presenter.uploadAvatar(motor,service, imgSmall, imgOriginal);
                    Log.e(TAG, "validate: "+motor.getKm_NextService());

                } else {
                    presenter.updateMotor(motor,service);
                    Log.e(TAG, "validate: "+motor.getKm_NextService());
                }

            }if(txtJenisService.getText().toString().equals("Ganti Oli")){

                if (imgOriginal != null) {
                    presenter.uploadAvatar(motor,service, imgSmall, imgOriginal);
                    Log.e(TAG, "validate: "+motor.getKm_NextService());

                } else {
                    presenter.updateMotor(motor,service);
                    Log.e(TAG, "validate: "+motor.getKm_NextService());
                }

            }else{

                if (imgOriginal != null) {
                    presenter.uploadAvatar(motor,service, imgSmall, imgOriginal);

                } else {
                    presenter.updateMotor(motor,service);
                }
            }

        }
    }


    public void successUploadImage(String url,Motor motor) {
        if (url != null) {
            service.setPhoto_url(url);
        }
        presenter.updateMotor(motor,service);
    }

    public void succesSaveMotor(Service service){
        presenter.saveService(service);
    }

    public void succesSaveService() {
        showLoading(false);
        String title = "Service disimpan";
        String desc = "Kami sedang melakukan update data service";
        int icon = R.drawable.ic_alarm_on;
        showAlertDialog(title, desc, icon);
    }

    void showLoading(boolean b) {
//        if(b){
//            viewProgress.setVisibility(View.VISIBLE);
//        }else {
//            viewProgress.setVisibility(View.GONE);
//        }
//        Log.e(TAG, "showLoading: "+b );
    }

    private void showAlertDialog(String title, String desc, int icon) {
        final Intent intent = new Intent(this, MainAct.class);
        intent.putExtra("motor", "motor");
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
                    imgNota.setImageBitmap(bitmap2);
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

    @OnClick(R.id.btnaddNota)
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
