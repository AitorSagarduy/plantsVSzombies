package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;

public class MenuPlantas extends JFrame{

	   private ArrayList<Planta> plantas; 
	   
	   public static void main(String[] args) {
	        ArrayList<Planta> plantas = new ArrayList<Planta>();
	        cargarPlantasCSV(plantas, "src/DatosCsv/plantas.csv");
	        MenuPlantas ventana = new MenuPlantas(plantas);
	   }
	   
	   // Se usan con el boton de eliminar
	   boolean quieroeliminar = false;
	   boolean abrirventana = false;
	   String ultimobotonseleccionado = "";
	
	   // Recibe un arraylist vacio y la direccion del csv que tiene que cargar
	public static void cargarPlantasCSV(ArrayList<Planta> plantas, String rutacsv) {
		File f = new File(rutacsv);
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(";");
				String tipo = campos[0];
				String nombre = campos[1];
				int vida = Integer.parseInt(campos[2]);
				int tmp_atac = Integer.parseInt(campos[3]);
				int danyo = Integer.parseInt(campos[4]);
				int rango = Integer.parseInt(campos[5]);
				int nivel = Integer.parseInt(campos[6]);
				
				// Esta puta mierda no funciona pero es para poner Girasol 1, Girasol 2...
				for (Planta planta : plantas) {
					if(planta.getNombre().equals(nombre)) {
						while(planta.getNombre().equals(nombre)) {
							int contador = 0;
							contador = contador + 1;
							nombre = nombre + " " + contador;
						}		
					}
				} 
				
				Planta nueva = new Planta(tipo, nombre, vida, tmp_atac, danyo, rango, nivel);
				plantas.add(nueva);
			}	
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public MenuPlantas(ArrayList<Planta> plantas) {
		setTitle("Menú Plantas");
		setSize(1280, 720);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
        
		// Tamaños y fuentes que se van a usar luego
        Dimension botontamanyo = new Dimension(317, 380); 
        Font fuente = new Font("Arial", Font.BOLD, 35);
        Font fuentebarra = new Font("Arial", Font.BOLD, 30);
        String[] posiblesplantas = {"Girasol", "Lanzaguisantes", "Hielaguisantes", "Apisonaflor", "Cactus", "Coltapulta", "Guisantralladora", "Humoseta", "Jalapeno", "Melonpulta","Nuez", "NuezGrande", "Patatapum", "PlantaCarronivora", "Repetidora", "SetaDesesporadora", "Trebolador", "Tripitidora" };
		FileFilter filtro = new FileNameExtensionFilter("Archivos CSV", "csv");
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 4, 20, 20)); //filas, columnas, hogap, vegap 
       
        // Cada boton de plantas
        for (Planta planta : plantas) {
        	JButton boton = new JButton(planta.getNombre());        	
        	
        	String plantadireccionimagen = "";
        	boolean parar = false;
        	for (String nombreplanta : posiblesplantas) {
        		if(planta.getTipo().contains(nombreplanta)) {
        			plantadireccionimagen = "src/imagenes/" + planta.getTipo() + ".png";
        			parar = true;
        			break;
        		} else {
        			plantadireccionimagen = "src/imagenes/NoIdentificada.png";
        		}
			}
        	
        	if(plantadireccionimagen.equals("src/imagenes/NoIdentificada.png")) {
        		boton.setForeground(Color.RED);
        	}
        	
        	// Ajustar el boton (meterle fotos, el tamaño...)
			ImageIcon imagenplanta = new ImageIcon(plantadireccionimagen);
			boton.setIcon(imagenplanta);
			boton.setPreferredSize(botontamanyo);
			boton.setVerticalTextPosition(JButton.BOTTOM); 
			boton.setHorizontalTextPosition(JButton.CENTER);
			boton.setIconTextGap(33);
			boton.setFont(fuente);
			
			String guardarrutaimagen = plantadireccionimagen;
			
			boton.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {;
				
					// Si se ha pulsado antes el boton de "eliminar"
				    if(quieroeliminar == true) {
				    	ultimobotonseleccionado = planta.getNombre();
				    	
				    	// Crear un archivo csv temporal con las plantas que NO se van a borrar
				    	JFileChooser ficherosguardar = new JFileChooser();
						FileFilter filtro = new FileNameExtensionFilter(".csv", "csv");
						ficherosguardar.setFileFilter(filtro);
						
						File archivo = new File("src/DatosCsv/temporal.csv");
						try (FileWriter escritor = new FileWriter(archivo)) { 
						    for (Planta planta : plantas) {
						        String linea = String.format("%s;%s;%d;%d;%d;%d;%d\n",
						                planta.getTipo(), planta.getNombre(), planta.getVida(),
						                planta.getTmp_atac(), planta.getDanyo(), planta.getRango(),
						                planta.getNivel());
						        escritor.write(linea);  
						    }
						} catch (IOException a) {
						    a.printStackTrace();  
						}
				    	   	
						////////////////////////////////////////
				    	ArrayList<Planta> nuevasplantas = eliminarplanta(ultimobotonseleccionado);
				    	recargarVentana(nuevasplantas);
				    	quieroeliminar = false;
				    	reproducirSonido("src/sonidos/mal.wav"); 
				    }
				    
				    // Si NO se ha pulsado antes el boton "eliminar"
				    if(abrirventana == false) { 
				    	// Abrir una ventana con mas datos de la planta
						JFrame nuevaVentana = new JFrame(planta.getNombre() + " - Estadísticas");
						nuevaVentana.setSize(700, 700);
						nuevaVentana.setLocationRelativeTo(null); //null=centro
						JPanel unaplanta = new JPanel(new BorderLayout());
						ImageIcon foto = new ImageIcon(guardarrutaimagen);
						reproducirSonido("src/sonidos/sol.wav");

						nuevaVentana.add(unaplanta);
						nuevaVentana.setVisible(true);
					}
				    
				    // Poner el abrirventa en false hasta que se vuelva a pulsar el boton "eliminar"
				    abrirventana = false;
 				}
			});
			
        	panel.add(boton);
		}

        JScrollPane scroll = new JScrollPane(panel);
        add(scroll, BorderLayout.CENTER);
        
        // Crear y ajustar la barra de arriba donde van los botones de eliminar, añadir...
        JMenuBar barra = new JMenuBar(); 
        barra.setPreferredSize(new Dimension(0, 60));
        setJMenuBar(barra);
        
        JPanel panelbarra = new JPanel();
        
        // Los botones que van dentro de la barra, en este caso el de AÑADIR
        JButton cargar = new JButton("Añadir");
        cargar.setFont(fuentebarra);
        cargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser ficheros = new JFileChooser();
				ficheros.setFileFilter(filtro);

                int continuar = ficheros.showOpenDialog(null); 

                // Si continuar vale APPROVE_OPTION significa que se ha seleccionado un archivo que cargar
                if (continuar == JFileChooser.APPROVE_OPTION) {
                    File archivo = ficheros.getSelectedFile();
                    cargarPlantasCSV(plantas, archivo.getAbsolutePath());
                    reproducirSonido("src/sonidos/sol.wav"); 
                    recargarVentana(plantas);    
                } 
            }
        });
        
        // Boton de GUARDAR
        JButton guardar = new JButton("Guardar");
        guardar.setFont(fuentebarra);
        guardar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser ficherosguardar = new JFileChooser();
				ficherosguardar.setFileFilter(filtro);
				
				 int returnValue = ficherosguardar.showSaveDialog(null); 
				  if (returnValue == JFileChooser.APPROVE_OPTION) {
			            File archivo = ficherosguardar.getSelectedFile();
			            try (FileWriter writer = new FileWriter(archivo + ".csv")) {
			                for (Planta planta : plantas) {
			                    String linea = String.format("%s;%s;%d;%d;%d;%d;%d\n",
			                            planta.getTipo(), planta.getNombre(), planta.getVida(),
			                            planta.getTmp_atac(), planta.getDanyo(), planta.getRango(),
			                            planta.getNivel());
			                    writer.write(linea); 
			                }
			            } catch (IOException a) {
							// TODO Auto-generated catch block
							a.printStackTrace();
						} 
			            reproducirSonido("src/sonidos/solmas.wav"); 
			            ImageIcon tickicon = new ImageIcon("src/imagenes/tick.png");
			            JOptionPane.showMessageDialog(null, "¡Plantas guardadas!", "Exito al guardar", JOptionPane.PLAIN_MESSAGE, tickicon);
			        }
            }
        });
        
        // Boton de ELIMINAR
        JButton eliminar = new JButton("Eliminar");
        eliminar.setFont(fuentebarra);
        eliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quieroeliminar = true;
				abrirventana = true;
			}
		}); 
        JButton atras = new JButton("Atras");
        atras.setFont(fuentebarra);
        atras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MenuInicial();
				dispose();
				
			}
		});
        
        panelbarra.add(atras);
        panelbarra.add(cargar);
        panelbarra.add(guardar);
        panelbarra.add(eliminar);
        barra.add(panelbarra);
        
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setVisible(true);
	}
	
	public static ArrayList<Planta> eliminarplanta(String planta) {
		ArrayList<Planta> plantasnoborrar = new ArrayList<Planta>();
		
		File f = new File("src/DatosCsv/temporal.csv");
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(";");
				String tipo = campos[0];
				String nombre = campos[1];
				int vida = Integer.parseInt(campos[2]);
				int tmp_atac = Integer.parseInt(campos[3]);
				int danyo = Integer.parseInt(campos[4]);
				int rango = Integer.parseInt(campos[5]);
				int nivel = Integer.parseInt(campos[6]);
				
				Planta plantanadir = new Planta(tipo, nombre, vida, tmp_atac, danyo, rango, nivel);
				
				if(nombre.equals(planta)) {
				} else {
					plantasnoborrar.add(plantanadir);
				}
			}		

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return plantasnoborrar;
	}
	
	private void recargarVentana(ArrayList<Planta> plantas) {
        dispose();
        new MenuPlantas(plantas);    
    }		
	
	private void reproducirSonido(String rutaSonido) {
	    try {
	        // Cargar el archivo de sonido
	        File archivoSonido = new File(rutaSonido);
	        AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoSonido);

	        // Preparar y reproducir el sonido
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioStream);
	        clip.start();  // Iniciar reproducción
	    } catch (Exception e) {
	        e.printStackTrace();  // Manejar excepciones
	    }
	}
}