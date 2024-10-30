package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SelecPlantas extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139614017884562916L;


	public static void main(String[] args) {
		ArrayList<Planta> plantas = new ArrayList<Planta>();
		MenuPlantas.cargarPlantasCSV(plantas, "src/DatosCsv/plantas.csv");
        SelecPlantas ventana = new SelecPlantas(plantas);

	}
	
	public SelecPlantas(ArrayList<Planta> plantas) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Selecciona Plantas");
		setSize(1280, 720);
		
		
		ModeloTabla modelo = new ModeloTabla(plantas);
		JTable tabla = new JTable(modelo);
		JScrollPane scrollPane = new JScrollPane(tabla);
		add(scrollPane, BorderLayout.CENTER);
		
		
		
		
		
		setVisible(true);
	}


}
