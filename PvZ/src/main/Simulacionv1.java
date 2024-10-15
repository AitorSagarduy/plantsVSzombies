package main;

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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Simulacionv1 extends JFrame{
	
	public static void main(String[] args) {
		Simulacionv1 ventana = new Simulacionv1();
	}
	//hago mi renderizado para interactuar como quiera con la jlist
	public class Mirenderizado extends DefaultListCellRenderer {

		
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			// Intento de crear una label con la que interactuar
			JLabel etiqueta = (JLabel)(super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus));
			//me aseguro que el objeto sea una planta
			if (value instanceof Planta) {
				// lo casteo para tener sus metodos
	            Planta planta = (Planta) value;
	            
	            // hago que se muestre el nombre
	            setText(planta.getNombre()); // solo muestra el nombre
	            // pruebo a ponerle un icono con el metodo getIconoPlanta
	            try {
					
	            	setIcon(new ImageIcon(getBuferedimagePlanta(planta).getScaledInstance(24, 24, Image.SCALE_SMOOTH))); //le pongo un icono 
				} catch (IOException e) {
					// si no lo encuentra esntonces saco el error por consola
					e.printStackTrace();
				} 
	        }
			
			return etiqueta;
		}

	}
	//Metodo para pillar su imagen de la carpeta de imagenes y como voy a leer le digo que puede dar error al leer
	private BufferedImage getBuferedimagePlanta(Planta planta) throws IOException {
		// dandole una planta me devulve su icono que deberia estar en imagenes
		//pillo la lista de ejemplos de plantas que ha metido mi compañero
		String[] posiblesplantas = {"Girasol", "Lanzaguisantes", "Hielaguisantes", "Apisonaflor", "Cactus", "Coltapulta", "Guisantralladora", "Humoseta", "Jalapeno", "Melonpulta","Nuez", "NuezGrande", "Patatapum", "PlantaCarronivora", "Repetidora", "SetaDesesporadora", "Trebolador", "Tripitidora" };
		//saco cada sombre de del array de los ejemplos
		for (String nombreplanta : posiblesplantas) {
			//pruebo a leer su imagen si es que existe
			try {
				// Leo la imagen en imagenLeer y lo escalo en imagen a 24x24 y devuelvo un icono creado a base de la imagen 
				BufferedImage imagenLeer = ImageIO.read(new File("src/imagenes/" + planta.getTipo() + ".png"));
				//Image imagen = imagenLeer.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
				//return new ImageIcon(imagen);
				return imagenLeer;
				//return new ImageIcon("src/imagenes/" + planta.getTipo() + ".png");
			} catch (Exception e) {
				// Si es que no encuentro la imagen entonces mando la imagen NoIdentificada que tenemos en imagenes y hago lo mismo que en la anterior
				//return new ImageIcon("src/imagenes/NoIdentificada.png");
				BufferedImage imagenLeer = ImageIO.read(new File("src/imagenes/NoIdentificada.png"));
				//Image imagen = imagenLeer.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
				//return new ImageIcon(imagen);
				return imagenLeer;
			}
		}
		return null;
	}

	Planta plantaSeleccionada = null; //variable para almacenar la planta que haya seleccionado el ussuario dentro de la lista
	// creo la ventana
	public Simulacionv1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("ventana de simulacion");
		setSize(640, 480);
		/*
		String[] plantasBasicas = {
				"Girasol","Lanzaguisantes","Nuez", "ojolera"
		};
		 * 
		 */
		ArrayList<Planta> plantas = new ArrayList<Planta>(); // creo el arraylist de plantas en la que voy a cargar las plantas leidas que hay en csv
		MenuPlantas.cargarPlantasCSV(plantas, "src/DatosCsv/plantas.csv"); // cargo las plantas con el metodo que ha creado mi compañero
		DefaultListModel<Planta> modelo = new DefaultListModel<Planta>(); // creo un modelo de lista predeterminado parametrizado a el objeto Planta
		// añado cada planta del arraylist al modelo de lista
		for(int i = 0; i<plantas.size();i++) {
			modelo.add(i, plantas.get(i));
		}
		JList<Planta> listaPlantas = new JList<Planta>(modelo); //creo el jlist en base al modelo de lista
		listaPlantas.setFixedCellWidth(100); //le pongo una largura definida
		listaPlantas.setCellRenderer(new Mirenderizado()); // le pongo mi renderizado creado previamente arriba
		// le pongo un listener para que cuando el usuario este eligiendo una opcion el programa lo escuche y actue
		listaPlantas.addListSelectionListener(new ListSelectionListener() { 
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// si se esta eligiendo una opcion poner esa opcion como planta seleccionada y lo printo para saber si se ha elegido bien
				if(!e.getValueIsAdjusting()) {
					System.out.println(listaPlantas.getSelectedValue().getNombre()+" ha sido seleccionada");
					plantaSeleccionada = listaPlantas.getSelectedValue();
				}
			}
		});
		
		JScrollPane scroll = new JScrollPane(listaPlantas); //creo el scrollbar en el que voy a poner la jlist
		add(scroll,BorderLayout.WEST); //lo pongo a la izquierda 
		
		//Crear una barra de progreso como en el juego original (no funcional, necesito saber de hilos de java)
		JProgressBar barraProgreso = new JProgressBar(0,100);
		add(barraProgreso, BorderLayout.NORTH);
		barraProgreso.setBackground(Color.gray);
		barraProgreso.setForeground(Color.GREEN);
		
		JPanel panelCesped = new JPanel(); //creo el panel que va a simular el patio
		panelCesped.setBackground(Color.GRAY);
		panelCesped.setLayout(new GridLayout(5, 10)); //en el juego original el patio es un 5*10 (incluyendo los cortacesped)
		//creo los 50 botones que voy a necesitar para interactuar con el patio
		for(int i = 0; i<50;i++) {
			JButton espacio = new JButton(""+i); //a cada boton le pongo un numero como nombre inicial
			espacio.setBackground(Color.GREEN); 
			//le pongo un listenner para que haga algo cada vez que lo aprieto
			espacio.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// funcion de interaccion del mouse con los botones 
					if(plantaSeleccionada == null) {
						//si es que no ha elegido ninguna todavia entonces sale error de no haber elegido ninguna planta
						JOptionPane.showMessageDialog(Simulacionv1.this, "No has seleccionado una planta ha colocar", "Error", JOptionPane.ERROR_MESSAGE);
					}else {
						// cambio el texto del boton por el nombre de la planta seleccionada
						//espacio.setText(plantaSeleccionada.getNombre());
						espacio.setText("");
						reproducirSonido("src/sonidos/plantado.wav");
						listaPlantas.getSelectedValue().setPlantando(true);
						
						
						try {
							// intento ponerle el icono de la planta seleccionada
							espacio.setIcon(new ImageIcon(getBuferedimagePlanta(plantaSeleccionada).getScaledInstance(espacio.getWidth(), espacio.getHeight(), Image.SCALE_SMOOTH)));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
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
		/*
		 * 
		for (int i = 0; i <= 100; i++) {
            barraProgreso.setValue(i); // Actualizar valor de la barra
            if(barraProgreso.isMaximumSizeSet()) {
            	barraProgreso.setForeground(Color.RED);
            }
            try {
                Thread.sleep(50); // Esperar 50 ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		 */
	}
}
