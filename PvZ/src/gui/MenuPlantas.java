package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;

public class MenuPlantas extends JFrame{
	
	   int soles = 0;
	   private JLabel soleslabel;
       int contador = 0;
 

	   private ArrayList<Planta> plantas; 
	   
	   public static void main(String[] args) {
	        ArrayList<Planta> plantas = new ArrayList<Planta>();
	        cargarPlantasCSV(plantas, "src/DatosCsv/TODAS.csv");
	        MenuPlantas ventana = new MenuPlantas(plantas);
	   }
	   
	   // Se usan con el boton de eliminar
	   boolean regada = false;
	   boolean quieroeliminar = false;
	   boolean abrirventana = false;
	   String ultimobotonseleccionado = "";
	   boolean regable = false;
	   boolean aparecerpanel1 = false;
	   boolean cogernombreplanta = false;
	   String ultimaplantaseleccionada;
	   Image imagenactual;
	   JPanel chiquitito1;
	   JPanel chiquitito2;
	   JPanel chiquitito3;
	   JPanel chiquitito4;
	   boolean panellleno1 = false;
	   boolean panellleno2 = false;
	   boolean panellleno3 = false;
	   boolean panellleno4 = false;

	   
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
		Color colorboton = new Color(103, 255, 102);
		Color colorfondo = new Color(38, 116, 68);
		Color colorregada = new Color(154, 231, 244);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		// Tamaños y fuentes que se van a usar luego
        Dimension botontamanyo = new Dimension(295, 330); //383
        Font fuente = new Font("Arial", Font.BOLD, 35);
        Font fuentebarra = new Font("Arial", Font.BOLD, 30);
        String[] posiblesplantas = {"Girasol", "Lanzaguisantes", "Hielaguisantes", "Apisonaflor", "Cactus", "Coltapulta", "Guisantralladora", "Humoseta", "Jalapeno", "Melonpulta","Nuez", "NuezCascaraRabias", "Patatapum", "PlantaCarronivora", "Repetidora", "SetaDesesporadora", "Trebolador", "Tripitidora", "MelonpultaCongelada" };
		FileFilter filtro = new FileNameExtensionFilter("Archivos CSV", "csv");
        
        JPanel panel = new JPanel();
        panel.setBackground(colorfondo);
        panel.setLayout(new GridLayout(0, 4, 20, 20)); //filas, columnas, hogap, vegap 
       
