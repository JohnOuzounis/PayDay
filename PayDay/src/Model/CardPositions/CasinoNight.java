package Model.CardPositions;

import Controller.Controller;
import Model.Player;
import View.OptionDialog;

public class CasinoNight extends CardPosition
{

    /**
     * <p>Precondition: <p>controller not null</p></p>
     * @param imageName
     * @param controller
     */
    public CasinoNight(String imageName, Controller controller) 
    {
        super(imageName, controller);
    }


    /**
     * This method adds 500€ to the player's balance if he rolled an even number
     * and removes 500€ otherwise.
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
            
        String message = "You rolled: " + player.GetRoll() + "\n";
        
        if (player.GetRoll() % 2 == 0)
        {
            player.SetMoney(player.GetMoney() + 500);
            message += "Congrats you won!!! 500 euro have been added to your balance";
        }
        else
        {
            player.Pay(500);
            message += "Oops you lost!!! You gave 500 euro to the jackpot";
        }
        new OptionDialog("Casino Night", message, "ok", null, GetIcon());
        player.SetTaskDone(true);
    }

    /**
     * @return <p>Returns info about the player's task</p>
     */
    public String toString()
    {
        return "Roll: Odd * pay to Jackpot, Even * collect";
    }
    
}
