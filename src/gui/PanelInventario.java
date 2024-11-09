package co.edu.uptc.tienda.gui;

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
	
	public void obtenerDatos(String[][] datos) {
		txInformacion.setFont(new Font("Monospaced", Font.PLAIN, 10));
		if (!txInformacion.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Ya se ha cargado el inventario");
			return;
		}
		StringBuilder sb = new StringBuilder();
        for (String[] dato : datos) {
			sb.append(String.format("%-9s | %-13s | %-7s | %-10s | %-8s\n",
					dato[0],
					dato[1],
					dato[2],
					dato[3],
					dato[4]));
			sb.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			sb.append("\n");
        }
		txInformacion.append(sb.toString());
	}
}
