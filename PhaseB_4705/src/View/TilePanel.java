package View;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Tile;

/**
 * <p>This class represents a tile in UI</p>
 * <p>A tile must contain a text, which will display a day the month,
 * and an image, which will display the tile's position type</p>
 */
public class TilePanel extends JPanel
{
    public JLabel text;
    private JLabel image;
    
    /**
     * Sets up a panel for a tile
     * <p>Precondition <p>tile parameter should not be null</p></p>
     * @param tile
     */
    public TilePanel(Tile tile)
    {
        if (tile == null)
            throw new NullPointerException("tile is null");

        this.setLayout(new GridLayout(2,1));
        this.setBackground(Color.PINK);
        
        text = new JLabel(tile.GetPosition().GetDay() + " " + tile.GetPosition().GetPosition());
        ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("resources/images/"+tile.GetPosition().GetIcon()));
        image = new JLabel(icon);
    
        this.add(text);
        this.add(image);
    }
}
