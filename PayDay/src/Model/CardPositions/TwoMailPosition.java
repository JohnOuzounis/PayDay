package Model.CardPositions;

import Controller.Controller;
import Model.Player;

public class TwoMailPosition extends MailPosition
{

    /**
     * <p>Precondition: <p>controller not null</p></p>
     * @param imageName
     * @param controller
     */
    public TwoMailPosition(String imageName, Controller controller) 
    {
        super(imageName,controller);
    }


    /**
     * Draws two mail cards from the mail cards deck and displays them,
     * it then performs the card's action
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
            
        OneMailPosition position = new OneMailPosition(null, GetController());

        position.performAction(player);
        player.SetTaskDone(false);  // the first call of performAction may set the taskisDone to true but we are not finished yet
        position.performAction(player);
    }

    /**
     * @return <p>Returns info about the player's task</p>
     */
    public String toString()
    {
        return "Draw two mail cards";
    }
    
}
