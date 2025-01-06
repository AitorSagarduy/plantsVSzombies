package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Ajustes extends JFrame {
    private static final long serialVersionUID = 1L;
    private static int lvalue = resolucionx(); // Leer ancho desde el archivo
    private static int vvalue = resoluciony(); // Leer alto desde el archivo

    public Ajustes() {
        setSize(lvalue, vvalue); // Aplicar la resolución inicial
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel con fondo personalizado
        JPanel fondoPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage envy = null;
                try {
                    // Cambia la ruta según la ubicación real de tu imagen
                    envy = ImageIO.read(getClass().getResourceAsStream("/imagenes/girasolxd.png"));
                    if (envy != null) {
                        g.drawImage(envy, 0, 0, getWidth(), getHeight(), null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        fondoPanel.setLayout(new BorderLayout());
        setContentPane(fondoPanel); // Configurar fondoPanel como el panel principal

        // Texto explicativo
        JTextField explicacion = new JTextField("Ajusta la resolución que tendrán las ventanas");
        explicacion.setEditable(false);
        explicacion.setFont(new Font("Arial", Font.PLAIN, 20));
        explicacion.setHorizontalAlignment(JTextField.CENTER);
        explicacion.setOpaque(false);
        explicacion.setBorder(null); 
        explicacion.setForeground(Color.WHITE); 

        // Slider para la altura (alto)
        JSlider vslider = new JSlider(480, Toolkit.getDefaultToolkit().getScreenSize().height - 45, vvalue);
        vslider.setMajorTickSpacing((Toolkit.getDefaultToolkit().getScreenSize().height - 480) / 4); 
        vslider.setMinorTickSpacing(10); // Espaciado pequeño
        vslider.setPaintTicks(true);
        vslider.setPaintLabels(true);
        vslider.setOpaque(false); // Transparente
        vslider.setForeground(Color.GREEN); 
        vslider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                vvalue = ((JSlider) e.getSource()).getValue();
            }
        });

        // Slider para el ancho
        JSlider lslider = new JSlider(640, Toolkit.getDefaultToolkit().getScreenSize().width, lvalue);
        lslider.setMajorTickSpacing((Toolkit.getDefaultToolkit().getScreenSize().width - 640) / 4); 
        lslider.setMinorTickSpacing(10); 
        lslider.setPaintTicks(true);
        lslider.setPaintLabels(true);
        lslider.setOpaque(false);
        lslider.setForeground(Color.GREEN); 
        lslider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lvalue = ((JSlider) e.getSource()).getValue();
            }
        });

        // Botón para aplicar los cambios
        JButton aplicar = new JButton("Aplicar");
        aplicar.addActionListener(e -> {
            setResolucion(lvalue, vvalue);
            setSize(lvalue, vvalue);
            setLocationRelativeTo(null);
        });
        aplicar.setBackground(Color.GREEN);
        aplicar.setForeground(Color.WHITE); 
        aplicar.setFont(new Font("Arial", Font.BOLD, 16)); 

        // Botón para volver atrás
        JButton atras = new JButton("Atrás");
        atras.addActionListener(e -> {
            new MenuInicial();
            dispose();
        });
        atras.setBackground(Color.GREEN); 
        atras.setForeground(Color.WHITE); 
        atras.setFont(new Font("Arial", Font.BOLD, 16));

        // Panel para los controles
        JPanel controles = new JPanel();
        controles.setOpaque(false); 
        controles.add(lslider);
        controles.add(vslider);
        controles.add(aplicar);
        controles.add(atras);

        // Mover los controles a la parte inferior
        fondoPanel.add(explicacion, BorderLayout.CENTER);
        fondoPanel.add(controles, BorderLayout.SOUTH);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static int resolucionx() {
        return leerResolucion()[0];
    }

    public static int resoluciony() {
        return leerResolucion()[1];
    }

    private static int[] leerResolucion() {
        Properties conexionProperties = new Properties();
        try (FileReader fr = new FileReader("src/DatosCsv/ajustes.properties")) {
            conexionProperties.load(fr);
            String resolucion = conexionProperties.getProperty("RESOLUCION", "640,480");
            String[] valores = resolucion.split(",");
            return new int[] { Integer.parseInt(valores[0]), Integer.parseInt(valores[1]) };
        } catch (Exception e) {
            e.printStackTrace();
            return new int[] { 640, 480 }; 
        }
    }

    public static void setResolucion(int x, int y) {
        Properties conexionProperties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/DatosCsv/ajustes.properties")) {
            conexionProperties.load(fis);
            conexionProperties.setProperty("RESOLUCION", x + "," + y);
            try (FileOutputStream fos = new FileOutputStream("src/DatosCsv/ajustes.properties")) {
                conexionProperties.store(fos, "Actualización de la resolución");
                System.out.println("Resolución actualizada a: " + x + "x" + y);
            }
        } catch (Exception e) {
            System.out.println("Error al guardar la resolución");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Ajustes();
    }
}
