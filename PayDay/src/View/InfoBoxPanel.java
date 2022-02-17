package View;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import Controller.Controller;
import Model.Player;
import Model.CardPositions.CardPosition;
import Model.CardPositions.DealPosition;
import Model.CardPositions.MailPosition;


/**
 * This class represents and displays important information about the game in a UI panel
 */
public class InfoBoxPanel extends JPanel
{
    private JLabel duration;
    private JLabel turn;
    private JLabel task;

    JButton deal, mail;    

    /**
     * Sets up the information box for the game
     * <p>Precondition: <p> a) Controller must not be null</p></p>
     * @param controller
     */
    public InfoBoxPanel(Controller controller)
    {
        if (controller == null)
            throw new NullPointerException("controller is null");

        this.setLayout(new GridLayout(4,1));
        
        String[] parts = controller.toString().split("\n");
        duration = new JLabel(parts[1]);
        turn = new JLabel(parts[2]);
        task = new JLabel(parts[3]);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        deal = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("resources/images/DealCard.png")));
        deal.addActionListener(
            /**
             * This function draws a card from the deal card deck and updates the UI
             * <p>Preconditions: <p>a) The position of the player must be of type "DealPosition"</p>
             *                   <p>b) The player must not have already played</p>
             * </p>
             */
            (event) -> {

                try
                {
                    Player player = controller.GetBoard().players[controller.GetTurn()-1];
                    if (player.HasPlayed() && player.IsTaskDone())
                        throw new Exception("Player has played already");
    
                    int pos = controller.GetBoard().players[controller.GetTurn()-1].GetPosition()-1;
                    if (pos < 0)
                        throw new Exception("Player is in start position");

                    if (!(controller.GetBoard().tiles[pos].GetPosition() instanceof DealPosition))
                        throw new Exception("Player is not in a Deal position");
    
                    DealPosition position = (DealPosition)controller.GetBoard().tiles[pos].GetPosition();
                    
                    position.performAction(player);
                    
                    controller.GetUI().UpdateUI();    
                }
                catch (Exception e)
                {
                    System.err.println(e);
                }
            }
        );
        
        mail = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("resources/images/MailCard.png")));
        mail.addActionListener(
            /**
             * This function draws a card from the mail card deck and updates the UI
             * <p>Preconditions: <p>a) The position of the player must be of type "MailPosition"</p>
             *                   <p>b) The player must not have already played</p>
             * </p>
             */
            (event) -> {
                try
                {
                    Player player = controller.GetBoard().players[controller.GetTurn()-1];
                    if (player.HasPlayed() && player.IsTaskDone())
                        throw new Exception("Player has played already");
                      
                    int pos = controller.GetBoard().players[controller.GetTurn()-1].GetPosition()-1;
                    if (pos < 0)
                        throw new Exception("Player is in start position");
                                          
                    CardPosition position = controller.GetBoard().tiles[pos].GetPosition();
                    if (!(position instanceof MailPosition))
                        throw new Exception("Player is not in a Mail position");
                    
    
                    ((MailPosition)position).performAction(player);
                
                    controller.GetUI().UpdateUI();
                }
                catch (Exception e)
                {
                    System.err.println(e);
                }
            }
        );

        buttonPanel.add(deal);
        buttonPanel.add(mail);

        this.add(new JLabel(parts[0]));
        this.add(duration);
        this.add(turn);
        this.add(task);
        this.add(buttonPanel);
    }

    /**
     * Updates the info box UI
     * <p>Preconditions: <p>a) turn must be 1 or 2</p>
     *                   <p>b) duration >=0</p>
     * </p>
     * @param duration
     * @param turn
     * @param task
     */
    public void UpdateInfoUI(int duration, int turn, String task)
    {
        if (duration < 0 || (turn != 1 && turn != 2))
            throw new IllegalArgumentException("Invalid game duration or player turn");
            
        this.task.setText("Task: " + task); 
        this.turn.setText("Turn: Player" + turn); 
        this.duration.setText(duration + " Months Left"); 
    }
}
