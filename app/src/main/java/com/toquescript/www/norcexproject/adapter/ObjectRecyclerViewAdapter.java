package com.toquescript.www.norcexproject.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toquescript.www.norcexproject.R;
import com.toquescript.www.norcexproject.callbacks.ClickListenerRecyclerAdapter;
import com.toquescript.www.norcexproject.holder.ObjectViewHolder;
import com.toquescript.www.norcexproject.model.MeasuresRealmModel;
import com.toquescript.www.norcexproject.model.ObjectRealmModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by BALAREZO on 10/06/2016.
 */
public class ObjectRecyclerViewAdapter extends SelectableAdapter<ObjectViewHolder> {

    /*Views*/
    private View view;

    private List<ObjectRealmModel> dataList;
    private List<ObjectRealmModel> filteredDataList;

    private Context mContext;
    private AppCompatActivity activity;
    private int po;
    private ObjectViewHolder rcv;

    /*Search*/
    private int resultcount;
    private List<ObjectRealmModel> resultvalues;

    /*Vars*/
    private String id, nombre, cantidad, medida;

    private Realm realm;

    /*Multichose*/
    private static final int TYPE_INACTIVE = 0;
    private static final int TYPE_ACTIVE = 1;

    /*Listener*/
    private ClickListenerRecyclerAdapter clickListenerRecyclerAdapter;

    public ObjectRecyclerViewAdapter(Context mContext,
                                     AppCompatActivity activity,
                                     RealmResults<ObjectRealmModel> dataList,
                                     ClickListenerRecyclerAdapter clickListenerRecyclerAdapter) {
        this.mContext = mContext;
        this.activity = activity;
        this.dataList = dataList;
        this.filteredDataList = dataList;
        this.clickListenerRecyclerAdapter = clickListenerRecyclerAdapter;
    }

    @Override
    public ObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_object_item, parent, false);
        rcv = new ObjectViewHolder(view, clickListenerRecyclerAdapter);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final ObjectViewHolder holder, final int position) {
        /*Obtenemos la posicion del dato y a su ves consultamos el ojectto
        al cual le pertenece esa poscion dentro de el modelo*/
        final ObjectRealmModel objectModel = (ObjectRealmModel) dataList.get(position);

        nombre = objectModel.nombre;
        cantidad = objectModel.cantidad;
        medida = objectModel.medida;

        holder.txt_nameobject.setText(nombre);


        if (cantidad != null && !cantidad.isEmpty()) {
            holder.txt_number.setText(cantidad);
        } else {
            holder.txt_number.setText("-");
        }

        if (medida != null && !medida.isEmpty()) {
            for (MeasuresRealmModel measuresRealmModel : objectModel.medidas) {
                if (measuresRealmModel.id.equals(medida)) {
                    holder.txt_typemeasure.setText(measuresRealmModel.tipo);
                }
            }
        } else {
            holder.txt_typemeasure.setText("-");
        }

        if (isSelected(position)) {
            holder.baseretaltive.setBackgroundColor(mContext.getResources().getColor(R.color.green_50));
        } else {
            holder.baseretaltive.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public ObjectRealmModel getInfoObject(int position) {
        return (ObjectRealmModel) dataList.get(position);
    }

    /*Multichose*/
    public void removeItem(int position) {
        removeMeasureQualityObjectRealmModel(position);
    }

    public void removeItems(List<Integer> positions) {
        // Reverse-sort the list
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        // Split the list in ranges
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                /*cuando la cantdad de items a borrar es 1*/
                removeItem(positions.get(0));
                positions.remove(0);
            } else {
                /*Cuando la cantidad de item a borrar es mayor a 1*/
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }

                if (count == 1) {
                    removeItem(positions.get(0));
                } else {
                    /*Envamos las posiciones ordenadas para ser eliminada
                    * en este caso sera actualizadas cambiandole la info*/
                    removeRange(positions, count);
                }

                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }
    }

    private void removeRange(List<Integer> positionStart, int itemCount) {
        for (Integer position : positionStart) {
            removeMeasureQualityObjectRealmModel(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        final ObjectRealmModel item = dataList.get(position);
        if (item.isActive) {
            return TYPE_ACTIVE;
        } else {
            return TYPE_INACTIVE;
        }
    }

    private void removeMeasureQualityObjectRealmModel(int position) {
        id = ((ObjectRealmModel) dataList.get(position)).id;
        realm = Realm.getDefaultInstance();
        ObjectRealmModel objectRealmModel = realm.where(ObjectRealmModel.class).equalTo("id", id).findFirst();
        realm.beginTransaction();
        objectRealmModel.cantidad = null;
        objectRealmModel.medida = null;
        realm.commitTransaction();
        realm.close();
    }

    /*********/


    public void getFilter(String constraint, boolean isfilter) {
        /*si el metodo filter fue llamado con true, significa que se hizo uso del viewsearch en el toolbar,
        * si se recibe false , significa que el evento filter fue llamado por un metodo solamente,
         * para verificar si necesita actualizar el adapter*/
        if (isfilter) {

            if (constraint != null && constraint.length() > 0) {
                List<ObjectRealmModel> filterList = new ArrayList<ObjectRealmModel>();
                for (int i = 0; i < filteredDataList.size(); i++) {
                    ObjectRealmModel objectModel = (ObjectRealmModel) filteredDataList.get(i);
                    if ((objectModel.nombre.toUpperCase(Locale.getDefault())).contains(constraint.toString().toUpperCase(Locale.getDefault()))) {
                        filterList.add((ObjectRealmModel) filteredDataList.get(i));
                    }
                }
                resultcount = filterList.size();
                resultvalues = filterList;
            } else {
                resultcount = filteredDataList.size();
                resultvalues = filteredDataList;
            }
            dataList = resultvalues;
            notifyDataSetChanged();
        }else{
            resultcount = filteredDataList.size();
            resultvalues = filteredDataList;
            dataList = resultvalues;
            notifyDataSetChanged();
        }

    }


}
