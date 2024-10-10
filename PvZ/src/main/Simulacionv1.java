package main;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Simulacionv1 extends JFrame{
	
	public static void main(String[] args) {
		Simulacionv1 ventana = new Simulacionv1();
	}
	public Simulacionv1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle("ventana de simulacion");
		setSize(640, 480);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		add(panel, BorderLayout.CENTER);
		
		JList listaPlantas = new JList();
		listaPlantas.setFixedCellWidth(200);
		//lista.setCellRenderer(new Mirenderizado());
		
		JScrollPane scroll = new JScrollPane(listaPlantas);
		add(scroll,BorderLayout.WEST);
		
		setVisible(true);
	}
}
