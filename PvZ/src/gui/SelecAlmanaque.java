package gui;

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
		JButton plantas = new JButton("Plantas");
		JButton zombies = new JButton("Zombis");
		
		central.add(plantas);
		central.add(zombies);
		
	}
	
	
	public static void main(String[] args) {
		SelecAlmanaque ventana = new SelecAlmanaque();
		
	}

}
