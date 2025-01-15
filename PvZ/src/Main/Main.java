package Main;

import java.sql.SQLException;
import javax.swing.SwingUtilities;
import db.GestorBD;
import gui.MenuInicial;

public class Main {
	public static int solespublic;
	public static int cerebrospublic;

	public static void main(String[] args) throws SQLException {
		GestorBD gestorBD = new GestorBD();
		gestorBD.initilizeFromCSV();
		gestorBD.getCoins();

		SwingUtilities.invokeLater(() -> new MenuInicial());

	}

}
