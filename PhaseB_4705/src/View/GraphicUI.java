package View;

import javax.swing.JFrame;

import Controller.Controller;

public class GraphicUI extends JFrame
{
    private GamePanel gamePanel;

    private Controller controller;
    
    /**
     * Opens a new window and starts the game
     */
    public GraphicUI()
    {
        controller = new Controller(this);
        gamePanel = new GamePanel(controller);

        // FOR FRAME
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(1500, 700);
        this.setResizable(false);

        // ADD COMPONENTS
        this.add(gamePanel);
    }

    /**
     * Updates the player and info box UI, the jackpot ui
     */
    public void UpdateUI()
    {
        gamePanel.UpdateGameUI();
    }
}
