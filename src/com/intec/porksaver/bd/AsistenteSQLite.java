/**
 * 
 */
package com.intec.porksaver.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.intec.porksaver.dom.Entrada;

/**
 * AsistenteSQLite, se encarga de crear la base de datos, las tablas y demas..
 * @author jmendoza | m: 2008-0060
 */
public class AsistenteSQLite extends SQLiteOpenHelper {

	private static final int VERSION_BASE_DATOS = 1;
	private static final String NOMBRE_BASE_DATOS = "PORK.db";

	public AsistenteSQLite(final Context contexto) {
		super(contexto, NOMBRE_BASE_DATOS, null, VERSION_BASE_DATOS);
	}

	/** Primero se ejecuta este metodo creando las tablas de lugar. */
	@Override
	public void onCreate(final SQLiteDatabase baseDatos) {
		baseDatos.execSQL(crearTablas());
	}

	/** NOTA ESTO ES PRUEBA. */
	/** hacer algo interesante con esto. */
	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
			final int newVersion) {
		Log.w(AsistenteSQLite.class.getName(),
				" Actualizando base de datos de version " + oldVersion + " a "
						+ newVersion + ", se eliminara toda la informacion vieja. ");
		db.execSQL(" DROP TABLE IF EXISTS " + Entrada.TABLA_ENTRADA);
		onCreate(db);
	}

	private String crearTablas() {
		final StringBuilder sb = new StringBuilder();
		sb.append(tablaEntrada());
		return sb.toString();
	}

	/**
	 * Este metodo abre una conexion con la base de datos SQLite para persistir
	 * data.
	 */
	public SQLiteDatabase obtenerEscritura() {
		return getWritableDatabase();
	}

	/** Este metodo abre una conexion con la base de datos SQLite para leer data. */
	public SQLiteDatabase obtenerLectura() {
		return getReadableDatabase();
	}

	public void cerrar() {
		close();
	}

	private String tablaEntrada() {
		final String entrada = " CREATE TABLE " + Entrada.TABLA_ENTRADA + " ( "
				+ Entrada.COLUMNA_ID + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " 
				+ Entrada.COLUMNA_FECHA + " TEXT NOT NULL, "
				+ Entrada.COLUMNA_MONTO + " REAL  NOT NULL ); ";
		return entrada;
	}

}
