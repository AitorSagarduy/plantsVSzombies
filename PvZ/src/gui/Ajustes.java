package gui;

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
		setSize(640,480);
		//2560 x 1440 como maximos y 640x480 como minimo
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextField KingBradley = new JTextField("alto");
		KingBradley.setEditable(false);
        KingBradley.setBounds(30, 110, 300, 60);
        
		JSlider vslider = new JSlider(480, 1440, (1440+480)/2);
		vslider.setMajorTickSpacing(200);
	    vslider.setMinorTickSpacing(50);
	    vslider.setPaintTicks(true);
	    vslider.setPaintLabels(true);
	    vslider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				vvalue = source.getValue();
				
			}
		});
	    JTextField didley = new JTextField("ancho");
		didley.setEditable(false);
        didley.setBounds(30, 110, 300, 60);
        
	    JSlider lslider = new JSlider(640, 2560, (2560+640)/2);
		lslider.setMajorTickSpacing(500);
	    lslider.setMinorTickSpacing(125);
	    lslider.setPaintTicks(true);
	    lslider.setPaintLabels(true);
	    lslider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				lvalue = source.getValue();
				
			}
		});
	    JButton goback = new JButton("Atras");
	    goback.addActionListener(new ActionListener(){

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
			}
		});
	    
	    
		JPanel panel = new JPanel();
		panel.add(vslider);
		panel.add(KingBradley);
		panel.add(lslider);
		panel.add(didley);
		panel.add(goback);
		panel.add(aplicar);
		add(panel);
		setVisible(true);
	
	
	
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
		new Ajustes();
	}
}