package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import Main.Main;
import db.GestorBD;
import domain.Planta;
import domain.Zombie;

public class Tienda extends JFrame{
	private static final long serialVersionUID = 1L;
	
	//Defino variables que seran usadas luego
	Font fuentebarra = new Font("Arial", Font.BOLD, 20);
	JLabel soleslabel;
	int soles;
	int cerebros;

	public Tienda() {
		GestorBD gestorbd = new GestorBD();
		
		soles = Main.solespublic;
        cerebros = Main.cerebrospublic;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Tienda");
		
		//Cargo los iconos de soles y cerebros y el label donde poner cada uno
		ImageIcon solicono = new ImageIcon("src/imagenes/sol.png");
		ImageIcon cerebroicono = new ImageIcon("src/imagenes/cerebro.png");
		JLabel soleslabel = new JLabel("Soles: " + soles + "     ", solicono, JLabel.LEFT); 
		JLabel cerebroslabel = new JLabel("Cerebros: " + cerebros + "      ", cerebroicono, JLabel.LEFT);
		
		soleslabel.setFont(fuentebarra);
		cerebroslabel.setFont(fuentebarra);
		
		//Para que suene la musica
		MusicaMenu player = new MusicaMenu();
	    Thread musicThread = new Thread(player);
        MusicaMenu.sonidoM = "/sonidos/tienda.wav";
        musicThread.start();
		
		//Creo todos los paneles necesario para la estructura de la tienda
		//Uno principal (central), uno (foto) que se ponga arriba (sin contar la barra), otro abajo del de la foto y dentro del de abajo uno a la izquierda y otro a la derecha
        JPanel barra = new JPanel();
        JPanel central = new JPanel();
		JPanel medio = new JPanel();
		JPanel tableplantas = new JPanel();
		JPanel tablezombis = new JPanel();
		
		//Para que dentro del panel se muestre la foto
		JPanel foto = new JPanel() {
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        ImageIcon imagentienda = new ImageIcon("src/imagenes/dave3.png");
		        g.drawImage(imagentienda.getImage(), 0, 0, getWidth(), getHeight(), this);
		    }	
		};
		
		//Ajusto el tamaño de los panales para que se ajusten dinamicamente a la pantalla
		foto.setPreferredSize(new Dimension(0, Ajustes.resoluciony()/5));
		//Ajustes.resolucionx()/2 --> el ancho de la pantalla entre 2 para que sea mitad y mitad
		tableplantas.setPreferredSize(new Dimension(Ajustes.resolucionx()/2-6, 0));
		tablezombis.setPreferredSize(new Dimension(Ajustes.resolucionx()/2-6, 0));
		
		//Les meto un borderlayout a todos los paneles necesarios
		medio.setLayout(new BorderLayout());
		central.setLayout(new BorderLayout());
		tableplantas.setLayout(new BorderLayout());
		tablezombis.setLayout(new BorderLayout());

		//Poner el contador de soles y cerebros al panel de la barra superior
		barra.add(soleslabel);
		barra.add(cerebroslabel);
		
