package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import domain.Planta;

public class ModeloTabla extends AbstractTableModel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<Planta> plantas;
	private String[] columnas = {"Nombre","Vida", "Velocidad.Atc", 
			"Daño", "Rango", "Nivel" };
	
	public ModeloTabla(ArrayList<Planta> plantas) {
		this.plantas = plantas;
		
	}
	
	@Override
	public Class<?> getColumnClass(int column) {
        // le damos clases a cada columna para evitar errores
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

	@Override
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
			
		
		}
		
		return null;
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return columnas[column];
	}
	
	
}
