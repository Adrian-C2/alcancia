package com.intec.porksaver.actividades;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.intec.porksaver.R;
import com.intec.porksaver.dao.impl.ImplDaoEntrada;
import com.intec.porksaver.dom.Entrada;
import com.intec.porksaver.social.ImplFacebook;

/**
 * ActividadAlcancia, es la actividad principal de la aplicacion.
 * @author Luis Adrian Cruz      | m: 2008-0019
 * @author Joan Mendoza          | m: 2008-0060
 * @author Jean Paul Demorizi    | m: 2008-0805
 * @author Luis Colon            | m: 2009-0038
 */
public class ActividadAlcancia extends Activity //implements OnTouchListener
{
	private static final String LLAVE_LIMITE_ROTOS = "maximoRotos";

	/** Representa la cantidad de rotos m�ximo del usuario. */
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
	 * Variables de sonido
	 **/
	MediaPlayer spuerco_muerto;
	MediaPlayer spuerco_ini;
	MediaPlayer smoneda;
	
	/**
	 * Constante de estado de movimiento 
	 **/
	private final static int COMIENZA_ARRASTAR = 0;
	private final static int TERMINA_ARRASTAR = 1;

	//Variable de base de datos
	private ImplDaoEntrada fuenteDatos;
	//Numero de rotos que tiene el cerdo
	private int roto_conteo = 0;
	//Estado 
	private int estado = 0;
	
	/**
	 * Variables de las imagenes 
	 **/
	private ImageView puerquito; 		//imagen del cerdo
	private ImageView roto;		 		//imagen del roto
	private ImageView moneda1 = null;	//imagen para la moneda 1
	private ImageView moneda5 = null;	//imagen para la moneda 5
	private ImageView moneda10 = null;	//imagen para la moneda 10
	private ImageView moneda25 = null;	//imagen para la moneda 25
	private TextView cmonedas = null; 	//cantidad de monedas que tiene el cerdo.
	private FrameLayout aLayout;
	
	//Dice cual es la moneda que se esta moviendo actualmente
	private int actual = 0;
	//cantidad de monedas en el cerdo
	private int conteo_monedas = 0;
	
	/**
	 * Social Luis Colon 09-0038
	 */
	ImplFacebook fb = null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		validarRotos();
		
