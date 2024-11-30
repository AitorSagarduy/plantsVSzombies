package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SelecAlmanaque extends JFrame{
	
	public SelecAlmanaque() {
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("ventana de lololoion");
		setSize(Resolucion.resolucionx("src/DatosCsv/resolucion.txt"), Resolucion.resoluciony("src/DatosCsv/resolucion.txt"));
		
		JPanel central = new JPanel();
		central.setLayout(new BorderLayout());
		add(central);
		central.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JButton plantas = new JButton("Plantas");
		plantas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Planta> sig = new ArrayList<Planta>();
		        MenuPlantas.cargarPlantasCSV(sig, "src/DatosCsv/TODAS.csv");
		        new MenuPlantas(sig);
			}
		}); 
		
		JButton zombies = new JButton("Zombis");
		zombies.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Zombie> sig = new ArrayList<Zombie>();
		        new MenuZombies(sig, "src/DatosCsv/zombies.csv");
				
			}
		}); 
		
		central.setLayout(new GridLayout(2, 1, 20, 20));
		central.setOpaque(true);
		central.add(plantas);
		central.add(zombies);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		SelecAlmanaque ventana = new SelecAlmanaque();
	}

}