        // Cada boton de plantas
        for (Planta planta : plantas) {
        	JButton boton = new JButton(planta.getNombre());
        	if(regada == false) {
        	boton.setBackground(colorboton);
        	} else {
        		boton.setBackground(Color.BLUE);
        	}
        	
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
			boton.setDisabledIcon(imagenplanta);
			boton.setIcon(imagenplanta);
			boton.setPreferredSize(botontamanyo);
			boton.setVerticalTextPosition(JButton.BOTTOM); 
			boton.setHorizontalTextPosition(JButton.CENTER);
			boton.setIconTextGap(23);
			boton.setFont(fuente);
			
			String guardarrutaimagen = plantadireccionimagen;

			// Darle click a un boton
			
			boton.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {;
				
				System.out.println(ultimaplantaseleccionada);
				
				if(regable == true) {
					
				ultimaplantaseleccionada = "src/imagenes/" + planta.getTipo() + ".png";
				imagenactual = new ImageIcon(ultimaplantaseleccionada).getImage();
				
				if(panellleno1 == false) {
					chiquitito1.repaint();
			        
			        Thread t = new Thread(() -> {
			        	JProgressBar barra1 = new JProgressBar();
			        	chiquitito1.add(barra1, BorderLayout.SOUTH);
						for(int a = 0; a < 100; a++) {
					        barra1.setValue(a);
							System.out.println(a);
							if(a == 99) {
					            System.out.println("cago en dios");
					            panellleno1 = false;
					            chiquitito1.remove(barra1);
					            imagenactual =null;
					            boton.setEnabled(true);
					            boton.setBackground(colorboton);
					            chiquitito1.repaint();
					        }
							try {
								Thread.sleep(180); // poner a 210
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
						
					});
					t.start();
					
				} else if(panellleno2 == false && panellleno1 == true) {
					chiquitito2.repaint();
			        
			        Thread t2 = new Thread(() -> {
			        	JProgressBar barra2 = new JProgressBar();
			        	chiquitito2.add(barra2, BorderLayout.SOUTH);
						for(int f = 0; f < 100; f++) {
					        barra2.setValue(f);
							System.out.println(f);
							if(f == 99) {
					            System.out.println("cago en dios");
					            panellleno2 = false;
					            chiquitito2.remove(barra2);
					            imagenactual =null;
					            boton.setEnabled(true);
					            boton.setBackground(colorboton);
					            chiquitito2.repaint();
					        }
							try {
								Thread.sleep(180); // poner a 210
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
					t2.start();
				} else if(panellleno3 == false && panellleno2 == true && panellleno1 == true) {
					chiquitito3.repaint();
			        
			        Thread t3 = new Thread(() -> {
			        	JProgressBar barra3 = new JProgressBar();
			        	chiquitito3.add(barra3, BorderLayout.SOUTH);
						for(int g = 0; g < 100; g++) {
					        barra3.setValue(g);
							System.out.println(g);
							if(g == 99) {
					            System.out.println("cago en dios");
					            panellleno3 = false;
					            chiquitito3.remove(barra3);
					            imagenactual =null;
					            boton.setEnabled(true);
					            boton.setBackground(colorboton);
					            chiquitito3.repaint();
					        }
							try {
								Thread.sleep(180); // poner a 210
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
					t3.start();
					chiquitito3.repaint();
				} else if(panellleno4 == false && panellleno1 == true && panellleno2 == true && panellleno3 == true) {
					chiquitito4.repaint();    
			        Thread t4 = new Thread(() -> {
			        	JProgressBar barra4 = new JProgressBar();
			        	chiquitito4.add(barra4, BorderLayout.SOUTH);
						for(int h = 0; h < 100; h++) {
					        barra4.setValue(h);
							System.out.println(h);
							if(h == 99) {
					            System.out.println("cago en dios");
					            panellleno4 = false;
					            chiquitito4.remove(barra4);
					            imagenactual =null;
					            boton.setEnabled(true);
					            boton.setBackground(colorboton);
					            chiquitito4.repaint();
					        }
							try {
								Thread.sleep(180); // poner a 210
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
						
					});
					t4.start();
					chiquitito4.repaint();
				}
				
				boton.setBackground(colorregada);
				int numrandom = (int)(Math.random() * 50 + 1);
				reproducirSonido("src/sonidos/sol.wav");
				soles = soles + numrandom;
				System.out.println(soles);
				soleslabel.setText("Soles: " + soles);
				boton.setEnabled(false);
				regable = false;
				reproducirSonido("src/sonidos/sol.wav");
		//		chiquitito1.repaint();
				}
 				}
			});
			
        	panel.add(boton);
		}

        JScrollPane scroll = new JScrollPane(panel);
        add(scroll, BorderLayout.CENTER);
        
        // Crear y ajustar la barra de arriba donde van los botones de eliminar, añadir...
        JMenuBar barra = new JMenuBar(); 
        barra.setPreferredSize(new Dimension(0, 80));
        setJMenuBar(barra);
        ImageIcon solicono = new ImageIcon("src/imagenes/sol.png");
        soleslabel = new JLabel("Soles: " + soles, solicono, JLabel.LEFT);
        soleslabel.setFont(fuentebarra);
        
        JPanel panelbarra = new JPanel();
        
        panelbarra.add(soleslabel);
        barra.add(panelbarra);
        
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
       // add(panel, BorderLayout.WEST);
        
        ImageIcon regadera = new ImageIcon("src/imagenes/regadera.png");
        
        JButton botonregadera = new JButton("REGADERA");
        botonregadera.setIcon(regadera);
        botonregadera.setVerticalTextPosition(JButton.BOTTOM); 
		botonregadera.setHorizontalTextPosition(JButton.CENTER);
		
        botonregadera.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//ImageIcon regadera2 = new ImageIcon("src/imagenes/MelonpultaCongelada.png");
				//botonregadera.setIcon(regadera2);
				regable = true;
				aparecerpanel1 = true;
				cogernombreplanta = true;
			}
		}); 
        
        JPanel derecha = new JPanel();
        derecha.setBackground(Color.RED);
        
        derecha.setPreferredSize(new Dimension(180, 0));
      //  derecha.setLayout(new GridLayout(5, 1, 600, 20));
        
        chiquitito1 = new JPanel() {
        	
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenactual != null) {
                    // Dibuja la imagen actualizada si existe
                    g.drawImage(imagenactual, 0, 0, getWidth(), getHeight(), this);
                    panellleno1 = true;
                }
            }
        };  
        
        chiquitito2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenactual != null) {
                    // Dibuja la imagen actualizada si existe
                    g.drawImage(imagenactual, 0, 0, getWidth(), getHeight(), this);
                    panellleno2 = true;
                }
            }
        };
        
        chiquitito3 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenactual != null) {
                    // Dibuja la imagen actualizada si existe
                    g.drawImage(imagenactual, 0, 0, getWidth(), getHeight(), this);
                    panellleno3 = true;
                }
            }
        };
        
        chiquitito4 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenactual != null) {
                    // Dibuja la imagen actualizada si existe
                    g.drawImage(imagenactual, 0, 0, getWidth(), getHeight(), this);
                    panellleno4 = true;
                }
            }
        };
    ////////////////////////////////////////////////////////
        
        chiquitito1.setPreferredSize(new Dimension(144, 144));
        chiquitito2.setPreferredSize(new Dimension(144, 144));
        chiquitito3.setPreferredSize(new Dimension(144, 144));
        chiquitito4.setPreferredSize(new Dimension(144, 144));
        
        derecha.add(botonregadera, BorderLayout.NORTH);
        derecha.add(chiquitito1, BorderLayout.CENTER);
        derecha.add(chiquitito2, BorderLayout.CENTER);
        derecha.add(chiquitito3, BorderLayout.CENTER);
        derecha.add(chiquitito4, BorderLayout.CENTER);
        
        add(derecha, BorderLayout.EAST);
        
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