/**
 * 
 */
package com.intec.porksaver.social;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author jmendoza | m: 2008-0060
 * @author lcolon | m: xxxx-xxxx
 */
public class FacebookImpl implements DialogListener {

	private static final String APP_ID = "";

	private Activity contexto;
	private Facebook facebook;

	public FacebookImpl(final Context contexto) {
		this.contexto = (Activity) contexto;
		facebook = new Facebook(APP_ID);
		facebook.authorize((Activity) contexto, this);
	}

	public void onComplete(Bundle values) {
	}

	public void onFacebookError(FacebookError e) {
	}

	public void onError(DialogError e) {
	}

	public void onCancel() {
	}
	
	public void autorizarRespuesta(int codigoSolicitado, int codigoResultado, Intent data ) {
		facebook.authorizeCallback(codigoSolicitado, codigoResultado, data);
	}
	
}
