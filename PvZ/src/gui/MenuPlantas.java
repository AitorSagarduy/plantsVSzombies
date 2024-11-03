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
import java.awt.Color;

public class MenuPlantas extends JFrame{

	   private ArrayList<Planta> plantas; 
	   
	   public static void main(String[] args) {
	        ArrayList<Planta> plantas = new ArrayList<Planta>();
	        cargarPlantasCSV(plantas, "src/DatosCsv/TODAS.csv");
	        MenuPlantas ventana = new MenuPlantas(plantas);
	   }
	   
	   int soles = 0;
	   private JLabel soleslabel;
       int contador = 0;
	   String ultimobotonseleccionado = "";
	   
	   boolean regada = false;
	   boolean quieroeliminar = false;
	   boolean abrirventana = false;
	   boolean regable = false;
	   boolean aparecerpanel1 = false;
	   boolean cogernombreplanta = false;
	   boolean panellleno1 = false;
	   boolean panellleno2 = false;
	   boolean panellleno3 = false;
	   boolean panellleno4 = false;

	   String ultimaplantaseleccionada;
	   Image imagenactual;
	   JPanel panelbotonregadera1;
	   JPanel panelbotonregadera2;
	   JPanel panelbotonregadera3;
	   JPanel panelbotonregadera4;
	   
	   
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
				
				// No funciona pero es para poner Girasol 1, Girasol 2...
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
		setTitle("Almanaque Plantas");
		setSize(1280, 720);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        
		// Tamaños, fuentes y colores que se van a usar luego
        Dimension botontamanyo = new Dimension(295, 330); //383
        Font fuente = new Font("Arial", Font.BOLD, 35);
        Font fuentebarra = new Font("Arial", Font.BOLD, 30);
		Color colorboton = new Color(103, 255, 102);
		Color colorfondo = new Color(38, 116, 68);
		Color colorregada = new Color(154, 231, 244);
        String[] posiblesplantas = {"Girasol", "Lanzaguisantes", "Hielaguisantes", "Apisonaflor", "Cactus", "Coltapulta", "Guisantralladora", "Humoseta", "Jalapeno", "Melonpulta","Nuez", "NuezCascaraRabias", "Patatapum", "PlantaCarronivora", "Repetidora", "SetaDesesporadora", "Trebolador", "Tripitidora", "MelonpultaCongelada" };
        
        JPanel panel = new JPanel();
        panel.setBackground(colorfondo);
        panel.setLayout(new GridLayout(0, 4, 20, 20)); //filas, columnas, hogap, vegap 
       
