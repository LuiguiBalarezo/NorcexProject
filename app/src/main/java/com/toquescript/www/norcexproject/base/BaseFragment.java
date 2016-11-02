package com.toquescript.www.norcexproject.base;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.toquescript.www.norcexproject.MainActivity;
import com.toquescript.www.norcexproject.R;
import com.toquescript.www.norcexproject.adapter.ObjectRecyclerViewAdapter;
import com.toquescript.www.norcexproject.adapter.SpinAdapter;
import com.toquescript.www.norcexproject.callbacks.CallBackOpenBottomSheet;
import com.toquescript.www.norcexproject.callbacks.ClickListenerRecyclerAdapter;
import com.toquescript.www.norcexproject.fragment.CreateList;
import com.toquescript.www.norcexproject.model.MeasuresRealmModel;
import com.toquescript.www.norcexproject.model.ObjectRealmModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by BALAREZO on 18/06/2016.
 */
public class BaseFragment extends Fragment implements ClickListenerRecyclerAdapter {

    private String TAG = this.getClass().getSimpleName();

    public View view;
    public Context mContext;
    public RecyclerView recyclerView;
    public ObjectRecyclerViewAdapter objectRecyclerViewAdapter;

    public MaterialDialog.Builder builder;
    public MaterialDialog dialog;
    public EditText edt_quantity, edt_name_object;
    public Spinner spinner;
    public SpinAdapter spinAdapter;
    public String id, nombre, cantidad, medida;
    public List<MeasuresRealmModel> measuresRealmModels;
    public MeasuresRealmModel measure;

    /*SeachView*/
    private static SearchView searchView;
    private static SearchManager searchManager;

    /*REALM RESULT*/
    public RealmResults<ObjectRealmModel> objectList;
    private Fragment fragment_createlist;
    /*REALM*/
    public Realm realm;

    /*Usign MultiChose*/
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;

    private static CallBackOpenBottomSheet callBackOpenBottomSheet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment_createlist = new CreateList();
    }

    public void setArgumentCallBack(CallBackOpenBottomSheet argumentCallBack){
        this.callBackOpenBottomSheet = argumentCallBack;
    }

    @Override
    public void onStart() {
        super.onStart();
         /*REALM*/
        realm = Realm.getDefaultInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_viewlist) {
            Toast.makeText(getActivity(), "VIEW", Toast.LENGTH_SHORT).show();
            callBackOpenBottomSheet.openBottomSheet();
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tool_menu, menu);

        searchManager = (SearchManager) ((Context) getActivity()).getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setIconifiedByDefault(true);

        /*0*/
        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                /*2*/
                getFilterAdapter(newText, true);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                getFilterAdapter(query, true);
                return true;
            }
        };
        /*1*/
        searchView.setOnQueryTextListener(textChangeListener);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    /*Meotodo utilizado para el filtrado de datos */
    public void getFilterAdapter(String constraint, boolean isfilter) {
        if (objectRecyclerViewAdapter != null) {
            objectRecyclerViewAdapter.getFilter(constraint, isfilter);
        }
    }

    public void clearFocusAndQuerySearchView() {
        if (searchView != null) {
            searchView.setQuery("", false);
            searchView.clearFocus();
        }
    }

    public void showDialod(ObjectRealmModel objectRealmModel) {

        id = objectRealmModel.id;
        nombre = objectRealmModel.nombre;
        cantidad = objectRealmModel.cantidad;
        medida = objectRealmModel.medida;

        builder = new MaterialDialog.Builder(mContext)
                .title("Agrege Cantidad")
                .autoDismiss(false)
                .customView(R.layout.fragment_object_dialog, true)
                .cancelable(false)
                .positiveText("Guardar")
                .negativeText("Cancelar")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        cantidad = edt_quantity.getText().toString();
                        if (cantidad != null && !cantidad.trim().isEmpty() &&
                                !cantidad.equals("0") && !cantidad.equals(".") && !String.valueOf(cantidad.charAt(0)).equals(".")) {
                            changeDataObjectModel(id, cantidad, medida);
                            dialog.dismiss();
                        } else {
                            edt_quantity.setError("Ingrese numero mayor a 0");
                        }
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();
                    }
                });

        if (cantidad != null && cantidad != "") {
            builder.neutralText("Eliminar")
                    .neutralColor(Color.RED)
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            changeDataObjectModel(id, null, null);
                            dialog.dismiss();
                        }
                    });
        }

        dialog = builder.build();
        edt_name_object = (EditText) dialog.findViewById(R.id.txt_name_object_dialog);
        edt_name_object.setText(nombre);
        edt_quantity = (EditText) dialog.findViewById(R.id.edt_quantity);
        edt_quantity.setText(cantidad);
        measuresRealmModels = objectRealmModel.medidas;
        spinAdapter = new SpinAdapter(mContext, android.R.layout.simple_spinner_item, measuresRealmModels);
        spinner = (Spinner) dialog.findViewById(R.id.spinner);
        spinner.setAdapter(spinAdapter);
        int count = 0;
        for (MeasuresRealmModel measuresRealmModel : measuresRealmModels) {
            String id = measuresRealmModel.id;
            if (id.equals(medida)) {
                spinner.setSelection(count);
            }
            count++;
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                measure = spinAdapter.getItem(position);
                medida = measure.id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {

            }
        });

        dialog.show();
    }

    private void changeDataObjectModel(String id, String cantidad, String medida) {
        ObjectRealmModel objectRealmModel = realm.where(ObjectRealmModel.class).equalTo("id", id).findFirst();
        realm.beginTransaction();
        objectRealmModel.cantidad = cantidad;
        objectRealmModel.medida = medida;
        realm.commitTransaction();
        objectRealmModel.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel element) {
                objectRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        if (actionMode != null) {
            toggleSelection(position);
        } else {
            showDialod(objectRecyclerViewAdapter.getInfoObject(position));
        }
    }

    @Override
    public boolean onItemLongClicked(int position) {
        if (actionMode == null) {
            /*Cuando este activo el action mode, se debe blockear el swipe de el drawer*/
            ((MainActivity) getActivity()).disableSwipeDrawe();
            actionMode = ((MainActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
        return true;
    }

    private void toggleSelection(int position) {
        objectRecyclerViewAdapter.toggleSelection(position);
        int count = objectRecyclerViewAdapter.getSelectedItemCount();

        if (count == 0) {
            actionModeFinish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.selected_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_broom:
                    objectRecyclerViewAdapter.removeItems(objectRecyclerViewAdapter.getSelectedItems());
                    actionModeFinish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionModeClearSelection();
            actionMode = null;
        }
    }

    public void actionModeFinish() {
        if (actionMode != null) {
            actionMode.finish();
            actionModeClearSelection();
            actionMode = null;
            /*Ccuando el action mode este desactivado, se debe habilitar el swipe de el drawer*/
            ((MainActivity) getActivity()).enabledSwipeDrawe();
        }
    }


    public void actionModeClearSelection() {
        objectRecyclerViewAdapter.clearSelection();
    }


}
