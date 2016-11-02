package com.toquescript.www.norcexproject.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toquescript.www.norcexproject.R;
import com.toquescript.www.norcexproject.callbacks.ClickListenerRecyclerAdapter;

/**
 * Created by BALAREZO on 10/06/2016.
 */
public class ObjectViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{

    public RelativeLayout rlv_item_object;
    public TextView txt_nameobject, txt_number, txt_typemeasure;
    public RelativeLayout baseretaltive;

    private ClickListenerRecyclerAdapter clickListenerRecyclerAdapter;

    public ObjectViewHolder(View itemView, ClickListenerRecyclerAdapter clickListenerRecyclerAdapter) {
        super(itemView);
        rlv_item_object = (RelativeLayout)itemView.findViewById(R.id.rlv_item_object);
        txt_nameobject =  (TextView)itemView.findViewById(R.id.txt_nameobject);
        txt_number =  (TextView)itemView.findViewById(R.id.txt_number);
        txt_typemeasure =  (TextView)itemView.findViewById(R.id.txt_typemeasure);
        baseretaltive = (RelativeLayout) itemView.findViewById(R.id.rlv_item_object);

        this.clickListenerRecyclerAdapter = clickListenerRecyclerAdapter;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(clickListenerRecyclerAdapter !=null){
            clickListenerRecyclerAdapter.onItemClicked(getPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (clickListenerRecyclerAdapter != null){
            return clickListenerRecyclerAdapter.onItemLongClicked(getPosition());
        }
        return false;
    }
}
