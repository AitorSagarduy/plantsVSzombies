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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import domain.Zombie;

import java.awt.Color;

public class MenuZombies extends JFrame{
	
	private static final long serialVersionUID = 1L;

	//   private ArrayList<Planta> plantas; 
	   
	   public static void main(String[] args) {
	      //  ArrayList<domain.Planta> plantas = new ArrayList<domain.Planta>();
	      //  cargarPlantasCSV(plantas, "src/DatosCsv/TODAS.csv");
		   
	        GestorBD gestorBD = new GestorBD();
	        ArrayList<Zombie> zombies = gestorBD.getZombies();

	        MenuZombies ventana = new MenuZombies(zombies);
	        ventana.setLocationRelativeTo(null);
	   }
	   
	 //public static Integer solespublic = 0;
	   int soles = 0;
	   int cerebros = 0;
	   
       int contador = 0;
       int cuantasplantas = 0;
	   int grids = 0;
	   
	   String ultimozombiseleccionado;
	   String ultimobotonseleccionado = "";
	   
	   boolean apagado = false;
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
	   
	   Image imagenactual;
	   JPanel panelbotonregadera1;
	   JPanel panelbotonregadera2;
	   JPanel panelbotonregadera3;
	   JPanel panelbotonregadera4;
	   private JLabel soleslabel;
	
