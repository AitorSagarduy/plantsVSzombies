package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Planta;
import domain.Zombie;

public class PRUEBA extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public PRUEBA() {
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("PRHEBA el almanaque");
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		
		JPanel central = new JPanel();
		central.setLayout(new GridLayout(3, 0));
		JButton boton1 = new JButton("Jugar");
		JButton boton2 = new JButton("Jugar2");
		JButton boton3 = new JButton("Jugar3");
		
		central.add(boton1);
		central.add(boton3);
		central.add(boton2);
		
		add(central);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		PRUEBA ventana = new PRUEBA();
		ventana.setLocationRelativeTo(null);
	}

}
