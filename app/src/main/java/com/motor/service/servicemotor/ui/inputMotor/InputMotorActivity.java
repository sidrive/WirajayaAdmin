package com.motor.service.servicemotor.ui.inputMotor;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.motor.service.servicemotor.R;
import com.motor.service.servicemotor.base.BaseActivity;
import com.motor.service.servicemotor.base.BaseApplication;
import com.motor.service.servicemotor.data.model.Category;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.remote.model.User;
import com.motor.service.servicemotor.ui.dialog.DialogUploadOption;
import com.motor.service.servicemotor.ui.main.MainAct;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Date;
import java.text.DateFormat;
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

/**
 * Created by ikun on 11/01/18.
 */

public class InputMotorActivity extends BaseActivity implements DialogUploadOption.OnDialogUploadOptionClickListener, EasyPermissions.PermissionCallbacks {

    private static final String TAG = "DetailMotorActivity";
    public static final int REQUST_CODE_CAMERA = 1002;
    public static final int REQUST_CODE_GALLERY = 1001;
    private static final int RC_CAMERA_PERM = 205;
    public static Uri mCapturedImageURI;

    @BindString(R.string.error_field_required)
    String strErrRequired;

    @Bind(R.id.viewPrrogress)
    LinearLayout viewProgress;

    @Bind(R.id.lnAddService)
    LinearLayout lnAddService;

    @Bind(R.id.chNo)
    RadioButton chNo;

    @Bind(R.id.chYes)
    RadioButton chYes;

    @Bind(R.id.lnKmratano)
    LinearLayout lnKmratano;

    @Bind(R.id.lnKmratayes)
    LinearLayout lnKmratayes;

    @Bind(R.id.txt_kmratamtrno)
    EditText kmratamtrno;

    @Bind(R.id.txt_kmjarakkerja)
    EditText kmjarakkerja;

    @Bind(R.id.txt_kmratakerja)
    EditText kmratakerja;

    @Bind(R.id.txtMerk)
    TextView txtmerk;

    @Bind(R.id.txtType)
    TextView txttype;

    @Bind(R.id.txtSeri)
    TextView txtseri;

    @Bind(R.id.imglogo)
    ImageView imglogo;

    @Bind(R.id.imageView2)
    ImageView imgAvatar;

    @Bind(R.id.txt_plat)
    TextView txtplat;


    @Bind(R.id.txt_norangka)
    TextView norangka;

    @Bind(R.id.txt_kmNow)
    TextView txtKmNow;

    @Bind(R.id.btn_pajak)
    Button btnpajak;

    @Bind(R.id.btn_tahunmtr)
    Button btnthnmtr;

    @Inject
    Motor motor;

    @Inject
    InputMotorPresenter presenter;

    @Inject
    User user;

    CharSequence[] merk;
    CharSequence[] type;
    CharSequence[] seri;

    List<Category> listMerk;
    List<Category> listType;
    List<Category> listSeri;

    int merkVal;
    int typeVal;
    int seriVal;

    String merkID;
    String typeID;
    String seriID;

    Calendar myCalendar;

