package main;
// del señor pellets
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.imageio.ImageIO;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuInicial extends JFrame {

    private static final long serialVersionUID = 1L;
    
    MusicaMenu player = new MusicaMenu();
    Thread musicThread = new Thread(player);
    
    
    public MenuInicial() {
        super("Ventana mínima");
        musicThread.start();
        // Ajustes de la ventana
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear panel 
        JPanel panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage envy = null; // la imagen
                try {
                    // Cargar imagen de fondo
                    envy = ImageIO.read(getClass().getResourceAsStream("/imagenes/surface.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Dibujar la imagen de fondo
                g.drawImage(envy, 0, 0, getWidth(), getHeight(), null);
            }
        };
        ;
        // Usar layout nulo para poder posicionar los componentes libremente
        panel.setLayout(null);
        // lo que pregunta el usurname
        
        JTextField EdwardElric = new JTextField(); // save del usuario
        EdwardElric.setBounds(30, 50, 300, 60);
        panel.add(EdwardElric);
        JButton KingBradley = new JButton("Guardar usuario"); //usuario
        KingBradley.setBounds(30, 110, 300, 60);
        panel.add(KingBradley);

        // Agregar el ActionListener al botón
        KingBradley.addActionListener(new GuardarUsuarioListener(EdwardElric));

        
        //botones

           
        //boton 1
        JButton selim = new JButton("SIMULADOR");
        selim.setBounds(350, 100, 500, 100); // Definir posición y tamaño
        selim.setBackground(java.awt.Color.GRAY); // Establecer el color de fondo en gris
        selim.setForeground(java.awt.Color.WHITE); // Establecer el color del texto en blanco
        selim.setFont(new Font("Arial Black", Font.BOLD, 24)); // Establecer la fuente del texto
        ActionListener s = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				player.stopPlaying();
				ArrayList<Planta> plantas = new ArrayList<Planta>();
				MenuPlantas.cargarPlantasCSV(plantas, "src/DatosCsv/plantas.csv");
		        SelecPlantas ventana = new SelecPlantas(plantas);
				
			}
		};
		selim.addActionListener(s);
      
				
        //boton 2
        JButton yoki = new JButton("ALMANAQUE");
        yoki.setBounds(350, 200, 500, 100); // Definir posición y tamaño
        yoki.setBackground(java.awt.Color.GRAY); // Establecer el color de fondo en gris
        yoki.setForeground(java.awt.Color.WHITE); // Establecer el color del texto en blanco
        yoki.setFont(new Font("Arial Black", Font.BOLD, 24)); // Establecer la fuente del texto
        ActionListener l = new ActionListener() {
		
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Planta> sig = new ArrayList<Planta>(); //plantas
		        MenuPlantas.cargarPlantasCSV(sig, "src/DatosCsv/plantas.csv");
		        new MenuPlantas(sig);
		        player.stopPlaying();
		        dispose();
			}
		};
		yoki.addActionListener(l);
        //boton 3
        JButton alphonse = new JButton("LOGROS");
        alphonse.setBounds(350, 300, 500, 100); // Definir posición y tamaño
        alphonse.setBackground(java.awt.Color.GRAY); // Establecer el color de fondo en gris
        alphonse.setForeground(java.awt.Color.WHITE); // Establecer el color del texto en blanco
        alphonse.setFont(new Font("Arial Black", Font.BOLD , 24)); // Establecer la fuente del texto
        alphonse.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {
                new Ajustes();
                dispose(); 
                player.stopPlaying();
            }
        });
        //boton 4
        JButton mustang = new JButton("CREDITOS");
        mustang.setBounds(350, 400, 500, 100); // Definir posición y tamaño
        mustang.setBackground(java.awt.Color.GRAY); // Establecer el color de fondo en gris
        mustang.setForeground(java.awt.Color.WHITE); // Establecer el color del texto en blanco
        mustang.setFont(new Font("Arial Black", Font.BOLD, 24)); // Establecer la fuente del texto
        mustang.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Creditos();
				dispose();
				player.stopPlaying();
				
			}
		});
        // Añadimos botones
        panel.add(selim);
        panel.add(yoki);
        panel.add(alphonse);
        panel.add(mustang);

        // Añadir el panel a la ventana
        add(panel);

        // Reproducir música de fondo
     // Reproducir música de fondo
        
   
        

        // Mostrar la ventana
        setVisible(true);
    }

    public static void main (String[] args) {
        new MenuInicial();
    }
}