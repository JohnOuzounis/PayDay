package Model.CardPositions;

import Controller.Controller;
import Model.Player;

public class StartPosition extends CardPosition
{
    
    /**
     * <p>Precondition: <p>controller not null</p></p>
     * @param imageName
     * @param controller
     */
    public StartPosition(String imageName, Controller controller) 
    {
        super(imageName, controller);
    }

    @Override
    public void performAction(Player player) 
    {        
    }

    /**
     * @return <p>Returns info about the player's task</p>
     */
    public String toString()
    {
        return "start";
    }
    
}
