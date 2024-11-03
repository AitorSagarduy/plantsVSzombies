package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.Simulacionv1.Mirenderizado;
public class Simulacionv2 extends JFrame{
	public static boolean desplantando = false;

	//hago mi renderizado para interactuar como quiera con la jlist
	public class Mirenderizado extends DefaultListCellRenderer {
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			// Intento de crear una label con la que interactuar
			JLabel etiqueta = (JLabel)(super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus));
			//me aseguro que el objeto sea una planta
			if (value instanceof Zombie) {
				// lo casteo para tener sus metodos
	            Zombie zombie = (Zombie) value;
	            // hago que se muestre el nombre
	            setText(zombie.getNombre()); // solo muestra el nombre
	            // pruebo a ponerle un icono con el metodo getIconoPlanta
	            try {
	            	setIcon(new ImageIcon(getBuferedimageZombie(zombie).getScaledInstance(24, 24, Image.SCALE_SMOOTH))); //le pongo un icono 
				} catch (IOException e) {
					// si no lo encuentra esntonces saco el error por consola
					e.printStackTrace();
				} 
	        }
			return etiqueta;
		}

	}

	//Metodo para pillar su imagen de la carpeta de imagenes y como voy a leer le digo que puede dar error al leer
	private BufferedImage getBuferedimageZombie(Zombie zombie) throws IOException {
		// dandole una planta me devulve su icono que deberia estar en imagenes
		try {
			// Leo la imagen en imagenLeer 
			BufferedImage imagenLeer = ImageIO.read(new File("src/imagenes/" + zombie.getTipo() + ".png"));
			return imagenLeer;
		} catch (Exception e) {
			// Si es que no encuentro la imagen entonces mando la imagen NoIdentificada
			BufferedImage imagenLeer = ImageIO.read(new File("src/imagenes/NoIdentificada.png"));
			return imagenLeer;
		}
	}
	Zombie zombieSeleccionado = null; //variable para almacenar la planta que haya seleccionado el ussuario dentro de la lista
	// creo la ventana
	public Simulacionv2() {
		//setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("ventana de simulacion");
		setSize(640, 480);
		
		
		ArrayList<Zombie> zombies = new ArrayList<Zombie>(); // creo el arraylist de plantas en la que voy a cargar las plantas leidas que hay en csv
		CargarZombies.cargarZombiesCSV(zombies, "src/DatosCsv/zombies.csv"); // cargo las plantas con el metodo que ha creado mi compañero
		DefaultListModel<Zombie> modelo = new DefaultListModel<Zombie>(); // creo un modelo de lista predeterminado parametrizado a el objeto Planta
		// añado cada planta del arraylist al modelo de lista
		for(int i = 0; i<zombies.size();i++) {
			modelo.add(i, zombies.get(i));
		}
		JList<Zombie> listaZombies = new JList<Zombie>(modelo); //creo el jlist en base al modelo de lista
		
		listaZombies.setFixedCellWidth(100); //le pongo una largura definida
		listaZombies.setCellRenderer(new Mirenderizado()); // le pongo mi renderizado creado previamente arriba
		// le pongo un listener para que cuando el usuario este eligiendo una opcion el programa lo escuche y actue
		listaZombies.addListSelectionListener(new ListSelectionListener() { 
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// si se esta eligiendo una opcion poner esa opcion como planta seleccionada y lo printo para saber si se ha elegido bien
				if(listaZombies.getSelectedValue() != null) {
					
					if(!e.getValueIsAdjusting()) {
						System.out.println(listaZombies.getSelectedValue().getNombre()+" ha sido seleccionado");
						zombieSeleccionado = listaZombies.getSelectedValue();
						if(desplantando) {
							desplantando = false;
						}
						
					}
				}
			}
		});
		JButton pala = new JButton("Pala");
		pala.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(desplantando) {
					desplantando = false;
				}else {
					desplantando = true;
				}
				
			}
		});
		add(pala, BorderLayout.NORTH);
		JScrollPane scroll = new JScrollPane(listaZombies); //creo el scrollbar en el que voy a poner la jlist
		add(scroll,BorderLayout.EAST); //lo pongo a la izquierda 
		
		JPanel panelCesped = new JPanel(); //creo el panel que va a simular el patio
		panelCesped.setBackground(Color.GRAY);
		panelCesped.setLayout(new GridLayout(5, 10)); //en el juego original el patio es un 5*10 (incluyendo los cortacesped)
		//creo los 50 botones que voy a necesitar para interactuar con el patio
		for(int i = 0; i<50;i++) {
			JButton espacio = new JButton(); //a cada boton le pongo un numero como nombre inicial
			//espacio.setBackground(Color.GREEN); 
//			espacio.setOpaque(true);
//			espacio.setContentAreaFilled(true);
//			espacio.setBorderPainted(true);
			//le pongo un listenner para que haga algo cada vez que lo aprieto
			espacio.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// funcion de interaccion del mouse con los botones 
					if(!desplantando) {
						
						if(zombieSeleccionado == null) {
							//si es que no ha elegido ninguna todavia entonces sale error de no haber elegido ninguna planta
							JOptionPane.showMessageDialog(Simulacionv2.this, "No has seleccionado un zombie a colocar", "Error", JOptionPane.ERROR_MESSAGE);
						}else {
							if(espacio.getClientProperty("zombie") == null) {
								
								reproducirSonido("src/sonidos/plantado.wav");
								modelo.removeElement(zombieSeleccionado);
								espacio.putClientProperty("zombie", zombieSeleccionado);
								try {
									espacio.setIcon(new ImageIcon(getBuferedimageZombie(zombieSeleccionado).getScaledInstance(espacio.getWidth(), espacio.getHeight(), Image.SCALE_SMOOTH)));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								zombieSeleccionado = null;
							} else {
								JOptionPane.showMessageDialog(Simulacionv2.this, "No puedes colocar un zombie encima de otro.", "Error", JOptionPane.ERROR_MESSAGE);
							}
							
						}
					}
					else if(espacio.getIcon() != null){
						modelo.addElement((Zombie) espacio.getClientProperty("zombie"));
						espacio.setIcon(null);
						espacio.putClientProperty("zombie", null);
					}
					
				}

				private void reproducirSonido(String ruta) {
					try {
				        // Cargar el archivo de sonido
				        File archivoSonido = new File(ruta);
				        AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoSonido);

				        // Preparar y reproducir el sonido
				        Clip clip = AudioSystem.getClip();
				        clip.open(audioStream);
				        clip.start();  // Iniciar reproducción
				    } catch (Exception e) {
				        e.printStackTrace();  // Manejar excepciones
				    }
				}
			});
			panelCesped.add(espacio); // añado el boton creado al panel
		}
		
		add(panelCesped, BorderLayout.CENTER); //pongo el panel en el centro para que ocupe lo que resta
		
		pack();
		setVisible(true);
	}
	public static void main(String[] args) {
		Simulacionv2 ventana = new Simulacionv2();

	}

}
