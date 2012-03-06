package com.intec.porksaver.actividades;

import java.util.Date;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.TextView;

import com.intec.porksaver.R;
import com.intec.porksaver.dao.impl.ImplDaoEntrada;
import com.intec.porksaver.dom.Entrada;
import com.intec.porksaver.social.FacebookImpl;
import com.intec.porksaver.social.TwitterImpl;

/**
 * ActividadAlcancia, es la actividad principal de la aplicacion.
 * 
 * @author jmendoza | m: 2008-0060
 * @author lcruz | m: 2008-0019
 * @author lcolon | m: 2008-yyyy
 * @author jdemorizzi | m: 2008-yyyy
 */
public class ActividadAlcancia extends Activity {
	/**
	 * 
	 */
	private static final int VALOR_DEFECTO = 0;
	/** Representa la cantidad de rotos máximo del usuario. */
	private static final String LLAVE_ROTO_MAXIMO = "rotoMaximo";
	private static final int ROTOS_MAXIMO = 5;
	/**
	 * Variables de posiciones originales de los objetos.
	 **/
	private int cerdo_x = 0;
	private int cerdo_y = 0;
	private int m1_x = 0;
	private int m1_y = 0;
	private int m5_x = 0;
	private int m5_y = 0;
	private int m10_x = 0;
	private int m10_y = 0;
	private int m25_x = 0;
	private int m25_y = 0;

	/**
	 * Dimensiones del cerdo
	 **/
	private int zona_px1 = 0;
	private int zona_px2 = 0;
	private int zona_py1 = 0;
	private int zona_py2 = 0;

	/**
	 * Constante de la monedas
	 **/
	private static final int MONEDA_1 = 1;
	private static final int MONEDA_5 = 2;
	private static final int MONEDA_10 = 3;
	private static final int MONEDA_25 = 4;

	/**
	 * Constante de estado de movimiento
	 **/
	private final static int COMIENZA_ARRASTAR = 0;
	private final static int TERMINA_ARRASTAR = 1;

	/**
	 * Variables de las imagenes
	 **/
	private ImageView puerquito; // imagen del cerdo
	private ImageView roto; // imagen del roto
	private ImageView moneda1 = null; // imagen para la moneda 1
	private ImageView moneda5 = null; // imagen para la moneda 5
	private ImageView moneda10 = null; // imagen para la moneda 10
	private ImageView moneda25 = null; // imagen para la moneda 25
	private TextView cmonedas = null; // cantidad de monedas que tiene el cerdo.
	private AbsoluteLayout aLayout;

	// Dice cual es la moneda que se esta moviendo actualmente
	private int actual = 0;
	// cantidad de monedas en el cerdo
	private int conteo_monedas = 0;

	private TwitterImpl twitter;
	private FacebookImpl facebook;
	// Variable de base de datos
	private ImplDaoEntrada fuenteDatos;
	// Numero de rotos que tiene el cerdo
	private int roto_conteo = 0;
	// Estado
	private int estado = 0;

	/**
	 * Variables de sonido
	 **/
	private MediaPlayer spuerco_muerto;
	private MediaPlayer spuerco_ini;
	private MediaPlayer smoneda;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		desinstalarAplicacion();