    byte[] imgSmall;
    Uri imgOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_motor);
        ButterKnife.bind(this);

        myCalendar = Calendar.getInstance();


        initInputService();
    }

    private void initInputService() {
        lnAddService.setVisibility(View.GONE);
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
                .plus(new InputMotorModule(this))
                .inject(this);
        BaseApplication.get(this).createInputMotorComponent(this);
    }

    public static void startWithUser(Activity activity, final User user) {
        Intent intent = new Intent(activity, InputMotorActivity.class);

        BaseApplication.get(activity).createUserComponent(user);
        activity.startActivity(intent);
    }

    @OnClick(R.id.ln_merk)
    void showCategoryMerk(){
        showMerk();
    }

    @OnClick(R.id.ln_type)
    void showCategoryType(){
        showType();
    }

    @OnClick(R.id.ln_seri)
    void showCategoryVarian(){
        showSeri();
    }

    private void showMerk(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
        alert.setTitle("Pilih Merk Motor");
        alert.setSingleChoiceItems(merk, merkVal, (dialog, which) -> {
            handleSelectCategoryMerk(which);
            changeLogo();
            dialog.dismiss();
        });
        alert.setIcon(R.drawable.ic_2000px_motorbike_svg);
        alert.show();

    }

    private void changeLogo(){
        if((txtmerk.getText().toString()).equalsIgnoreCase("HONDA")){
            imglogo.setImageResource(R.drawable.ic_logo_honda);
        }if((txtmerk.getText().toString()).equalsIgnoreCase("YAMAHA")){
            imglogo.setImageResource(R.drawable.ic_logo_yamaha);
        }if((txtmerk.getText().toString()).equalsIgnoreCase("SUZUKI")){
            imglogo.setImageResource(R.drawable.ic_logo_suzuki);
        }if((txtmerk.getText().toString()).equalsIgnoreCase("KAWASAKI")){
            imglogo.setImageResource(R.drawable.ic_logo_kawasaki);
        }
    }

    private void handleSelectCategoryMerk(int pos){
        merkVal = pos;
        String merks = merk[pos].toString();
        txtmerk.setText(merks);
        presenter.getSubCategories(listMerk.get(pos).getId());
    }

    private void showType() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
        alert.setTitle("Pilih Type Motor");
        alert.setSingleChoiceItems(type, typeVal, (dialog, which) -> {
            handleSelectSubCategoryType(which);
            dialog.dismiss();

        });
        alert.setIcon(R.drawable.ic_2000px_motorbike_svg);
        alert.show();
    }

    private void handleSelectSubCategoryType(int pos){
        typeVal = pos;
        String types = type[pos].toString();
        txttype.setText(types);

        merkID = listType.get(pos).getId();
        presenter.getLevels(listType.get(pos).getSeri());
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
        for (int i=0;i<listMerk.size();i++){
            String catId = listMerk.get(i).getId();
            if (id.equals(catId)){
                handleSelectCategoryMerk(i);
            }
        }
    }

    public void initMerk(List<Category> listMerk){
        this.listMerk = listMerk;
        merk = new CharSequence[listMerk.size()];
        for (int i=0;i<listMerk.size();i++){
            merk[i] = listMerk.get(i).getName();
        }

        if (seriID != null){
            init(seriID);
        }

    }


    public void initType(List<Category> listType){
        this.listType = listType;
        type = new CharSequence[listType.size()];
        for (int i=0;i<listType.size();i++){
            type[i] = listType.get(i).getName();
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

    @OnClick(R.id.btn_pajak)
    void initTahunpajak(){
        btnpajak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(InputMotorActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        myCalendar.set(Calendar.YEAR,year);

                        String formatTanggal = "dd MMMM y";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        btnpajak.setText(sdf.format(myCalendar.getTime()));
                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    public void validate(){
        txtmerk.setError(null);
        txttype.setError(null);
        txtseri.setError(null);
        txtplat.setError(null);
        btnpajak.setError(null);
        btnthnmtr.setError(null);

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(txtmerk.getText().toString()) && txtmerk.getText().toString().equals("Merk Motor")){
            txtmerk.setError(strErrRequired);
            focusView = txtmerk;
            cancel = true;
        }
        if(TextUtils.isEmpty(txttype.getText().toString()) && txttype.getText().toString().equals("Type Motor")){
            txttype.setError(strErrRequired);
            focusView = txttype;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtseri.getText().toString()) && txtseri.getText().toString().equals("Varian")){
            txtseri.setError(strErrRequired);
            focusView = txtseri;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtplat.getText().toString())){
            txtplat.setError(strErrRequired);
            focusView = txtplat;
            cancel = true;
        }
        if(TextUtils.isEmpty(btnpajak.getText().toString())){
            btnpajak.setError(strErrRequired);
            focusView = btnpajak;
            cancel = true;
        }
        if(TextUtils.isEmpty(btnthnmtr.getText().toString())){
            btnthnmtr.setError(strErrRequired);
            focusView = btnthnmtr;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtKmNow.getText().toString())){
            txtKmNow.setError(strErrRequired);
            focusView = txtKmNow;
            cancel = true;
        }
        if(chYes.isChecked()){
            if(TextUtils.isEmpty(kmjarakkerja.getText().toString())){
                kmjarakkerja.setError(strErrRequired);
                focusView = kmjarakkerja;
                cancel = true;
            }
        }
        if(chNo.isChecked()){
            if(TextUtils.isEmpty(kmratamtrno.getText().toString())){
                kmratamtrno.setError(strErrRequired);
                focusView = kmratamtrno;
                cancel = true;
            }
        }
        if (cancel) {
            focusView.requestFocus();
        }else {

            motor.setUserid(user.getUid());
            motor.setIdmotor(String.valueOf(txtmerk.getText())+String.valueOf(txtplat.getText()).toUpperCase());
            motor.setMerk(String.valueOf(txtmerk.getText()));
            motor.setType(String.valueOf(txttype.getText()));
            motor.setSeri(String.valueOf(txtseri.getText()));
            motor.setPlat(String.valueOf(txtplat.getText()).toUpperCase());
            motor.setTahun_buat(String.valueOf(btnthnmtr.getText()));
            motor.setNo_rangka(String.valueOf(norangka.getText()));

            Log.e(TAG, "validate before: "+myCalendar);
            myCalendar.add(Calendar.YEAR,1);
            motor.setTahun_pajak(myCalendar.getTimeInMillis());
            Log.e(TAG, "validate after: "+myCalendar );

            motor.setKm_now(Integer.valueOf(txtKmNow.getText().toString()));
            if(chYes.isChecked()){
                if(TextUtils.isEmpty(kmratakerja.getText().toString())){
                    motor.setKm_ratarata(Integer.valueOf(kmjarakkerja.getText().toString())+0);
                } else {
                    motor.setKm_ratarata(Integer.valueOf(kmjarakkerja.getText().toString())+Integer.valueOf(kmratakerja.getText().toString()));
                }
            }

            if (imgOriginal != null) {
                presenter.uploadAvatar(motor, imgSmall, imgOriginal);
            } else {
                presenter.savemotor(motor);
            }

        }

    }

    public void succesSaveMotor() {
        showLoading(false);
        String title = "Motor disimpan";
        String desc = "Kami sedang melakukan update data motor";
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


    @OnClick(R.id.btn_tahunmtr)
    void showYear(){
        final AlertDialog.Builder d = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.year_picker_dialog, null);
        d.setTitle("Pilih Tahun Pembuatan Motor Sesuai BPKB");
        d.setView(dialogView);
        final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
        int yearNow = myCalendar.get(Calendar.YEAR);
        Log.e("InputMotorActivity", "showYear: " + myCalendar.get(Calendar.YEAR));
        numberPicker.setMaxValue(yearNow);
        numberPicker.setMinValue(2004);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
//                Log.d(TAG, "onValueChange: ");
            }
        });
        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e("activity", "onClick: " + numberPicker.getValue());
                btnthnmtr.setText(String.valueOf(numberPicker.getValue()));
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
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
            motor.setPhoto_url(url);
        }
        presenter.savemotor(motor);
    }

    public void successUpdateMotor(Motor motor) {
        showLoading(false);
        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
        BaseApplication.get(this).createMotorComponent(motor);
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

    @OnCheckedChanged(R.id.chYes)
    void showMotorutama(){
        if(chYes.isChecked()){
            lnKmratayes.setVisibility(View.VISIBLE);
            if(lnKmratano.getVisibility() == View.VISIBLE){
                lnKmratano.setVisibility(View.GONE);
            }
        }

    }

    @OnCheckedChanged(R.id.chNo)
    void showMotor(){
        if(chNo.isChecked()){
            lnKmratano.setVisibility(View.VISIBLE);
            if(lnKmratayes.getVisibility() == View.VISIBLE){
                lnKmratayes.setVisibility(View.GONE);
            }

        }

    }


}
