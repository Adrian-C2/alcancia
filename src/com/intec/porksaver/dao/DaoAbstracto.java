/**
 * 
 */
package com.intec.porksaver.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.intec.porksaver.bd.AsistenteSQLite;

/**
 * DaoAbstracto, contiene propiedades comunes para el acceso a la base de datos.
 * 
 * @author jmendoza | m: 2008-0060
 * @param <E> Entidad
 */
public abstract class DaoAbstracto<E> {

	protected SQLiteDatabase baseDatos;
	protected AsistenteSQLite asistente;

	public DaoAbstracto(final Context contexto) {
		asistente = new AsistenteSQLite(contexto);
	}

	public void abrir() {
		baseDatos = asistente.obtenerEscritura();
	}

	public void cerrar() {
		asistente.cerrar();
	}

	/** Transforma el resultado del cursor al tipo de <E> */
	protected abstract E transformarConsulta(final Cursor consulta);
	
}
