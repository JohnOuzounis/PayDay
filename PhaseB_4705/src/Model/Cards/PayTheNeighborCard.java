package Model.Cards;

import Model.Board;
import Model.Player;

public class PayTheNeighborCard extends MailCard
{

    /**
     * <p>Precondition: icon not null</p>
     * @param type
     * @param message
     * @param choise
     * @param euro
     * @param icon
     */
    public PayTheNeighborCard(String type, String message, String choise, double euro, String icon) 
    {
        super(type, message, choise, euro, icon);
    }


    /**
     * Removes  a certain amount of money from one player
     * and add it to the balance of the other
     * <p>The player giving the money may need a loan</p>
     * 
     * <p>Precondition: <p>Player and Board parameter are not null</p>
     * </p>
     * 
     * <p>Postcondition: <p>a) Both players have positive balance after the exchange</p>
     * </p>
     * 
     */
    @Override
    public void Action(Board board, Player player) 
    {
        if (player == null || board == null)
            throw new NullPointerException("Board or player is null");
        Player other = board.OtherPlayer(player);
        
        player.Pay(this.GetEuro());
        other.SetMoney(other.GetMoney() + this.GetEuro());
    }   
}
