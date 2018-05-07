package com.motor.service.servicemotor.ui.editprofil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.motor.service.servicemotor.R;
import com.motor.service.servicemotor.base.BaseActivity;
import com.motor.service.servicemotor.base.BaseApplication;
import com.motor.service.servicemotor.data.remote.model.User;
import com.motor.service.servicemotor.ui.dialog.DialogUploadOption;
import com.motor.service.servicemotor.ui.dialog.DialogUploadOption.OnDialogUploadOptionClickListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks;
import de.hdodenhof.circleimageview.CircleImageView;

import com.motor.service.servicemotor.ui.main.MainAct;
import com.motor.service.servicemotor.utils.DateFormater;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImage.ActivityResult;
import com.bumptech.glide.Glide;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;

/**
 * Created by ikun on 29/12/17.
 */

public class EditProfilActivity extends BaseActivity implements OnDateSetListener,
        OnDialogUploadOptionClickListener, PermissionCallbacks, OnCameraIdleListener, OnMapReadyCallback {

    private static final String TAG = "EditProfilActivity";
    public static final int REQUST_CODE_CAMERA = 1002;
    public static final int REQUST_CODE_GALLERY = 1001;
    private static final int RC_CAMERA_PERM = 205;
    private static final double[] TODO = {0,0};
    public static Uri mCapturedImageURI;

    @BindString(R.string.error_field_required)
    String strErrRequired;
    @BindColor(R.color.colorBlack)
    int colorBlack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Bind(R.id.input_name)
    EditText inputName;

    @Bind(R.id.layout_input_birthday)
    TextInputLayout inputLayoutBirthday;

    @Bind(R.id.layout_input_gender)
    TextInputLayout inputLayoutGender;

    @Bind(R.id.input_birthday)
    EditText inputBirthDay;

    @Bind(R.id.input_gender)
    EditText inputGender;

    @Bind(R.id.input_email)
    EditText inputEmail;

    @Bind(R.id.input_phone)
    EditText inputPhone;

    @Bind(R.id.input_alamat)
    EditText inputAlamat;

    @Bind(R.id.input_jalan)
    EditText inputJalan;

    @Bind(R.id.input_kota)
    EditText inputKota;

    @Bind(R.id.input_provinsi)
    EditText inputProvinsi;

    @Bind(R.id.input_kodepos)
    EditText inputKodepos;

    @Bind(R.id.input_sim)
    EditText inputSim;

    @Bind(R.id.img_avatar)
    CircleImageView imgAvatar;

    @Bind(R.id.imgMap)
    ImageView imgMap;

    @Bind(R.id.rel_map)
    RelativeLayout relMap;

    @Inject
    EditProfilPresenter presenter;

    @Inject
    User user;

    Calendar cal;

    private GoogleMap mMap;

    private boolean mapMode = false;

    private double latitude = 0;
    private double longitude = 0;
    SupportMapFragment mapFragment;

    MenuItem menuDone;

    CharSequence[] charGenders;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    int genderVal = 3;
    long dateBirthDay = 0;

    byte[] imgSmall;
    Uri imgOriginal;

    boolean register = false;

    private LocationManager lm;
    private android.location.Location mlocation;

//    private static final int RC_ALL_PERMISSION= 111;
//    private static final String[] PERMISION =
//            {Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.READ_CONTACTS,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.CAMERA
//            };

    public static void startWithUser(BaseActivity activity, final User user, boolean register) {
        Intent intent = new Intent(activity, EditProfilActivity.class);
        intent.putExtra("register", register);
        BaseApplication.get(activity).createUserComponent(user);
        activity.startActivity(intent);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cal = Calendar.getInstance();

        getCurrentLocationUser();

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setTitle("Ubah Data Profil");

        charGenders = getResources().getStringArray(R.array.list_gender);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            register = extras.getBoolean("register");
        }

        init();
    }

    @Override
    public void onBackPressed() {
        if (viewProgress.getVisibility() == View.GONE) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.done, menu);
        menuDone = menu.findItem(R.id.menu_done);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            /*finish();*/
            MainAct.startWithUser(this, user);
        }

        if (id == R.id.menu_done) {
            showLoading(true);
            validate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getUserComponent()
                .plus(new EditProfilActivityModule(this))
                .inject(this);

    }

    public void showLoading(boolean show) {
        if (show) {
            viewProgress.setVisibility(View.VISIBLE);
        } else {
            viewProgress.setVisibility(View.GONE);
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
            ActivityResult result = CropImage.getActivityResult(data);
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

    private void init() {
        inputEmail.setText(mUser.getEmail());
        inputEmail.setEnabled(false);
        /*if (user.getUid() != null) {
            presenter.getPayment(user.getUid());
        }*/

        if (user.getFull_name() != null) {
            inputName.setText(user.getFull_name());
        }
        if (user.getBirthday() != 0) {
            initBirthDay(user.getBirthday());
        }
        if (user.getGender() != null) {
            initGender(user.getGender());
        }
        if (user.getNomor_sim() != null) {
            inputSim.setText(user.getNomor_sim());
        }
        if (user.getPhone() != null) {
            inputPhone.setText(user.getPhone());
        }
        if (user.getFullAddress() != null) {
            inputAlamat.setText(user.getFullAddress());
        }
        if (user.getNamaJalan() != null) {
            inputJalan.setText(user.getNamaJalan());
        }
        if (user.getKota() != null) {
            inputKota.setText(user.getKota());
        }
        if (user.getProvinsi() != null) {
            inputProvinsi.setText(user.getProvinsi());
        }
        if (user.getKodepos() != null) {
            inputKodepos.setText(user.getKodepos());
        }
        if (user.getPhoto_url() != null) {
            if (!user.getPhoto_url().equals("NOT")) {
                Glide.with(this)
                        .load(user.getPhoto_url())
                        .placeholder(R.color.colorSoft)
                        .dontAnimate()
                        .into(imgAvatar);
            }
        }


        if (!register) {
            inputBirthDay.setEnabled(false);
            inputName.setEnabled(false);
            inputEmail.setEnabled(false);
            inputPhone.setEnabled(false);
        }
    }

    private void initGender(String i) {
        if (i.equalsIgnoreCase("M")) {
            genderVal = 0;
            inputGender.setText("Laki laki");
        }
        if (i.equalsIgnoreCase("F")) {
            genderVal = 1;
            inputGender.setText("Perempuan");
        }

    }

    private void initBirthDay(long timemilis) {
        dateBirthDay = timemilis;
        String date = DateFormater.getDate(dateBirthDay, "dd MMMM Y");
        inputBirthDay.setText(date);
    }

    // Onclick list -------------//

    @OnClick(R.id.input_birthday)
    void showBirthDay() {
        showDialogDatePicker();
    }

    @OnClick(R.id.input_gender)
    void showGender() {
        showDialogGender();
    }

    @OnClick(R.id.btn_upload_avatar)
    void showDialogUploadOption() {
        DialogUploadOption dialogUploadOption = new DialogUploadOption(this);
        dialogUploadOption.show();
    }

    // Onclick list -------------//

    private void showDialogDatePicker() {

        new android.app.DatePickerDialog(EditProfilActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        myCalendar.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                cal.set(Calendar.YEAR,year);

                String formatTanggal = "dd MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                inputBirthDay.setText(sdf.format(cal.getTime()));
            }
        },
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showDialogGender() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Jenis Kelamin");
        alert.setSingleChoiceItems(charGenders, genderVal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String whichIs = charGenders[which].toString();

                inputGender.setText(whichIs);
                genderVal = which;

                dialog.dismiss();

            }
        });
        alert.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        initBirthDay(calendar.getTimeInMillis());
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
                .setCropShape(CropImageView.CropShape.OVAL)
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
            user.setPhoto_url(url);
        }
        presenter.updateProfile(user);

    }

    public void successUpdateProfile(User user) {
        showLoading(false);
        if (register) {
            if (user.isAcceptTOS()) {
                /*VerificationActivity.startWithUser(this, user);*/
                MainAct.startWithUser(this, user);

            } else {
                MainAct.startWithUser(this, user);
            }
        } else {
            Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
            BaseApplication.get(this).createUserComponent(user);
            finish();
        }
    }

    public void validate() {
        inputName.setError(null);
        inputEmail.setError(null);
        inputGender.setError(null);
        inputBirthDay.setError(null);
        inputPhone.setError(null);
        inputAlamat.setError(null);
        inputJalan.setError(null);
        inputKota.setError(null);
        inputProvinsi.setError(null);
        inputKodepos.setError(null);
        inputSim.setError(null);

        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String gender = "";
        if (genderVal == 0) {
            gender = "M";
        }
        if (genderVal == 1) {
            gender = "F";
        }

        String phone = inputPhone.getText().toString();
        String alamat = inputAlamat.getText().toString();
        String jalan = inputJalan.getText().toString();
        String kota = inputKota.getText().toString();
        String provinsi = inputProvinsi.getText().toString();
        String kodepos = inputKodepos.getText().toString();
        String nosim = inputSim.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            inputName.setError(strErrRequired);
            focusView = inputName;
            cancel = true;
            showLoading(false);
        }

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError(strErrRequired);
            focusView = inputEmail;
            cancel = true;
            showLoading(false);
        } else {
            if (!isValidEmail(email)) {
                inputEmail.setError("Email not valid");
                focusView = inputEmail;
                cancel = true;
                showLoading(false);
            }
        }

        if (TextUtils.isEmpty(inputBirthDay.getText().toString())) {
            inputBirthDay.setError(strErrRequired);
            focusView = inputBirthDay;
            cancel = true;
            showLoading(false);
        }

        if (TextUtils.isEmpty(alamat)) {
            inputAlamat.setError(strErrRequired);
            focusView = inputAlamat;
            cancel = true;
            showLoading(false);
        }
        if (TextUtils.isEmpty(jalan)) {
            inputJalan.setError(strErrRequired);
            focusView = inputJalan;
            cancel = true;
            showLoading(false);
        }
        if (TextUtils.isEmpty(kota)) {
            inputKota.setError(strErrRequired);
            focusView = inputKota;
            cancel = true;
            showLoading(false);
        }
        if (TextUtils.isEmpty(provinsi)) {
            inputProvinsi.setError(strErrRequired);
            focusView = inputProvinsi;
            cancel = true;
            showLoading(false);
        }
        if (TextUtils.isEmpty(kodepos)) {
            inputKodepos.setError(strErrRequired);
            focusView = inputKodepos;
            cancel = true;
            showLoading(false);
        }
        if (TextUtils.isEmpty(nosim)) {
            inputSim.setError(strErrRequired);
            focusView = inputSim;
            cancel = true;
            showLoading(false);
        }

        if (!TextUtils.isEmpty(phone)) {
            if (!isValidPhoneNumber(phone)) {
                inputPhone.setError("Phone number not valid");
                focusView = inputPhone;
                cancel = true;
                showLoading(false);
            }
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            user.setAcceptTOS(true);
            user.setFull_name(name);
            user.setEmail(email);
            user.setFullAddress(alamat);
            if (!TextUtils.isEmpty(phone)) {
                user.setPhone(phone);
            }
            if (!TextUtils.isEmpty(jalan)) {
                user.setNamaJalan(jalan);
            }
            if (!TextUtils.isEmpty(kota)) {
                user.setKota(kota);
            }
            if (!TextUtils.isEmpty(provinsi)) {
                user.setProvinsi(provinsi);
            }
            if (!TextUtils.isEmpty(kodepos)) {
                user.setKodepos(kodepos);
            }
            if (!TextUtils.isEmpty(nosim)) {
                user.setNomor_sim(nosim);
            }

            if (genderVal != 3) {
                user.setGender(gender);
            }
            if (dateBirthDay != 0) {
                user.setBirthday(dateBirthDay);
            }
            if (register) {
                user.setCreatedAt(System.currentTimeMillis());
            }
            if (!register) {
                user.setUpdateAt(System.currentTimeMillis());
            }

            user.setBirthday(cal.getTimeInMillis());
            user.setUpdateAt(System.currentTimeMillis());


            if (imgOriginal != null) {
                presenter.uploadAvatar(user, imgSmall, imgOriginal);
            } else {
                presenter.updateProfile(user);
            }

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

    private boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean isValidPhoneNumber(CharSequence target) {
        Pattern mPattern = Pattern.compile("^08[0-9]{9,}$");
        if (target == null) {
            return false;
        } else {
            return mPattern.matcher(target).matches();
        }
    }

    private void getCurrentLocationUser() {
        lm = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isNetworkEnabled) {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
                mlocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Log.e(TAG, "getCurrentLocationUser: "+mlocation );
            } else if (isGPSEnabled) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
                mlocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Log.e(TAG, "getCurrentLocationUser: "+mlocation );
            }
        }
    }

    private android.location.LocationListener locationListener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

