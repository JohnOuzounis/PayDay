package Model.Cards;

import Model.Player;
import Model.Board;

public class BillCard extends MailCard
{

    /**
     * <p>Precondition: icon not null</p>
     * @param type
     * @param message
     * @param choise
     * @param euro
     * @param icon
     */
    public BillCard(String type, String message, String choise, double euro, String icon) 
    {
        super(type, message, choise, euro, icon);
    }

    /**
     * Removes a certain amount of money from the player
     * <p>The player may need a loan</p>
     * <p>Precondition: <p>Player and Board parameter are not null</p>
     * </p>
     */
    @Override
    public void Action(Board board, Player player)
    {
        if (player == null || board == null)
            throw new NullPointerException("Board or player is null");
            
        double bills = player.GetBills();  
        double euro = this.GetEuro();

        // pay bill
        player.Pay(euro);
        player.SetBills(bills - euro);
    }  
}
