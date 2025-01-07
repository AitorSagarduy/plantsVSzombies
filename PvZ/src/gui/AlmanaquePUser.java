package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import domain.Planta;

public class AlmanaquePUser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		ArrayList<Planta> plantas = new ArrayList<Planta>();
		MenuPlantas.cargarPlantasCSV(plantas, "src/DatosCsv/TODAS.csv");
        AlmanaquePUser ventana = new AlmanaquePUser(plantas);
        ventana.setLocationRelativeTo(null);

	}
	
	public AlmanaquePUser (ArrayList<Planta> plantas) {
        setTitle("Almanaque Plantas");
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel plantsPanel = new JPanel();
        plantsPanel.setLayout(new GridLayout(0, 3, 10, 10)); 
        plantsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        


        for (Planta planta : plantas) {
            JButton plantaButton = new JButton(planta.getNombre());
            plantaButton.setFont(new Font("Arial", Font.BOLD, 12));
            plantaButton.setIcon(new ImageIcon("src/imagenes/" + planta.getTipo() + ".png"));
            plantaButton.setHorizontalTextPosition((int) CENTER_ALIGNMENT);
            plantaButton.setVerticalTextPosition(SwingConstants.BOTTOM);
 

            plantaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, planta.toString(), "Estadísticas de " + planta.getNombre(), JOptionPane.INFORMATION_MESSAGE);
                }
            });

            plantsPanel.add(plantaButton);
        }
        // Crear el botón de ordenación
        JButton botonOrdenar = new JButton("Ordenar");

        // Crear el menú emergente
        JPopupMenu menuOrdenar = new JPopupMenu();
        JMenuItem ordenarNombre = new JMenuItem("Ordenar por Nombre");
        JMenuItem ordenarTipo = new JMenuItem("Ordenar por Tipo");
        menuOrdenar.add(ordenarNombre);
        menuOrdenar.add(ordenarTipo);
        
		ordenarNombre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 // Llamar al método recursivo para ordenar la lista por nombre
				ordenarPorNombre(plantas, plantas.size());

		        // Actualizar el panel con las plantas ordenadas
		        plantsPanel.removeAll(); // Eliminar todos los botones actuales
		        for (Planta planta : plantas) {
		            // Crear un botón para cada planta ordenada
		            JButton plantaButton = new JButton(planta.getNombre());
		            plantaButton.setFont(new Font("Arial", Font.BOLD, 12));
		            plantaButton.setIcon(new ImageIcon("src/imagenes/" + planta.getTipo() + ".png"));
		            plantaButton.setHorizontalTextPosition(SwingConstants.CENTER);
		            plantaButton.setVerticalTextPosition(SwingConstants.BOTTOM);

		            plantaButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
		                    JOptionPane.showMessageDialog(null, planta.toString(), 
		                        "Estadísticas de " + planta.getNombre(), JOptionPane.INFORMATION_MESSAGE);
		                }
		            });

		            // Añadir el botón al panel
		            plantsPanel.add(plantaButton);
		        }

		        // Refrescar la interfaz gráfica para mostrar los cambios
		        plantsPanel.revalidate();
		        plantsPanel.repaint();
				
			}
		});

        // Añadir ActionListener al botón para mostrar el menú
        botonOrdenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuOrdenar.show(botonOrdenar, botonOrdenar.getWidth(), botonOrdenar.getHeight());
            }
        });

        JScrollPane scrollPane = new JScrollPane(plantsPanel);
        JLabel titleLabel = new JLabel("Estas son tus plantas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panelTitulo = new JPanel(new BorderLayout());

	     
	     JLabel titulo = new JLabel("Estas son tus plantas", SwingConstants.CENTER);
	     titulo.setFont(new Font("Arial", Font.BOLD, 16));
	
	     JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
	     menuPanel.add(botonOrdenar);
	
	     
	     panelTitulo.add(menuPanel, BorderLayout.WEST); 
	     panelTitulo.add(titulo, BorderLayout.CENTER); 
	
	     
	    mainPanel.add(panelTitulo, BorderLayout.NORTH);
        
        mainPanel.add(panelTitulo, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
       
        add(mainPanel);
        setVisible(true);
		
        
	}
	private void ordenarPorNombre(ArrayList<Planta> plantas, int n) {
	    if (n == 1) {
	        return;
	    }

	    for (int i = 0; i < n - 1; i++) {
	        if (plantas.get(i).getNombre().compareToIgnoreCase(plantas.get(i + 1).getNombre()) > 0) {
	            // Intercambiar si están en el orden incorrecto
	            Planta temp = plantas.get(i);
	            plantas.set(i, plantas.get(i + 1));
	            plantas.set(i + 1, temp);
	        }
	    }

	    // Llamada recursiva para ordenar el resto de la lista
	    ordenarPorNombre(plantas, n - 1);
	}

}