		fuenteDatos = new ImplDaoEntrada(this);
		fuenteDatos.abrir();		
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		init();
	}

	/**
	 * @author Joan Mendoza          | m: 2008-0060
	 * @author Jean Paul Demorizi    | m: 2008-0805
	 */
	public void onClick(final View vista) {
		switch (vista.getId()) {
			case R.id.boton_entrada:
				final Intent entradaActividad = new Intent(vista.getContext(),
						ActividadEntrada.class);
				startActivity(entradaActividad);
				break;
			case R.id.boton_RedesSociales:
				final Intent twitterActividad = new Intent(this,
						TweetToTwitterActivity.class);
				startActivity(twitterActividad);
				break;
			default:
				break;
		}
	}
	/**
	 * @author Luis Adrian Cruz      | m: 2008-0019
	 * @author Jean Paul Demorizi    | m: 2008-0805
	 */
	public void init() {
		aLayout= (FrameLayout)findViewById(R.id.aLayout2);
		puerquito = (ImageView) findViewById(R.id.puerco_Vista);	
		smoneda = MediaPlayer.create(ActividadAlcancia.this, R.raw.coin);
		
		zona_px1 = 90;
		zona_px2 = 400;
		zona_py1 = 200;
		zona_py2 = 500;
		
		/**Luis Colon 09-0038*/
		fb = new ImplFacebook(this);
		
		cmonedas = (TextView) findViewById(R.id.cmonedas);
		
		moneda1 = (ImageView) findViewById(R.id.moneda1_Vista);
		moneda1.setOnTouchListener(new OnTouchListener() {		
			public boolean onTouch(View v, MotionEvent event) {
				estado=1;
				actual = 1;
				Log.i("ImageStatus",""+estado);	
				return false;
			}
		});
		moneda1.setDrawingCacheEnabled(true);
//		moneda1.setOnTouchListener(this);
		moneda1.setScaleType(ScaleType.CENTER_INSIDE);
		
		moneda5 = (ImageView) findViewById(R.id.moneda5_Vista);
		moneda5.setOnTouchListener(new OnTouchListener() {		
			public boolean onTouch(View v, MotionEvent event) {
				estado=1;
				actual = 2;
				Log.i("ImageStatus",""+estado);	
				return false;	
			}
		});
		moneda5.setDrawingCacheEnabled(true);
//		moneda5.setOnTouchListener(this);
		
		moneda10 = (ImageView) findViewById(R.id.moneda10_Vista);
		moneda10.setOnTouchListener(new OnTouchListener() {		
			public boolean onTouch(View v, MotionEvent event) {
				estado=1;
				actual = 3;
				Log.i("ImageStatus",""+estado);	
				return false;
			}
		});
		moneda10.setDrawingCacheEnabled(true);
//		moneda10.setOnTouchListener(this);
		
		moneda25 = (ImageView) findViewById(R.id.moneda25_Vista);
		moneda25.setOnTouchListener(new OnTouchListener() {		
			public boolean onTouch(View v, MotionEvent event) {
				estado=1;
				actual = 4;
				Log.i("ImageStatus",""+estado);	
				return false;
			}
		});
		moneda25.setDrawingCacheEnabled(true);
//		moneda25.setOnTouchListener(this);
		
		aLayout.setOnTouchListener(new OnTouchListener() {		
			public boolean onTouch(View v, MotionEvent event) {
				if(actual == 0)
					return false;
				ImageView img = null;
				Log.i("touch",""+event);
				switch(actual)
				{
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
				
				if(estado==1) // any event from down and move
				{
					LayoutParams lp = new LayoutParams(img.getWidth(),img.getHeight());
					img.setLayoutParams(lp);
				}
				if(event.getAction()==MotionEvent.ACTION_UP){
					estado = TERMINA_ARRASTAR;	
					//img.setBackgroundColor(Color.TRANSPARENT);	
//					gravedad gmod = new gravedad(img, puerquito);
//					gmod.start();
					esValida(event, img);
					img = null;
					actual = 0;
				}
				return true;
			}
		});
		
		cmonedas.setText("$"+fuenteDatos.obtenerMontoTotal());
	}
	
	/**
	*	@Descripcion	Valida si la moneda esta o no sobre la imagen del cerdito.
	*	@author			Luis Adrian Cruz      | m: 2008-0019
	*/
	public void esValida(MotionEvent event, ImageView img)
	{
		System.out.println("*************\nEsta en la funcion\n -->"+event.getX()+" = "+zona_px1+"\n"+event.getX()+" = "+zona_px2+"\n"+event.getY()+" = "+zona_py1+"\n"+event.getY()+" = "+zona_py2+"\n************");
		if(event.getX() >= zona_px1 && event.getX() <= zona_px2
				&& event.getY() >= zona_py1 && event.getY() <= zona_py2)
		{
			System.out.println("*************\nEsta arriba del cerdo\n************");
			Entrada nEntrada = null;
			LayoutParams lp;
			int valor = 0;
			
			switch(actual)
			{
				case MONEDA_1:
					nEntrada = Entrada.crearEntrada(1.0D, new Date().toGMTString());
					valor = 1;
					lp = new LayoutParams(30,540);
					img.setScaleType(ScaleType.CENTER_INSIDE);//.setLayoutParams(lp);	
					break;
				case MONEDA_5:
					nEntrada = Entrada.crearEntrada(5.0D, new Date().toGMTString());
					valor = 5;
					//lp = new LayoutParams(145,540);
					//img.setLayoutParams(lp);
					break;
				case MONEDA_10:
					nEntrada = Entrada.crearEntrada(10.0D, new Date().toGMTString());
					valor = 10;
//					lp = new LayoutParams(260,540);
//					img.setLayoutParams(lp);
					break;
				case MONEDA_25:
					nEntrada = Entrada.crearEntrada(25.0D, new Date().toGMTString());
					valor = 25;
					break;
			}	
		     smoneda.start();
			 fuenteDatos.insertar(nEntrada);
			 conteo_monedas += valor;
			 cmonedas.setText("$"+fuenteDatos.obtenerMontoTotal());
			 
			 fb.postiarMensaje("He puesto $"+fuenteDatos.obtenerMontoTotal()+" en mi cerdito.!!");
			 
		}
		else
		{
			LayoutParams lp;
			
			switch(actual)
			{
				case MONEDA_1:
//					lp = new LayoutParams(30,540);
//					img.setLayoutParams(lp);	
					break;
				case MONEDA_5:
//					lp = new LayoutParams(145,540);
//					img.setLayoutParams(lp);
					break;
				case MONEDA_10:
//					lp = new LayoutParams(260,540);
//					img.setLayoutParams(lp);
					break;
				case MONEDA_25:
//					lp = new LayoutParams(375,540);
//					img.setLayoutParams(lp);
					break;
			}	
		}
	}
	
	public boolean onTouch(View v, MotionEvent event) 
	{
//		if(actual == 0)
//			return false;
//		ImageView img = null;
//		switch(actual)
//		{
//			case MONEDA_1:
//				img = moneda1;
//				break;
//			case MONEDA_5:
//				img = moneda5;
//				break;
//			case MONEDA_10:
//				img = moneda10;
//				break;
//			case MONEDA_25:
//				img = moneda25;
//				break;
//		}
//		
//		if (event.getAction() == MotionEvent.ACTION_UP) 
//		{
//			estado = 1;
//			Log.i("Drag", "Stopped Dragging");
//			esValida(event, img);
//			LayoutParams lp = new LayoutParams(img.getWidth(),img.getHeight());
//			img.setLayoutParams(lp);
//		} 
//		else if (event.getAction() == MotionEvent.ACTION_MOVE) 
//		{
//			if (estado == 0) {
//				System.out.println("Dragging");
//				img.setPadding((int) event.getRawX(), (int) event.getRawY(), 0, 0);
//				img.invalidate();
//			}
//		}
		return false;
	}

	@Override
	protected void onResume() {
		fuenteDatos.abrir();
		super.onResume();
	}

	@Override
	protected void onPause() {
		fuenteDatos.cerrar();
		final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(LLAVE_LIMITE_ROTOS, roto_conteo);
		editor.commit();
		super.onPause();
	}

	/**
	 * @author jmendoza | 2008-0060
	 * Metodo que se encarga de validar si la cantidad de rotos es 5 para desinstalar la aplicacion.
	 * Bajo construccion.
	 */
	private void validarRotos() {
		final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		final int rotos = prefs.getInt(LLAVE_LIMITE_ROTOS, 0);
		if (rotos == ROTOS_MAXIMO) {
			final Intent actividadDesinstalar = new Intent(this, ActividadDesinstalar.class);
			startActivity(actividadDesinstalar);
		}
	}
	
	private boolean haAlcanzadoToquesMaximo() {
		return (roto_conteo >= 3) ;
	}
	
	/**
	 * @Descripci�n: Esta es una clase privada para generar el efecto de caida con gravedad de las monedas
	 * @parametro ImageView
	 * @author Luis Adrian Cruz      | m: 2008-0019
	 */
	class gravedad extends Thread
	{
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
		public synchronized void start() {
			while(moneda.getKeepScreenOn())
			{
				lp = new LayoutParams(
						(int)moneda.getWidth()/2-1,
						(int)moneda.getHeight()/2-1);
				moneda.setLayoutParams(lp);
			}
			stop();
		}
		
	}
}