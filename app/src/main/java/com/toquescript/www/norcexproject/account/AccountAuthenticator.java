package com.toquescript.www.norcexproject.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.toquescript.www.norcexproject.Login;
import com.toquescript.www.norcexproject.constant.Constant;

public class AccountAuthenticator extends AbstractAccountAuthenticator {

    public Context mContext;

    private Intent intent = null;
    private Bundle bundle = null;

    private AccountManager mAccountmanager = null;

    public AccountAuthenticator(Context context) {
        super(context);
        mContext = context;
        mAccountmanager = AccountManager.get(mContext);
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response,
                                 String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response,
                             String accountType, String authTokenType,
                             String[] requiredFeatures, Bundle options)
            throws NetworkErrorException {

        Account[] accounts = mAccountmanager.getAccountsByType(accountType);

        if (accounts.length > 0) {

            Toast.makeText(mContext, "Solo se admite una cuenta Norcex", Toast.LENGTH_SHORT).show();

        } else {

            intent = new Intent(mContext, Login.class);
            intent.putExtra(Constant.ARG_ACCOUNT_TYPE, accountType);
            intent.putExtra(Constant.ARG_AUTH_TYPE, authTokenType != null ? authTokenType : Constant.ACCOUNT_TOKEN_TYPE);
            intent.putExtra(Constant.ARG_CLIENT_ID, Constant.CLIENT_ID);
            intent.putExtra(Constant.ARG_CLIENT_SECRET, Constant.CLIENT_SECRET);
            intent.putExtra(Constant.ARG_GRANT_TYPE_PASSWORD, Constant.GRANT_TYPE_PASSWORD);
            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
            bundle = new Bundle();
            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
            return (bundle);

        }

        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response,
                                     Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response,
                               Account account, String authTokenType, Bundle options)
            throws NetworkErrorException {
        return null;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response,
                                    Account account, String authTokenType, Bundle options)
            throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response,
                              Account account, String[] features) throws NetworkErrorException {
        return null;
    }
}
