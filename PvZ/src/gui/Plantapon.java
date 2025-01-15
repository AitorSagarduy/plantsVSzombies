package gui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


import java.util.Random;






public class Plantapon extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JLabel[] slots;
	private JButton startButton = new JButton("Pagar(50 SOLES)");
	private JButton stopButton = new JButton("Parar");
	private Thread[] threads;
	
	private String[] recompensas = {"sol", "cerebro"};
	private boolean premioObtenido = true;
	
	public Plantapon() {
		JPanel slotPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		slots = new JLabel[3];
		
		for (int i = 0; i < 3; i++) {
			slots[i] = new JLabel();
			Random random = new Random();
			this.actualizarLabel(slots[i], recompensas[random.nextInt(recompensas.length)]);
			slotPanel.add(slots[i]);
		}
		startButton.addActionListener(e -> {
			// Si se ha ganado un premio, no se puede seguir
			if (premioObtenido) {
				JOptionPane.showMessageDialog(this, "Dinero insuficiente, gana soles en el almanaque", "no money", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			startButton.setEnabled(false);
			startButton.setEnabled(true);
			startGame();
			
	      });
		stopButton.addActionListener(e -> {
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
			stopGame();
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
	}
	

	private void stopGame() {
		for (Thread thread : threads) {
			if (thread != null) {
				thread.interrupt();
			}
		}
		
	}


	private void startGame() {
		// TODO Auto-generated method stub
		
	}

	private void actualizarLabel(JLabel lab, String stringx) {
		SwingUtilities.invokeLater(() -> {
			String imagePath = "src/images/" + stringx + ".png";
			lab.setIcon(new ImageIcon(imagePath));
			lab.setHorizontalAlignment(JLabel.CENTER);
			lab.setOpaque(true);
			
		});
		
		
	}

}
