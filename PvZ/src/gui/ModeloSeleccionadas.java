package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ModeloSeleccionadas extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Planta> plantas;
	private String[] columnas = {"Nombre","Vida", "Velocidad.Atc", 
			"Daño", "Rango", "Nivel", "Cantidad" };
	private ArrayList<Integer> cantidades = new ArrayList<Integer>();
	
	public  ModeloSeleccionadas(ArrayList<Planta> plantas) {
		this.plantas = plantas;
		for (int i = 0; i < plantas.size(); i++) {
			cantidades.add(0);
		}
	}
	@Override
	public Class<?> getColumnClass(int column) {
        // en este caso, las dos primeras columnas es String
        // String, mientras que el dato de la última es LocalDate
        if (column == 0) {
            return String.class;
        } else {
            return Integer.class;
        }
    }
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return plantas.size();
	}
	

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnas.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		
		case 0:
			return plantas.get(rowIndex).getNombre();
		case 1:
			return plantas.get(rowIndex).getVida();
		case 2:
			return plantas.get(rowIndex).getTmp_atac();
		case 3:
			return plantas.get(rowIndex).getDanyo();
		case 4:
			return plantas.get(rowIndex).getRango();
		case 5:
			return plantas.get(rowIndex).getNivel();
		case 6:
			return cantidades.get(rowIndex);
		
		}
		return null;
	}
	
	
	
	@Override
	public String getColumnName(int column) {
		
		return columnas[column];
	}
	public void sumarCantidad(int nFila) {
		cantidades.set(nFila, cantidades.get(nFila) + 1);
		fireTableCellUpdated(nFila, 6);
	}
	public void restarCantidad (int nFila) {
		int cantidadRestada  = cantidades.get(nFila) - 1;
		
		if(cantidadRestada <= 0) {
			plantas.remove(nFila);
			cantidades.remove(nFila);
			fireTableRowsDeleted(nFila, nFila);
		} else {
			cantidades.set(nFila, cantidadRestada);
			fireTableCellUpdated(nFila, 6);
		}
	}
	
	
	/**
	 * @return the cantidades
	 */
	public ArrayList<Integer> getCantidades() {
		return cantidades;
	}
	
	
	/**
	 * @return the plantas
	 */
	public ArrayList<Planta> getPlantas() {
		return plantas;
	}
	
	

}
