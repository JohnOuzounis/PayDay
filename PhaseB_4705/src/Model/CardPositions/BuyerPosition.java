package Model.CardPositions;

import Controller.Controller;
import Model.Player;
import Model.Cards.DealCard;
import View.OptionDialog;

public class BuyerPosition extends CardPosition
{

    /**
     * <p>Precondition: <p>controller not null</p></p>
     * @param imageName
     * @param controller
     */
    public BuyerPosition(String imageName, Controller controller) 
    {
        super(imageName, controller);
    }


    /**
     * This method displays the player's deal cards for him to choose one
     * and then performs that card's action
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

        OptionDialog op;
        if (player.GetDealCards().size() == 0)
        {
            op = new OptionDialog("Buyer", "You have no items to sell", "Ok", null, this.GetIcon());
            player.SetTaskDone(true);
            return;
        }
        
        for (int option = 0; option != 1;)
        {
            for (DealCard dealCard : player.GetDealCards())
            {
                String message = dealCard.GetMessage() + "\nCost: " + dealCard.GetCost() + "\nValue: " + dealCard.GetValue();
                op = new OptionDialog("Buyer", message, "Sell", "Next", dealCard.GetImage());
                if ((option = op.GetOption()) == 1)
                {
                    dealCard.Action(this.GetController().GetBoard(), player);
                    break;
                }
            }
        }
        player.SetTaskDone(true);
    }

    /**
     * @return <p>Returns info about the player's task</p>
     */
    public String toString()
    {
        return "Sell an item";
    }
    
}
