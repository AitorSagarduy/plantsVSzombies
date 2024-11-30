package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SelecAlmanaque extends JFrame{
	
	public SelecAlmanaque() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("ventana de lololoion");
		setSize(640, 480);
		setVisible(true);
		
		
		JPanel central = new JPanel();
		add(central);
		JButton plantas = new JButton("Plantas");
		plantas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Planta> sig = new ArrayList<Planta>();
		        MenuPlantas.cargarPlantasCSV(sig, "src/DatosCsv/TODAS.csv");
		        new MenuPlantas(sig);
				System.out.println("hshshhshs");
			}
		}); 
		
		
		plantas.setPreferredSize(new Dimension(200, 200));
		JButton zombies = new JButton("Zombis");
		
		central.setOpaque(true);
		central.add(plantas, BorderLayout.NORTH);
		central.add(zombies, BorderLayout.SOUTH);
		
	}
	
	
	public static void main(String[] args) {
		SelecAlmanaque ventana = new SelecAlmanaque();
		
	}

}
