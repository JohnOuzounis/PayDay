package Model.CardPositions;

import Controller.Controller;
import Model.Player;
import Model.Cards.DealCard;
import View.OptionDialog;

public class DealPosition extends CardPosition
{

    /**
     * <p>Precondition: <p>controller not null</p></p>
     * @param imageName
     * @param controller
     */
    public DealPosition(String imageName, Controller controller) 
    {
        super(imageName,controller);
    }

    /**
     * Draws a card from the deal cards deck and displays it in a new window.
     * The player can then either buy or discard the card
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
            
        int size = GetController().GetBoard().dealCards.size();
        DealCard card = GetController().GetBoard().dealCards.remove(size-1);
        OptionDialog a = new OptionDialog(card.GetType(), card.GetMessage() + "\nCost: "+card.GetCost() + "\nValue: "+card.GetValue(), card.GetChoice(1), card.GetChoice(2), card.GetImage());
        
        if (a.GetOption() == 1)
        {
            player.AddDealCard(card);
            player.Pay(card.GetCost());
        }
        else
            GetController().GetBoard().dealCards.add(0, card);

        player.SetTaskDone(true);
    }

    /**
     * @return <p>Returns info about the player's task</p>
     */
    public String toString()
    {
        return "Draw a deal card";
    }
    
}
