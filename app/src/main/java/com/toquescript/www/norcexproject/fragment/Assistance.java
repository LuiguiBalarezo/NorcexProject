package com.toquescript.www.norcexproject.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.toquescript.www.norcexproject.R;
import com.toquescript.www.norcexproject.adapter.SpinAdapter;
import com.toquescript.www.norcexproject.model.MeasuresRealmModel;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class Assistance extends Fragment {

    // You spinner view
    private Spinner mySpinner;
    // Custom Spinner adapter (ArrayAdapter<User>)
    // You can define as a private to use it in the all class
    // This is the object that is going to do the "magic"
    private SpinAdapter adapter;

    public Assistance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assistance, container, false);

        Realm realm = Realm.getDefaultInstance();

        RealmResults<MeasuresRealmModel> measuresRealmModels = realm.where(MeasuresRealmModel.class).findAll();

        adapter = new SpinAdapter(getActivity(), android.R.layout.simple_spinner_item, measuresRealmModels);
        mySpinner = (Spinner) view.findViewById(R.id.spinner1);
        mySpinner.setAdapter(adapter); // Set the custom adapter to the spinner
        // You can create an anonymous listener to handle the event when is selected an spinner item
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                MeasuresRealmModel user = adapter.getItem(position);
                // Here you can do the action you want to...
                Toast.makeText(getActivity(), "ID: " + user.id + "\nName: " + user.tipo,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        return view;
    }

}
