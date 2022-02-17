package Model.Cards;

import Model.Board;
import Model.Player;
import Model.CardPositions.BuyerPosition;
import Model.CardPositions.DealPosition;
import View.OptionDialog;

public class MoveToBuyerDealCard extends MailCard
{

    /**
     * <p>Precondition: icon not null</p>
     * @param type
     * @param message
     * @param choise
     * @param euro
     * @param icon
     */
    public MoveToBuyerDealCard(String type, String message, String choise, double euro, String icon) 
    {
        super(type, message, choise, euro, icon);
    }


    /**
     * Changes the player's position to the nearest deal or buyers position
     * <p>Precondition: <p>a) A deal or buyer position exists before the end of the month</p>
     *                  <p>b)Player and Board parameter are not null</p>
     * </p>
     */
    @Override
    public void Action(Board board, Player player) 
    {
        if (player == null || board == null)
            throw new NullPointerException("Board or player is null");
        
        int moveTo = 0;
        for (int i = player.GetPosition(); i < board.tiles.length; i++)
        {
            if ((board.tiles[i].GetPosition() instanceof DealPosition) || (board.tiles[i].GetPosition() instanceof BuyerPosition))
            {
                moveTo = i;
                break;
            }
        }
        if (moveTo != 0)
        {
            player.SetPosition(moveTo+1);
            board.tiles[player.GetPosition()-1].GetPosition().performAction(player);
        }
        else // else moveTo == 0 which means no suitable positions exist before the end
            new OptionDialog("Move to Deal Buyer", "There are no buyers or deals until the end of the month", "Ok", null, this.GetImage());
    }
    
}
