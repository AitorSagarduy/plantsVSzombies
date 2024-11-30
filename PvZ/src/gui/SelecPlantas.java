package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

public class SelecPlantas extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139614017884562916L;
	
	


	public static void main(String[] args) {
		ArrayList<Planta> plantas = new ArrayList<Planta>();
		MenuPlantas.cargarPlantasCSV(plantas, "src/DatosCsv/plantas.csv");
        @SuppressWarnings("unused")
		SelecPlantas ventana = new SelecPlantas(plantas);

	}

	public SelecPlantas(ArrayList<Planta> plantas) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Selecciona Plantas");
		setSize(1280, 720);


		ModeloTabla modelo = new ModeloTabla(plantas);
		
		JTable tabla = new JTable(modelo);
		JScrollPane scrollPane = new JScrollPane(tabla);
		
		
		
		
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
        ArrayList<Planta> plantasJ = new ArrayList<Planta>();
		ModeloSeleccionadas modeloSelec = new ModeloSeleccionadas(plantasJ);
		JTable tablaSelec = new JTable(modeloSelec);
		JScrollPane scrollSelec = new JScrollPane(tablaSelec);
		add(scrollSelec, BorderLayout.SOUTH);
		scrollSelec.setVisible(false);
	
		
		
        JButton agregar = new JButton("Añadir");
        agregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
				int filaSeleccionada = tabla.getSelectedRow();
				TableColumn nombreColumn = tablaSelec.getColumnModel().getColumn(0);
				nombreColumn.setCellRenderer(new RendererNombre());
				
				
				if(filaSeleccionada  == -1) {
					JOptionPane.showMessageDialog(SelecPlantas.this, "Selecciona una planta", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int numeroFilas = tablaSelec.getRowCount();
				boolean existe = false;
				
				String nombrePlanta = (String) tabla.getValueAt(filaSeleccionada, 0);
				
				for (int i = 0; i < numeroFilas; i++) {
					if(modeloSelec.getValueAt(i, 0).equals(nombrePlanta)) {
						modeloSelec.sumarCantidad(i);
						existe = true;
						
					}
				}
				
				if (!existe) {
				    Planta plantaSeleccionada = plantas.get(filaSeleccionada);
				    modeloSelec.getPlantas().add(plantaSeleccionada);
				    modeloSelec.getCantidades().add(1);
				    modeloSelec.fireTableRowsInserted(modeloSelec.getRowCount() - 1, modeloSelec.getRowCount() - 1);
				}
				
				if (numeroFilas == 0) {
					scrollSelec.setVisible(true);
				    revalidate();
				    repaint();
				}
				
				
			}
		});
        
        
      
        JButton eliminar = new JButton("Eliminar");
        
        eliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaSelec.getSelectedRow();
				if(filaSeleccionada  == -1) {
					JOptionPane.showMessageDialog(SelecPlantas.this, "Selecciona una planta de la tabla de abajo", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				modeloSelec.restarCantidad(filaSeleccionada);
				int numeroFilas = tablaSelec.getRowCount();
				
				if(numeroFilas == 0) {
					scrollSelec.setVisible(false);
					revalidate();
					repaint();
				}
				
				
			}
		} );
        
        
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
