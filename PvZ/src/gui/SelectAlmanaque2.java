package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.GestorBD;
import domain.Planta;
import domain.Zombie;

public class SelectAlmanaque2 extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public SelectAlmanaque2() {
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Selecciona el almanaque");
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		
		GestorBD gestorBD = new GestorBD();
        ArrayList<Planta> lol = gestorBD.getPlantasTienda();
        System.out.println("esta abajo bro");
        System.out.println(lol);
		
		JPanel central = new JPanel();
		central.setLayout(new BorderLayout());
		add(central);
		central.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JButton plantas = new JButton();

		ImageIcon bannerplantas = new ImageIcon("src/imagenes/bannerplantas.png");
		Image img = bannerplantas.getImage();

		// Escucha cambios en el tamaño del botón
		plantas.addComponentListener(new java.awt.event.ComponentAdapter() {
		    @Override
		    public void componentResized(java.awt.event.ComponentEvent e) {
		        // Redimensiona la imagen al tamaño actual del botón
		        int width = plantas.getWidth();
		        int height = plantas.getHeight();
		        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Ajusta tamaño
		        plantas.setIcon(new ImageIcon(resizedImg)); // Configura el nuevo icono
		    }
		});

		plantas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        GestorBD gestorBD = new GestorBD();

		       // MenuPlantas.cargarPlantasCSV(sig, "src/DatosCsv/TODAS.csv");
		        AlmanaquePUser ventana = new AlmanaquePUser(gestorBD.getPlantas());
		        ventana.setLocationRelativeTo(null);
		        dispose();
			}
		}); 
		
		JButton zombies = new JButton();
		ImageIcon bannerzombies = new ImageIcon("src/imagenes/bannerzombies.png");
		Image img1 = bannerzombies.getImage();

		// Escucha cambios en el tamaño del botón
		zombies.addComponentListener(new java.awt.event.ComponentAdapter() {
		    @Override
		    public void componentResized(java.awt.event.ComponentEvent e) {
		        // Redimensiona la imagen al tamaño actual del botón
		        int width = zombies.getWidth();
		        System.out.println(width);
		        int height = zombies.getHeight();
		        Image resizedImg = img1.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Ajusta tamaño
		        zombies.setIcon(new ImageIcon(resizedImg)); // Configura el nuevo icono
		    }
		});

		// Configura el botón con el icono redimensionado
		zombies.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Zombie> sig = new ArrayList<Zombie>();
		        AlmanaqueZUser ventana = new AlmanaqueZUser(gestorBD.getZombies());
		        ventana.setLocationRelativeTo(null);
		        dispose();
			}
		}); 
		
		central.setLayout(new GridLayout(2, 1, 20, 20));
		central.setOpaque(true);
		central.add(plantas);
		central.add(zombies);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SelecAlmanaque ventana = new SelecAlmanaque();
		ventana.setLocationRelativeTo(null);
	}

}
