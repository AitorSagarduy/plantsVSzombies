package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class SelecPlantas extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139614017884562916L;
	
	


	public static void main(String[] args) {
		ArrayList<Planta> plantas = new ArrayList<Planta>();
		MenuPlantas.cargarPlantasCSV(plantas, "src/DatosCsv/plantas.csv");
        SelecPlantas ventana = new SelecPlantas(plantas);

	}

	public SelecPlantas(ArrayList<Planta> plantas) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Selecciona Plantas");
		setSize(1280, 720);


		ModeloTabla modelo = new ModeloTabla(plantas);
		JTable tabla = new JTable(modelo);
		JScrollPane scrollPane = new JScrollPane(tabla);
		add(scrollPane, BorderLayout.CENTER);
		add(scrollPane, BorderLayout.NORTH);
		
		TableColumn nombreColumn = tabla.getColumnModel().getColumn(0);
		nombreColumn.setCellRenderer(new RendererNombre());
		
		JButton atras = new JButton("Atras");
        
        atras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MenuInicial();
				dispose();
				
			}
		});
        JButton agregar = new JButton("Añadir");
        JButton eliminar = new JButton("Eliminar");
        JButton batalla = new JButton("Batalla");
        batalla.addActionListener(e -> SwingUtilities.invokeLater(() -> new Simulacionv1()));
        

        JPanel panel = new JPanel();
        panel.add(atras);
        panel.add(agregar);
        panel.add(eliminar);
        panel.add(batalla);
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        setVisible(true);
	}
}