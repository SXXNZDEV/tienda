package co.edu.uptc.gui;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import java.awt.*;

public class DialogoLista extends JDialog {

	private JTextArea txaLista;
	public DialogoLista() {
		// TODO Auto-generated constructor stub
		txaLista= new JTextArea();
		setSize(1200, 200);
		add(txaLista);
	}
	
	public void agregarTexto(String texto) {
			txaLista.append(texto);
			txaLista.setFont(new Font("Monospaced", Font.PLAIN, 15));
			txaLista.append("\n");	
			
		
	}
}
