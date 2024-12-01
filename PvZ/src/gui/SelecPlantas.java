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
import javax.swing.text.Position;

import domain.Planta;

public class SelecPlantas extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139614017884562916L;
	
	


	public static void main(String[] args) {
		//se crea la ventana
        @SuppressWarnings("unused")
		SelecPlantas ventana = new SelecPlantas();
	}

	public SelecPlantas() {
		// ajustes de la ventana y
		//cargar la lista plantas con plantas desde el csv
		ArrayList<Planta> plantas = new ArrayList<Planta>();
		MenuPlantas.cargarPlantasCSV(plantas, "src/DatosCsv/plantas.csv");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Selecciona Plantas");
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setSize(Ajustes.resolucionx(), Ajustes.resoluciony());

		// crear el modelo con las plantas y crear la tabla con el modelo 
		ModeloTabla modelo = new ModeloTabla(plantas);
		JTable tabla = new JTable(modelo);
		tabla.setRowHeight(30);
		
		// meter la tabla en el scrollpane
		JScrollPane scrollPane = new JScrollPane(tabla);
		
		
		
		// implementar el renderer del nombre a la columna 0
		TableColumn nombreColumn = tabla.getColumnModel().getColumn(0);
		nombreColumn.setCellRenderer(new RendererNombre());
		
		//botones
		JButton atras = new JButton("Atras");
        // añadir actionlistener para q al pulsar se abra el menuinicial
        atras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MenuInicial();
				// dispose es para que al abrir la nueva ventana se cierre la anterior
				dispose();
				
			}
		});
        
        // crear una lista donde el usuario la llene y pasarsela al modelo
        ArrayList<Planta> plantasJ = new ArrayList<Planta>();
		ModeloSeleccionadas modeloSelec = new ModeloSeleccionadas(plantasJ);
		
		// crear la tabla con el modelo 
		JTable tablaSelec = new JTable(modeloSelec);
		 tablaSelec.setRowHeight(30);
		
		// meter la tabla en un scrollpane
		JScrollPane scrollSelec = new JScrollPane(tablaSelec);
		
		
		//colocarlo abajo y hacer que no se vea
		add(scrollSelec, BorderLayout.SOUTH);
		scrollSelec.setVisible(false);
	
		
		
        JButton agregar = new JButton("Añadir");
        
        // Actionlistener para que al pulsar el boton 
        //se cree una tabla con con las estadisticas pero con una columna 
        //adicional indicando la cantidad q el usuario quiere utilizar
        // y si el la planta ya este en la tabla que la columna cantidad se vaya sumando
        
        agregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//obtener la fila seleccionada por el usuario y 
				//añadirle el renderer nombre para que aparezcan 
				//iconos junto al nombre
				
				int filaSeleccionada = tabla.getSelectedRow();
				TableColumn nombreColumn = tablaSelec.getColumnModel().getColumn(0);
				nombreColumn.setCellRenderer(new RendererNombre());
				
				// mensaje de error si se selecciona mal la planta
				//(si el getSelectedrow no tiene ninguna fila seleccionada devuelve -1)
				if(filaSeleccionada  == -1) {
					JOptionPane.showMessageDialog(SelecPlantas.this, "Selecciona una planta", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//obtener el numero de filas de y una variable 
				//que nos dice si la tabla existe o no
				int numeroFilas = tablaSelec.getRowCount();
				boolean existe = false;
				
				String nombrePlanta = (String) tabla.getValueAt(filaSeleccionada, 0);
				// si existe sumarle la cantidad 
				for (int i = 0; i < numeroFilas; i++) {
					if(modeloSelec.getValueAt(i, 0).equals(nombrePlanta)) {
						modeloSelec.sumarCantidad(i);
						existe = true;
						
					}
				}
				// si no añadir la fila seleccionada con la cantidad a 1 
				if (!existe) {
				    Planta plantaSeleccionada = plantas.get(filaSeleccionada);
				    modeloSelec.getPlantas().add(plantaSeleccionada);
				    modeloSelec.getCantidades().add(1);
				    modeloSelec.fireTableRowsInserted(modeloSelec.getRowCount() - 1, modeloSelec.getRowCount() - 1);
				}
				// al pulsar añadir por primera vez que se vea la tabla
				if (numeroFilas == 0) {
					scrollSelec.setVisible(true);
				    revalidate();
				    repaint();
				}
				
				
			}
		});
        
        
      
        JButton eliminar = new JButton("Eliminar");
        
        eliminar.addActionListener(new ActionListener() {
			
        	//actionListener para que al seleccionar una fila y pulsa eliminar 
        	//se reste una a la columna cantidad (la comprobacion si fila si tiene cantidad 
        	// o no para elinarla esta en el modelo de la tabla)
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaSelec.getSelectedRow();
				//si el usuario selecciona mal la planta le saldra un mensaje de error
				if(filaSeleccionada  == -1) {
					JOptionPane.showMessageDialog(SelecPlantas.this, "Selecciona una planta de la tabla de abajo", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//restar la cantidad
				modeloSelec.restarCantidad(filaSeleccionada);
				int numeroFilas = tablaSelec.getRowCount();
				//si la tabla se queda sin plantas se oculte la tabla
				if(numeroFilas == 0) {
					scrollSelec.setVisible(false);
					revalidate();
					repaint();
				}
				
				
			}
		} );
        
        // al pulsar continuar que se abra el SelecZombies
        JButton batalla = new JButton("Continuar");
        batalla.addActionListener(e -> SwingUtilities.invokeLater(() -> new SelecZombies()));
        
        //Añadir todas los botones a un panel y ajustarlo yodo
        JPanel panel = new JPanel();
        panel.add(atras);
        panel.add(agregar);
        panel.add(eliminar);
        panel.add(batalla);
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
		setLocationRelativeTo(null);

        
        setVisible(true);
	}

	
}