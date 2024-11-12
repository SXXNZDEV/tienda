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
		String info = inventario.obtenerDatos();
		administracion.loadInventory(info);
	}
	public void loadSales() {
		String info = infoVentas.obtenerDatos();
		administracion.loadSales(info);
	}

	public void showSales() {
		DialogoLista nuevo= new DialogoLista();
		String txt = administracion.reportInventoy();
		nuevo.agregarTexto(txt);
		nuevo.setVisible(true);
	}

	public void reportSales() {
		DialogoLista nuevo= new DialogoLista();
		String txt = administracion.reportSales();
		nuevo.agregarTexto(txt);
		nuevo.setVisible(true);
	}

	public void reportBestSelling() {
		DialogoLista nuevo= new DialogoLista();
		String txt = administracion.reportBestSelling();
		nuevo.agregarTexto(txt);
		nuevo.setVisible(true);
	}

	public void reportIVA() {
		DialogoLista nuevo= new DialogoLista();
		String txt = administracion.reportTaxes();
		nuevo.agregarTexto(txt);
		nuevo.setVisible(true);
	}

	public void salir() {
		System.exit(0);
	}
}
