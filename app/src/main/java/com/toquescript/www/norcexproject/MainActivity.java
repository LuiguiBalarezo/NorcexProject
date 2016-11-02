package com.toquescript.www.norcexproject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.toquescript.www.norcexproject.api.ApiClient;
import com.toquescript.www.norcexproject.api.ApiNorcex;
import com.toquescript.www.norcexproject.constant.Constant;
import com.toquescript.www.norcexproject.fragment.Assistance;
import com.toquescript.www.norcexproject.fragment.CreateList;
import com.toquescript.www.norcexproject.model.MeRealmModel;
import com.toquescript.www.norcexproject.response.MeResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    /**
     * ACCOUNTMANAGER
     */
    private AccountManager mAccountManager = null;
//    private Account[] accounts = null;
//    AccountManagerFuture<Bundle> future = null;

    /**
     * Views
     */
    Toolbar toolbar = null;
    DrawerLayout drawerLayout = null;
    NavigationView navigationView = null;
    EditText edtuser = null, edtpass = null;
    Button btnaddnewaccount = null;
    View viewheadernavigation = null;
    TextView txt_username = null, txt_useremail = null;

    /**
     * MODELS
     */
    private MeRealmModel user = null;

    /**
     * VARS
     */
    private String
            accounttype = null,
            accountname = null,
            token = null,
            refreshtoken = null;

    /**
     * Api
     */
    private boolean error = false;
    private String message = null, nombres = null, apellidos = null, correo = null;
    private MeRealmModel result = null;
    ApiNorcex apinorcex = null;
    MeResponse meResponse = null;
    Call<MeResponse> meResponseCall = null;
    Map<String, String> memaprequest = null;
    private int countrepeat = 0;

    /**
     * REALM
     */
    private Realm realm;
    private RealmConfiguration realmConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**API*/
        apinorcex = ApiClient.getClient().create(ApiNorcex.class);
        memaprequest = new HashMap<>();

        /**Esta clase proporciona acceso a un registro centralizado de cuentas en línea del usuario*/
        mAccountManager = AccountManager.get(MainActivity.this);

        /**Realm*/
        // Create the Realm configuration
        realmConfig = new RealmConfiguration.Builder(this).build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);

        /**CastViews*/
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navview);

        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
        navigationView.setItemIconTintList(null);
        viewheadernavigation = navigationView.getHeaderView(0);
        txt_username = (TextView) viewheadernavigation.findViewById(R.id.txt_username);
        txt_useremail = (TextView) viewheadernavigation.findViewById(R.id.txt_useremail);

        /*Verificamos si ya existe un usario logeado*/
        getAccounts();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getAccounts() {
        mAccountManager.getAuthTokenByFeatures(Constant.ACCOUNT_TYPE, Constant.ACCOUNT_TOKEN_TYPE, null, MainActivity.this, null, null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {

                            Bundle bundle = future.getResult();
                            accounttype = bundle.getString(AccountManager.KEY_ACCOUNT_TYPE);
                            accountname = bundle.getString(AccountManager.KEY_ACCOUNT_NAME);
                            token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
                            Account[] accounts = mAccountManager.getAccountsByType(accounttype);
                            refreshtoken = mAccountManager.getUserData(accounts[0], Constant.ARG_REFRESH_TOKEN);

                            /*CARGA DE INFO A VIEWS*/
                            getDateUser();

                        } catch (OperationCanceledException e) {
                            e.printStackTrace();
                        } catch (AuthenticatorException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                null);
    }

    private void getDateUser() {
        final MeRealmModel user = realm.where(MeRealmModel.class).findFirst();
        txt_username.setText(user.nombres + " " + user.apellidos);
        txt_useremail.setText(user.correo);
    }

    NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            boolean fragmentTransaction = false;
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.item_createlist:
                    fragment = new CreateList();
                    fragmentTransaction = true;
                    break;
                case R.id.item_assitence:
                    fragment = new Assistance();
                    fragmentTransaction = true;
                    break;
                case R.id.item_setting:
                    Toast.makeText(MainActivity.this, "SETTING", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.item_help:
                    Toast.makeText(MainActivity.this, "HELP", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.item_about:
                    Toast.makeText(MainActivity.this, "ABOUT", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.item_signout:
                    Toast.makeText(MainActivity.this, "SIGNOUT", Toast.LENGTH_SHORT).show();
                    break;
            }

            if (fragmentTransaction) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                item.setChecked(true);
                getSupportActionBar().setTitle(item.getTitle());
            }

            drawerLayout.closeDrawers();

            return true;
        }
    };

    public void disableSwipeDrawe() {
         /*Block Drawer swipe*/
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, navigationView);
    }

    public void enabledSwipeDrawe() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, navigationView);
    }

}
