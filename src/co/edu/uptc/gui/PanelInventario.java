package co.edu.uptc.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PanelInventario extends JPanel {

	private JTextArea txInformacion;
	
	public PanelInventario(Evento evento) {
		setBorder(new TitledBorder("Inventario:"));
		txInformacion= new JTextArea(60,30);
		
		JButton accion1= new JButton(Evento.CARGAR);
		accion1.addActionListener(evento);
		accion1.setActionCommand(Evento.CARGAR);
		setLayout(new BorderLayout());
		txInformacion.setEditable(true);
		
		add(txInformacion,BorderLayout.CENTER);
		add(accion1,BorderLayout.SOUTH);
	}
	
	public String obtenerDatos() {
		return txInformacion.getText();
	}
}
