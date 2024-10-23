package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Creditos extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Creditos() {
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		JPanel panel = new JPanel() {
		
			private static final long serialVersionUID = 1L;
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage envy = null; // la imagen
                try {
                    // Cargar imagen de fondo
                    envy = ImageIO.read(getClass().getResourceAsStream("/imagenes/p1.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Dibujar la imagen de fondo
                g.drawImage(envy, 0, 0, getWidth(), getHeight(), null);
            }
			
			
		
		
		};
		JButton atras = new JButton("Atras");
		atras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MenuInicial();
				dispose();
				
			}
		});
		atras.setForeground(Color.BLACK);
		atras.setBackground(Color.WHITE);
		panel.setLayout(new BorderLayout());
		add(panel);
		panel.add(atras, BorderLayout.NORTH);
		
		
	}
	public static void main(String[] args) {
		new Creditos();
	}
	

}