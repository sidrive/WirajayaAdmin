package com.wirajaya.adventure.admin.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;

import com.wirajaya.adventure.admin.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ikun on 29/12/17.
 */

public class DialogUploadOption extends Dialog {
    OnDialogUploadOptionClickListener mCallBack;
    private Context context;

    @BindView(R.id.btn_gallery)
    Button btnGallery;

    @BindView(R.id.btn_ambil_photo)
    Button btnTakeAPhoto;

    public interface OnDialogUploadOptionClickListener {
        public void onGalleryClicked(Dialog dialog);

        public void onCameraClicked(Dialog dialog);
    }

    public DialogUploadOption(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_upload_option);
        ButterKnife.bind(this);
        setCancelable(true);
        init();
        try {
            mCallBack = (OnDialogUploadOptionClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnDialogSchedluleClickListener");
        }

    }

    private void init() {

    }

    @OnClick(R.id.btn_gallery)
    void positifClicked() {
        mCallBack.onGalleryClicked(this);
    }

    @OnClick(R.id.btn_ambil_photo)
    void negatifClicked() {
        mCallBack.onCameraClicked(this);
    }

}
