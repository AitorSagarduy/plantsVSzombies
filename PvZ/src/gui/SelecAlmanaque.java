package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import domain.Planta;
import domain.Zombie;

public class SelecAlmanaque extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public SelecAlmanaque() {
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Selecciona el almanaque");
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		
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
		        MenuPlantas ventana = new MenuPlantas(sig);
		        ventana.setLocationRelativeTo(null);
		        dispose();
			}
		}); 
		
		JButton zombies = new JButton("Zombis");
		zombies.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Zombie> sig = new ArrayList<Zombie>();
		        MenuZombies ventana = new MenuZombies(sig, "src/DatosCsv/zombies.csv");
		        ventana.setLocationRelativeTo(null);
		        dispose();
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
		ventana.setLocationRelativeTo(null);
	}

}
