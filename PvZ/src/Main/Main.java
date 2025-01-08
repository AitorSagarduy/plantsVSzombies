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
		System.out.println(gestorBD.getPlantas());
		System.out.println(gestorBD.getZombies());
		gestorBD.Level_updater_P("Lanzaguisantes", 69, 69, 69, 3, 100);
		gestorBD.Level_updater_Z("Zombi Buzo", 0, 0, 0, 0, 0);
		System.out.println(gestorBD.getPlantas());
		SwingUtilities.invokeLater(() -> new MenuInicial());
		gestorBD.Name_updater_P("Girasol", "el lado luminoso");
		gestorBD.Name_updater_Z("Zombi Buzo", "el lado oscuro");
	}

}
