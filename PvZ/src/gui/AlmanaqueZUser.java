package gui;

import java.awt.BorderLayout;
import java.awt.Color;
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

import domain.Zombie;

public class AlmanaqueZUser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	public static void main(String[] args) {
		ArrayList<Zombie> zombies = new ArrayList<Zombie>();
		CargarZombies.cargarZombiesCSV(zombies, "src/DatosCsv/zombies.csv");
        
		ArrayList<Zombie> zombiesUsuario = new ArrayList<Zombie>();
		zombiesUsuario.add(new Zombie("ZombiAbanderado","Zombi Abanderado",100,1,10,3,4));
		zombiesUsuario.add(new Zombie("ZombiBuzo","Zombi Buzo",80,4,3,200,11));
		zombiesUsuario.add( new Zombie("ZombiCaracubo","Zombi Caracono",300,500,444,44,5));
		AlmanaqueZUser ventana = new AlmanaqueZUser(zombies, zombiesUsuario );
        ventana.setLocationRelativeTo(null);

	} 
	
	public AlmanaqueZUser(ArrayList<Zombie> zombies, ArrayList<Zombie> zombiesUsuario) {
		setTitle("Almanaque Zombies");
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel zombiesPanel = new JPanel();
        zombiesPanel.setLayout(new GridLayout(0, 3, 10, 10)); 
        zombiesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Zombie zombie : zombies) {
            JButton plantaButton = new JButton(zombie.getNombre());
            plantaButton.setFont(new Font("Arial", Font.BOLD, 12));
            plantaButton.setIcon(new ImageIcon("src/imagenes/" + zombie.getNombre().replaceAll("[\\s-]+", "") + ".png"));
            plantaButton.setHorizontalTextPosition((int) CENTER_ALIGNMENT);
            plantaButton.setVerticalTextPosition(SwingConstants.BOTTOM);
 

            plantaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, zombie.toString(), "Estadísticas de " + zombie.getNombre(), JOptionPane.INFORMATION_MESSAGE);
                }
            });
            boolean encontrado = false;
            for (Zombie zombieU : zombiesUsuario) {
				if(zombie.getTipo().equals(zombieU.getTipo())) {
					System.out.println(zombie.getTipo() +"," + zombieU.getTipo());
					plantaButton.setBackground(Color.green);
					encontrado = true;
					break;

				} 
			}
            if(!encontrado) {
            	//plantaButton.setBackground(Color.GRAY);
            	
            }

            zombiesPanel.add(plantaButton);
        }

        JScrollPane scrollPane = new JScrollPane(zombiesPanel);
        JLabel titleLabel = new JLabel("Estas son tus Zombies", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
       
        add(mainPanel);
        setVisible(true);
		
	
	}

}
