package com.wirajaya.adventure.admin.ui.mainfragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.wirajaya.adventure.admin.R;
import com.wirajaya.adventure.admin.data.adapter.AdapterListBarang;
import com.wirajaya.adventure.admin.data.model.Barang;
import com.wirajaya.adventure.admin.data.remote.CategoryService;
import com.wirajaya.adventure.admin.data.remote.model.User;
import com.wirajaya.adventure.admin.ui.main.MainAct;

import java.util.ArrayList;
import java.util.List;

import static com.crashlytics.android.core.CrashlyticsCore.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TendaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TendaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AdapterListBarang adapterListBarang;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TendaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TendaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TendaFragment newInstance(String param1, String param2) {
        TendaFragment fragment = new TendaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_tenda, container, false);
        View view = inflater.inflate(R.layout.fragment_tenda, container, false);
        RecyclerView lsbarang = (RecyclerView) view.findViewById(R.id.listbarang);

        initRecycleView(lsbarang);
        getTenda(lsbarang);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void initRecycleView(RecyclerView lsbarang) {

        lsbarang.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        lsbarang.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        lsbarang.setLayoutManager(new LinearLayoutManager(getActivity()));
//        lsbarang.setLayoutManager(layoutManager);
        lsbarang.setNestedScrollingEnabled(false);

    }

    public void getTenda(RecyclerView lsbarang){
        CategoryService categoryService = new CategoryService();

        categoryService.getTendaDom().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Barang> listBarang = new ArrayList<Barang>();
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Barang barang = postSnapshot.getValue(Barang.class);
//                    Log.e("MainPresenter", "onDataChange: " + dataSnapshot.getChildren());
                    listBarang.add(barang);
                    Log.e("MainPresenter", "onDataChange: " + listBarang);
                }

                initListTendaDoom(listBarang,lsbarang);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void initListTendaDoom(List<Barang> listBarang, RecyclerView lsbarang){
        adapterListBarang = new AdapterListBarang((ArrayList<Barang>) listBarang,getContext(), (MainAct) getActivity());
//        adapterStatusMotor.UpdateMotor(listBarang);
        lsbarang.setAdapter(adapterListBarang);
    }

}
