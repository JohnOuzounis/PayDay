package Model.CardPositions;

import Controller.Controller;
import Model.Player;
import Model.Cards.BillCard;
import Model.Cards.MailCard;
import View.OptionDialog;

public class OneMailPosition extends MailPosition
{

    /**
     * <p>Precondition: <p>controller not null</p></p>
     * @param imageName
     * @param controller
     */
    public OneMailPosition(String imageName, Controller controller) 
    {
        super(imageName,controller);
    }

    /**
     * Draws one mail card from the mail cards deck and displays it in a new window,
     * then it performs the card's action
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
            
        int size = GetController().GetBoard().mailCards.size();
        MailCard card = GetController().GetBoard().mailCards.remove(size-1);
        
        new OptionDialog(card.GetType(), card.GetMessage(), card.GetChoice(), null, card.GetImage());
        
        if (card instanceof BillCard)
        {
            player.AddMailCard(card);
            player.SetBills(player.GetBills() + card.GetEuro());
        }
        else
        {
            card.Action(GetController().GetBoard(), player);
            GetController().GetBoard().mailCards.add(0, card);            
        }
        
        player.SetTaskDone(true);
    }

    /**
     * @return <p>Returns info about the player's task</p>
     */
    public String toString()
    {
        return "Draw one mail card";
    }  
}
