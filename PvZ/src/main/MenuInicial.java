package main;
// esta parte es de Pele

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuInicial extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MenuInicial() {
		super("Ventana mínima");
        setSize(320, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        JPanel panel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage imagen = null;
                try {
					imagen = ImageIO.read(getClass().getResourceAsStream("/imagenes/menu.jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), null);
            }
			
        };
        add(panel);
        
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	

	public static void main(String[] args) {
		new MenuInicial();

	}

}
