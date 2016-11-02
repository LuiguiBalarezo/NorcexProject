package com.toquescript.www.norcexproject.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toquescript.www.norcexproject.MainActivity;
import com.toquescript.www.norcexproject.R;
import com.toquescript.www.norcexproject.adapter.ObjectRecyclerViewAdapter;
import com.toquescript.www.norcexproject.base.BaseFragment;
import com.toquescript.www.norcexproject.model.ObjectRealmModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class Epps extends BaseFragment{


    public Epps() {
        // Required empty public constructor

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*REALM*/
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_objects, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_objecs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        objectList = realm.where(ObjectRealmModel.class).equalTo("id_tipo", "3").findAll();
        objectRecyclerViewAdapter = new ObjectRecyclerViewAdapter(getActivity(), ((MainActivity)getActivity()), objectList,this);
        recyclerView.setAdapter(objectRecyclerViewAdapter);
    }

}
