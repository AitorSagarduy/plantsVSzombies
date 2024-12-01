package gui;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	public Ajustes(){
		setSize(320, 240);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextField KingBradley = new JTextField("alto");
		KingBradley.setEditable(false);
        KingBradley.setBounds(30, 110, 300, 60);
        
		JSlider vslider = new JSlider(0, 100, 50);
		vslider.setMajorTickSpacing(20);
	    vslider.setMinorTickSpacing(5);
	    vslider.setPaintTicks(true);
	    vslider.setPaintLabels(true);
	    vslider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				int vvalue = source.getValue();
				
			}
		});
	    JTextField didley = new JTextField("ancho");
		didley.setEditable(false);
        didley.setBounds(30, 110, 300, 60);
        
	    JSlider lslider = new JSlider(0, 100, 50);
		lslider.setMajorTickSpacing(20);
	    lslider.setMinorTickSpacing(5);
	    lslider.setPaintTicks(true);
	    lslider.setPaintLabels(true);
	    lslider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				int lvalue = source.getValue();
				
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
		JPanel panel = new JPanel();
		panel.add(vslider);
		panel.add(KingBradley);
		panel.add(lslider);
		panel.add(didley);
		panel.add(goback);
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
	public static void main(String[] args) {
		new Ajustes();
	}
}