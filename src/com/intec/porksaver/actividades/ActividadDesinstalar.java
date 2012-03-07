/**
 * 
 */
package com.intec.porksaver.actividades;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * ActividadDesinstalar es responsable de correr la accion de borrar la aplicacion.
 * @author jmendoza | m: 2008-0060
 */
public class ActividadDesinstalar extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Uri uri = Uri.parse("package:com.intec.porksaver");
		final Intent desinstalar = new Intent(Intent.ACTION_DELETE, uri);
		startActivity(desinstalar);
	}
}