		//Creo un boton de atras para poder volver al menu principal
		JButton atras = new JButton("Atras");
        atras.setFont(fuentebarra);
        atras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//Antes de cerrarlo todo guardo la cantidad de soles en la bd para que no se pierdan 
					GestorBD bdgei = new GestorBD();
					bdgei.updateCoins(Main.solespublic, Main.cerebrospublic);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				//Dejar de sonar la musica, abrir una ventana del menu inicial, centra la nueva ventana y cerrar esta ventana
		        player.stopPlaying();
				MenuInicial ventana = new MenuInicial();
				ventana.setLocationRelativeTo(null);
				dispose();
			}
		});
        
        //Por ultimo añadir el boton a la barra
        barra.add(atras);
		
        //Añadir todos los panales a sus sitios correspondientes
        add(central);
        central.add(medio);
		central.add(barra, BorderLayout.NORTH);
		medio.add(foto, BorderLayout.NORTH);
		medio.add(tableplantas, BorderLayout.WEST);
		medio.add(tablezombis, BorderLayout.EAST);
		
		//Cargar de la base de datos las listas con las plantas y zombis para la tienda
        GestorBD gestorBD = new GestorBD();
        ArrayList<Planta> plantasarray = gestorBD.getPlantasTienda();
        ArrayList<Zombie> zombiesarray = gestorBD.getZombiesTienda();
        for (Zombie zombie : zombiesarray) {
			zombie.setTipo(zombie.getTipo().replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑ ]", ""));
		}
        
        
        //Tabla para las plantas ligeramente modificada de "SelecPlantas" (Eder)
        //Creado un nuevo modelo para que en este caso salga el precio de cada planta
		ModeloTablaTienda modelo = new ModeloTablaTienda(plantasarray);
		JTable tabla = new JTable(modelo);
		tabla.setRowHeight(30);
		
		//Al darle click a una fila
		tabla.getSelectionModel().addListSelectionListener(event -> {
		    // Verifica que no es un ajuste temporal para evitar eventos duplicados
		    if (!event.getValueIsAdjusting()) {
		        int filaseleccionada = tabla.getSelectedRow(); 
		        if (filaseleccionada != -1) { 
		        	
		        	//Coger algunos datos de la fila seleccionada para usarloas mas adelante, el 0 y 6 se refiere a la posicion del modelo de la que tendran que sacar el dato
		            String nombre = (String) tabla.getModel().getValueAt(tabla.convertRowIndexToModel(filaseleccionada), 0);
		            Integer precio = (Integer) tabla.getModel().getValueAt(tabla.convertRowIndexToModel(filaseleccionada), 6);
		        	int pregunta = JOptionPane.showConfirmDialog(null, "¿Quieres comprar la planta «" + nombre + "»?\nCuesta " + precio +" soles", "Comprar «"  +nombre + "»", JOptionPane.YES_NO_OPTION);
		        	if(pregunta == JOptionPane.YES_OPTION) {
		        		//Si se responde que si y NO hay suficientes soles
		        		if(soles < precio) {
		        			reproducirSonido("src/sonidos/mal.wav");
		        			//Mostrar mensaje explicando la situacion
		     				JOptionPane.showMessageDialog(null, "Necesitas mas soles","¡Soles insuficientes!", JOptionPane.ERROR_MESSAGE);
		        		} else {
		        			//En caso de tener suficiente soles...
		        			soles = soles - precio;
		        			
		    				try {
		    					gestorbd.updateCoins(soles, cerebros);
		    				} catch (SQLException e1) {
		    					// TODO Auto-generated catch block
		    					e1.printStackTrace();
		    				}
		    				
		    				Main.solespublic = soles;
		        			
		        			soleslabel.setText("Soles: " + soles);
		        			reproducirSonido("src/sonidos/solmas.wav");
		        			
		        			//Coger de la lista la planta completa de la clase Planta que se quiere añadir a la base de datos
		        			Planta plantainsertarbd = plantasarray.get(tabla.getSelectedRow());
		        			//Convertirlo en un array
		        			Planta[] plantaArray = new Planta[] { plantainsertarbd };
		        			//Y usando la funcion insertarplanta se consigue meter
		        			gestorBD.insertarPlanta(plantaArray);
		        			
		        			//Usando la funcion borrarplatatienda se borra de la base de datos de la tienda la planta que se acaba de comprar
	            			gestorBD.borrarPlantaTienda(nombre);
	            			
	            			//Sin embargo, se ha eliminado de la bd y no de la lista por lo que hay que usar la nueva funcion del modelo de la tienda para borrarloa visualmente
	            			modelo.eliminarfila(filaseleccionada); 
		        		}
		        	}
		        }
		    }
		});

		// meter la tabla en el scrollpane
		JScrollPane scrollPane = new JScrollPane(tabla);
	
		TableColumn nombreColumn = tabla.getColumnModel().getColumn(0);
		System.out.println("nombrecolum" + nombreColumn);
		nombreColumn.setCellRenderer(new RendererNombre());
		
		//Hacer que si la ventana es mas pequeña que un determinado tamaño la columna del nombre tambien se vuelva mas pequeña
		TableColumn columna = tabla.getColumnModel().getColumn(0);
		if(Ajustes.resolucionx()/2 < 500) {
			columna.setMinWidth(100);
		} else {
			columna.setMinWidth(150);
		}
		
		DefaultTableCellRenderer centralRenderer = new DefaultTableCellRenderer();
	    centralRenderer.setHorizontalAlignment(JLabel.CENTER); 
	    centralRenderer.setVerticalAlignment(JLabel.CENTER);   
	    
	    for (int i = 1; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centralRenderer);
        }

	    //Añadir el scroll a la tabla
	    tableplantas.add(scrollPane);
	    
	    //Repetir todo el proceso solo que para la tabla de los zombis (los comentarios seran iguales)
