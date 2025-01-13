package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
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
import javax.swing.BorderFactory;
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

import domain.Planta;
import domain.Zombie;
import gui.Simulacionv1.BackgroundPanel;

public class Simulacionv2 extends JFrame{
	private static final long serialVersionUID = 1L;
	private static boolean desplantando = false;
	private HashMap<ArrayList<Integer>, Planta> mapaFinalPlanta;
	private HashMap<ArrayList<Integer>, Zombie> mapaFinalZombie;
	
	public HashMap<ArrayList<Integer>, Planta> getMapaFinal() {
		return mapaFinalPlanta;
	}
	
	public HashMap<ArrayList<Integer>, Zombie> getMapaFinalZombie() {
		return mapaFinalZombie;
	}
	public static void main(String[] args) {
		ArrayList<Zombie> resultadoZ = new ArrayList<Zombie>();
		CargarZombies.cargarZombiesCSV(resultadoZ, "src/DatosCsv/zombies.csv");
		ArrayList<Planta> resultadoP = new ArrayList<Planta>();
		MenuPlantas.cargarPlantasCSV(resultadoP, "src/DatosCsv/plantas.csv");
		new Simulacionv1(resultadoZ, resultadoP);
	}

	//hago mi renderizado para interactuar como quiera con la jlist
	public class Mirenderizado extends DefaultListCellRenderer {
		private static final long serialVersionUID = -384283149801911558L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			// Intento de crear una label con la que interactuar
			JLabel etiqueta = (JLabel)(super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus));
			//me aseguro que el objeto sea una planta
			if (value instanceof Zombie) {
				// lo casteo para tener sus metodos
	            Zombie planta = (Zombie) value;
	            // hago que se muestre el nombre
	            setText(""); // solo muestra el nombre
	            // pruebo a ponerle un icono con el metodo getIconoPlanta
	            etiqueta.setAlignmentX(JLabel.CENTER_ALIGNMENT);
	            try {
	            	setIcon(new ImageIcon(getBuferedimagePlanta(planta).getScaledInstance(Ajustes.resolucionx()/7, Ajustes.resolucionx()/7, Image.SCALE_SMOOTH))); //le pongo un icono 
				} catch (IOException e) {
					// si no lo encuentra esntonces saco el error por consola
					e.printStackTrace();
				} 
	        }
			return etiqueta;
		}

	}
	public class BackgroundPanel extends JPanel {
	    private static final long serialVersionUID = 1L;
	    private Image backgroundImage;

	    public BackgroundPanel(String filePath) {
	        try {
	            backgroundImage = ImageIO.read(new File(filePath));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if (backgroundImage != null) {
	            int width = getWidth();
	            int height = getHeight();
	            g.drawImage(backgroundImage, 0, 0, width, height, this);
	        }
	    }
	}

	//Metodo para pillar su imagen de la carpeta de imagenes y como voy a leer le digo que puede dar error al leer
	public static BufferedImage getBuferedimagePlanta(Zombie zombie) throws IOException {
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
	Zombie plantaSeleccionada = null; //variable para almacenar la planta que haya seleccionado el ussuario dentro de la lista
	// creo la ventana
	public Simulacionv2(HashMap<ArrayList<Integer>, Planta> mapaFinal1, ArrayList<Zombie> resultadoZ, ArrayList<Planta> plantas) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle("ventana de simulacion");
		
		this.mapaFinalPlanta = mapaFinal1;
		
		ArrayList<Zombie> zombies = new ArrayList<Zombie>(resultadoZ); // creo el arraylist de plantas en la que voy a cargar las plantas leidas que hay en csv
		HashMap<ArrayList<Integer>, Zombie> mapaFinal = new HashMap<ArrayList<Integer>, Zombie>();
		DefaultListModel<Zombie> modelo = new DefaultListModel<Zombie>(); // creo un modelo de lista predeterminado parametrizado a el objeto Planta
		// añado cada planta del arraylist al modelo de lista
		for(int i = 0; i<zombies.size();i++) {
			modelo.add(i, zombies.get(i));
		}
		BackgroundPanel panelFondo = new BackgroundPanel("src/imagenes/PvZ-CALLE.PNG");
		JList<Zombie> listaPlantas = new JList<Zombie>(modelo); //creo el jlist en base al modelo de lista
		listaPlantas.setOpaque(false);
		listaPlantas.setBackground(new Color(0,0,0,0));
		listaPlantas.setFixedCellWidth(Ajustes.resolucionx()/5); //le pongo una largura definida
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
		JButton finalizar = new JButton("Batalla");
		
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
		JButton atras = new JButton("Volver");
		atras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					new Simulacionv1(resultadoZ, plantas);
					
				});
				dispose();
				
			}
		});
		JPanel opciones = new JPanel();
		add(opciones, BorderLayout.NORTH);
		opciones.setLayout(new FlowLayout());
		opciones.add(atras);
		opciones.add(pala);
		opciones.add(finalizar);
		JScrollPane scroll = new JScrollPane(listaPlantas); //creo el scrollbar en el que voy a poner la jlist
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelFondo.add(scroll);
		add(panelFondo,BorderLayout.EAST); //lo pongo a la izquierda 
		
		BackgroundPanel panelCesped = new BackgroundPanel("src/imagenes/PvZ-CESPED.PNG"); //creo el panel que va a simular el patio
		panelCesped.setLayout(new GridLayout(5, 9)); //en el juego original el patio es un 5*10 (incluyendo los cortacesped)
		//creo los 50 botones que voy a necesitar para interactuar con el patio
		ArrayList<JButton> botones = new ArrayList<JButton>();
		for(int i = 0; i<45;i++) {
			int fila = i / 9; // 10 columnas por fila
		    int columna = i % 9;
		    
			JButton espacio = new JButton(); //a cada boton le pongo un numero como nombre inicial
			botones.add(espacio);			
			espacio.putClientProperty("fila", fila);
		    espacio.putClientProperty("columna", columna + 9);
		    espacio.setBorder(BorderFactory.createEmptyBorder());
	        espacio.setFocusPainted(false);
	        espacio.setContentAreaFilled(false);
	        espacio.setOpaque(false); 
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
							JOptionPane.showMessageDialog(Simulacionv2.this, "No has seleccionado una planta ha colocar", "Error", JOptionPane.ERROR_MESSAGE);
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
								JOptionPane.showMessageDialog(Simulacionv2.this, "No puedes colocar una planta encima de otra.", "Error", JOptionPane.ERROR_MESSAGE);
							}
							
						}
					}
					else if(espacio.getIcon() != null){
						modelo.addElement((Zombie) espacio.getClientProperty("planta"));
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
				for(int i = 0; i<45;i++) {
					JButton botoncito = (JButton) panelCesped.getComponent(i);
					ArrayList<Integer> coordenadas = new ArrayList<Integer>();
					coordenadas.add((Integer) botoncito.getClientProperty("fila"));
					coordenadas.add((Integer) botoncito.getClientProperty("columna"));
					mapaFinal.put(coordenadas, (Zombie) botoncito.getClientProperty("planta"));
					
				}
				System.out.println(mapaFinal);
				SwingUtilities.invokeLater(() -> {
					new Batalla(mapaFinalPlanta, mapaFinalZombie);
					dispose();
				});
			}
		});
		pack();
		setVisible(true);
		this.mapaFinalZombie = mapaFinal;
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		setLocationRelativeTo(null);
	}
}
