package com.motor.service.servicemotor.data.adapter;

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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.motor.service.servicemotor.R;
import com.motor.service.servicemotor.data.remote.model.User;
import com.motor.service.servicemotor.ui.main.MainAct;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.GraphRequest.TAG;

public class AdapterProfileUser extends Adapter<AdapterProfileUser.ViewHolder> {

    private Context mcontext;
    private List<User> mitem;
    private MainAct activity;

    public AdapterProfileUser(ArrayList<User> item, Context context, MainAct activity){
        this.mcontext = context;
        this.mitem = item;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.list_profile, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = getItem(position);
        Log.e(TAG, "onBindViewHolder: "+user.getNomor_sim());

        holder.txtphone.setText(user.getPhone());
        holder.txtemail.setText(user.getEmail());
        holder.txtnosim.setText(user.getNomor_sim());
        holder.txtjmlmotor.setText(String.valueOf(user.getTotalMotor()));

    }

    @Override
    public int getItemCount() {
        return mitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        @Bind(R.id.txtphone)
        TextView txtphone;

        @Bind(R.id.txtemail)
        TextView txtemail;

        @Bind(R.id.txtnosim)
        TextView txtnosim;

        @Bind(R.id.txtjmlmotor)
        TextView txtjmlmotor;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.setIsRecyclable(false);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    private User getData(int adptPosition){
        return mitem.get(adptPosition);
    }

    @Nullable
    public User getItem(int position) {
        return mitem.get(position);
    }

    public void UpdateUser(List<User> listarray) {
        mitem = listarray;
        notifyDataSetChanged();
    }
}
