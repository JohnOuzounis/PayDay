package Model.CardPositions;

import Controller.Controller;
import Model.Player;
import View.OptionDialog;

public class RadioContest extends CardPosition implements Runnable
{
    private Player player1;
    private Player player2;

    /**
     * <p>Precondition: <p>controller not null</p></p>
     * @param imageName
     * @param controller
     */
    public RadioContest(String imageName, Controller controller) 
    {
        super(imageName, controller);
    }

    /**
     * Both players roll a die, 1000€ are added to the player's balance with the max roll
     * <p>Precondition: <p>Player parameter is not null</p>
     * </p>
     * <p>Postcondition:<p>Player's task is done at the end of the method call</p>
     * </p> 
     */
    @Override
    public void performAction(Player player) 
    {
        if (player == null)
            throw new NullPointerException("player is null");   
        
        player1 = player;
        player2 = GetController().GetBoard().OtherPlayer(player);
        
        new Thread(this).start();
    }

    /**
     * @return <p>Returns info about the player's task</p>
     */
    public String toString()
    {
        return "Both players roll dice";
    }

    @Override
    public void run() 
    {
        int roll1 = 0, roll2 = 0;
        player1.SetRoll(0);
        while (player1.GetRoll() == 0)
        {
            try 
            {
                Thread.sleep(100);
            } catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
            roll1 = player1.GetRoll();
        }
        player2.SetRoll(0);
        while (player2.GetRoll() == 0)
        {
            try 
            {
                Thread.sleep(100);
            } catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
            roll2 = player2.GetRoll();
        }

        
        String winner = "Player"+player1.GetId();
        if (roll2 > roll1)
        {
            player2.SetMoney(player2.GetMoney() + 1000);
            winner = "Player"+player2.GetId();
        }
        else
            player1.SetMoney(player1.GetMoney() + 1000);
        
        String message = "Player"+player1.GetId()+" rolled "+ roll1 +
                         "\nPlayer"+player2.GetId()+" rolled "+ roll2 +
                         "\nWinner: "+winner+"\n1000€ have been added to your account";
        new OptionDialog("RadioContest", message, "Ok", null, GetIcon());
        player1.SetTaskDone(true); 

        if (GetController().GetUI() != null)
            GetController().GetUI().UpdateUI();
    }
    
}
