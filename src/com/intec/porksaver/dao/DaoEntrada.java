/**
 * 
 */
package com.intec.porksaver.dao;

import java.util.List;

import com.intec.porksaver.dom.Entrada;

/**
 * DaoEntrada, es para el acceso a la base de datos en la tabla {@link Entrada}.
 * 
 * @author jmendoza | m: 2008-0060
 */
public interface DaoEntrada {

	/** Inserta una nueva {@link Entrada}.
	 * @return {@link Entrada} persistida. */
	Entrada insertar(final Entrada entrada);

	/**
	 * Busca una {@link Entrada} dado el id.
	 * @return {@link Entrada}
	 * */
	Entrada encontrar(final long id);

	/** @return una lista de todas las {@link Entrada}. */
	List<Entrada> obtenerEntradas();

	/**
	 * @return el suma total de montos.
	 */
	Double obtenerMontoTotal();
	
}
