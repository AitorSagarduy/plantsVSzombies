package main;
// de pele
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

// Clase del botón personalizado con forma de trapecio
class CustomPolygonButton extends JButton {
    private static final long serialVersionUID = 1L;
    private Polygon polygonShape; // Definir la forma de polígono

    public CustomPolygonButton(String text) {
        super(text);
        setContentAreaFilled(false); // No rellenar el área por defecto
        setFocusPainted(false); // Sin enfoque
        setBorderPainted(false); // Sin borde

        // Definir la forma de trapecio usando coordenadas X e Y
        int[] xPoints = { 20, 180, 300, 0 }; // Coordenadas X de los vértices
        int[] yPoints = { 0, 0, 100, 100 }; // Coordenadas Y de los vértices
        polygonShape = new Polygon(xPoints, yPoints, xPoints.length);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Dibujar el polígono (trapecio)
        g2d.setColor(getModel().isArmed() ? java.awt.Color.LIGHT_GRAY : java.awt.Color.GREEN);
        g2d.fill(polygonShape);

        // Dibujar el texto centrado dentro del botón
        g2d.setColor(java.awt.Color.BLACK);
        g2d.drawString(getText(), getWidth() / 3, getHeight() / 2);
    }

    @Override
    public boolean contains(int x, int y) {
        // Verificar si el punto (x, y) está dentro de la forma del polígono
        return polygonShape.contains(x, y);
    }
}

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

        // Crear botón personalizado con forma de trapecio
        CustomPolygonButton botonAventura = new CustomPolygonButton("Aventura");
        botonAventura.setBounds(150, 300, 200, 100); // Definir posición y tamaño

        // Añadir el botón al panel
        panel.add(botonAventura);

        // Añadir el panel a la ventana
        add(panel);

        // Mostrar la ventana
        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuInicial();
    }
}

