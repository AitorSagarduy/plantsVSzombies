package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class MenuPlantas extends JFrame{

	   private ArrayList<Planta> plantas; 

	   public static void main(String[] args) {
	        ArrayList<Planta> plantas = new ArrayList<Planta>();
	        cargarPlantasCSV(plantas);
	        MenuPlantas ventana = new MenuPlantas(plantas);  
	   }
	
	public static void cargarPlantasCSV(ArrayList<Planta> plantas) {
		File f = new File("src/DatosCsv/plantas.csv");
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
				int rango = Integer.parseInt(campos[5]);
				int nivel = Integer.parseInt(campos[6]);
				
				int contador = 0;
				for (Planta planta : plantas) {
					while(planta.getNombre().equals(nombre)) {
						contador++;
						nombre = nombre + " " + contador;
					}
				}
				
				Planta nueva = new Planta(tipo, nombre, vida, tmp_atac, danyo, rango, nivel);
				plantas.add(nueva);
			}	
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MenuPlantas(ArrayList<Planta> plantas) {
		setTitle("PVZ");
        setSize(1280, 720);
        Dimension botontamanyo = new Dimension(300, 400);
      
        String[] posiblesplantas = {"Girasol", "Lanzaguisantes", "Hielaguisantes", "Apisonaflor", "Cactus", "Coltapulta", "Guisantralladora", "Humoseta", "Jalapeno", "Melonpulta","Nuez", "NuezGrande", "Patatapum", "PlantaCarronivora", "Repetidora", "SetaDesesporadora", "Trebolador", "Tripitidora" };
        
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 4, 20, 20)); //Aitor:filas, columnas, hgap, vgap 
        
        for (Planta planta : plantas) {
        	String plantaimagen = "";
        	JButton boton = new JButton(planta.getNombre());

        	for (String nombreplanta : posiblesplantas) {
        		if(planta.getTipo().contains(nombreplanta)) {
        			plantaimagen = "src/imagenes/" + planta.getTipo() + ".png";
        		} else {
        			plantaimagen = "src/imagenes/NoIdentificada.png";
        		}
			}
        	
        	
			ImageIcon imagen = new ImageIcon(plantaimagen);
			boton.setIcon(imagen);
			boton.setPreferredSize(botontamanyo);
			boton.setVerticalTextPosition(JButton.BOTTOM); 
			boton.setHorizontalTextPosition(JButton.CENTER);
			boton.setIconTextGap(40);
			
			Font fuente = new Font("Arial", Font.BOLD, 45);
			boton.setFont(fuente);
			
			
        	panel.add(boton);
		}
        
        JScrollPane scroll = new JScrollPane(panel);
       // scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scroll, BorderLayout.CENTER);
        
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        //panel.setBackground(Color.GREEN);

        setVisible(true);
		
		//POR HACER:
        //Que el texto se vuelva mas pequeño cuando no quepa en el boton
	}


	
}
