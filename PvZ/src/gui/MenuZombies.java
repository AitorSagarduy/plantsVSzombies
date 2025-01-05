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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import Main.Main;
import db.GestorBD;

import java.awt.Color;

public class MenuZombies extends JFrame{
	   private static final long serialVersionUID = 1L;
	   
	   public static void main(String[] args) {
	        ArrayList<domain.Zombie> zombies = new ArrayList<domain.Zombie>();
	        String ruta = "src/DatosCsv/zombies.csv";
	        CargarZombies.cargarZombiesCSV(zombies, "src/DatosCsv/zombies.csv");
	        MenuZombies ventana = new MenuZombies(zombies, ruta);
	        ventana.setLocationRelativeTo(null);
	   }
	   
	   //Establecer las variables que se van a usar luego
	   int soles = 0;
	   private JLabel soleslabel;
       int contador = 0;
	   String ultimobotonseleccionado = "";
	   int cuantasplantas = 0;
	   
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
	
	public MenuZombies(ArrayList<domain.Zombie> zombies, String rutacsv) {		
		setTitle("Almanaque Zombies");
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		CargarZombies.cargarZombiesCSV(zombies, rutacsv);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    MusicaMenu player = new MusicaMenu();
	    Thread musicThread = new Thread(player);
        MusicaMenu.sonidoM = "/sonidos/zengarden.wav";
        musicThread.start();
		        
		// Tamaños, fuentes y colores que se van a usar luego
        Dimension botontamanyo = new Dimension(295, 330); //383
        Font fuente = new Font("Arial", Font.BOLD, 35);
        Font fuentemini = new Font("Arial", Font.BOLD, 25);
        Font fuentebarra = new Font("Arial", Font.BOLD, 30);
		Color colorboton = new Color(103, 255, 102);
		Color colornivelmax = new Color(255, 216, 0);
		Color colorfondo = new Color(38, 116, 68);
		Color colorregada = new Color(154, 231, 244);
		 String[] posiblesplantas = {"ZombiAbanderado", "ZombiBuzo", "ZombiCaracono", "ZombiCaracubo", "ZombiDeportista", "ZombiLector", "ZombiPortero", "Zombistein", "ZombiBasico"};

        HashMap<String, Integer> mapaniveles = new HashMap<>();
        
        JPanel panel = new JPanel();
        panel.setBackground(colorfondo);
        panel.setLayout(new GridLayout(0, 4, 20, 20)); //filas, columnas, hogap, vegap 
       
        // Cada boton de plantas
        for (domain.Zombie zombie : zombies) {
        	
			if(mapaniveles.containsKey(zombie.getNombre())) {
			} else {
				mapaniveles.put(zombie.getNombre(), zombie.getNivel());
			}
        	
        	JButton boton = new JButton(zombie.getNombre());
        	
        	if(regada == false) {
        		if(mapaniveles.get(zombie.getNombre()) > 3) {
        			boton.setBackground(colornivelmax);
        		} else {
        			boton.setBackground(colorboton);
        		}
        	} else {
        		boton.setBackground(colorregada);
        	}
        	
        	String plantadireccionimagen = "";
        	@SuppressWarnings("unused")
        	boolean parar = false;
        	for (String nombreplanta : posiblesplantas) {
        		if(zombie.getTipo().contains(nombreplanta)) {
        			plantadireccionimagen = "src/imagenes/" + zombie.getTipo() + ".png";
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
			
			if(zombie.getNombre().length() > 15) {
				boton.setFont(fuentemini);
			} else {
				boton.setFont(fuente);
			}
			
			// Darle click a un boton
			boton.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
				
				if(regable == true) {
				ultimaplantaseleccionada = "src/imagenes/" + zombie.getTipo() + ".png";
				imagenactual = new ImageIcon(ultimaplantaseleccionada).getImage();
				
				if(panellleno1 == false) {
					panelbotonregadera1.repaint();
			        
			        Thread t = new Thread(() -> {
			        	cuantasplantas = cuantasplantas +1;
			        	JProgressBar barra1 = new JProgressBar();
			        	Integer aa = randomhilos();
			        	barra1.setMaximum(aa);
			        	panelbotonregadera1.add(barra1, BorderLayout.SOUTH);
						for(int a = 0; a < aa; a++) {
					        barra1.setValue(a);
							if(a == aa-1) {
					            panellleno1 = false;
					            panelbotonregadera1.remove(barra1);
					            imagenactual =null;
					            boton.setEnabled(true);
					            
				        		if(mapaniveles.get(zombie.getNombre()) > 3) {
				        			boton.setBackground(colornivelmax);
				        		} else {
				        			boton.setBackground(colorboton);
				        		}
					            
					            panelbotonregadera1.repaint();
					            cuantasplantas = cuantasplantas -1;
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
			        	cuantasplantas = cuantasplantas +1;
			        	JProgressBar barra2 = new JProgressBar();
			        	Integer ff = randomhilos();
			        	barra2.setMaximum(ff);
			        	panelbotonregadera2.add(barra2, BorderLayout.SOUTH);
						for(int f = 0; f < ff; f++) {
					        barra2.setValue(f);
							if(f == ff-1) {
					            panellleno2 = false;
					            panelbotonregadera2.remove(barra2);
					            imagenactual =null;
					            boton.setEnabled(true);
					            
				        		if(mapaniveles.get(zombie.getNombre()) > 3) {
				        			boton.setBackground(colornivelmax);
				        		} else {
				        			boton.setBackground(colorboton);
				        		}
					            
					            panelbotonregadera2.repaint();
					            cuantasplantas = cuantasplantas -1;
					            reproducirSonido("src/sonidos/regada.wav");
					        }
							try {
								Thread.sleep(180); 
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
			        	cuantasplantas = cuantasplantas +1;
			        	JProgressBar barra3 = new JProgressBar();
			        	Integer gg = randomhilos();
			        	barra3.setMaximum(gg);
			        	panelbotonregadera3.add(barra3, BorderLayout.SOUTH);
						for(int g = 0; g < gg; g++) {
					        barra3.setValue(g);
							if(g == gg-1) {
					            panellleno3 = false;
					            panelbotonregadera3.remove(barra3);
					            imagenactual =null;
					            boton.setEnabled(true);
					            
				        		if(mapaniveles.get(zombie.getNombre()) > 3) {
				        			boton.setBackground(colornivelmax);
				        		} else {
				        			boton.setBackground(colorboton);
				        		}
					            
					            panelbotonregadera3.repaint();
					            cuantasplantas = cuantasplantas -1;
					            reproducirSonido("src/sonidos/regada.wav");
					        }
							try {
								Thread.sleep(180); 
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
			        	cuantasplantas = cuantasplantas +1;
			        	JProgressBar barra4 = new JProgressBar();
			        	Integer hh = randomhilos();
			        	barra4.setMaximum(hh);
			        	panelbotonregadera4.add(barra4, BorderLayout.SOUTH);
						for(int h = 0; h < hh; h++) {
					        barra4.setValue(h);
							if(h == hh-1) {
					            panellleno4 = false;
					            panelbotonregadera4.remove(barra4);
					            imagenactual =null;
					            boton.setEnabled(true);
					            
				        		if(mapaniveles.get(zombie.getNombre()) > 3) {
				        			boton.setBackground(colornivelmax);
				        		} else {
				        			boton.setBackground(colorboton);
				        		}
					            
					            panelbotonregadera4.repaint();
					            cuantasplantas = cuantasplantas -1;
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
				int numrandom = (int)(Math.random() * 80 + 1);
				soles = soles + numrandom;
				reproducirSonido("src/sonidos/regar.wav");
				soleslabel.setText("Cerebros: " + soles);
				} else {
					
					if(mapaniveles.get(zombie.getNombre()) < 4) {
						int respuesta = JOptionPane.showConfirmDialog(null, "¿Quieres mejorar «" +zombie.getNombre() + "» al nivel " + nivelmasuno(mapaniveles.get(zombie.getNombre())) + "?\nCuesta 100 cerebros", "Mejorar " +zombie.getNombre(), JOptionPane.YES_NO_OPTION);
					if(respuesta == JOptionPane.YES_OPTION) {
		     			if(soles < 100) {
		     				reproducirSonido("src/sonidos/mal.wav");
		     				JOptionPane.showMessageDialog(null, "Necesitas mas cerebros","¡Soles insuficientes!", JOptionPane.ERROR_MESSAGE);
		     			} else {
		     				subirnivel(zombie, mapaniveles.get(zombie.getNombre()));
		     				mapaniveles.put(zombie.getNombre(), zombie.getNivel()); 
		     				soles = soles - 100;
		     				reproducirSonido("src/sonidos/sol.wav");
		     				soleslabel.setText("Cerebros: " + soles);
		     				if(mapaniveles.get(zombie.getNombre()) > 3) {
		            			boton.setBackground(colornivelmax);
		            			reproducirSonido("src/sonidos/solmas.wav");
		            			
		            		} else {
		            			boton.setBackground(colorboton);
		            		}
		     			}
		     		} else  {
		     		}
					} else {
						reproducirSonido("src/sonidos/mal.wav");
						ImageIcon iconomaximo = new ImageIcon("src/imagenes/solmini.png");
						JOptionPane.showMessageDialog(null, "¡«" + zombie.getNombre() + "» ya está al nivel máximo!", "Nivel máximo", JOptionPane.PLAIN_MESSAGE, iconomaximo);
					}
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
        ImageIcon solicono = new ImageIcon("src/imagenes/cerebro.png");
        soleslabel = new JLabel("Cerebros: " + soles, solicono, JLabel.LEFT);
        soleslabel.setFont(fuentebarra);
        
        GestorBD bdgei = new GestorBD();
        JPanel panelbarra = new JPanel();
        JButton atras = new JButton("Atras");
        atras.setFont(fuentebarra);
        atras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					bdgei.updateCoins(Main.solespublic, Main.cerebrospublic);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				player.stopPlaying();
				MenuInicial ventana = new MenuInicial();
				ventana.setLocationRelativeTo(null);
				dispose();
			}
		});
        
        barra.add(atras);
        panelbarra.add(soleslabel);
        barra.add(panelbarra);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        ImageIcon regadera = new ImageIcon("src/imagenes/alimentar.png");
        
        JButton botonregadera = new JButton("ALIMENTAR");
        botonregadera.setIcon(regadera);
        botonregadera.setVerticalTextPosition(JButton.BOTTOM); 
		botonregadera.setHorizontalTextPosition(JButton.CENTER);
		
        botonregadera.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cuantasplantas < 4) {
					regable = true;
					aparecerpanel1 = true;
					cogernombreplanta = true;
				}
			}
		}); 
        
        JPanel derecha = new JPanel();
        derecha.setBackground(Color.RED);
        
        derecha.setPreferredSize(new Dimension(180, 0));
      //  derecha.setLayout(new GridLayout(5, 1, 600, 20));    
        
        panelbotonregadera1 = new JPanel() {
        	private static final long serialVersionUID = 1L;
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
        	private static final long serialVersionUID = 1L;
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
        	private static final long serialVersionUID = 1L;
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
        	private static final long serialVersionUID = 1L;
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
        
        setResizable(false);
        setVisible(true);
	}
	
	private Integer randomhilos() {
		int resultado = 15 + (int)(Math.random() * (80 - 15 + 1));
		return resultado;
	}
	
	private void subirnivel(domain.Zombie planta, Integer nivel) {
		String nombreplanta = planta.getNombre();
		String ruta = "src/DatosCsv/zombies.csv";
	    
	    ArrayList<String[]> todaslasplantas = new ArrayList<>();
	    try (Scanner sc = new Scanner(new File(ruta))) {
	        while (sc.hasNextLine()) {
	            String linea = sc.nextLine();
	            String[] campos = linea.split(";");
	            String nombre = campos[1];
	            if (nombre.equals(nombreplanta)) { 
	                campos[6] = Integer.toString(nivel + 1);
	                planta.setNivel(nivel + 1);
	            }
	            todaslasplantas.add(campos);
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }

	    
	    try (PrintWriter pw = new PrintWriter(new File(ruta))) {
	        for (String[] unaplanta : todaslasplantas) {
	            pw.println(String.join(";", unaplanta)); 
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private Integer nivelmasuno(Integer numero) {
		Integer resultado = numero + 1;
		return resultado;
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