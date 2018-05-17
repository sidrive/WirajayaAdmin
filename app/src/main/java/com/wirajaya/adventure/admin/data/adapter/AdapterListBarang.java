package com.wirajaya.adventure.admin.data.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.wirajaya.adventure.admin.R;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.data.remote.CategoryService;
import com.wirajaya.adventure.admin.ui.editbarang.EditBarangActivity;
import com.wirajaya.adventure.admin.ui.main.MainAct;
import com.wirajaya.adventure.admin.ui.mainfragment.TendaFragment;
import com.wirajaya.adventure.admin.utils.DateFormater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.crashlytics.android.core.CrashlyticsCore.TAG;

public class AdapterListBarang extends Adapter<AdapterListBarang.ViewHolder> {

    private Context mcontext;
    private List<Barang> mitem;
    private MainAct activity;

    CategoryService categoryService = new CategoryService();

    public AdapterListBarang(ArrayList<Barang> item, Context context, MainAct activity){
        this.mcontext = context;
        this.mitem = item;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.list_item_barang, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Barang barang = getItem(position);
        Log.e(TAG, "onBindViewHolder: "+ barang);
        if(barang.getKategoriBarang().equals("Accesories")){
            holder.txtNamaBrg.setText(barang.getNamaBarang()+" "+barang.getMerkBarang()+" "+barang.getKeteranganBarang());
        }else {
            holder.txtNamaBrg.setText(barang.getKategoriBarang()+" "+barang.getNamaBarang()+" "+barang.getMerkBarang()+" "+barang.getKeteranganBarang());
        }

        String tglUpdate;

        if(barang.getUpdateTerakhir() == null){
            tglUpdate = null;
        }else{
            tglUpdate = DateFormater.getDate(barang.getUpdateTerakhir(),"d MMMM y H:mm");
        }


        holder.txtHarga.setText("Rp. "+barang.getHargaBarang()+"/hari");
        holder.txtStok.setText(String.valueOf(barang.getStokBarang()));
        holder.txtTglUpdateBrg.setText(tglUpdate);

        initProfilePhoto(barang,holder);

        holder.btnUpdateStok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(mcontext);
                View promptsView = li.inflate(R.layout.update_stok, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mcontext);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.txtUpdateStok);
                userInput.setText(String.valueOf(barang.getStokBarang()));
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Update",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                    barang.setStokBarang(Integer.valueOf(userInput.getText().toString()));
                                    barang.setUpdateTerakhir(System.currentTimeMillis());
                                        showLoading(true,holder.viewProgress);
                                        updateStok(barang,holder);

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        holder.imgPhotoBrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(barang.getPhoto_url() != null){
                    if(!barang.getPhoto_url().equalsIgnoreCase("NOT")){
                        String list = barang.getPhoto_url();

                        new ImageViewer.Builder(mcontext, Collections.singletonList(list))
                                .setStartPosition(1)
                                .hideStatusBar(true)
                                .allowZooming(true)
                                .allowSwipeToDismiss(true)
                                .setBackgroundColorRes(R.color.com_facebook_blue)
                                .setContainerPadding(mcontext, R.dimen.activity_half_margin)
                                .show();
                    }
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        return mitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        @Bind(R.id.txtNamaBrg)
        TextView txtNamaBrg;

        @Bind(R.id.txtHarga)
        TextView txtHarga;

        @Bind(R.id.txtStok)
        TextView txtStok;

        @Bind(R.id.txtTglUpdateBrg)
        TextView txtTglUpdateBrg;

        @Bind(R.id.btnUpdateStok)
        Button btnUpdateStok;

        @Bind(R.id.imgPhotoBrg)
        ImageView imgPhotoBrg;

        @Bind(R.id.view_progress)
        LinearLayout viewProgress;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.setIsRecyclable(false);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Barang barang = getItem(this.getAdapterPosition());
            Log.e(TAG, "onClick: "+ activity);
            EditBarangActivity.startWithBarang(activity, barang);
        }
    }

    private Barang getData(int adptPosition){
        return mitem.get(adptPosition);
    }

    @Nullable
    public Barang getItem(int position) {
        return mitem.get(position);
    }

    public void UpdateMotor(List<Barang> listarray) {
        mitem = listarray;
//        notifyDataSetChanged();
    }

    public void updateStok(Barang barang, ViewHolder holder){

        categoryService.saveBarang(barang).addOnCompleteListener(task -> succesUpdateStok(holder)).addOnFailureListener(e -> {
            showLoading(false,holder.viewProgress);
            Toast.makeText(mcontext, "Gagal menyimpan barang", Toast.LENGTH_SHORT).show();
        });

    }

    public void succesUpdateStok(ViewHolder holder) {
        showLoading(false,holder.viewProgress);
        String title = "Barang disimpan";
        String desc = "Kami sedang melakukan update data barang";
        int icon = R.drawable.ic_alarm_on;
        showAlertDialog(title, desc, icon);
    }



    private void showAlertDialog(String title, String desc, int icon) {
        final Intent intent = new Intent(mcontext, MainAct.class);
        intent.putExtra("barang", "barang");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        new android.support.v7.app.AlertDialog.Builder(mcontext)
                .setTitle(title)
                .setMessage(desc)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    // continue with delete
                    dialog.dismiss();
                    mcontext.startActivity(intent);

                })
                .setIcon(icon)
                .show();
    }

    public void initProfilePhoto(Barang barang, ViewHolder holder){
        if (barang.getPhoto_url() != null) {
            if (!barang.getPhoto_url().equalsIgnoreCase("NOT")){
                Glide.with(mcontext)
                        .load(barang.getPhoto_url()).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e("IMAGE_EXCEPTION", "Exception " + e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.d("smtime img's not loaded",  "n dis tex's not di");
                        return false;
                    }
                });
                        /*.placeholder(R.color.colorSoft)
                        .dontAnimate()
                        .into(imgAvatar);*/

                Glide.with(mcontext)
                        .load(barang.getPhoto_url())
                        .placeholder(R.color.colorSoft)
                        .dontAnimate()
                        .into(holder.imgPhotoBrg);
            }
        }
    }

    public void showLoading(boolean b, LinearLayout viewProgress) {
        if(b){
            viewProgress.setVisibility(View.VISIBLE);
        }else {
            viewProgress.setVisibility(View.GONE);
        }
        Log.e("MainAct", "showLoading: "+b );
    }
}
