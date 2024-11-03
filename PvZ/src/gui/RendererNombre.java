package gui;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
class RendererNombre extends JLabel implements TableCellRenderer{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		try {
			BufferedImage imagenleer = ImageIO.read(new File("src/imagenes/" + value.toString().replaceAll("\\s+", "") + ".png"));
			setIcon(new ImageIcon(imagenleer.getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 String valor = (String) table.getValueAt(row, column);
         setText(valor);
		
		return this;
	}
	
}
