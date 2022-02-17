package Model.CardPositions;
import Controller.Controller;
import Model.Player;
import View.OptionDialog;

public class JackpotPosition extends CardPosition
{
    double jackpot;

    /**
    * <p>Precondition: <p>controller not null</p></p>
    * @param imageName
    * @param controller
    */
    public JackpotPosition(String imageName, Controller controller)
    {
        super(imageName, controller);
    }

    /**
     * Gives an amount of money to the player
     * <p>Precondition:<p>a)Player parameter is not null</p>
     * </p>
     * <p>Postcondition:<p>Player's task is done at the end of the method call</p>
     * </p> 
     */
    @Override
    public void performAction(Player player) 
    {
        if (player == null)
            throw new NullPointerException("player is null");   
            
        player.SetMoney(player.GetMoney() + GetJackpot());
        new OptionDialog("Jackpot", "You won " + GetJackpot(), "Ok", null, GetIcon());
        SetJackpot(0);
    }

    /**
     * @return Returns the amount of money in jackpot
     */
    public double GetJackpot()
    {
        return jackpot;
    }

    /**
     * Sets the jackpot money
     * @param jackpot
     */
    public void SetJackpot(double jackpot)
    {
        this.jackpot = jackpot;
    }
    
}
