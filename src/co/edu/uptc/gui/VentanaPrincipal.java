package co.edu.uptc.gui;

import co.edu.uptc.negocio.AdministracionSistema;

import java.awt.*;
import java.io.IOException;
import javax.swing.JFrame;

public class VentanaPrincipal extends JFrame {

	private PanelInventario inventario;
	private PanelVentas infoVentas;
	private PanelBotones botones;
	private PanelPersona persona;
	private AdministracionSistema administracion = new AdministracionSistema();
	
	public VentanaPrincipal(){
		setTitle("Mi Tienda");
		setSize(1100,600);
		
		Evento evento= new Evento(this);
		inventario = new PanelInventario(evento);
		infoVentas= new PanelVentas(evento);
		botones = new PanelBotones(evento);
		persona = new PanelPersona(evento);
		
		setLayout(new BorderLayout());
		add(inventario,BorderLayout.WEST);
		add(persona,BorderLayout.CENTER);
		add(infoVentas,BorderLayout.EAST);
		add(botones,BorderLayout.SOUTH);

		
	}

	public static void main(String[] args) throws IOException {//IOException toca quitarlo
		VentanaPrincipal nueva= new VentanaPrincipal();
		nueva.setVisible(true);
	}

	public void loadSellers() {
		String info = persona.obtenerDatos();
		administracion.loadSellers(info);
	}
	
	public void cargarInventario() {
		inventario.obtenerDatos(administracion.cargarInfoInventario());
	}

	public void cargarVentas() {
	}

	public void cargarPersonas() {}



	
	public void cargarInfoVentas() {
		//TODO implementar logica para separa información
		// trae lo que hay en textArea en String



	}
	
	public void generarInformeInventario() {
		DialogoLista nuevo= new DialogoLista();
		String txt = administracion.generarStock();
		nuevo.agregarTexto(txt);
		nuevo.setVisible(true);
	}

	public void salir() {
		System.exit(0);
	}
}
