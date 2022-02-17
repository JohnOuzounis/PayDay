package Model.Cards;

import Model.Board;
import Model.Player;
import Model.CardPositions.JackpotPosition;

public class CharityCard extends MailCard
{

    /**
     * <p>Precondition: icon not null</p>
     * @param type
     * @param message
     * @param choise
     * @param euro
     * @param icon
     */
    public CharityCard(String type, String message, String choise, double euro, String icon) 
    {
        super(type, message, choise, euro, icon);
    }


     /**
     * Removes  a certain amount of money from this player
     * and adds it to the jackpot
     * <p>The player may need a loan</p>
     * <p>Precondition: <p>Player and Board parameter are not null</p>
     * </p>
     */
    @Override
    public void Action(Board board, Player player) 
    {
        if (player == null || board == null)
            throw new NullPointerException("Board or player is null");

        if (!(board.jackpot.GetPosition() instanceof JackpotPosition))
            return;

        //add to jackpot
        JackpotPosition jackpot = (JackpotPosition)board.jackpot.GetPosition();
        jackpot.SetJackpot(jackpot.GetJackpot() + this.GetEuro());

        //remove from player
        player.Pay(this.GetEuro());
    }
    
}