        // Cada boton de plantas
        for (Planta planta : plantas) {
        	JButton boton = new JButton(planta.getNombre());
        	
        	if(regada == false) {
        		boton.setBackground(colorboton);
        	} else {
        		boton.setBackground(colorregada);
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
			boton.setIcon(imagenplanta);
			boton.setPreferredSize(botontamanyo);
			boton.setVerticalTextPosition(JButton.BOTTOM); 
			boton.setHorizontalTextPosition(JButton.CENTER);
			boton.setIconTextGap(23);
			boton.setFont(fuente);
			
			// Darle click a un boton
			boton.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
				
				if(regable == true) {
				ultimaplantaseleccionada = "src/imagenes/" + planta.getTipo() + ".png";
				imagenactual = new ImageIcon(ultimaplantaseleccionada).getImage();
				
				if(panellleno1 == false) {
					panelbotonregadera1.repaint();
			        
			        Thread t = new Thread(() -> {
			        	JProgressBar barra1 = new JProgressBar();
			        	panelbotonregadera1.add(barra1, BorderLayout.SOUTH);
						for(int a = 0; a < 100; a++) {
					        barra1.setValue(a);
							System.out.println(a);
							if(a == 99) {
					            panellleno1 = false;
					            panelbotonregadera1.remove(barra1);
					            imagenactual =null;
					            boton.setEnabled(true);
					            boton.setBackground(colorboton);
					            panelbotonregadera1.repaint();
					            reproducirSonido("src/sonidos/regada.wav");
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
					panelbotonregadera2.repaint();
			        
			        Thread t2 = new Thread(() -> {
			        	JProgressBar barra2 = new JProgressBar();
			        	panelbotonregadera2.add(barra2, BorderLayout.SOUTH);
						for(int f = 0; f < 100; f++) {
					        barra2.setValue(f);
							System.out.println(f);
							if(f == 99) {
					            panellleno2 = false;
					            panelbotonregadera2.remove(barra2);
					            imagenactual =null;
					            boton.setEnabled(true);
					            boton.setBackground(colorboton);
					            panelbotonregadera2.repaint();
					            reproducirSonido("src/sonidos/regada.wav");
					        }
							try {
								Thread.sleep(180); // poner a 210
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
					t2.start();
					panelbotonregadera2.repaint();
					
				} else if(panellleno3 == false && panellleno2 == true && panellleno1 == true) {
					panelbotonregadera3.repaint();
			        
			        Thread t3 = new Thread(() -> {
			        	JProgressBar barra3 = new JProgressBar();
			        	panelbotonregadera3.add(barra3, BorderLayout.SOUTH);
						for(int g = 0; g < 100; g++) {
					        barra3.setValue(g);
							System.out.println(g);
							if(g == 99) {
					            panellleno3 = false;
					            panelbotonregadera3.remove(barra3);
					            imagenactual =null;
					            boton.setEnabled(true);
					            boton.setBackground(colorboton);
					            panelbotonregadera3.repaint();
					            reproducirSonido("src/sonidos/regada.wav");
					        }
							try {
								Thread.sleep(180); // poner a 210
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
					t3.start();
					panelbotonregadera3.repaint();
					
				} else if(panellleno4 == false && panellleno1 == true && panellleno2 == true && panellleno3 == true) {
					panelbotonregadera4.repaint();    
			        Thread t4 = new Thread(() -> {
			        	JProgressBar barra4 = new JProgressBar();
			        	panelbotonregadera4.add(barra4, BorderLayout.SOUTH);
						for(int h = 0; h < 100; h++) {
					        barra4.setValue(h);
							System.out.println(h);
							if(h == 99) {
					            panellleno4 = false;
					            panelbotonregadera4.remove(barra4);
					            imagenactual =null;
					            boton.setEnabled(true);
					            boton.setBackground(colorboton);
					            panelbotonregadera4.repaint();
					            reproducirSonido("src/sonidos/regada.wav");
					        }
							try {
								Thread.sleep(180); // poner a 210
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
					t4.start();
					panelbotonregadera4.repaint();
				}
				
				boton.setBackground(colorregada);
				boton.setEnabled(false);
				regable = false;
				
				// Añadir los soles aleatoriamente
				int numrandom = (int)(Math.random() * 50 + 1);
				soles = soles + numrandom;
				reproducirSonido("src/sonidos/sol.wav");
				soleslabel.setText("Soles: " + soles);
				}
 				}
			});
			
        	panel.add(boton);
		}

        JScrollPane scroll = new JScrollPane(panel);
        add(scroll, BorderLayout.CENTER);
        
        // Crear y ajustar la barra de arriba donde van los soles y el boton de atras
        JMenuBar barra = new JMenuBar(); 
        barra.setPreferredSize(new Dimension(0, 80));
        setJMenuBar(barra);
        ImageIcon solicono = new ImageIcon("src/imagenes/sol.png");
        soleslabel = new JLabel("Soles: " + soles, solicono, JLabel.LEFT);
        soleslabel.setFont(fuentebarra);
        
        JPanel panelbarra = new JPanel();
        JButton atras = new JButton("Atras");
        atras.setFont(fuentebarra);
        atras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MenuInicial();
				dispose();
				
			}
		});
        
        barra.add(atras);
       
        
        panelbarra.add(soleslabel);
        barra.add(panelbarra);
        
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        ImageIcon regadera = new ImageIcon("src/imagenes/regadera.png");
        
        JButton botonregadera = new JButton("REGADERA");
        botonregadera.setIcon(regadera);
        botonregadera.setVerticalTextPosition(JButton.BOTTOM); 
		botonregadera.setHorizontalTextPosition(JButton.CENTER);
		
        botonregadera.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				regable = true;
				aparecerpanel1 = true;
				cogernombreplanta = true;
			}
		}); 
        
        JPanel derecha = new JPanel();
        derecha.setBackground(Color.RED);
        
        derecha.setPreferredSize(new Dimension(180, 0));
      //  derecha.setLayout(new GridLayout(5, 1, 600, 20));
        
        panelbotonregadera1 = new JPanel() {
        	
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
        
        panelbotonregadera2 = new JPanel() {
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
        
        panelbotonregadera3 = new JPanel() {
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
        
        panelbotonregadera4 = new JPanel() {
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
        
        panelbotonregadera1.setPreferredSize(new Dimension(144, 144));
        panelbotonregadera2.setPreferredSize(new Dimension(144, 144));
        panelbotonregadera3.setPreferredSize(new Dimension(144, 144));
        panelbotonregadera4.setPreferredSize(new Dimension(144, 144));
        
        derecha.add(botonregadera, BorderLayout.NORTH);
        derecha.add(panelbotonregadera1, BorderLayout.CENTER);
        derecha.add(panelbotonregadera2, BorderLayout.CENTER);
        derecha.add(panelbotonregadera3, BorderLayout.CENTER);
        derecha.add(panelbotonregadera4, BorderLayout.CENTER);
        
        add(derecha, BorderLayout.EAST);
        
        setVisible(true);
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