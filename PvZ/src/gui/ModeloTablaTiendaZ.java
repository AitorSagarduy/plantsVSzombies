package gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import domain.Zombie;

public class ModeloTablaTiendaZ extends AbstractTableModel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<Zombie> zombies;
	private String[] columnas = {"Tipo","Nombre","Vida", "Velocidad.Atc", 
			"Daño", "Rango", "Nivel", "Precio" };
	
	public ModeloTablaTiendaZ(ArrayList<Zombie> zombies) {
		this.zombies = zombies;
		
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
		return zombies.size();
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
			int resultado;
			int nivel = zombies.get(rowIndex).getNivel();
			int danyo = zombies.get(rowIndex).getDanyo();
			if(danyo > 10) {
				resultado = nivel * 100 +50;
			} else if (danyo > 20) {
				resultado = nivel * 100 + 75;
			} else {
				resultado = nivel * 100 +100;
			}
			 return resultado;
			
		
		}
		
		return null;
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return columnas[column];
	}

	public void eliminarfila(int fila) {
		//Eliminar de la lista de plantas la planta de la fila que hay que eliminar
			zombies.remove(fila);
	        //Notificar a la tabla de que se ha eliminado una fila
	        //los dos valores del () representan desde donde hasta donde se ha eliminado
	        fireTableRowsDeleted(fila, fila); 
	        System.out.println(zombies);
	}


	
}
