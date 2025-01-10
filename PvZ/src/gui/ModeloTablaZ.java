package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import domain.Zombie;

public class ModeloTablaZ extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Zombie> zombies;
	private String[] columnas = {"Tipo", "Nombre","Vida", "Velocidad.Atc", 
			"Daño", "Rapidez", "Nivel"};
	
	public ModeloTablaZ(ArrayList<Zombie> zombies) {
		this.zombies = zombies;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
        // le damos clases a cada columna para evitar errores

		if (columnIndex == 0) {
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

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch(columnIndex) {
		case 0:
			return zombies.get(rowIndex).getTipo()
;		case 1:
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
		}
		
		return null;
		
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return columnas[column];
	}

}
