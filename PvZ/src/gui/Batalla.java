package gui;

import java.awt.GridLayout;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Batalla extends JFrame{
	private HashMap<ArrayList<Integer>, Planta> mapaFinalPlantas;
	public static void main(String[] args) {
		
		Simulacionv1 ventanaSimulacionv1 = new Simulacionv1();
	}
	public Batalla(HashMap<ArrayList<Integer>, Planta> mapaFinal1) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("ventana de batalla");
		setSize(640, 480);
		this.mapaFinalPlantas = mapaFinal1;
		setLayout(new GridLayout(5, 15));
		
		ArrayList<JButton> botones = new ArrayList<JButton>();
		for(int i = 0; i<75;i++) {
			JButton botonCesped = new JButton();
			botones.add(botonCesped);
			add(botonCesped);
		}
		for(int i = 0; i<75;i++) {
			int fila = i / 15; // 10 columnas por fila
		    int columna = i % 15;
		    ArrayList<Integer> coordenadas = new ArrayList<Integer>();
		    coordenadas.add(fila);
		    coordenadas.add(columna);
		    if(mapaFinalPlantas.containsKey(coordenadas)) {
		    	try {
					botones.get(i).setIcon(new ImageIcon(Simulacionv1.getBuferedimagePlanta(mapaFinalPlantas.get(coordenadas))));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
		
		
		
		pack();
		setVisible(true);
	}

}