	public MenuZombies(ArrayList<domain.Zombie> zombies) {
		GestorBD gestorbd = new GestorBD();
		setTitle("Almanaque Zombis");
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    MusicaMenu player = new MusicaMenu();
	    Thread musicThread = new Thread(player);
        MusicaMenu.sonidoM = "/sonidos/zengarden.wav";
        musicThread.start();
        
        soles = Main.solespublic;
        cerebros = Main.cerebrospublic;
        
		// Tamaños, fuentes y colores que se van a usar luego
        Dimension botontamanyo = new Dimension(290, 330); //383
        Dimension botontamanyomini = new Dimension(184, 190);
        Dimension botontamanyomid = new Dimension(184, 280);
        Font fuente = new Font("Arial", Font.BOLD, 26);
        Font fuentemini = new Font("Arial", Font.BOLD, 13);
        Font fuentemid = new Font("Arial", Font.BOLD, 20);
        Font fuentebarra = new Font("Arial", Font.BOLD, 30);
		Color colorboton = new Color(103, 255, 102);
		Color colornivelmax = new Color(255, 216, 0);
		Color colorfondo = new Color(38, 116, 68);
		Color colorregada = new Color(154, 231, 244);
		Color colortierra = new Color(177, 129, 86);
        String[] posibleszombies = {"ZombiAbanderado","ZombiBasico", "ZombiBuzo", "ZombiCaracono", "ZombiCaracubo", "ZombiDeportista", "ZombiLector", "ZombiPortero", "Zombistein", "ZombiBasico"};
        
        HashMap<String, Integer> mapaniveles = new HashMap<>();
        
        JPanel panel = new JPanel();
        grids = sacargrids();
        panel.setBackground(colorfondo);
        panel.setLayout(new GridLayout(0, sacargrids(), 20, 20)); //filas, columnas, hogap, vegap 
       
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
        	for (String nombreplanta : posibleszombies) {
        		if(zombie.getTipo().contains(nombreplanta)) {
        			plantadireccionimagen = "src/imagenes/" + zombie.getTipo().replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑ ]", "") + ".png";
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

			//(getBuferedimagePlanta(planta).getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
			if(grids == 2) {
				boton.setPreferredSize(botontamanyomini);
				imagenplanta = new ImageIcon(new ImageIcon(plantadireccionimagen).getImage().getScaledInstance(93, 93, Image.SCALE_SMOOTH));
				boton.setFont(fuentemini);
			} else if(grids == 4){
				boton.setPreferredSize(botontamanyo);
				boton.setIconTextGap(23);
				boton.setFont(fuente);
			} else if (grids == 3) {
				boton.setPreferredSize(botontamanyomid);
				boton.setIconTextGap(13);
				imagenplanta = new ImageIcon(new ImageIcon(plantadireccionimagen).getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH));
				boton.setFont(fuentemid);
			}
			
			boton.setIcon(imagenplanta);
			
			boton.setVerticalTextPosition(JButton.BOTTOM); 
			boton.setHorizontalTextPosition(JButton.CENTER);
			
			// Darle click a un boton
			boton.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
				
				if(regable == true) {
				ultimozombiseleccionado = "src/imagenes/" + zombie.getTipo() + ".png";
				imagenactual = new ImageIcon(ultimozombiseleccionado).getImage().getScaledInstance(panelbotonregadera1.getHeight(), panelbotonregadera1.getHeight(), Image.SCALE_SMOOTH);
				
				if(panellleno1 == false) {
					panelbotonregadera1.repaint();
			        
			        Thread t = new Thread(() -> {
			        	cuantasplantas = cuantasplantas +1;
			        	JProgressBar barra1 = new JProgressBar();
			        	Integer aa = randomhilos();
			        	barra1.setMaximum(aa);
			        	panelbotonregadera1.add(barra1, BorderLayout.SOUTH);
						for(int a = 0; a < aa; a++) {
				            if (Thread.currentThread().isInterrupted()) { // Verifica si el hilo ha sido interrumpido
				                break;
				            }
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
					            reproducirSonido("src/sonidos/alimentar.wav");
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
							  if (Thread.currentThread().isInterrupted()) { // Verifica si el hilo ha sido interrumpido
					                System.out.println("Hilo detenido.");
					             //   Thread.currentThread().interrupt();
					                break;
					            }
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
					            reproducirSonido("src/sonidos/alimentar.wav");
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
							  if (Thread.currentThread().isInterrupted()) { // Verifica si el hilo ha sido interrumpido
					                System.out.println("Hilo detenido.");
					             //   Thread.currentThread().interrupt();
					                break;
					            }
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
					            reproducirSonido("src/sonidos/alimentar.wav");
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
				            if (Thread.currentThread().isInterrupted()) { // Verifica si el hilo ha sido interrumpido
				                break;
				            }
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
					            reproducirSonido("src/sonidos/alimentar.wav");
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
				cerebros = cerebros + numrandom;
				reproducirSonido("src/sonidos/regar.wav");
				soleslabel.setText("Cerebros: " + cerebros);
				} else {
					
					if(mapaniveles.get(zombie.getNombre()) < 4) {
						int respuesta = JOptionPane.showConfirmDialog(null, "¿Quieres mejorar «" +zombie.getNombre() + "» al nivel " + nivelmasuno(mapaniveles.get(zombie.getNombre())) + "?\nCuesta 100 cerebros", "Mejorar " +zombie.getNombre(), JOptionPane.YES_NO_OPTION);
					if(respuesta == JOptionPane.YES_OPTION) {
		     			if(cerebros < 100) {
		     				reproducirSonido("src/sonidos/mal.wav");
		     				JOptionPane.showMessageDialog(null, "Necesitas mas cerebros","¡Cerebros insuficientes!", JOptionPane.ERROR_MESSAGE);
		     			} else {
		     				
		     				GestorBD gestorbd = new GestorBD();
		     				int posicion = zombies.indexOf(zombie);
		     				
		     				try {
		     					//Modificar la planta segun el nombre y luego con el numero de la posicion del arraylist coger sus datos de la bd para modificarlos
		     					gestorbd.Level_updater_Z(zombie.getNombre(), gestorbd.getZombies().get(posicion).getVida()+25,gestorbd.getZombies().get(posicion).getTmp_atac(),gestorbd.getZombies().get(posicion).getDanyo()+50,gestorbd.getZombies().get(posicion).getVelocidad(),gestorbd.getZombies().get(posicion).getNivel()+1);
		     					
		     					
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		     				
		     				
		     				Integer llave = mapaniveles.get(zombie.getNombre());
		     				mapaniveles.put(zombie.getNombre(), llave+1); 
		     				cerebros = cerebros - 100;
		     				
		     				try {
									gestorbd.updateCoins(soles, cerebros);
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
			     				
			     				Main.cerebrospublic=cerebros;
		     				
		     				reproducirSonido("src/sonidos/sol.wav");
		     				soleslabel.setText("Cerebros: " + cerebros);
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
						ImageIcon iconomaximo = new ImageIcon("src/imagenes/cerebromini.png");
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
        soleslabel = new JLabel("Cerebros: " + cerebros, solicono, JLabel.LEFT);
        soleslabel.setFont(fuentebarra);
        
        JPanel panelbarra = new JPanel();
        JPanel panelatras = new JPanel();
        panelatras.setLayout(new BorderLayout());
        JButton atras = new JButton("Atras");
        atras.setFont(fuentebarra);
        atras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					gestorbd.updateCoins(soles, cerebros);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Main.cerebrospublic=cerebros;
				
				player.stopPlaying();
				MenuInicial ventana = new MenuInicial();
				ventana.setLocationRelativeTo(null);
				dispose();
				apagado = true;
			}
		});
        
        panelatras.add(atras, BorderLayout.WEST);
        panelbarra.add(panelatras);
        panelbarra.add(soleslabel);
        barra.add(panelbarra);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton botonregadera = new JButton();
        ImageIcon regadera = new ImageIcon(new ImageIcon("src/imagenes/alimentar.png").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
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
        derecha.setBackground(colortierra);
        
        derecha.setPreferredSize(new Dimension(180, 0));
      //  derecha.setLayout(new GridLayout(5, 1, 600, 20));  
        
        panelbotonregadera1 = new JPanel() {
        	private static final long serialVersionUID = 1L;
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenactual != null) {
                	int x = (getWidth() - imagenactual.getWidth(this)) / 2; // Centra horizontalmente
                    int y = (getHeight() - imagenactual.getHeight(this)) / 2; // Centra verticalmente
                    g.drawImage(imagenactual, x, y, this);
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
                	int x = (getWidth() - imagenactual.getWidth(this)) / 2; // Centra horizontalmente
                    int y = (getHeight() - imagenactual.getHeight(this)) / 2; // Centra verticalmente
                	g.drawImage(imagenactual, x, y, this);
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
                	int x = (getWidth() - imagenactual.getWidth(this)) / 2; // Centra horizontalmente
                    int y = (getHeight() - imagenactual.getHeight(this)) / 2; // Centra verticalmente
                	g.drawImage(imagenactual, x, y, this);
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
                	int x = (getWidth() - imagenactual.getWidth(this)) / 2; // Centra horizontalmente
                    int y = (getHeight() - imagenactual.getHeight(this)) / 2; // Centra verticalmente
                	g.drawImage(imagenactual, x, y, this);
                    panellleno4 = true;
                }
            }
        };
        
        //Ajustar las dimensiones
        if(sacartamanyo() == "mini") {
        	botonregadera.setPreferredSize(new Dimension(90, 90));
            panelbotonregadera1.setPreferredSize(new Dimension(144, 60));
            panelbotonregadera2.setPreferredSize(new Dimension(144, 60));
            panelbotonregadera3.setPreferredSize(new Dimension(144, 60));
            panelbotonregadera4.setPreferredSize(new Dimension(144, 60));
        } else if(sacartamanyo() == "mid") {
        	botonregadera.setPreferredSize(new Dimension(120, 120));
            panelbotonregadera1.setPreferredSize(new Dimension(144, 82));
            panelbotonregadera2.setPreferredSize(new Dimension(144, 82));
            panelbotonregadera3.setPreferredSize(new Dimension(144, 82));
            panelbotonregadera4.setPreferredSize(new Dimension(144, 82));
        } else if(sacartamanyo() == "max") {
        	botonregadera.setPreferredSize(new Dimension(150, 150));
            panelbotonregadera1.setPreferredSize(new Dimension(144, 120));
            panelbotonregadera2.setPreferredSize(new Dimension(144, 120));
            panelbotonregadera3.setPreferredSize(new Dimension(144, 120));
            panelbotonregadera4.setPreferredSize(new Dimension(144, 120));
        }
        
        addWindowStateListener(e -> {
            if (e.getNewState() == JFrame.ICONIFIED || !isFocused()) {
                player.stopPlaying();
                dispose();
                MenuInicial ventana = new MenuInicial();
                ventana.setLocationRelativeTo(null);
                setState(JFrame.ICONIFIED);
                ventana.setState(JFrame.ICONIFIED);
            }
        });

        derecha.add(botonregadera, BorderLayout.NORTH);
        derecha.add(panelbotonregadera1, BorderLayout.CENTER);
        derecha.add(panelbotonregadera2, BorderLayout.CENTER);
        derecha.add(panelbotonregadera3, BorderLayout.CENTER);
        derecha.add(panelbotonregadera4, BorderLayout.CENTER);
        add(derecha, BorderLayout.EAST);
        setResizable(false);
        setVisible(true);
	}
	
	//Da un numero aleatorio para la barra de progreso de los hilos
	private Integer randomhilos() {
		int resultado = 15 + (int)(Math.random() * (80 - 15 + 1));
		return resultado;
	}
	
	private String sacartamanyo() {
		String tamanyo = "";
		if(Ajustes.resoluciony() > 780) {
			tamanyo = "max";
		} else if(Ajustes.resoluciony() > 590) {
			tamanyo = "mid";
		} else {
			tamanyo = "mini";
		}
		return tamanyo;
	}
	
	
	private Integer sacargrids() {
		int resultado = 0;
		if(Ajustes.resolucionx() < 1000) {
			resultado = 2;
		} else if(Ajustes.resolucionx() < 1400){
			resultado = 3;
		} else {
			resultado = 4;
		}
		return resultado;
		
	}
	
	//Guarda en el csv el nuevo nivel
	
	//Le suma 1 al nivel
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
	        if(apagado == true) {
	        	
	        } else {
	        clip.start(); // Iniciar reproducción
	        }
	    } catch (Exception e) {
	        e.printStackTrace();  // Manejar excepciones
	    }
	}
	
	
}