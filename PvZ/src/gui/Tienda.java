package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import Main.Main;
import db.GestorBD;
import domain.Planta;
import domain.Zombie;

public class Tienda extends JFrame{
	private static final long serialVersionUID = 1L;
	
	Font fuentebarra = new Font("Arial", Font.BOLD, 20);
	int soles = 0;
	JLabel soleslabel;

	public Tienda() {
		
		Main mainInstance = new Main();
        int solesmain = mainInstance.solespublic;
        soles = solesmain;
        
        
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Tienda");
		ImageIcon solicono = new ImageIcon("src/imagenes/sol.png");
		JLabel soleslabel = new JLabel("Soles: " + soles + "     ", solicono, JLabel.LEFT); 
		soleslabel.setFont(fuentebarra);
		
		MusicaMenu player = new MusicaMenu();
	    Thread musicThread = new Thread(player);
        MusicaMenu.sonidoM = "/sonidos/tienda.wav";
        musicThread.start();
		
		ImageIcon cerebroicono = new ImageIcon("src/imagenes/cerebro.png");
		JLabel cerebros = new JLabel("Cerebros: " + "900" + "      ", cerebroicono, JLabel.LEFT); 
		cerebros.setFont(fuentebarra);
		
		ImageIcon imagentienda = new ImageIcon("src/imagenes/dave.png");
		
		JPanel central = new JPanel();
		JPanel medio = new JPanel();
		JPanel foto = new JPanel() {
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawImage(imagentienda.getImage(), 0, 0, getWidth(), getHeight(), this);
		    }	
			
		};
		
		foto.setBackground(Color.GREEN);
		foto.setPreferredSize(new Dimension(0, Ajustes.resoluciony()/5));
		
		
		JPanel tableplantas = new JPanel();
		tableplantas.setBackground(Color.BLUE);
		tableplantas.setPreferredSize(new Dimension(Ajustes.resolucionx()/2, 0));
		JPanel tablezombis = new JPanel();
		tablezombis.setBackground(Color.PINK);
		tablezombis.setPreferredSize(new Dimension(Ajustes.resolucionx()/2, 0));
		medio.setBackground(Color.RED);
		medio.setLayout(new BorderLayout());
		
		central.setLayout(new BorderLayout());
		JPanel barra = new JPanel();

		barra.add(soleslabel);
		barra.add(cerebros);
		
		JButton atras = new JButton("Atras");
        atras.setFont(fuentebarra);
        GestorBD bdgei = new GestorBD();
        atras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					bdgei.updateCoins(Main.solespublic, Main.cerebrospublic);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(soles);
				Main.solespublic = soles;
		        player.stopPlaying();
				MenuInicial ventana = new MenuInicial();
				ventana.setLocationRelativeTo(null);
				dispose();
			}
		});
        
        barra.add(atras);
		
		central.add(barra, BorderLayout.NORTH);
		central.add(medio);
		medio.add(foto, BorderLayout.NORTH);
		medio.add(tableplantas, BorderLayout.WEST);
		medio.add(tablezombis, BorderLayout.EAST);
		
        GestorBD gestorBD = new GestorBD();
        ArrayList<Planta> plantasarray = gestorBD.getPlantasTienda();
        ArrayList<Zombie> zombiesarray = gestorBD.getZombiesTienda();
        
		ModeloTablaTienda modelo = new ModeloTablaTienda(plantasarray);
		JTable tabla = new JTable(modelo);
		tabla.setRowHeight(30);
		
		// Al darle click a una fila
		tabla.getSelectionModel().addListSelectionListener(event -> {
		    // Verifica que no es un ajuste temporal para evitar eventos duplicados
		    if (!event.getValueIsAdjusting()) {
		        int selectedRow = tabla.getSelectedRow(); // Obtén la fila seleccionada en la vista
		        if (selectedRow != -1) { // Si hay una fila seleccionada
		            String nombre = (String) tabla.getModel().getValueAt(tabla.convertRowIndexToModel(selectedRow), 0);
		            Integer precio = (Integer) tabla.getModel().getValueAt(tabla.convertRowIndexToModel(selectedRow), 6);
		            System.out.println(precio);
		        	int respuesta = JOptionPane.showConfirmDialog(null, "¿Quieres comprar la planta «" + nombre + "»?\nCuesta " + precio +" soles", "Comprar «"  +nombre + "»", JOptionPane.YES_NO_OPTION);
		        	if(respuesta == JOptionPane.YES_OPTION) {
		        		if(soles < precio) {
		        			reproducirSonido("src/sonidos/mal.wav");
		     				JOptionPane.showMessageDialog(null, "Necesitas mas soles","¡Soles insuficientes!", JOptionPane.ERROR_MESSAGE);
		        		} else {
	            			gestorBD.borrarPlantaTienda(nombre);
	            			soles = soles - precio;
	            			soleslabel.setText("Soles: " + soles);
	            			modelo.eliminarfila(selectedRow); 
		        			reproducirSonido("src/sonidos/solmas.wav");
		        		}
		        	}
		        }
		    }
		});

		
		// meter la tabla en el scrollpane
		JScrollPane scrollPane = new JScrollPane(tabla);
	
		TableColumn nombreColumn = tabla.getColumnModel().getColumn(0);
		nombreColumn.setCellRenderer(new RendererNombre());
		
		System.out.println(Ajustes.resolucionx()/2);
		
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
		
	    //tableplantas.add(tabla);
	    tableplantas.add(scrollPane);
		tableplantas.setLayout(new BorderLayout());
		tableplantas.add(scrollPane, BorderLayout.CENTER);
	    
		add(central);
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		setLocationRelativeTo(null);
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
