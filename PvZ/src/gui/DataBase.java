package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JFrame;

public class DataBase extends JFrame{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	public static String EncontrarDatosUsuario(String i) {
		String p = null;
		File f = new File("usurname.txt");
		try  {
			ArrayList<String> users = new ArrayList<String>();
			ArrayList<String> datafiles = new ArrayList<String>();
			try (Scanner sc = new Scanner(f)) {
				while(sc.hasNextLine()) {
					String linea = sc.nextLine();
					String[] datos = linea.split(";");
					String usuario = datos[0];
					String dataFile = datos[1];
					users.add(usuario);
					datafiles.add(dataFile);
				}
			}
			int w = 0;
			for (String string : users) {	
				if(string.equals("i")) {
					p = datafiles.get(w);
					p = "";
				}
				w++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	

}
