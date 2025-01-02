/*package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class Tienda extends JFrame{
	private static final long serialVersionUID = 1L;

	public Tienda() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Tienda");
		
		JPanel central = new JPanel();
		
		
		// Crear el modelo con las plantas y crear la tabla con el modelo 
		ModeloTabla modelo = new ModeloTabla(plantas);
		JTable tabla = new JTable(modelo);
		tabla.setRowHeight(30);

		// Meter la tabla en el scrollpane
		JScrollPane scrollPane = new JScrollPane(tabla);

		// Implementar el renderer del nombre a la columna 0
		TableColumn nombreColumn = tabla.getColumnModel().getColumn(0);
		nombreColumn.setCellRenderer(new RendererNombre());

		// Crear una lista donde el usuario la llene y pasársela al modelo
		ArrayList<Planta> plantasJ = new ArrayList<Planta>();
		ModeloSeleccionadas modeloSelec = new ModeloSeleccionadas(plantasJ);

		// Crear la tabla con el modelo
		JTable tablaSelec = new JTable(modeloSelec);
		tablaSelec.setRowHeight(30);

		// Meter la tabla en un scrollpane
		JScrollPane scrollSelec = new JScrollPane(tablaSelec);

		// Colocarlo abajo y hacer que no se vea
		add(scrollSelec, BorderLayout.SOUTH);
		scrollSelec.setVisible(false);

		
		
		add(central);
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		setLocationRelativeTo(null);
		setVisible(true);		
	}
	
	public static void main(String[] args) {
		Tienda ventana = new Tienda();
		ventana.setLocationRelativeTo(null);
	}
}
*/