package gui;
// del señor pellets
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Main.Main;
import db.GestorBD;

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
        setLocationRelativeTo(null);
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
        JButton bSimu = createButton("SIMULADOR"); // boton de simu
        JButton bJardin = createButton("JARDIN ZEN"); // boton de jardin
        JButton bAjustes = createButton("AJUSTES"); // boton de ajustes
        JButton bCreditos = createButton("CREDITOS"); // boton de creditos
        JButton bTienda = createButton("TIENDA");  // boton de tienda
        JButton bAlmanaque = createButton("ALMANAQUE"); // boton de almanaque 
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Para que los botones ocupen todo el ancho disponible
        gbc.insets = new java.awt.Insets(10, 10, 10, 10); // Separación entre los botones

        gbc.gridx = 0;   // Columna 0 (centrado)
        gbc.gridy = 0;   // Fila 0
        gbc.gridwidth = 1; // Un solo botón por celda
        gbc.weightx = 1.0; // El botón ocupa todo el ancho de la ventana
        botonPanel.add(bSimu, gbc);
        
        gbc.gridy = 1;   // F1
        botonPanel.add(bAlmanaque, gbc);

        gbc.gridy = 2;   // F2
        botonPanel.add(bTienda, gbc);

        gbc.gridy = 3;   // F 3
        botonPanel.add(bJardin, gbc);
        
        gbc.gridy = 4; //f4
        botonPanel.add(bAjustes, gbc);
        
        gbc.gridy = 5; //f4
        botonPanel.add(bCreditos, gbc);
        
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
    	GestorBD bdgei = new GestorBD();
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
                    try {
						bdgei.updateCoins(Main.solespublic, Main.cerebrospublic);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                   
                }
            });
        } else if (text.equals("JARDIN ZEN")) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	SelecAlmanaque.estado=0;
                    ArrayList<Planta> sig = new ArrayList<Planta>(); //plantas 
                    MenuPlantas.cargarPlantasCSV(sig, "src/DatosCsv/plantas.csv");
                    new SelecAlmanaque();
                    player.stopPlaying();
                    dispose();
                    try {
						bdgei.updateCoins(Main.solespublic, Main.cerebrospublic);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
                }
            });
        } else if (text.equals("AJUSTES")) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Ajustes();
                    dispose();
                    player.stopPlaying();
                    try {
						bdgei.updateCoins(Main.solespublic, Main.cerebrospublic);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            });
        } else if (text.equals("CREDITOS")) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Creditos();
                    dispose();
                    player.stopPlaying();
                    try {
						bdgei.updateCoins(Main.solespublic, Main.cerebrospublic);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            });
        } else if (text.equals("TIENDA")) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Tienda();
                    dispose();
                    player.stopPlaying();
                    try {
						bdgei.updateCoins(Main.solespublic, Main.cerebrospublic);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            });
        }else if (text.equals("ALMANAQUE")) {
        	button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	SelecAlmanaque.estado=1;
                    new SelecAlmanaque();
                    dispose();
                    player.stopPlaying();
                    try {
						bdgei.updateCoins(Main.solespublic, Main.cerebrospublic);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            });
        }
        

        return button;
    }

    public static void main (String[] args) {
        new MenuInicial();
    }
}
