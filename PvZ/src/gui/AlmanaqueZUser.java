package gui;

import java.awt.BorderLayout;
import java.awt.Color;
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

import domain.Zombie;

public class AlmanaqueZUser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		ArrayList<Zombie> zombies = new ArrayList<Zombie>();
		CargarZombies.cargarZombiesCSV(zombies, "src/DatosCsv/zombies.csv");
        AlmanaqueZUser ventana = new AlmanaqueZUser(zombies);
        ventana.setLocationRelativeTo(null);

	}
	
	public AlmanaqueZUser (ArrayList<Zombie> zombies) {
        setTitle("Almanaque Zombies");
        setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
	//	setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
        JPanel mainPanel = new JPanel();
        
		Color colorfondo = new Color(38, 116, 68);
		mainPanel.setBackground(colorfondo);

        mainPanel.setLayout(new BorderLayout());

        JPanel zombiePanel = new JPanel();
        zombiePanel.setBackground(colorfondo);
        zombiePanel.setLayout(new GridLayout(0, 3, 10, 10)); 
        zombiePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        


        colocarBotones(zombiePanel, zombies);
        
        // Crear el botón de ordenación
        JButton botonOrdenar = new JButton("Ordenar");

        // Crear el menú emergente
        JPopupMenu menuOrdenar = new JPopupMenu();
        JMenuItem ordenarNombre = new JMenuItem("Ordenar por Nombre");
        JMenuItem ordenarNivel = new JMenuItem("Ordenar por Nivel");
        menuOrdenar.add(ordenarNombre);
        menuOrdenar.add(ordenarNivel);
        
		ordenarNombre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 // Llamar al método recursivo para ordenar la lista por nombre
				ordenarPorNombre(zombies, zombies.size());
				colocarBotones(zombiePanel, zombies);
			}
		});
		
		ordenarNivel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 // Llamar al método recursivo para ordenar la lista por nombre
				ordenarPorNivel(zombies, zombies.size());
				// Llamar al método para actualizar el panel con las plantas orden	
				colocarBotones(zombiePanel, zombies);
			}
		});
		
        // Añadir ActionListener al botón para mostrar el menú
        botonOrdenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuOrdenar.show(botonOrdenar, botonOrdenar.getWidth(), botonOrdenar.getHeight());
            }
        });
        
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

        JScrollPane scrollPane = new JScrollPane(zombiePanel);
        JLabel titleLabel = new JLabel("Estos son tus zombies", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panelTitulo = new JPanel(new BorderLayout());

	     
	     JLabel titulo = new JLabel("Estas son tus plantas", SwingConstants.CENTER);
	     titulo.setFont(new Font("Arial", Font.BOLD, 16));
	
	     JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
	     menuPanel.add(botonOrdenar);
	
	     
	     panelTitulo.add(menuPanel, BorderLayout.EAST);
	     panelTitulo.add(atras, BorderLayout.WEST);
	     panelTitulo.add(titulo, BorderLayout.CENTER); 
	
	     
	    mainPanel.add(panelTitulo, BorderLayout.NORTH);
        
        mainPanel.add(panelTitulo, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
       
        add(mainPanel);
        setVisible(true);
		
        
	}
	private void colocarBotones(JPanel panel, ArrayList<Zombie> zombies) {
		 // Actualizar el panel con las plantas ordenadas
       panel.removeAll(); // Eliminar todos los botones actuales
       for (Zombie zombie : zombies) {
           // Crear un botón para cada planta ordenada
           JButton zombieButton = new JButton(zombie.getNombre().replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑ ]", ""));
           zombieButton.setFont(new Font("Arial", Font.BOLD, 12));
           Color colorboton = new Color(103, 255, 102);
	   	   Color colornivelmax = new Color(255, 216, 0);
	   	   zombieButton.setBackground(colorboton);
	   	   if (zombie.getNivel() == 4) {
	   		   zombieButton.setBackground(colornivelmax);
	   	   }
           ImageIcon icono = new ImageIcon("src/imagenes/" + zombie.getTipo().replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑ]", "") + ".png");
           zombieButton.setIcon(icono);
           zombieButton.setHorizontalTextPosition(SwingConstants.CENTER);
           zombieButton.setVerticalTextPosition(SwingConstants.BOTTOM);

           zombieButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   JOptionPane.showMessageDialog(null, zombie.toString(), 
                       "Estadísticas de " + zombie.getNombre(), JOptionPane.INFORMATION_MESSAGE);
               }
           });

           // Añadir el botón al panel
           panel.add(zombieButton);
       }

       // Refrescar la interfaz gráfica para mostrar los cambios
       panel.revalidate();
       panel.repaint();
   
   }
	private void ordenarPorNivel(ArrayList<Zombie> zombies, int n) {
		    if (n == 1) {
		        return;
		    }

		    for (int i = 0; i < n - 1; i++) {
		        if (zombies.get(i).getNivel() < zombies.get(i + 1).getNivel()) {
		            // Intercambiar si están en el orden incorrecto
		            Zombie temp = zombies.get(i);
		            zombies.set(i, zombies.get(i + 1));
		            zombies.set(i + 1, temp);
		        }
		    }

		    // Llamada recursiva para ordenar el resto de la lista
		    ordenarPorNivel(zombies, n - 1);
		}
	
		
	
	private void ordenarPorNombre(ArrayList<Zombie> zombies, int n) {
	    if (n == 1) {
	        return;
	    }

	    for (int i = 0; i < n - 1; i++) {
	        if (zombies.get(i).getNombre().compareToIgnoreCase(zombies.get(i + 1).getNombre()) > 0) {
	            // Intercambiar si están en el orden incorrecto
	            Zombie temp = zombies.get(i);
	            zombies.set(i, zombies.get(i + 1));
	            zombies.set(i + 1, temp);
	        }
	    }

	    // Llamada recursiva para ordenar el resto de la lista
	    ordenarPorNombre(zombies, n - 1);
	}

}