//        LatLng indonesia = new LatLng(-7.803249, 110.3398253);
        LatLng indonesia = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
        Log.e(TAG, "initMap: "+indonesia );
        initMap(indonesia);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 16));
        mMap.setOnCameraIdleListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.addMarker(new MarkerOptions()
                .position(mMap.getCameraPosition().target)
                .title("Marker"));
    }

    public void initMap(LatLng latLng) {

        if(user.getLatitude() != 0.0 && user.getLongitude() != 0.0){
            latitude = user.getLatitude();
            longitude = user.getLongitude();
        }else {

            latitude = latLng.latitude;
            longitude = latLng.longitude;
        }

        Log.e(TAG, "initMap: "+user.getLongitude());

        String url =
                "http://maps.googleapis.com/maps/api/staticmap?zoom=17&size=800x400&maptype=roadmap%20&markers=color:red%7Clabel:S%7C"
                        + latitude + "," + longitude + "+&sensor=false";
        Log.d("initmap", "url = " + url);
        Glide.with(this)
                .load(url)
                .placeholder(R.color.colorShadow2)
                .centerCrop()
                .dontAnimate()
                .into(imgMap);

    }

    @Override
    public void onCameraIdle() {
        LatLng latLng = mMap.getCameraPosition().target;
    }

    @OnClick(R.id.imgMap)
    void showMap() {
        relMap.setVisibility(View.VISIBLE);
        mapMode = true;
//        menuDone.setVisible(true);
    }

    @OnClick(R.id.input_peta)
    void showPeta(){
        relMap.setVisibility(View.VISIBLE);
        mapMode = true;
        menuDone.setVisible(false);
    }

    @OnClick(R.id.btnSimpanMap)
    void hideMap(){
        relMap.setVisibility(View.GONE);
        mapMode = false;
        menuDone.setVisible(true);
        LatLng latLng = mMap.getCameraPosition().target;
        Log.e(TAG, "hideMap: "+latLng );

        getAddress(latLng);
    }

    private void getAddress(LatLng latLng) {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "getAddress: "+addresses.get(0));
        String fulladdress = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getSubAdminArea();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

        String [] add = fulladdress.split(",");

        inputJalan.setText(add[0]);
        inputKota.setText(city);
        inputProvinsi.setText(state);
        inputKodepos.setText(postalCode);

        user.setLatitude(latLng.latitude);
        user.setLongitude(latLng.longitude);
    }

}
