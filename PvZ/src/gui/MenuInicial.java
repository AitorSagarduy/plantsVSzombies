package gui;
// del señor pellets
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import domain.Planta;

public class MenuInicial extends JFrame {

    private static final long serialVersionUID = 1L;

    MusicaMenu player = new MusicaMenu();
    Thread musicThread = new Thread(player);

    public MenuInicial() {
        super("Simulador PvZ");
        MusicaMenu.sonidoM = "/sonidos/flsh2.wav";
        musicThread.start();
        // Ajustes de la ventana
        setSize(Ajustes.resolucionx(), Ajustes.resoluciony());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el panel con fondo
        JPanel fondoPanel = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage envy = null;
                try {
                    envy = ImageIO.read(getClass().getResourceAsStream("/imagenes/bg.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(envy, 0, 0, getWidth(), getHeight(), null); // Aseguramos que el fondo se ajuste bien
            }
        };

        fondoPanel.setLayout(new GridBagLayout()); // Layout para los botones
        GridBagConstraints gbc = new GridBagConstraints();

        // Crear panel para los botones
        JPanel botonPanel = new JPanel();
        botonPanel.setLayout(new GridBagLayout());
        botonPanel.setOpaque(false); // Hacer que el fondo de los botones sea transparente

        // Crear botones
        JButton selim = createButton("SIMULADOR");
        JButton yoki = createButton("ALMANAQUE");
        JButton alphonse = createButton("AJUSTES");
        JButton mustang = createButton("CREDITOS");
        JButton tienda = createButton("TIENDA"); // vale, me puse serio
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Para que los botones ocupen todo el ancho disponible
        gbc.insets = new java.awt.Insets(10, 10, 10, 10); // Separación entre los botones

        gbc.gridx = 0;   // Columna 0 (centrado)
        gbc.gridy = 0;   // Fila 0
        gbc.gridwidth = 1; // Un solo botón por celda
        gbc.weightx = 1.0; // El botón ocupa todo el ancho de la ventana
        botonPanel.add(selim, gbc);
        
        gbc.gridy = 1;   // F1
        botonPanel.add(yoki, gbc);

        gbc.gridy = 2;   // F2
        botonPanel.add(alphonse, gbc);

        gbc.gridy = 3;   // F 3
        botonPanel.add(mustang, gbc);
        
        gbc.gridy = 4; //f4
        botonPanel.add(tienda, gbc);
        // Añadir el panel de botones al fondoPanel
        fondoPanel.add(botonPanel);

        // Establecer el fondoPanel como el fondo principal y hacerlo negro
        fondoPanel.setBackground(java.awt.Color.BLACK); // Fondo negro

        // Añadir fondoPanel al JFrame
        add(fondoPanel);

        // Hacer visible la ventana
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(java.awt.Color.BLACK);
        button.setForeground(java.awt.Color.WHITE);
        button.setFont(new Font("Arial Black", Font.BOLD, 24));

        // ActionListener para cada botón
        if (text.equals("SIMULADOR")) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    player.stopPlaying();
                    new SelecPlantas();
                }
            });
        } else if (text.equals("ALMANAQUE")) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Planta> sig = new ArrayList<Planta>(); //plantas 
                    MenuPlantas.cargarPlantasCSV(sig, "src/DatosCsv/plantas.csv");
                    new SelecAlmanaque();
                    player.stopPlaying();
                    dispose();
                }
            });
        } else if (text.equals("AJUSTES")) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Ajustes();
                    dispose();
                    player.stopPlaying();
                }
            });
        } else if (text.equals("CREDITOS")) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Creditos();
                    dispose();
                    player.stopPlaying();
                }
            });
        } else if (text.equals("TIENDA")) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Tienda();
                    dispose();
                    player.stopPlaying();
                }
            });}

        return button;
    }

    public static void main (String[] args) {
        new MenuInicial();
    }
}
