package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import domain.Zombie;

public class ModeloSelecZombies extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Zombie> zombies;
	private String[] columnas = {"Tipo","Nombre","Vida", "Velocidad.Atc", 
			"Daño", "Rapidez", "Nivel", "Cantidad" };
	private ArrayList<Integer> cantidades = new ArrayList<Integer>();
	
	public  ModeloSelecZombies(ArrayList<Zombie> zombies) {
		this.zombies = zombies;
		for (int i = 0; i < zombies.size(); i++) {
			cantidades.add(0);
		}
	}
	@Override
	public Class<?> getColumnClass(int column) {
		// le damos clases para q no salgan errores
        if (column == 0) {
            return String.class;
        } else {
            return Integer.class;
        }
    }
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return zombies.size();
	}
	

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnas.length;
	}
	
	

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return zombies.get(rowIndex).getTipo();
		case 1:
			return zombies.get(rowIndex).getNombre();
		case 2:
			return zombies.get(rowIndex).getVida();
		case 3:
			return zombies.get(rowIndex).getTmp_atac();
		case 4:
			return zombies.get(rowIndex).getDanyo();
		case 5:
			return zombies.get(rowIndex).getVelocidad();
		case 6:
			return zombies.get(rowIndex).getNivel();
		case 7:
			return cantidades.get(rowIndex);
		
		}
		return null;
	}
	
	
	
	@Override
	public String getColumnName(int column) {
		
		return columnas[column];
	}
	
	// metodo para sumar cantidad y tambien el 
	//firetablecellupdate para notificar que se ha actualizado
	public void sumarCantidad(int nFila) {
		cantidades.set(nFila, cantidades.get(nFila) + 1);
		fireTableCellUpdated(nFila, 7);
	}
	
	// metodo para restar cantidad con el fire notifica que se ha actualizado
	// y tambien elimina la fila llegar a cantidad = 0 
	public void restarCantidad (int nFila) {
		int cantidadRestada  = cantidades.get(nFila) - 1;
		
		if(cantidadRestada <= 0) {
			zombies.remove(nFila);
			cantidades.remove(nFila);
			fireTableRowsDeleted(nFila, nFila);
		} else {
			cantidades.set(nFila, cantidadRestada);
			fireTableCellUpdated(nFila, 7);
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
	public ArrayList<Zombie> getZombies() {
		return zombies;
	}
	
	

}