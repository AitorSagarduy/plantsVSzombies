package main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.BufferedInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.imageio.ImageIO;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuInicial extends JFrame {

    private static final long serialVersionUID = 1L;

    public MenuInicial() {
        super("Ventana mínima");

        // Ajustes de la ventana
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear panel personalizado
        JPanel panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage imagen = null;
                try {
                    // Cargar imagen de fondo
                    imagen = ImageIO.read(getClass().getResourceAsStream("/imagenes/menu.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Dibujar la imagen de fondo
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), null);
            }
        };

        // Usar layout nulo para poder posicionar los componentes libremente
        panel.setLayout(null);

        // Crear botones rectangulares
        JButton botonAventura1 = new JButton("Aventura 1");
        botonAventura1.setBounds(350, 100, 500, 100); // Definir posición y tamaño
        botonAventura1.setBackground(java.awt.Color.GRAY); // Establecer el color de fondo en gris
        botonAventura1.setForeground(java.awt.Color.WHITE); // Establecer el color del texto en blanco
        botonAventura1.setFont(new Font("Arial Black", Font.BOLD, 24)); // Establecer la fuente del texto

        JButton botonAventura2 = new JButton("Aventura 2");
        botonAventura2.setBounds(350, 200, 500, 100); // Definir posición y tamaño
        botonAventura2.setBackground(java.awt.Color.GRAY); // Establecer el color de fondo en gris
        botonAventura2.setForeground(java.awt.Color.WHITE); // Establecer el color del texto en blanco
        botonAventura2.setFont(new Font("Arial Black", Font.BOLD, 24)); // Establecer la fuente del texto

        JButton botonAventura3 = new JButton("Aventura 3");
        botonAventura3.setBounds(350, 300, 500, 100); // Definir posición y tamaño
        botonAventura3.setBackground(java.awt.Color.GRAY); // Establecer el color de fondo en gris
        botonAventura3.setForeground(java.awt.Color.WHITE); // Establecer el color del texto en blanco
        botonAventura3.setFont(new Font("Arial Black", Font.BOLD, 24)); // Establecer la fuente del texto

        JButton botonAventura4 = new JButton("Aventura 4");
        botonAventura4.setBounds(350, 400, 500, 100); // Definir posición y tamaño
        botonAventura4.setBackground(java.awt.Color.GRAY); // Establecer el color de fondo en gris
        botonAventura4.setForeground(java.awt.Color.WHITE); // Establecer el color del texto en blanco
        botonAventura4.setFont(new Font("Arial Black", Font.BOLD, 24)); // Establecer la fuente del texto

        // Añadir los botones al panel
        panel.add(botonAventura1);
        panel.add(botonAventura2);
        panel.add(botonAventura3);
        panel.add(botonAventura4);

        // Añadir el panel a la ventana
        add(panel);

        // Reproducir música de fondo
     // Reproducir música de fondo
        Thread musica = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    java.io.InputStream musicaInputStream = getClass().getResourceAsStream("/sonidos/sly.wav");
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(musicaInputStream);
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
                    javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.loop(javax.sound.sampled.Clip.LOOP_CONTINUOUSLY); // Reproduce en bucle
                } catch (Exception e) {
                    System.out.println("Error al reproducir la música de fondo");
                }
            }
        });
        musica.start();
        

        // Mostrar la ventana
        setVisible(true);
    }

    public static void main (String[] args) {
        new MenuInicial();
    }
}