package co.edu.uptc.tienda.gui;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import java.awt.*;

public class DialogoLista extends JDialog {

	private JTextArea txaLista;
	public DialogoLista() {
		// TODO Auto-generated constructor stub
		txaLista= new JTextArea();
		setSize(600, 300);
		add(txaLista);
	}
	
	public void agregarTexto(String texto) {
			txaLista.append(texto);
			txaLista.setFont(new Font("Monospaced", Font.PLAIN, 10));
			txaLista.append("\n");	
			
		
	}
}
