package gui;

import java.awt.*;
import java.io.*;
import java.util.Properties;
import javax.swing.*;

public class Ajustes extends JFrame {
    private static final long serialVersionUID = 1L;
    private static int lvalue = 640;
    private static int vvalue = 480;

    public Ajustes() {
        setSize(resolucionx(), resoluciony());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Texto explicativo
        JTextField explicacion = new JTextField("Ajusta la resolución que tendrán las ventanas");
        explicacion.setEditable(false);
        explicacion.setFont(new Font("Arial", Font.PLAIN, 18));

        // Sliders y etiquetas
        JLabel textoAlto = new JLabel("Alto:", JLabel.CENTER);
        JLabel textoAncho = new JLabel("Ancho:", JLabel.CENTER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        JSlider vslider = new JSlider(480, screenHeight - 45, resoluciony());
        vslider.setMajorTickSpacing((screenHeight - 45 - 480) / 2);
        vslider.setPaintTicks(true);
        vslider.setPaintLabels(true);
        vslider.addChangeListener(e -> vvalue = ((JSlider) e.getSource()).getValue());

        JSlider lslider = new JSlider(640, screenWidth, resolucionx());
        lslider.setMajorTickSpacing((screenWidth - 640) / 2);
        lslider.setPaintTicks(true);
        lslider.setPaintLabels(true);
        lslider.addChangeListener(e -> lvalue = ((JSlider) e.getSource()).getValue());

        // Botones
        JButton atras = new JButton("Atras");
        atras.addActionListener(e -> {
            MenuInicial ventana = new MenuInicial();
            ventana.setLocationRelativeTo(null);
            dispose();
        });

        JButton aplicar = new JButton("Aplicar");
        aplicar.addActionListener(e -> {
            setResolucion(lvalue, vvalue);
            setSize(lvalue, vvalue);
            setLocationRelativeTo(null);
        });

        // Imagen
        JLabel imagenLabel = new JLabel();
        ImageIcon imagen = new ImageIcon("src/imagenes/girasolxd.png"); 
        imagen.setImage(imagen.getImage().getScaledInstance(-1, 300, Image.SCALE_SMOOTH)); // Escalar la imagen
        imagenLabel.setIcon(imagen);
        imagenLabel.setHorizontalAlignment(JLabel.CENTER);

        // Panel de controles (parte superior)
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new GridLayout(3, 2, 10, 10));
        panelControles.add(textoAlto);
        panelControles.add(vslider);
        panelControles.add(textoAncho);
        panelControles.add(lslider);
        panelControles.add(atras);
        panelControles.add(aplicar);

        // Añadir los componentes al marco principal
        add(explicacion, BorderLayout.NORTH);
        add(panelControles, BorderLayout.SOUTH); // Parte superior ocupa solo el espacio necesario
        add(imagenLabel, BorderLayout.CENTER); // Imagen ocupa el espacio restante

        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static int resolucionx() {
        String resolucion;
        Properties conexionProperties = new Properties();
        try {
            conexionProperties.load(new FileReader("src/DatosCsv/ajustes.properties"));
            resolucion = conexionProperties.getProperty("RESOLUCION");
            String[] resolucionxy = resolucion.split(",");
            return Integer.parseInt(resolucionxy[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return 640;
        }
    }

    public static int resoluciony() {
        String resolucion;
        Properties conexionProperties = new Properties();
        try {
            conexionProperties.load(new FileReader("src/DatosCsv/ajustes.properties"));
            resolucion = conexionProperties.getProperty("RESOLUCION");
            String[] resolucionxy = resolucion.split(",");
            return Integer.parseInt(resolucionxy[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return 480;
        }
    }

    public static void setResolucion(int x, int y) {
        Properties conexionProperties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/DatosCsv/ajustes.properties")) {
            conexionProperties.load(fis);
            conexionProperties.setProperty("RESOLUCION", x + "," + y);
            try (FileOutputStream fos = new FileOutputStream("src/DatosCsv/ajustes.properties")) {
                conexionProperties.store(fos, "Actualización del valor de RESOLUCION");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Ajustes();
    }
}
