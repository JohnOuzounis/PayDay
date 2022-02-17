package Model.Cards;

import Model.Board;
import Model.Player;

public class AdvertisementCard extends MailCard
{

    /**
     * <p>Precondition: icon not null</p>
     * @param type
     * @param message
     * @param choise
     * @param euro
     * @param icon
     */
    public AdvertisementCard(String type, String message, String choise, double euro, String icon) 
    {
        super(type, message, choise, euro, icon);
    }


    /**
     * This method adds a certain amount of money to the player's balance
     * <p>Precondition: <p>Player and Board parameter are not null</p>
     * </p>
     */
    @Override
    public void Action(Board board, Player player) 
    {
        if (player == null || board == null)
        throw new NullPointerException("Board or player is null");
        
        player.SetMoney(player.GetMoney() + this.GetEuro());
    }
}
