package Model.CardPositions;

import Controller.Controller;
import Model.Player;
import View.OptionDialog;

public class Sweepstakes extends CardPosition implements Runnable
{
    private Player player;

    public Sweepstakes(String imageName, Controller controller)
    {
        super(imageName, controller);
    }

    /**
     * Allows the player to roll a die and adds 1000*X€ to his balance
     * <p>Precondition: <p>Player parameter is not null</p>
     * </p>
     * <p>Postcondition:<p>Player's task is done at the end of the method call</p>
     * </p> 
     */
    @Override
    public void performAction(Player player) 
    {
        if (player == null)
            throw new NullPointerException("Player is null");

        this.player = player;
        new Thread(this).start();    
    }

    /**
     * @return <p>Returns info about the player's task</p>
     */
    public String toString()
    {
        return "Roll dice";
    }

    @Override
    public void run() 
    {
        player.SetRoll(0);
        int roll = 0;
        while (player.GetRoll() == 0)
        {
            try 
            {
                Thread.sleep(100);    
            } catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
            roll = player.GetRoll();
        }
        player.SetMoney(player.GetMoney() + 1000*roll);
        new OptionDialog("Sweeptakes", "Your roll: "+roll+"\nYou won " + 1000*roll+"€!!", "Ok", null, GetIcon());
        player.SetTaskDone(true);

        if (GetController().GetUI() != null)
            GetController().GetUI().UpdateUI();
    }   
}
