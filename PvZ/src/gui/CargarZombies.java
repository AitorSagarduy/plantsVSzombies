package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CargarZombies {

	public static void cargarZombiesCSV(ArrayList<Zombie> zombies, String rutacsv) {
		File f = new File(rutacsv);
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(";");
				String tipo = campos[0];
				String nombre = campos[1];
				int vida = Integer.parseInt(campos[2]);
				int tmp_atac = Integer.parseInt(campos[3]);
				int danyo = Integer.parseInt(campos[4]);
				int velocidad = Integer.parseInt(campos[5]);
				int nivel = Integer.parseInt(campos[6]);
				 
				Zombie nuevo = new Zombie(tipo, nombre, vida, tmp_atac, danyo, velocidad, nivel, false);
				zombies.add(nuevo);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
}
