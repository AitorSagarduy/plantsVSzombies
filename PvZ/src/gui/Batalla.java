package gui;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import domain.Planta;
import domain.Zombie;

public class Batalla extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	private HashMap<ArrayList<Integer>, Planta> mapaFinalPlantas;
	private HashMap<ArrayList<Integer>, Zombie> mapaFinalZombies;
	
	private boolean detener;
	public static void main(String[] args) {
		new Simulacionv1();
	}
	public Batalla(HashMap<ArrayList<Integer>, Planta> mapaFinal1, HashMap<ArrayList<Integer>, Zombie> mapaFinal2) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ventana de batalla");
		this.mapaFinalPlantas = mapaFinal1;
		this.mapaFinalZombies = mapaFinal2;
		setLayout(new GridLayout(5, 20));
		
		ArrayList<JButton> botones = new ArrayList<JButton>();
		for(int i = 0; i<100;i++) {
			int fila = i / 20; // 10 columnas por fila
		    int columna = i % 20;
			JButton botonCesped = new JButton();
			botonCesped.putClientProperty("fila", fila);
			botonCesped.putClientProperty("columna", columna);
			ArrayList<Integer> coordenadas = new ArrayList<Integer>();
		    coordenadas.add(fila);
		    coordenadas.add(columna);
		    
		    if(mapaFinalPlantas.get(coordenadas) instanceof Planta) {
		    	try {
					botonCesped.setIcon(new ImageIcon(Simulacionv1.getBuferedimagePlanta(mapaFinalPlantas.get(coordenadas)).getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
					botonCesped.putClientProperty("planta", mapaFinalPlantas.get(coordenadas));
		    	} catch (IOException e) {
					e.printStackTrace();
				}
		    } else if (mapaFinalZombies.get(coordenadas) instanceof Zombie) {
		    	try {
					botonCesped.setIcon(new ImageIcon(Simulacionv2.getBuferedimagePlanta(mapaFinalZombies.get(coordenadas)).getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
					botonCesped.putClientProperty("planta", mapaFinalZombies.get(coordenadas));
		    	} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		    
			botonCesped.addActionListener(e -> System.out.println(botonCesped.getClientProperty("fila")+","+ botonCesped.getClientProperty("columna")));
			
			botones.add(botonCesped);
			add(botonCesped);
		}
		
		
		
		pack();
		setVisible(true);
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		setLocationRelativeTo(null);
		setResizable(false);
		
		Thread hilo = new Thread();
		detener = false;
		hilo =  new Thread(() -> {
			while(!detener) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i = 0; i<100;i++) {
					JButton boton = botones.get(i);
					if(boton.getClientProperty("planta") instanceof Zombie) {
						if((int)(boton.getClientProperty("columna")) == 0) {
							detener = true;
							System.out.format("El zombie %s gana", ((Zombie)boton.getClientProperty("planta")).getTipo());
						}else {
							botones.get(i-1).setIcon(boton.getIcon());
							boton.setIcon(null);
							botones.get(i-1).putClientProperty("planta", boton.getClientProperty("planta"));
							boton.putClientProperty("planta", null);
						}
					}
				}
				
			}
		});
		hilo.start();
	}
	@Override
	public void run() {
		SwingUtilities.invokeLater(()->new Simulacionv1());
		
	}

}