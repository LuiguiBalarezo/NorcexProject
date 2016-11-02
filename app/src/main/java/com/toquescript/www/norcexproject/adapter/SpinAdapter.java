package com.toquescript.www.norcexproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.toquescript.www.norcexproject.model.MeasuresRealmModel;

import java.util.List;

/**
 * Created by BALAREZO on 16/06/2016.
 */
public class SpinAdapter extends ArrayAdapter<MeasuresRealmModel>{

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private List<MeasuresRealmModel> values;

    public SpinAdapter(Context context, int textViewResourceId, List<MeasuresRealmModel> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public MeasuresRealmModel getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setPaddingRelative(20,20,20,20);
        label.setGravity(Gravity.CENTER);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values.get(position).tipo);

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setPaddingRelative(20,20,20,20);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).tipo);

        return label;
    }
}
