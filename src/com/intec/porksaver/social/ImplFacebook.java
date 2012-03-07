package com.intec.porksaver.social;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;

import android.content.Context;

import com.expertiseandroid.lib.sociallib.connectors.FacebookConnector;
import com.expertiseandroid.lib.sociallib.connectors.SocialNetworkHelper;
import com.expertiseandroid.lib.sociallib.exceptions.NotAuthentifiedException;
import com.expertiseandroid.lib.sociallib.model.Post;

// cambios jmendoza: renombrando Clase de SocialFacebook a FacebookImpl.
/**
 * @author lcolon | 2009-0038
 */
public class ImplFacebook {

	/**
	 * PostiarFacebook se encarga de encapsular la logica para postiar un mensaje a facebook.
	 * 
	 * @author jmendoza
	 * 
	 */
	private class PostiarFacebook extends Post {

		private String mensaje;

		public PostiarFacebook(final String mensaje) {
			this.mensaje = mensaje;
		}

		@Override
		public String getContents() {
			return mensaje;
		}

		// TODO : porque un "0"?.
		@Override
		public String getId() {
			return "0";
		}

		@Override
		public void setContents(String arg0) {
		}

		@Override
		public void setId(String arg0) {
		}

	}

	/**
	 * Cambios: jmendoza 1. Renombrando constantes a mayuscula 2. renombrando propiedades a espanol.
	 */

	/** ID de la aplicacion que se autentifica con facebook. */
	private final static String APP_ID = "186047261508807";
	/** Password de la aplicacion para facebook. */
	// TODO : esto se usa?.
	// private final static String APP_SECRET = "5b74398c91916197e6a71b070fe02e6e";

	private String[] permisos = new String[] { "publish_stream" };
	private FacebookConnector fb;

	// cambios jmendoza: cambiando parametro Activity a Context.
	public ImplFacebook(Context contexto) {
		fb = SocialNetworkHelper.createFacebookConnector(APP_ID, permisos);
		fb.requestAuthorization(contexto);
	}

	public FacebookConnector obtenerConectorFacebook() {
		return fb;
	}

	// cambios jmendoza: renombrando nombre del metodo.
	public void postiarMensaje(final String mensaje) {
		try {
			fb.post(new PostiarFacebook(mensaje));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotAuthentifiedException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	};

}
