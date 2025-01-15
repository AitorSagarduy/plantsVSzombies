package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import db.GestorBD;

public class SelecAlmanaque extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public SelecAlmanaque() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Selecciona el almanaque");
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		
		//GestorBD para poder usar la base de datos
		GestorBD gestorBD = new GestorBD();

		//Crear un panel donde van a ir los botones
		JPanel central = new JPanel();
		central.setLayout(new BorderLayout());
		add(central);
		
		//Crear los botones de las plantas y los zombies
		JButton plantas = new JButton();
		JButton zombies = new JButton();
		
		//Cargar las imagenes que van a ir en los botones
		ImageIcon bannerplantas = new ImageIcon("src/imagenes/bannerplantas.png");
		ImageIcon bannerzombies = new ImageIcon("src/imagenes/bannerzombies.png");
		//Los botones necesitan Image y no ImageIcon, hay que convertirlos
		Image img = bannerplantas.getImage();
		Image img1 = bannerzombies.getImage();
		
		//Hacer que la imagen se ajuste dinamicamente al tamaño del boton
		//El componentlistener detecta cuando el boton cambia de tamaño
		plantas.addComponentListener(new java.awt.event.ComponentAdapter() {
		    @Override
		    public void componentResized(java.awt.event.ComponentEvent e) {
		        //Sacar el tamaño del boton, al ajustarse de forma dinamica, hay que hacerlo asi
		        int width = plantas.getWidth();
		        int height = plantas.getHeight();
		        Image redimensionada = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		        //Ponerle al boton la imagen
		        plantas.setIcon(new ImageIcon(redimensionada));
		    }
		});

		//Al darle click al boton de las plantas
		plantas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Cargar de la base de datos la arraylist con las plantas
		        MenuPlantas ventana = new MenuPlantas(gestorBD.getPlantas());
		        //Cerrar esta ventana y dejar la nueva en el centro de la pantalla
		        ventana.setLocationRelativeTo(null);
		        dispose();
			}
		}); 
		
		//Lo mismo que se hizo con la imagen de las plantas
		zombies.addComponentListener(new java.awt.event.ComponentAdapter() {
		    @Override
		    public void componentResized(java.awt.event.ComponentEvent e) {
		        int width = zombies.getWidth();
		        int height = zombies.getHeight();
		        Image redimensionada = img1.getScaledInstance(width, height, Image.SCALE_SMOOTH); 
		        zombies.setIcon(new ImageIcon(redimensionada)); 
		    }
		});

		//Al darle click al boton de los zombies
		zombies.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MenuZombies ventana = new MenuZombies(gestorBD.getZombies());
		        ventana.setLocationRelativeTo(null);
		        dispose();
			}
		}); 

		central.add(plantas);
		central.add(zombies);
		
		//Hacer un gridlayout para que los botones salgan uno encima del otro
		central.setLayout(new GridLayout(2, 1, 20, 20));
		
		central.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		central.setOpaque(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SelecAlmanaque ventana = new SelecAlmanaque();
		ventana.setLocationRelativeTo(null);
	}

}
