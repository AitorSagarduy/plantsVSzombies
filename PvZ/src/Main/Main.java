package Main;

import javax.swing.SwingUtilities;

import db.GestorBD;
import gui.MenuInicial;

public class Main {

	public static void main(String[] args) {
		GestorBD gestorBD = new GestorBD();
		gestorBD.initilizeFromCSV();
		SwingUtilities.invokeLater(() -> new MenuInicial());
	}

}
