/**
 * 
 */
package com.intec.porksaver.dom;

import java.io.Serializable;

import com.intec.porksaver.util.UtilidadFecha;

/**
 * Entrada, entidad que representa las transacciones depositadas en la alcancia.
 * 
 * @author jmendoza | m: 2008-0060
 */
public class Entrada implements Identificable<Entrada>, Serializable {

	/** Nombre de la tabla entrada. */
	public static final String TABLA_ENTRADA = " entradas ";
	/** Nombre de la columna fecha. */
	public static final String COLUMNA_FECHA = " fecha ";
	/** Nombre de la columna moneda. */
	public static final String COLUMNA_MONTO = " monto ";

	private static final long serialVersionUID = 1L;

	private Integer id;
	// SQLite no maneja Tipo de Datos "Date", la documentacion dice que se utilize
	// TEXT, REAL, INTEGER.
	private String fecha;
	private Double monto;

	public Integer obtenerId() {
		return id;
	}

	public Entrada asignarId(final Integer id) {
		this.id = id;
		return this;
	}

	public String obtenerFecha() {
		return fecha;
	}

	public Entrada asignarFecha(final String fecha) {
		this.fecha = fecha;
		return this;
	}

	public Double obtenerMonto() {
		return monto;
	}

	public Entrada asignarMonto(final Double monto) {
		this.monto = monto;
		return this;
	}

	// convierte esta entidad a
	// " [fecha=[10/10/10 24:00:00],moneda=[25.00] ]"
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(" [Fecha = [ " + UtilidadFecha.convertirFecha(obtenerFecha()) + " ] ");
		sb.append(" Moneda = [ " + obtenerMonto() + " ] ]");

		return sb.toString();
	}

	/** metodo estatico para realizar la consulta de las entradas. */
	public static String[] columnas() {
		return new String[] { COLUMNA_ID, COLUMNA_FECHA, COLUMNA_MONTO };
	}

	public static Entrada crearEntrada(final Double monto, final String fecha) {
		final Entrada entrada = new Entrada();
		entrada.asignarFecha(fecha);
		entrada.asignarMonto(monto);

		return entrada;
	}

	public Entrada conId(final Integer eid) {
		asignarId(eid);
		return this;
	}

}
