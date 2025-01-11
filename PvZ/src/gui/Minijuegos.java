package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.event.MouseInputAdapter;

public class Minijuegos extends JFrame {
    private static final long serialVersionUID = 1L;
    private List<String> minijuegos;

    public Minijuegos(List<String> minijuegos) {
        this.minijuegos = minijuegos;
        MinijuegosModel modeloTabla = new MinijuegosModel(this.minijuegos);
        JTable tablaMinijuegos = new JTable(modeloTabla);
        tablaMinijuegos.setShowGrid(true);
        tablaMinijuegos.getTableHeader().setReorderingAllowed(false);
        tablaMinijuegos.setRowHeight(Ajustes.resoluciony() / 4);

        TableCellRenderer cellrenderer = (table, value, isSelected, hasFocus, row, column) -> {
            JLabel result = new JLabel();
            result.setHorizontalAlignment(SwingConstants.CENTER);
            result.setOpaque(true);

            String nombre = (String) modeloTabla.getValueAt(row, column);
            if (nombre != null && !nombre.isEmpty()) {
                String imagePath = "src/imagenes/" + nombre + ".png";
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    ImageIcon originalIcon = new ImageIcon(imagePath);
                    Image scaledImage = originalIcon.getImage().getScaledInstance(
                        Ajustes.resolucionx() / 10, // Ajusta el ancho
                        Ajustes.resoluciony() / 10, // Ajusta el alto
                        Image.SCALE_SMOOTH
                    );
                    result.setIcon(new ImageIcon(scaledImage));
                } else {
                    result.setText("No image");
                }
            } else {
                result.setText("Empty");
            }

            result.setBackground(isSelected ? Color.green : new Color(0, 128, 0));
            return result;
        };

        tablaMinijuegos.setDefaultRenderer(Object.class, cellrenderer);

        // Listener para manejar clics en las celdas
        tablaMinijuegos.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tablaMinijuegos.rowAtPoint(e.getPoint());
                int column = tablaMinijuegos.columnAtPoint(e.getPoint());
                if (row >= 0 && column >= 0) {
                    String selectedMinijuego = (String) modeloTabla.getValueAt(row, column);
                    if (selectedMinijuego != null && !selectedMinijuego.isEmpty()) {
                        openMinijuegoPanel(selectedMinijuego);
                    }
                }
            }
        });

        this.add(tablaMinijuegos, BorderLayout.CENTER);
        this.setTitle("Minijuegos");
        this.setVisible(true);
        this.setSize(Ajustes.resolucionx(), Ajustes.resoluciony());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Abre un nuevo JPanel basado en el minijuego seleccionado.
     *
     * @param minijuego Nombre del minijuego seleccionado.
     */
    private void openMinijuegoPanel(String minijuego) {
        switch (minijuego) {
		case "plantanueces" : {
			new Plantanueces();
		}
		case "plantapon" : {
			new Plantapon();
		}
		case "apocalipsis" : {
			new Apocalipsis();
		}
			
		}
		
		}
    

    public static void main(String[] args) {
        List<String> minijuegos = List.of(
            "plantanueces", "plantapon", "apocalipsis", 
            "coming_soon", "coming_soon", "coming_soon",
            "coming_soon", "coming_soon", "coming_soon",
            "coming_soon", "coming_soon", "coming_soon"
        );
        new Minijuegos(minijuegos);
    }
}
