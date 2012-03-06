/**
 * 
 */
package com.intec.porksaver.dao.impl;

import static com.intec.porksaver.dom.Entrada.TABLA_ENTRADA;
import static com.intec.porksaver.dom.Entrada.columnas;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.intec.porksaver.dao.DaoAbstracto;
import com.intec.porksaver.dao.DaoEntrada;
import com.intec.porksaver.dom.Entrada;

/**
 * @author jmendoza | m: 2008-0060
 */
public class ImplDaoEntrada extends DaoAbstracto<Entrada> implements DaoEntrada {

	public ImplDaoEntrada(final Context contexto) {
		super(contexto);
	}

	public Entrada insertar(final Entrada entrada) {
		final ContentValues valores = new ContentValues();
		valores.put(Entrada.COLUMNA_ID, entrada.obtenerId());
		valores.put(Entrada.COLUMNA_FECHA, entrada.obtenerFecha());
		valores.put(Entrada.COLUMNA_MONTO, entrada.obtenerMonto());
		Log.i(ImplDaoEntrada.class.getName(), " Persistiendo: " + entrada.toString());
		final long idFila = baseDatos.insert(TABLA_ENTRADA, null, valores);

		return encontrar(idFila);
	}

	public Entrada encontrar(final long id) {
		final Cursor consulta = baseDatos.query(TABLA_ENTRADA, columnas(),
				Entrada.COLUMNA_ID + " = " + id, null, null, null, null);
		consulta.moveToFirst();
		final Entrada entrada = transformarConsulta(consulta);
		consulta.close();
		return entrada;
	}

	public List<Entrada> obtenerEntradas() {
		final List<Entrada> entradas = new ArrayList<Entrada>();
		final Cursor consulta = baseDatos.query(TABLA_ENTRADA, columnas(), null,
				null, null, null, null);
		consulta.moveToFirst();

		while (!consulta.isAfterLast()) {
			entradas.add(transformarConsulta(consulta));
			consulta.moveToNext();
		}

		consulta.close();
		return entradas;
	}

	public Double obtenerMontoTotal() {
		final List<Entrada> entradas = obtenerEntradas();
		Double montoTotal = Double.valueOf(0D);
		for (final Entrada entrada : entradas) {
			montoTotal += entrada.obtenerMonto();
		}

		return montoTotal;
	}
	
	@Override
	protected Entrada transformarConsulta(final Cursor consulta) {
		final Entrada entrada = new Entrada();
		entrada.asignarId(consulta.getInt(0));
		entrada.asignarFecha(consulta.getString(1));
		entrada.asignarMonto(consulta.getDouble(2));

		return entrada;
	}

}
