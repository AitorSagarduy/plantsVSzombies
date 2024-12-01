package Main;

import javax.swing.SwingUtilities;

import gui.MenuInicial;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MenuInicial());
	}

}
