package com.wirajaya.adventure.admin.data.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wirajaya.adventure.admin.R;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.ui.mainfragment.TendaFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.crashlytics.android.core.CrashlyticsCore.TAG;

public class AdapterListBarang extends Adapter<AdapterListBarang.ViewHolder> {

    private Context mcontext;
    private List<Barang> mitem;
    private TendaFragment activity;

    public AdapterListBarang(ArrayList<Barang> item, Context context, TendaFragment activity){
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

        holder.txtNamaBrg.setText("Doom "+barang.getNamaBarang()+" "+barang.getMerkBarang()+" "+barang.getKeteranganBarang());
        holder.txtHarga.setText("Rp. "+barang.getHargaBarang()+"/hari");
        holder.txtStok.setText(String.valueOf(barang.getStokBarang()));
        holder.txtTglUpdateBrg.setText("");


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



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.setIsRecyclable(false);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Barang barang = getItem(this.getAdapterPosition());
            Log.e(TAG, "onClick: "+ barang);
//            EditMotorActivity.startWithMotor(activity, barang);
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
}
