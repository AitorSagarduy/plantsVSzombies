package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MinijuegosModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final int COLUMNAS = 3;
    private static final int FILAS = 4;
    private List<String> minijuegos;

    public MinijuegosModel(List<String> minijuegos) {
        this.minijuegos = minijuegos;
    }

    @Override
    public int getRowCount() {
        return FILAS;
    }

    @Override
    public int getColumnCount() {
        return COLUMNAS;
    }

    @Override
    public String getValueAt(int rowIndex, int columnIndex) {
        int index = rowIndex * COLUMNAS + columnIndex;
        if (index >= 0 && index < minijuegos.size()) {
            return minijuegos.get(index);
        }
        return "coming_soon";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // Todas las celdas son no editables
    }
}