////////////////////////////////
	    ModeloTablaTiendaZ modelozombies = new ModeloTablaTiendaZ(zombiesarray);
	    JTable tablazombies = new JTable(modelozombies);
	    tablazombies.setRowHeight(30);
		
		//Al darle click a una fila
	    tablazombies.getSelectionModel().addListSelectionListener(event -> {
		    // Verifica que no es un ajuste temporal para evitar eventos duplicados
		    if (!event.getValueIsAdjusting()) {
		        int filaseleccionada = tablazombies.getSelectedRow(); 
		        if (filaseleccionada != -1) { 
		        	
		        	//Coger algunos datos de la fila seleccionada para usarloas mas adelante, el 0 y 6 se refiere a la posicion del modelo de la que tendran que sacar el dato
		            String nombre = (String) tablazombies.getModel().getValueAt(tablazombies.convertRowIndexToModel(filaseleccionada), 0);
		            Integer precio = (Integer) tablazombies.getModel().getValueAt(tablazombies.convertRowIndexToModel(filaseleccionada), 6);
		        	int pregunta = JOptionPane.showConfirmDialog(null, "¿Quieres comprar el zombi «" + nombre + "»?\nCuesta " + precio +" cerebros", "Comprar «"  +nombre + "»", JOptionPane.YES_NO_OPTION);
		        	if(pregunta == JOptionPane.YES_OPTION) {
		        		//Si se responde que si y NO hay suficientes soles
		        		//soles < precio
		        		if(cerebros < precio) {
		        			reproducirSonido("src/sonidos/mal.wav");
		        			//Mostrar mensaje explicando la situacion
		     				JOptionPane.showMessageDialog(null, "Necesitas mas cerebros","¡Cerebros insuficientes!", JOptionPane.ERROR_MESSAGE);
		        		} else {
		        			//En caso de tener suficiente soles...
		        			cerebros = cerebros - precio;
		    				try {
		    					gestorbd.updateCoins(soles, cerebros);
		    				} catch (SQLException e1) {
		    					// TODO Auto-generated catch block
		    					e1.printStackTrace();
		    				}
		    				
		    				Main.cerebrospublic = cerebros;
		        			
		        			
		        			
		        			cerebroslabel.setText("Cerebros: " + cerebros);
		        			reproducirSonido("src/sonidos/solmas.wav");
		        			
		       			//Coger de la lista la planta completa de la clase Planta que se quiere añadir a la base de datos
		        			Zombie zombieinsertarbd = zombiesarray.get(tablazombies.getSelectedRow());
		        			//Convertirlo en un array
		        			Zombie[] zombieArray = new Zombie[] { zombieinsertarbd };
		        			//Y usando la funcion insertarplanta se consigue meter
		        			gestorBD.insertarZombie(zombieArray);
		        			
		        			//Usando la funcion borrarplatatienda se borra de la base de datos de la tienda la planta que se acaba de comprar
	            			gestorBD.borrarZombieTienda(nombre);
	            			
	            			//Sin embargo, se ha eliminado de la bd y no de la lista por lo que hay que usar la nueva funcion del modelo de la tienda para borrarloa visualmente
	            			modelozombies.eliminarfila(filaseleccionada); 
		        		}
		        	}
		        }
		    }
		});

		// meter la tabla en el scrollpane
		JScrollPane scrollPanezombie = new JScrollPane(tablazombies);
	
		TableColumn nombreColumnzombie = tablazombies.getColumnModel().getColumn(0);
		nombreColumnzombie.setCellRenderer(new RendererNombre());
		
		//Hacer que si la ventana es mas pequeña que un determinado tamaño la columna del nombre tambien se vuelva mas pequeña
		TableColumn columnazombie = tablazombies.getColumnModel().getColumn(0);
		if(Ajustes.resolucionx()/2 < 500) {
			columnazombie.setMinWidth(100);
		} else {
			columnazombie.setMinWidth(150);
		}
		
		DefaultTableCellRenderer centralRendererzombie = new DefaultTableCellRenderer();
	    centralRenderer.setHorizontalAlignment(JLabel.CENTER); 
	    centralRenderer.setVerticalAlignment(JLabel.CENTER);   
	    
	    for (int i = 1; i < tablazombies.getColumnCount(); i++) {
	    	tablazombies.getColumnModel().getColumn(i).setCellRenderer(centralRendererzombie);
        }

	    //Añadir el scroll a la tabla
	    tablezombis.add(scrollPanezombie);
	    
	    
	    	    
		//Ajustar el tamaño de la ventana y su posicion
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		setLocationRelativeTo(null);
		setVisible(true);		
	}
	
	//El reproductor de efectos de sonido
	private void reproducirSonido(String rutaSonido) {
	    try {
	        // Cargar el archivo de sonido
	        File archivoSonido = new File(rutaSonido);
	        AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoSonido);

	        // Preparar y reproducir el sonido
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioStream);
	        clip.start(); // Iniciar reproducción
	    } catch (Exception e) {
	        e.printStackTrace();  // Manejar excepciones
	    }
	}
	
	public static void main(String[] args) {
		Tienda ventana = new Tienda();
		ventana.setLocationRelativeTo(null);
	}

}
