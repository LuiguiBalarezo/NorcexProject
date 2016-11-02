package com.toquescript.www.norcexproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.toquescript.www.norcexproject.account.AccountAuthenticator;

public class AccountAuthenticatorService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		Log.i("Script", "AccountAuthenticatorService.onBind()");
		AccountAuthenticator mAccountAuthenticator = new AccountAuthenticator(AccountAuthenticatorService.this);
		return(mAccountAuthenticator.getIBinder());
	}

}
