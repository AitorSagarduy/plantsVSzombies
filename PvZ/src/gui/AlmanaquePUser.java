package gui;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

        JScrollPane scrollPane = new JScrollPane(plantsPanel);
        JLabel titleLabel = new JLabel("Estas son tus plantas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
       
        add(mainPanel);
        setVisible(true);
		
	
	}

}
