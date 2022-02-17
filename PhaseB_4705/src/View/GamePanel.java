package View;

import javax.swing.JPanel;
import java.awt.*;

import Controller.Controller;
import Model.Player;
import Model.CardPositions.JackpotPosition;

public class GamePanel extends JPanel
{
    private BoardPanel boardPanel;
    private JPanel eastPanel;
    private PlayerPanel[] players;
    private InfoBoxPanel infoBox;

    private Controller controller;

    public GamePanel(Controller controller)
    {
        this.controller = controller;
        players = new PlayerPanel[2];

        // FOR PANELS
        boardPanel = new BoardPanel(controller);

        eastPanel = new JPanel();
        eastPanel.setLayout(new GridLayout(3,3));

        // FOR PLAYER1
        players[0] = new PlayerPanel(controller.GetBoard().players[0], controller);
        eastPanel.add(players[0]);

        // FOR INFO BOX
        infoBox = new InfoBoxPanel(controller);
        eastPanel.add(infoBox);

        // FOR PLAYER2
        players[1] = new PlayerPanel(controller.GetBoard().players[1],controller);
        eastPanel.add(players[1]);
        
        // FOR GAME PANEL
        this.setLayout(new BorderLayout());

        // ADD COMPONENTS
        this.add(boardPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
    }  
    
    /**
     * Updates the player and info box UI
     * as well as the jackpot ui and the pawns
     */
    public void UpdateGameUI()
    {
        Player player = controller.GetBoard().players[0];
        players[0].UpdatePlayerUI(player.GetMoney(), player.GetLoans(), player.GetBills());

        player = controller.GetBoard().players[1];
        players[1].UpdatePlayerUI(player.GetMoney(), player.GetLoans(), player.GetBills());
        
        infoBox.UpdateInfoUI(controller.GetGameDuration(), controller.GetTurn(), controller.GetTask());

        boardPanel.UpdateJackpot(((JackpotPosition)controller.GetBoard().jackpot.GetPosition()).GetJackpot()); 

        boardPanel.UpdatePawns();
    }

}
