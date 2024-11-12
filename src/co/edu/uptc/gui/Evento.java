package co.edu.uptc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Evento implements ActionListener {

	public static final String VENTAS="VENTAS";
	
	public static final String STOCK="STOCK";
	
	public static final String MAS_VENDIDO="+ VENDIDO";
	
	public static final String IMPUESTOS="IMPUESTOS";
	
	public static final String CARGAR="Cargar Inverntario";
	
	public static final String CARGAR_PERSONAS="Cargar Persona";
	
	public static final String CARGAR_VENTAS="Cargar Ventas";
	
	public static final String SALIR="SALIR";
	
	
	private final VentanaPrincipal ventana;
	
	public Evento(VentanaPrincipal ventanaPrincipal) {
		this.ventana= ventanaPrincipal;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String evento= e.getActionCommand();
		
		//TODO reto del dia llamar a los demas metodos
        switch (evento) {
            case CARGAR -> ventana.cargarInventario();
			case CARGAR_PERSONAS -> ventana.loadSellers();
			case CARGAR_VENTAS -> ventana.loadSales();
            case STOCK -> ventana.showSales();
            case VENTAS -> ventana.reportSales();
			case MAS_VENDIDO -> ventana.reportBestSelling();
			case IMPUESTOS -> ventana.reportIVA();
            case SALIR -> ventana.salir();
        }
	}
}
