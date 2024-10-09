package main;
// de Pele
import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;

public class BotonEspecial1 extends JButton {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BotonEspecial1(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Shape buttonShape = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
        
        if (getModel().isArmed()) {
            g2.setColor(Color.LIGHT_GRAY);
        } else {
            g2.setColor(Color.GREEN);
        }
        g2.fill(buttonShape);
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        Shape buttonShape = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
        return buttonShape.contains(x, y);
    }
}