		fuenteDatos = new ImplDaoEntrada(this);
		twitter = new TwitterImpl(this);
		facebook = new FacebookImpl(this);
		fuenteDatos.abrir();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		init();
	}

	/**
	 * <p>
	 * Metodo que se encarga de validar si el puerco se ha roto 5 veces y
	 * desinstala la app.
	 * </p>
	 */
	private void desinstalarAplicacion() {
		final SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
		int rotos = prefs.getInt(LLAVE_ROTO_MAXIMO, VALOR_DEFECTO);
		if (rotos == ROTOS_MAXIMO) {
			final Intent actividadDesintalar = new Intent(this, ActividadDesinstalar.class);
			startActivity(actividadDesintalar);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.autorizarRespuesta(requestCode, resultCode, data);
	}

	public void onClick(final View vista) {
		switch (vista.getId()) {
			case R.id.boton_entrada:
				final Intent entradaActividad = new Intent(vista.getContext(),
						ActividadEntrada.class);
				startActivity(entradaActividad);
				break;
			default:
				break;
		}
	}

	public void init() {
		aLayout = (AbsoluteLayout) findViewById(R.id.aLayout2);
		puerquito = (ImageView) findViewById(R.id.puerco_Vista);
		smoneda = MediaPlayer.create(ActividadAlcancia.this, R.raw.coin);

		zona_px1 = 90;
		zona_px2 = 400;
		zona_py1 = 200;
		zona_py2 = 500;

		cmonedas = (TextView) findViewById(R.id.cmonedas);
		cmonedas.setText("$" + fuenteDatos.obtenerMontoTotal());

		moneda1 = (ImageView) findViewById(R.id.moneda1_Vista);
		moneda1.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				estado = 1;
				actual = 1;
				Log.i("ImageStatus", "" + estado);
				return false;
			}
		});

		moneda5 = (ImageView) findViewById(R.id.moneda5_Vista);
		moneda5.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				estado = 1;
				actual = 2;
				Log.i("ImageStatus", "" + estado);
				return false;
			}
		});

		moneda10 = (ImageView) findViewById(R.id.moneda10_Vista);
		moneda10.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				estado = 1;
				actual = 3;
				Log.i("ImageStatus", "" + estado);
				return false;
			}
		});

		moneda25 = (ImageView) findViewById(R.id.moneda25_Vista);
		moneda25.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				estado = 1;
				actual = 4;
				Log.i("ImageStatus", "" + estado);
				return false;
			}
		});

		aLayout.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (actual == 0) return false;
				ImageView img = null;
				Log.i("touch", "" + event);
				switch (actual) {
					case MONEDA_1:
						img = moneda1;
						break;
					case MONEDA_5:
						img = moneda5;
						break;
					case MONEDA_10:
						img = moneda10;
						break;
					case MONEDA_25:
						img = moneda25;
						break;
				}

				if (estado == 1) // any event from down and move
				{
					LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, (int) event.getX() - img.getWidth()
									/ 2, (int) event.getY() - img.getHeight() / 2);
					img.setLayoutParams(lp);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					estado = TERMINA_ARRASTAR;
					// img.setBackgroundColor(Color.TRANSPARENT);
					// gravedad gmod = new gravedad(img, puerquito);
					// gmod.start();
					esValida(event, img);
					img = null;
					actual = 0;
				}
				return true;
			}
		});
	}

	/**
	 * @Descripcion Valida si la moneda esta o no sobre la imagen del cerdito.
	 * @retorna No retorna nada
	 * @author Luis Adrian Cruz Castillo Matricula 2008-0019
	 */
	@SuppressWarnings("deprecation")
	public void esValida(MotionEvent event, ImageView img) {
		System.out.println("*************\nEsta en la funcion\n -->" + event.getX()
				+ " = " + zona_px1 + "\n" + event.getX() + " = " + zona_px2 + "\n"
				+ event.getY() + " = " + zona_py1 + "\n" + event.getY() + " = "
				+ zona_py2 + "\n************");
		if (event.getX() >= zona_px1 && event.getX() <= zona_px2
				&& event.getY() >= zona_py1 && event.getY() <= zona_py2) {
			System.out.println("*************\nEsta arriba del cerdo\n************");
			Entrada nEntrada = null;
			LayoutParams lp;
			int valor = 0;

			switch (actual) {
				case MONEDA_1:
					nEntrada = Entrada.crearEntrada(1.0D, new Date().toGMTString());
					valor = 1;
					lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, 30, 540);
					img.setLayoutParams(lp);
					break;
				case MONEDA_5:
					nEntrada = Entrada.crearEntrada(5.0D, new Date().toGMTString());
					valor = 5;
					lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, 145, 540);
					img.setLayoutParams(lp);
					break;
				case MONEDA_10:
					nEntrada = Entrada.crearEntrada(10.0D, new Date().toGMTString());
					valor = 10;
					lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, 260, 540);
					img.setLayoutParams(lp);
					break;
				case MONEDA_25:
					nEntrada = Entrada.crearEntrada(25.0D, new Date().toGMTString());
					valor = 25;
					lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, 375, 540);
					img.setLayoutParams(lp);
					break;
			}
			smoneda.start();
			fuenteDatos.insertar(nEntrada);
			cmonedas.setText("$" + fuenteDatos.obtenerMontoTotal());
		} else {
			LayoutParams lp = null;

			switch (actual) {
				case MONEDA_1:
					lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, 30, 540);
					img.setLayoutParams(lp);
					break;
				case MONEDA_5:
					lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, 145, 540);
					img.setLayoutParams(lp);
					break;
				case MONEDA_10:
					lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, 260, 540);
					img.setLayoutParams(lp);
					break;
				case MONEDA_25:
					lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, 375, 540);
					img.setLayoutParams(lp);
					break;
			}
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

	private boolean haAlcanzadoToquesMaximo() {
		return (roto_conteo >= 3);
	}

	/**
	 * @Descripción: Esta es una clase privada para generar el efecto de caida con
	 *               gravedad de las monedas
	 * 
	 * @parametro ImageView
	 * 
	 * @author Adrian Cruz | lcruz
	 * 
	 */
	class gravedad extends Thread {
		ImageView moneda = null, puerco = null;
		LayoutParams lp = null;

		public gravedad(ImageView mda, ImageView pco) {
			moneda = mda;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
		}

		@Override
		@SuppressWarnings("deprecation")
		public synchronized void start() {
			while (moneda.getKeepScreenOn()) {
				lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT, (int) moneda.getWidth() / 2 - 1,
						(int) moneda.getHeight() / 2 - 1);
				moneda.setLayoutParams(lp);
			}
			stop();
		}

	}

	public void onLoginTwitter(View v) {
		setContentView(twitter.attempLogin());
	}

}