package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Ajustes extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int lvalue = 640;
	private static int vvalue = 480;

	public Ajustes(){
		setSize(resolucionx(),resoluciony());
		//2560 x 1440 como maximos y 640x480 como minimo
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField explicacion = new JTextField("Ajusta la resolución que tendrán las ventanas");
		explicacion.setEditable(false);
		explicacion.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JTextField textoalto = new JTextField("Alto");
		textoalto.setEditable(false);
		textoalto.setBounds(30, 110, 300, 60);
        
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        System.out.println(screenWidth);
	    System.out.println(screenHeight);
        
	    JSlider vslider = new JSlider(480, screenHeight - 45, (screenHeight + 480) / 2);
	    vslider.setMajorTickSpacing((screenHeight - 45 - 480) / 2); // Espaciado entre valores
	    vslider.setMinorTickSpacing(0); // No mostrar ticks menores
	    vslider.setPaintTicks(true);
	    vslider.setPaintLabels(true);
	    vslider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				vvalue = source.getValue();
				
			}
		});
	    JTextField didley = new JTextField("Ancho");
		didley.setEditable(false);
        didley.setBounds(30, 110, 300, 60);
        
        JSlider lslider = new JSlider(640, screenWidth, (screenWidth + 640) / 2);
        lslider.setMajorTickSpacing((screenWidth - 640) / 2); // Espaciado entre valores
        lslider.setMinorTickSpacing(0); // No mostrar ticks menores
        lslider.setPaintTicks(true);
        lslider.setPaintLabels(true);
	    lslider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				lvalue = source.getValue();
				
			}
		});
	    JButton atras = new JButton("Atras");
	    atras.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new MenuInicial();
				dispose();
			}
	    	
	    	
	    });
	    
	    JButton aplicar = new JButton("Aplicar");
	    aplicar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setResolucion(lvalue, vvalue);
				setSize(lvalue, vvalue);
				setLocationRelativeTo(null);
			}
		});
	    
	    vslider.setValue(resoluciony());
	    lslider.setValue(resolucionx());
	    
		JPanel panel = new JPanel();
		JPanel medio = new JPanel();
		JPanel arriba = new JPanel();
		JPanel abajo = new JPanel();
		panel.setLayout(new BorderLayout());
		medio.setLayout(new BorderLayout());
		panel.add(explicacion, BorderLayout.NORTH);
		arriba.add(vslider);
		arriba.add(textoalto);
		arriba.add(lslider);
		arriba.add(didley);
		abajo.add(atras);
		abajo.add(aplicar);
		add(panel);
		medio.add(arriba, BorderLayout.NORTH); 
		medio.add(abajo, BorderLayout.CENTER);  
		panel.add(medio, BorderLayout.CENTER);

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
			System.out.println("Error en cargar la propiedad RESOLUCION");
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
			System.out.println("Error en cargar la propiedad RESOLUCION");
			e.printStackTrace();
			return 480;
		}
	}
	
	public static void setResolucion(int x, int y) {
		Properties conexionProperties = new Properties();
		try (FileInputStream fis = new FileInputStream("src/DatosCsv/ajustes.properties")) {
			conexionProperties.load(fis);
			conexionProperties.setProperty("RESOLUCION", x+","+y);
			try (FileOutputStream fos = new FileOutputStream("src/DatosCsv/ajustes.properties")) {
                conexionProperties.store(fos, "Actualización del valor de RESOLUCION");
                System.out.println("El valor de 'RESOLUCION' ha sido actualizado a "+x+","+y);
            }
		} catch (Exception e) {
			System.out.println("Error en la resolucion");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Ajustes ventana = new Ajustes();
		ventana.setLocationRelativeTo(null);
	}
}