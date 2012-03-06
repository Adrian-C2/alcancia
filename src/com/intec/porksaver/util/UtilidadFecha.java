/**
 * 
 */
package com.intec.porksaver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * UtilidadFecha es responsable de convertir las fechas al formato dd/MM/yyyy hh:mm.
 * @author jmendoza | m: 2008-0060
 */
public final class UtilidadFecha {

	/** Formato de la fecha a convertir. */
	public static final String PATRON_FECHA = "dd/MM/yyyy hh:mm";
	
	private static final SimpleDateFormat formato = new SimpleDateFormat(PATRON_FECHA);
	
	public static String convertirFecha(final Date fecha) {
		return formato.format(fecha);
	}
	
	public static String convertirFecha(final String fecha) {
		return formato.format(new Date(fecha));
	}
	
}
