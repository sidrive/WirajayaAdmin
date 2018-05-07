package com.motor.service.servicemotor.data.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.motor.service.servicemotor.R;
import com.motor.service.servicemotor.data.model.Service;
import com.motor.service.servicemotor.ui.historyservice.HistoryServiceActivity;
import com.motor.service.servicemotor.utils.DateFormater;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.GraphRequest.TAG;

public class AdapterService extends RecyclerView.Adapter<AdapterService.ViewHolder> {

    private Context mcontext;
    private List<Service> mitem;
    private HistoryServiceActivity activity;

    public AdapterService(ArrayList<Service> item, Context context, HistoryServiceActivity activity){
        this.mcontext = context;
        this.mitem = item;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.list_item_service, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Service service = getItem(position);
        Log.e(TAG, "onBindViewHolder: "+service );

        String tglService = DateFormater.getDate(service.getTglService(),"d MMMM Y");
        if(service.getPhoto_url() == null){
            holder.btnViewNota.setVisibility(View.GONE);
        }

        holder.txtIdmotor.setText(service.getIdmotor());
        holder.txtJenisService.setText(service.getJenisService());
        holder.tglService.setText(tglService);
        holder.txtKetService.setText(service.getKeterangan());
        holder.txtkmservice.setText(String.valueOf(service.getKmservice())+" KM");

        holder.btnViewNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String list = service.getPhoto_url();

                new ImageViewer.Builder(mcontext, Collections.singletonList(list))
                        .setStartPosition(1)
                        .hideStatusBar(true)
                        .allowZooming(true)
                        .allowSwipeToDismiss(true)
                        .setBackgroundColorRes(R.color.com_facebook_blue)
                        .setContainerPadding(mcontext, R.dimen.activity_half_margin)
                        .show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.txtIdmotor)
        TextView txtIdmotor;

        @Bind(R.id.txtJenisService)
        TextView txtJenisService;

        @Bind(R.id.txtKetService)
        TextView txtKetService;

        @Bind(R.id.txtkmservice)
        TextView txtkmservice;

        @Bind(R.id.txttglservice)
        TextView tglService;

        @Bind(R.id.btnViewNota)
        Button btnViewNota;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.setIsRecyclable(false);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private Service getData(int adptPosition){
        return mitem.get(adptPosition);
    }

    @Nullable
    public Service getItem(int position) {
        return mitem.get(position);
    }

    public void UpdateService(List<Service> listarray) {
        mitem = listarray;
        notifyDataSetChanged();
    }
}
