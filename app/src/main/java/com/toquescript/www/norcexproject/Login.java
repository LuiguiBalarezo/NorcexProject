package com.toquescript.www.norcexproject;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.toquescript.www.norcexproject.api.ApiClient;
import com.toquescript.www.norcexproject.api.ApiNorcex;
import com.toquescript.www.norcexproject.callbacks.CallbackTaskFinish;
import com.toquescript.www.norcexproject.callbacks.LoginTaksFinish;
import com.toquescript.www.norcexproject.constant.Constant;
import com.toquescript.www.norcexproject.model.MeasureModel;
import com.toquescript.www.norcexproject.model.MeasuresRealmModel;
import com.toquescript.www.norcexproject.model.ObjectMeasureModel;
import com.toquescript.www.norcexproject.model.ObjectRealmModel;
import com.toquescript.www.norcexproject.request.SignInRequest;
import com.toquescript.www.norcexproject.task.LoginTask;
import com.toquescript.www.norcexproject.task.MeTask;
import com.toquescript.www.norcexproject.task.MeasureTask;
import com.toquescript.www.norcexproject.task.ObjectMeasureTask;
import com.toquescript.www.norcexproject.task.ObjectTask;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Login extends AccountAuthenticatorActivity implements View.OnClickListener, LoginTaksFinish, CallbackTaskFinish {

    private String TAG = this.getClass().getSimpleName();

    /*ACCOUNT*/
    private Account account;
    private AccountManager mAccountManager;

    /*VARS*/
    private String accountType, accountName,
            authTokenType, client_id, client_secret,
            grant_type, username, userpass, token;

    /*VIEWS*/
    private EditText edt_user, edt_pass;
    private Button btnn_login;

    /*API*/
    ApiNorcex apiNorcex;
    SignInRequest signinrequest;

    /*REALM*/
    private Realm realm;

    /*Realm List*/
    private List<ObjectRealmModel> objectModelList;
    private List<MeasureModel> measureModelList;
    private List<ObjectMeasureModel> objectMeasureModelList;

    /*ASYNCTASK*/
    private LoginTask loginTask;
    private MeTask meTask;
    private ObjectTask objectTask;
    private MeasureTask measureTask;
    private ObjectMeasureTask objectMeasureTask;

    /*INTENT FINAL*/
    private Intent intentfinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        /*API*/
        apiNorcex = ApiClient.getClient().create(ApiNorcex.class);
        signinrequest = new SignInRequest();

        /*REALM*/
        realm = Realm.getDefaultInstance();

        /*ACCOUNT*/
        mAccountManager = AccountManager.get(Login.this);
        accountType = getIntentStringExtraString(Constant.ARG_ACCOUNT_TYPE);
        accountName = getIntentStringExtraString(Constant.ARG_AUTH_TYPE);
        authTokenType = getIntentStringExtraString(Constant.ARG_AUTH_TYPE);
        client_id = getIntentStringExtraString(Constant.ARG_CLIENT_ID);
        client_secret = getIntentStringExtraString(Constant.ARG_CLIENT_SECRET);
        grant_type = getIntentStringExtraString(Constant.ARG_GRANT_TYPE_PASSWORD);

        edt_user = (EditText) findViewById(R.id.edt_user);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        btnn_login = (Button) findViewById(R.id.btnlogin);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogin:
                signIn();
                break;
        }
    }

    private void signIn() {
        username = edt_user.getText().toString();
        userpass = edt_pass.getText().toString();

        signinrequest.grant_type = grant_type;
        signinrequest.client_id = client_id;
        signinrequest.client_secret = client_secret;
        signinrequest.username = username;
        signinrequest.password = userpass;

        /*ASYNCTASK*/
        /*Primer asynctask el cual obtendra los datos necesarios para poder utilizar los recursos de las api
        * mediante el token*/
        loginTask = new LoginTask(this, apiNorcex, signinrequest, accountType, username);
        loginTask.loginTaksFinish = this;
        loginTask.execute();
    }

    /*CALLBACK LOGIN
    * UTIL PARA OBTENER EL RESULTADO TOTAL AL FINALIZAR LA TAREA DEL LOGIN*/
    @Override
    public void taskFinishLogin(Intent intent) {
        intentfinish = intent;
        Log.d(TAG, " taskFinishLogin " + TAG);
        /*CREAR CUENTA*/
        accountType = intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
        accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        token = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        account = new Account(accountName, accountType);
        mAccountManager.addAccountExplicitly(account, null, null);
        mAccountManager.setAuthToken(account, authTokenType, intent.getStringExtra(AccountManager.KEY_AUTHTOKEN));
        /*TAREAS ASYNCK*/
        runTasks(token);
    }

    /*CONSULTA DE TAREAS A EJECUTARSE CONSECUTIVAMENTE*/
    private void runTasks(String t) {

        meTask = new MeTask(this, t);
        meTask.taksFinish = this;
        meTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

        objectTask = new ObjectTask(this, apiNorcex, t);
        objectTask.taksFinish = this;
        objectTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

        measureTask = new MeasureTask(this, apiNorcex, t);
        measureTask.taksFinish = this;
        measureTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

        objectMeasureTask = new ObjectMeasureTask(this, apiNorcex, t);
        objectMeasureTask.taksFinish = this;
        objectMeasureTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

    }

    /*CONJUNTO DE CALLBACKS QUE SERAN USADOS PARA LLENAR LA BD REALM
     * CON LOS RESULTADOS OBTENIDOS
     * AL FINALIZAR LA TAREA*/
    @Override
    public void taskFinishMe(RealmObject obj) {
        Log.d(TAG, " taskFinishMe " + TAG);
        processRealmMe(obj);
    }

    @Override
    public void taskFinishObject(List<ObjectRealmModel> obj) {
        Log.d(TAG, " taskFinishObject " + TAG);
        processRealmObject(obj);
    }

    @Override
    public void taskFinishMeasure(List<MeasureModel> obj) {
        Log.d(TAG, " taskFinishMeasure " + TAG);
        processRealmMeasure(obj);
    }

    @Override
    public void taskFinishObjectMeasure(List<ObjectMeasureModel> obj) {
        Log.d(TAG, " taskFinishObjectMeasure " + TAG);
        processRealmObjectMeasure(obj);
    }

    private void processRealmMe(RealmObject obj) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
    }

    private void processRealmObject(List<ObjectRealmModel> obj) {
        objectModelList = obj;
    }

    private void processRealmMeasure(List<MeasureModel> obj) {
        measureModelList = obj;
    }

    private void processRealmObjectMeasure(List<ObjectMeasureModel> obj) {
        objectMeasureModelList = obj;

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                /*Convertir los datos relacionales a no relacional*/
                for (ObjectMeasureModel objectMeasureModel : objectMeasureModelList) {
                    for (MeasureModel measureModel : measureModelList) {

                        if (measureModel.id.equals(objectMeasureModel.id_medida)) {
                            for (ObjectRealmModel objectModel : objectModelList) {

                                if (objectModel.id.equals(objectMeasureModel.id_objeto)) {

                                    long countobject = realm.where(ObjectRealmModel.class).equalTo("id", objectMeasureModel.id_objeto).count();
                                    if (countobject > 0) {

                                        ObjectRealmModel objectexists = realm.where(ObjectRealmModel.class).equalTo("id", objectMeasureModel.id_objeto).findFirst();

                                        long countmedidas = realm.where(MeasuresRealmModel.class).equalTo("id", objectMeasureModel.id).count();
                                        if (countmedidas > 0) {

                                            MeasuresRealmModel medidaeexists = realm.where(MeasuresRealmModel.class).equalTo("id", objectMeasureModel.id).findFirst();
                                            medidaeexists.tipo = measureModel.tipo;

                                        } else {
                                            MeasuresRealmModel medidas = realm.createObject(MeasuresRealmModel.class);
                                            medidas.id = objectMeasureModel.id;
                                            medidas.tipo = measureModel.tipo;
                                            objectexists.medidas.add(medidas);
                                        }

                                    } else {

                                        ObjectRealmModel newObject = realm.createObject(ObjectRealmModel.class);
                                        newObject.id = objectMeasureModel.id_objeto;
                                        newObject.nombre = objectModel.nombre;
                                        newObject.id_tipo = objectModel.id_tipo;
                                        newObject.cantidad = null;
                                        newObject.medida = null;

                                        MeasuresRealmModel medidas = realm.createObject(MeasuresRealmModel.class);
                                        medidas.id = objectMeasureModel.id;
                                        medidas.tipo = measureModel.tipo;
                                        newObject.medidas.add(medidas);

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "SUCCESS");
               /*Colocamos el metodo final aqui ya que necesitamos que todo la carga de todas los task
//                * esten completas*/
                finishProcessLogin(intentfinish);

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "ERROR");
                RealmResults<ObjectRealmModel> objectsall = realm.where(ObjectRealmModel.class).findAll();
            }
        });
    }

    private String getIntentStringExtraString(String s) {
        return getIntent().getStringExtra(s);
    }

    private void finishProcessLogin(Intent intent) {
        setAccountAuthenticatorResult(intent.getExtras());
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }

}
