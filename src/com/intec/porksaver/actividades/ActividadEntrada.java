/**
 * 
 */
package com.intec.porksaver.actividades;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.intec.porksaver.R;
import com.intec.porksaver.dao.impl.ImplDaoEntrada;
import com.intec.porksaver.dom.Entrada;

/**
 * ActividadEntrada, presenta todas las monedas guardadas.
 * @author jmendoza | m: 2008-0060
 */
public class ActividadEntrada extends ListActivity {

	private ImplDaoEntrada fuenteDatos;

	@Override
	protected void onCreate(final Bundle instancia) {
		super.onCreate(instancia);
		setContentView(R.layout.entradas);
		fuenteDatos = new ImplDaoEntrada(this);
		fuenteDatos.abrir();

		final List<Entrada> historiales = fuenteDatos.obtenerEntradas();
		final ArrayAdapter<Entrada> listaAdaptada = new ArrayAdapter<Entrada>(this,
				android.R.layout.simple_list_item_1, historiales);
		setListAdapter(listaAdaptada);
		
		final TextView montoTotal = (TextView)findViewById(R.id.montoTotal);
		montoTotal.setText("Mi cerdito tiene: " + fuenteDatos.obtenerMontoTotal() + " $RD!! ");
	}

	public void onClick(final View vista) {
		switch (vista.getId()) {
			case R.id.boton_salir:
				final Intent actividadPrincipal = new Intent(vista.getContext(), ActividadAlcancia.class);
				startActivity(actividadPrincipal);
				break;
		}
	}

	@Override
	protected void onResume() {
		fuenteDatos.abrir();
		super.onResume();
	}

	@Override
	protected void onPause() {
		fuenteDatos.cerrar();
		super.onPause();
	}

}
