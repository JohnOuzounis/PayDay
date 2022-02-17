package View;


import java.awt.*;


import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Controller.Controller;

/**
 * This class represents the board of the game in UI
 */
public class BoardPanel extends JPanel
{
    private TilePanel[] tiles;
    private Image[] pawns;

    private TilePanel jackpot;
    private TilePanel start;

    private Controller controller;

    private double x,y;

    /**
     * Sets up the board for the game
     * <p>Precondition: <p>Controller must not be null</p></p>
     * @param controller
     */
    public BoardPanel(Controller controller)
    {
        if (controller == null)
            throw new NullPointerException("controller is null");
        this.controller = controller;

        this.setLayout(new GridLayout(5,7));
        this.setBackground(Color.PINK);

        tiles = new TilePanel[this.controller.GetBoard().tiles.length];

        start = new TilePanel(this.controller.GetBoard().start);
        this.add(start);
        for (int i = 0; i < this.controller.GetBoard().tiles.length; i++)
        {
            tiles[i] = new TilePanel(this.controller.GetBoard().tiles[i]);
            this.add(tiles[i]);
        }
        jackpot = new TilePanel(this.controller.GetBoard().jackpot);
        this.add(jackpot);

        pawns = new Image[2];
        String filename = "src/resources/images/";
        pawns[0] = new ImageIcon(filename+"pawn1.png").getImage();
        pawns[1] = new ImageIcon(filename+"pawn2.png").getImage();
    }

    /**
     * Updates the text of the jackpot position
     * <p>Preconditions: <p>a) jackpot must be >= 0</p></p>
     * @param jackpot = the new amount of money in jackpot
     */
    public void UpdateJackpot(double jackpot)
    {
        if (jackpot < 0)
            throw new IllegalArgumentException("Jackpot must be positive");
        this.jackpot.text.setText("Jackpot " + jackpot);
    }

    /**
     * Updates the position of the pawn images
     */
    public void UpdatePawns()
    {
        repaint();
    }

    /**
     * Paints the pawn images at the position of the tile the player is sitting
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        int playerPos = this.controller.GetBoard().players[this.controller.GetTurn()-1].GetPosition();
        if (playerPos == 0)
        {
            x = start.getLocation().getX();
            y = start.getLocation().getY();
        }
        else
        {
            playerPos -= 1;
            x = tiles[playerPos].getLocation().getX();
            y = tiles[playerPos].getLocation().getY();
        }
        g2d.drawImage(pawns[this.controller.GetTurn()-1], (int)x, (int)y, null);


        playerPos = this.controller.GetBoard().players[this.controller.GetNextTurn()-1].GetPosition();
        if (playerPos == 0)
        {
            x = start.getLocation().getX();
            y = start.getLocation().getY();
        }
        else
        {
            playerPos -= 1;
            x = tiles[playerPos].getLocation().getX();
            y = tiles[playerPos].getLocation().getY();
        }
        g2d.drawImage(pawns[this.controller.GetNextTurn()-1], (int)x, (int)y, null);
    }
}
