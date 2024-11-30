package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Simulacionv1 extends JFrame{
	private static boolean desplantando = false;
	private HashMap<ArrayList<Integer>, Planta> mapaFinal;
	
	
	public HashMap<ArrayList<Integer>, Planta> getMapaFinal() {
		return mapaFinal;
	}
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
	public static BufferedImage getBuferedimagePlanta(Planta planta) throws IOException {
		// dandole una planta me devulve su icono que deberia estar en imagenes
		try {
			// Leo la imagen en imagenLeer 
			BufferedImage imagenLeer = ImageIO.read(new File("src/imagenes/" + planta.getTipo() + ".png"));
			return imagenLeer;
		} catch (Exception e) {
			// Si es que no encuentro la imagen entonces mando la imagen NoIdentificada
			BufferedImage imagenLeer = ImageIO.read(new File("src/imagenes/NoIdentificada.png"));
			return imagenLeer;
		}
	}
	Planta plantaSeleccionada = null; //variable para almacenar la planta que haya seleccionado el ussuario dentro de la lista
	// creo la ventana
	public Simulacionv1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("ventana de simulacion");
		setSize(640, 480);
		
		
		ArrayList<Planta> plantas = new ArrayList<Planta>(); // creo el arraylist de plantas en la que voy a cargar las plantas leidas que hay en csv
		HashMap<ArrayList<Integer>, Planta> mapaFinal = new HashMap<ArrayList<Integer>, Planta>();
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
				if(listaPlantas.getSelectedValue() != null) {
					
					if(!e.getValueIsAdjusting()) {
						System.out.println(listaPlantas.getSelectedValue().getNombre()+" ha sido seleccionada");
						plantaSeleccionada = listaPlantas.getSelectedValue();
						if(desplantando) {
							desplantando = false;
						}
						
					}
				}
			}
		});
		JButton finalizar = new JButton("Terminar");
		
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
		JPanel opciones = new JPanel();
		add(opciones, BorderLayout.NORTH);
		opciones.setLayout(new FlowLayout());
		opciones.add(pala);
		opciones.add(finalizar);
		JScrollPane scroll = new JScrollPane(listaPlantas); //creo el scrollbar en el que voy a poner la jlist
		add(scroll,BorderLayout.WEST); //lo pongo a la izquierda 
		
		JPanel panelCesped = new JPanel(); //creo el panel que va a simular el patio
		panelCesped.setBackground(Color.GRAY);
		panelCesped.setLayout(new GridLayout(5, 10)); //en el juego original el patio es un 5*10 (incluyendo los cortacesped)
		//creo los 50 botones que voy a necesitar para interactuar con el patio
		ArrayList<JButton> botones = new ArrayList<JButton>();
		for(int i = 0; i<50;i++) {
			int fila = i / 10; // 10 columnas por fila
		    int columna = i % 10;
		    
			JButton espacio = new JButton(); //a cada boton le pongo un numero como nombre inicial
			espacio.putClientProperty("fila", fila);
		    espacio.putClientProperty("columna", columna);
			//espacio.setBackground(Color.GREEN); 
			//le pongo un listenner para que haga algo cada vez que lo aprieto
			espacio.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// funcion de interaccion del mouse con los botones
					System.out.println(espacio.getClientProperty("fila"));
					System.out.println(espacio.getClientProperty("columna"));
					if(!desplantando) {
						
						if(plantaSeleccionada == null) {
							//si es que no ha elegido ninguna todavia entonces sale error de no haber elegido ninguna planta
							JOptionPane.showMessageDialog(Simulacionv1.this, "No has seleccionado una planta ha colocar", "Error", JOptionPane.ERROR_MESSAGE);
						}else {
							if(espacio.getClientProperty("planta") == null) {
								
								reproducirSonido("src/sonidos/plantado.wav");
								modelo.removeElement(plantaSeleccionada);
								espacio.putClientProperty("planta", plantaSeleccionada);
								try {
									espacio.setIcon(new ImageIcon(getBuferedimagePlanta(plantaSeleccionada).getScaledInstance(espacio.getWidth(), espacio.getHeight(), Image.SCALE_SMOOTH)));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								plantaSeleccionada = null;
							}else {
								JOptionPane.showMessageDialog(Simulacionv1.this, "No puedes colocar una planta encima de otra.", "Error", JOptionPane.ERROR_MESSAGE);
							}
							
						}
					}
					else if(espacio.getIcon() != null){
						modelo.addElement((Planta) espacio.getClientProperty("planta"));
						espacio.setIcon(null);
						espacio.putClientProperty("planta", null);
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
		finalizar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i<50;i++) {
					JButton botoncito = (JButton) panelCesped.getComponent(i);
					ArrayList<Integer> coordenadas = new ArrayList<Integer>();
					coordenadas.add((Integer) botoncito.getClientProperty("fila"));
					coordenadas.add((Integer) botoncito.getClientProperty("columna"));
					mapaFinal.put(coordenadas, (Planta) botoncito.getClientProperty("planta"));
					
				}
				System.out.println(mapaFinal);
				SwingUtilities.invokeLater(() -> {
					Simulacionv2 ventana2 = new Simulacionv2(mapaFinal);
					
				});
				dispose();
			}

			
		});
		pack();
		setVisible(true);
		this.mapaFinal = mapaFinal;
	}
}
