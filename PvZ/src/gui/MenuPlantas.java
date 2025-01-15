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

public class MenuPlantas extends JFrame{
	private static final long serialVersionUID = 1L;

	   
	   public static void main(String[] args) {
	        GestorBD gestorBD = new GestorBD();
	        ArrayList<domain.Planta> plantas = gestorBD.getPlantas();
	        MenuPlantas ventana = new MenuPlantas(plantas);
	        ventana.setLocationRelativeTo(null);
	   }
	   
	 //Definir todos lo parametros que se usaran
	   private JLabel soleslabel;
	   
	   String ultimaplantaseleccionada;
	   JPanel panelbotonregadera1;
	   JPanel panelbotonregadera2;
	   JPanel panelbotonregadera3;
	   JPanel panelbotonregadera4;
	   
	   int soles;
	   int cerebros;
       int contador = 0;
	   int cuantasplantas = 0;
	   int grids = 0;
	   
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
	   
	   //El metodo cargarplantas que recibe un arraylist vacio y lo llena añadiendole plantas
	   //Se usaba antes cuando aun no estaba implementada la base de datos
	   //Recibe un arraylist vacio y la direccion del csv que tiene que cargar
	public static void cargarPlantasCSV(ArrayList<domain.Planta> plantas, String rutacsv) {
		File f = new File(rutacsv);
		try (Scanner sc = new Scanner(f)) {
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(";");
				//Separar las lineas del csv y "convertirlos" en los parametros que tocan
				String tipo = campos[0];
				String nombre = campos[1];
				int vida = Integer.parseInt(campos[2]);
				int tmp_atac = Integer.parseInt(campos[3]);
				int danyo = Integer.parseInt(campos[4]);
				int rango = Integer.parseInt(campos[5]);
				int nivel = Integer.parseInt(campos[6]);

				//Crear la nueva planta
				domain.Planta nueva = new domain.Planta(tipo, nombre, vida, tmp_atac, danyo, rango, nivel);
				//Y añadirsela al arraylist
				plantas.add(nueva);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MenuPlantas(ArrayList<domain.Planta> plantas) {
		
		GestorBD gestorbd = new GestorBD();
		setTitle("Almanaque Plantas");
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());

		//Hacer que suene la musica usando el metodo de la clase MusicaMenu
	    MusicaMenu player = new MusicaMenu();
	    Thread musicThread = new Thread(player);
        MusicaMenu.sonidoM = "/sonidos/zengarden.wav";
        musicThread.start();  
        
        //Cargar los soles y cerebros del main
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
		
		//Un array con todos los tipos posibles de plantas por si por error se introduce alguna que no lo sea, poder detectarlo despues
        String[] posiblesplantas = {"Girasol", "Lanzaguisantes", "Hielaguisantes", "Apisonaflor", "Cactus", "Coltapulta", "Guisantralladora", "Humoseta", "Jalapeno", "Melonpulta","Nuez", "NuezCascaraRabias", "Patatapum", "PlantaCarronivora", "Repetidora", "SetaDesesporadora", "Trebolador", "Tripitidora", "MelonpultaCongelada" };
        
        //Mapa que sera usado mas adelante
        HashMap<String, Integer> mapaniveles = new HashMap<>();
        
        JPanel panel = new JPanel();
        panel.setBackground(colorfondo);
        
        //filas, columnas, hogap, vegap 
        grids = sacargrids();
        panel.setLayout(new GridLayout(0, sacargrids(), 20, 20)); 
        
        //Cada boton de plantas que ira dentro del gridlayout
        for (domain.Planta planta : plantas) {
        	
        	//Si el mapa ya tiene la planta actual, no hara nada
			if(mapaniveles.containsKey(planta.getNombre())) {
				
			} else {
				//Si el mapa no tiene la planta actual, se le añadira con su nivel correspondiente
				mapaniveles.put(planta.getNombre(), planta.getNivel());
			}
        	
			//Crear el boton
        	JButton boton = new JButton(planta.getNombre());
        	
        	//Ponerle un color al boton dependiendo de si no ha pasado nada, se ha regado o si esta al nivel maximo
        	if(regada == false) {
        		if(mapaniveles.get(planta.getNombre()) > 3) {
        			boton.setBackground(colornivelmax);
        		} else {
        			boton.setBackground(colorboton);
        		}
        	} else {
        		//Ponerle el color de la regada porque regada es = true
        		boton.setBackground(colorregada);
        	}
        	
        	String plantadireccionimagen = "";
        	@SuppressWarnings("unused")
        	boolean parar = false;
        	//Recorrer la lista de posibles plantas para ver si la actual esta en ella
        	for (String nombreplanta : posiblesplantas) {
        		if(planta.getTipo().contains(nombreplanta)) {
        			//En caso de ser asi se coge la direccion de la imagen y se para el bucle
        			plantadireccionimagen = "src/imagenes/" + planta.getTipo() + ".png";
        			parar = true;
        			break;
        		} else {
        			//En caso negativo, se pondra en el icono un "?"
        			plantadireccionimagen = "src/imagenes/NoIdentificada.png";
        		}
			}
        	
        	//Ponerle un color rojo si la planta no ha sido identificada
        	if(plantadireccionimagen.equals("src/imagenes/NoIdentificada.png")) {
        		boton.setForeground(Color.RED);
        	}
        	
        	//Coger la ruta de la imagen y convertirla en un icono
			ImageIcon imagenplanta = new ImageIcon(plantadireccionimagen);
			
			
			//Segun el tamaño de la pantalla se decide entre 3 posibles tamaños (mini, mid y max)
			//Ponerle un tamaño u otro a los botones dependiendo de la cantidad de grids
			if(grids == 2) {
				boton.setPreferredSize(botontamanyomini);
				imagenplanta = new ImageIcon(new ImageIcon(plantadireccionimagen).getImage().getScaledInstance(93, 93, Image.SCALE_SMOOTH));
				boton.setFont(fuentemini);	
			}else if (grids == 3) {
				boton.setPreferredSize(botontamanyomid);
				boton.setIconTextGap(13);
				imagenplanta = new ImageIcon(new ImageIcon(plantadireccionimagen).getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH));
				boton.setFont(fuentemid); 
			}else if(grids == 4){
				boton.setPreferredSize(botontamanyo);
				boton.setIconTextGap(23);
				boton.setFont(fuente);
			} 
			
			boton.setIcon(imagenplanta);
			
			//Ajustar donde va a ir el texto dentro de los botones
			boton.setVerticalTextPosition(JButton.BOTTOM); 
			boton.setHorizontalTextPosition(JButton.CENTER);
			
			//Darle click a un boton
			boton.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
				
				//Si se le da a una planta cuando se tiene la regadera seleccionada
				if(regable == true) {
				ultimaplantaseleccionada = "src/imagenes/" + planta.getTipo() + ".png";
				//Guardar cual es la imagen de la planta para usarla mas adelante
				imagenactual = new ImageIcon(ultimaplantaseleccionada).getImage().getScaledInstance(panelbotonregadera1.getHeight(), panelbotonregadera1.getHeight(), Image.SCALE_SMOOTH);
				
				//Ir verificando si los paneles de la derecha estan llenos o vacios, en caso de estar vacios, la planta ira ahi hasta que se termine de regar
				if(panellleno1 == false) {
					
					//Comenzar un hilo que muestre una barra de progreso
			        Thread t = new Thread(() -> {
			        	//Integer que ira contando si hay una planta en el panel o no
			        	cuantasplantas = cuantasplantas +1;
			        	JProgressBar barra1 = new JProgressBar();
			        	Integer aa = randomhilos();
			        	//Poner como valor maximo de la barra el numero generado aleatoriamente, para que cada planta se riegue a un ritmo diferente
			        	barra1.setMaximum(aa);
			        	panelbotonregadera1.add(barra1, BorderLayout.SOUTH);
			        	//Bucle para que la barra se vaya llenando
						for(int a = 0; a < aa; a++) {
				            if (Thread.currentThread().isInterrupted()) { // Verifica si el hilo ha sido interrumpido
				                System.out.println("Hilo detenido.");
				             //   Thread.currentThread().interrupt();
				                break;
				            }
				            barra1.setValue(a);
				            //Comprobar si el valor actual es igual al valor final (se activara cuando este lleno)
							if(a == aa-1) {
								//Vaciar el panel, vaciar el valor de la foto actual y hacer que el boton de la planta se pueda volver a pulsar
					            panellleno1 = false;
					            panelbotonregadera1.remove(barra1);
					            imagenactual =null;
					            panelbotonregadera1.repaint();
					            boton.setEnabled(true);
					            
					            //Volver a hacer la comprobacion de nivel para ponerle un color u otro al boton
				        		if(mapaniveles.get(planta.getNombre()) > 3) {
				        			boton.setBackground(colornivelmax);
				        		} else {
				        			boton.setBackground(colorboton);
				        		}
					            
					            //Restarle 1 al integer marcando que hay una planta menos siendo regada
					            cuantasplantas = cuantasplantas -1;
					            reproducirSonido("src/sonidos/regada.wav");
					        }
							//La velocidad a la que se ira llenando la barra
							try {
								Thread.sleep(180); 
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
					t.start();
					
				//El mismo proceso repetido para los 3 paneles restantes
				} else if(panellleno2 == false && panellleno1 == true) {
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
					            
				        		if(mapaniveles.get(planta.getNombre()) > 3) {
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
					
				} else if(panellleno3 == false && panellleno2 == true && panellleno1 == true) {
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
					            
				        		if(mapaniveles.get(planta.getNombre()) > 3) {
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
					
				} else if(panellleno4 == false && panellleno1 == true && panellleno2 == true && panellleno3 == true) {  
			        Thread t4 = new Thread(() -> {
			        	cuantasplantas = cuantasplantas +1;
			        	JProgressBar barra4 = new JProgressBar();
			        	Integer hh = randomhilos();
			        	barra4.setMaximum(hh);
			        	panelbotonregadera4.add(barra4, BorderLayout.SOUTH);
						for(int h = 0; h < hh; h++) {
				            if (Thread.currentThread().isInterrupted()) { // Verifica si el hilo ha sido interrumpido
				                System.out.println("Hilo detenido.");
				                break;
				            }
				            barra4.setValue(h);
							if(h == hh-1) {
					            panellleno4 = false;
					            panelbotonregadera4.remove(barra4);
					            imagenactual =null;
					            boton.setEnabled(true);
					            
				        		if(mapaniveles.get(planta.getNombre()) > 3) {
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
				}
				
				//Cuando se le da a regar ponerle el color de las regadas y hacer que no se pueda seleccionar
				boton.setBackground(colorregada);
				boton.setEnabled(false);
				regable = false;
				
				//Añadir los soles aleatoriamente
				int numrandom = (int)(Math.random() * 80 + 1);
				soles = soles + numrandom;
				
				
				reproducirSonido("src/sonidos/regar.wav");
				soleslabel.setText("Soles: " + soles);
				
				//En el caso de no haber seleccionado la regadera
				} else {
					//Comprobar si el nivel de la planta es menor que 4
					if(mapaniveles.get(planta.getNombre()) < 4) {
						//En caso de serlo mostrar un cuadro de texto preguntando si se quiere mejorar
						int respuesta = JOptionPane.showConfirmDialog(null, "¿Quieres mejorar «" +planta.getNombre() + "» al nivel " + nivelmasuno(mapaniveles.get(planta.getNombre())) + "?\nCuesta 100 soles", "Mejorar " +planta.getNombre(), JOptionPane.YES_NO_OPTION);
					if(respuesta == JOptionPane.YES_OPTION) {
						//Si se le da a que si y NO tener suficientes soles
		     			if(soles < 100) {
		     				reproducirSonido("src/sonidos/mal.wav");
		     				JOptionPane.showMessageDialog(null, "Necesitas mas soles","¡Soles insuficientes!", JOptionPane.ERROR_MESSAGE);
		     			//En caso de tener los suficientes soles
		     			} else {
		     			//	subirnivel(planta, mapaniveles.get(planta.getNombre()));
		     				
		     				////////////////////
		     				
		     				
		     				//Mirar en el arraylist de las plantas en que posicion esta la planta que se quiere mejorar
		     				int posicion = plantas.indexOf(planta);
		     				
		     				try {
		     					//Modificar la planta segun el nombre y luego con el numero de la posicion del arraylist coger sus datos de la bd para modificarlos
								gestorbd.Level_updater_P(planta.getNombre(), gestorbd.getPlantas().get(posicion).getVida()+25,gestorbd.getPlantas().get(posicion).getTmp_atac(),gestorbd.getPlantas().get(posicion).getDanyo()+50,gestorbd.getPlantas().get(posicion).getRango(),gestorbd.getPlantas().get(posicion).getNivel()+1);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

		     				//Añadirle un nivel al mapa de las plantas, si se volviera a pillar el nivel desde el arraylist no saldria con el correspondiente
		     				Integer llave = mapaniveles.get(planta.getNombre());
		     				mapaniveles.put(planta.getNombre(), llave+1); 
		     				
		     				//Restarle la cantidad de soles que ha costado 
		     				soles = soles - 100;
		     				reproducirSonido("src/sonidos/sol.wav");
		     				
		     				//Añadir a la base de datos
		     				try {
								gestorbd.updateCoins(soles, cerebros);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		     				
		     				Main.solespublic= soles;
		     				
		     				//Modificar el label de los soles con la cantidad correspondiente
		     				soleslabel.setText("Soles: " + soles);
		     				
		     				//Mirar si con el nuevo nivel obtenido ya esta al maximo nivel y en caso afirmativo, ponerle el color que le toca
		     				if(mapaniveles.get(planta.getNombre()) > 3) {
		            			boton.setBackground(colornivelmax);
		            			reproducirSonido("src/sonidos/solmas.wav");
		            		} else {
		            			boton.setBackground(colorboton);
		            		}
		     			}
		     		} else  {
		     		}
					
					//En caso de que el nivel de la planta sea el maximo
					} else {
						reproducirSonido("src/sonidos/mal.wav");
						
						//Mostrar un cartel diciendo que la planta no se puede mejorar mas junto con un iconito de un sol
						ImageIcon iconomaximo = new ImageIcon("src/imagenes/solmini.png");
						JOptionPane.showMessageDialog(null, "¡«" + planta.getNombre() + "» ya está al nivel máximo!", "Nivel máximo", JOptionPane.PLAIN_MESSAGE, iconomaximo);
					}
				}
 				}
			});
        	
			//Añadir los botones
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
        
        //Crear el boton de atras
        JPanel panelbarra = new JPanel();
        JPanel panelatras = new JPanel();
        panelatras.setLayout(new BorderLayout());
        JButton atras = new JButton("Atras");
        atras.setFont(fuentebarra);
        atras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(soles);
				
				//Actualizar la cantidad de soles
				try {
					System.out.println("llevas" + soles);
					gestorbd.updateCoins(soles, cerebros);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Main.solespublic = soles;
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
        ImageIcon regadera = new ImageIcon(new ImageIcon("src/imagenes/regadera.png").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
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
        	System.out.println("mini");
        	botonregadera.setPreferredSize(new Dimension(90, 90));
            panelbotonregadera1.setPreferredSize(new Dimension(144, 60));
            panelbotonregadera2.setPreferredSize(new Dimension(144, 60));
            panelbotonregadera3.setPreferredSize(new Dimension(144, 60));
            panelbotonregadera4.setPreferredSize(new Dimension(144, 60));
        } else if(sacartamanyo() == "mid") {
        	System.out.println("mid");
        	botonregadera.setPreferredSize(new Dimension(120, 120));
            panelbotonregadera1.setPreferredSize(new Dimension(144, 82));
            panelbotonregadera2.setPreferredSize(new Dimension(144, 82));
            panelbotonregadera3.setPreferredSize(new Dimension(144, 82));
            panelbotonregadera4.setPreferredSize(new Dimension(144, 82));
        } else if(sacartamanyo() == "max") {
        	System.out.println("max");
        	botonregadera.setPreferredSize(new Dimension(150, 150));
            panelbotonregadera1.setPreferredSize(new Dimension(144, 120));
            panelbotonregadera2.setPreferredSize(new Dimension(144, 120));
            panelbotonregadera3.setPreferredSize(new Dimension(144, 120));
            panelbotonregadera4.setPreferredSize(new Dimension(144, 120));
        }
        
        addWindowStateListener(e -> {
            if (e.getNewState() == JFrame.ICONIFIED || !isFocused()) {
                System.out.println("Adios");
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	//Segun el tamaño de la pantalla esta funcion dara una cantidad u otra (esta cantidad siendo la de botones horizontales que apareceran)...
	//...para que la cantidad de botones se ajuste dinamicante
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