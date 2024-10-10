package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Simulacionv1 extends JFrame{
	
	public static void main(String[] args) {
		Simulacionv1 ventana = new Simulacionv1();
	}
	public class Mirenderizado extends DefaultListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			// TODO Intento de crear una label con la que interactuar
			JLabel etiqueta = (JLabel)(super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus));
			
			
			return etiqueta;
		}
	}

	String plantaSeleccionada = "";
	
	public Simulacionv1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle("ventana de simulacion");
		setSize(640, 480);
		
		String[] plantasBasicas = {
				"Girasol","Lanzaguisantes","Nuez"
		};
		JList listaPlantas = new JList(plantasBasicas);
		listaPlantas.setFixedCellWidth(200);
		listaPlantas.setCellRenderer(new Mirenderizado());
		listaPlantas.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO si se esta eligiendo una opcion superponer esa opcion en algun boton a selecionar
				if(!e.getValueIsAdjusting()) {
					System.out.println(listaPlantas.getSelectedValue().toString()+" ha sido seleccionada");
					plantaSeleccionada = listaPlantas.getSelectedValue().toString();
				}
			}
		});
		
		JScrollPane scroll = new JScrollPane(listaPlantas);
		add(scroll,BorderLayout.WEST);
		
		//Crear una barra de progreso como en el juego original
		JProgressBar barraProgreso = new JProgressBar(0,100);
		add(barraProgreso, BorderLayout.NORTH);
		barraProgreso.setBackground(Color.gray);
		barraProgreso.setForeground(Color.GREEN);
		
		JPanel panelCesped = new JPanel();
		panelCesped.setBackground(Color.GRAY);
		panelCesped.setLayout(new GridLayout(5, 10));
		
		for(int i = 0; i<50;i++) {
			JButton espacio = new JButton(""+i);
			espacio.setBackground(Color.GREEN);
			espacio.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO funcion de interaccion del mouse 
					if(plantaSeleccionada.equals("")) {
						JOptionPane.showMessageDialog(Simulacionv1.this, "No has seleccionado una planta ha colocar", "Error", JOptionPane.ERROR_MESSAGE);
					}else {
						espacio.setText(plantaSeleccionada);						
					}
				}
			});
			panelCesped.add(espacio);
		}
		
		add(panelCesped, BorderLayout.CENTER);
		
		
		setVisible(true);
		for (int i = 0; i <= 100; i++) {
            barraProgreso.setValue(i); // Actualizar valor de la barra
            if(barraProgreso.isMaximumSizeSet()) {
            	barraProgreso.setForeground(Color.RED);
            }
            try {
                Thread.sleep(50); // Esperar 50 ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
}
