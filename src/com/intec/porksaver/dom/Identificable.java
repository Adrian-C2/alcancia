/**
 * 
 */
package com.intec.porksaver.dom;

/**
 * @author jmendoza | m: 2008-0060
 * @param <T> Entidad.
 */
public interface Identificable<E> {

	/** Nombre de la columna id. */
	public static final String COLUMNA_ID = " id ";
	
	/** Asigna un id, sin necesidad de ser persistida. */
	E conId(final Integer id);
	
}
