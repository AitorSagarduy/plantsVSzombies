package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Resolucion {
	
	public static Integer resolucionx(String ruta) {
		File f = new File(ruta);
		Integer resol = 0;
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(";");
				int x = Integer.parseInt(campos[0]);
				resol = x;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return resol;
	}
	
	public static Integer resoluciony(String ruta) {
		File f = new File(ruta);
		Integer resol = 0;
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(";");
				int y = Integer.parseInt(campos[1]);
				resol = y;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return resol;
	}

}
