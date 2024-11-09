package co.edu.uptc.tienda.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PanelVentas extends JPanel {

	private JTextArea txInformacion;
	
	public PanelVentas(Evento evento) {
		setBorder(new TitledBorder("Linea Texto de Ventas:"));
		txInformacion= new JTextArea(60,30);
		
		JButton accion1= new JButton(Evento.CARGAR_VENTAS);
		accion1.addActionListener(evento);
		accion1.setActionCommand(Evento.CARGAR_VENTAS);
		setLayout(new BorderLayout());
		
		
		add(txInformacion,BorderLayout.CENTER);
		add(accion1,BorderLayout.SOUTH);
	}
	
	public void obtenerDatos(String[][] datos) {//El tipo de datto que se devuelve era String
		txInformacion.setFont(new Font("Monospaced", Font.PLAIN, 8));
		if (!txInformacion.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Ya se ha cargado la información de ventas");
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (String[] dato : datos) {
			sb.append(String.format("%-15s - %-7s - %-8s\n", dato[0], dato[1], dato[2]));
		}
		txInformacion.append(sb.toString());

	}
}
