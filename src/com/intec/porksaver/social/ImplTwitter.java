/**
 * 
 */
package com.intec.porksaver.social;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * @author jdemorizzi | m: 2008-0805
 */
public class ImplTwitter {

	/**
	 * 
	 */
	private static final String TAG = ImplTwitter.class.getName();
	/** Name to store the users access token */
	private static final String PREF_ACCESS_TOKEN = "515698055-hzv7Dv8bah6EZgqnWH321hgMFCRGCtzZsMY0OybM";
	/** Name to store the users access token secret */
	private static final String PREF_ACCESS_TOKEN_SECRET = "5t84EGBJRaQgCsWvrsVz5OaD8b6uQ145YNxkkrUoJlU";
	/**
	 * Consumer Key generated when you registered your app at
	 * https://dev.twitter.com/apps/
	 */
	private static final String CONSUMER_KEY = "RloMNq8EkqX2kn769SAw";
	/**
	 * Consumer Secret generated when you registered your app at
	 * https://dev.twitter.com/apps/
	 */
	private static final String CONSUMER_SECRET = "KpqqJVoY1W9YVGp8fhMgCISr6Ehalvz0tVohlUZ9A"; // XXX
																																															// Encode
	/**
	 * The url that Twitter will redirect to after a user log's in - this will be
	 * picked up by your app manifest and redirected into this activity
	 */
	private static final String CALLBACK_URL = "tweet-to-twitter-blundell-01-android:///";
	/** Preferences to store a logged in users credentials */
	private SharedPreferences mPrefs;
	/** Twitter4j object */
	private Twitter mTwitter;
	/**
	 * The request token signifies the unique ID of the request you are sending to
	 * twitter
	 */
	private RequestToken mReqToken;
	private Context contexto;
	private boolean login;

	public ImplTwitter(Context contexto) {
		mTwitter = new TwitterFactory().getInstance();
		mTwitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		this.contexto = contexto;
		this.mPrefs = contexto.getSharedPreferences("tweet", Context.MODE_PRIVATE);
	}

	public Twitter getTwitter() {
		return mTwitter;
	}

	public boolean obtenerLogin() {
		return login;
	}
	
	public void asignarLoggin(final boolean login){
		this.login = login;
	}
	
	public void postiarTweet(final int totalCantidad) {
		try {
			getTwitter().updateStatus(" Hola ladrones!! tengo " + totalCantidad);
		} catch (final TwitterException e) {
			Log.i(TAG,
					" Publicando tweet lanzo la siguiente excepcion: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public WebView attempLogin() {
		Log.i(TAG, " Login presionado. ");
		if (mPrefs.contains(PREF_ACCESS_TOKEN)) {
			Log.i(TAG, " Mismo usuario. ");
			asignarLoggin(true);
			loginAuthorisedUser();
		} else {
			Log.i(TAG, " Nuevo usuario. ");
			WebView loginNewUser = loginNewUser();
			if (loginNewUser != null) {
				asignarLoggin(true);
			}
			return loginNewUser;
		}
		return null;
	}

	private WebView loginNewUser() {
		try {
			mReqToken = mTwitter.getOAuthRequestToken(CALLBACK_URL);
			WebView twitterSite = new WebView(contexto);
			twitterSite.loadUrl(mReqToken.getAuthenticationURL());
			return twitterSite;

		} catch (TwitterException e) {
			Toast.makeText(contexto, " No puede iniciar, intentelo mas tarde. ", Toast.LENGTH_SHORT).show();
		}
		
		return null;
	}

	private void loginAuthorisedUser() {
		String token = mPrefs.getString(PREF_ACCESS_TOKEN, null);
		String secret = mPrefs.getString(PREF_ACCESS_TOKEN_SECRET, null);

		AccessToken at = new AccessToken(token, secret);

		mTwitter.setOAuthAccessToken(at);
		
		Toast.makeText(contexto, " Bienvenido! ", Toast.LENGTH_SHORT).show();
	}

}
