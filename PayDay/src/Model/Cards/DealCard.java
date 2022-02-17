package Model.Cards;

import Model.Board;
import Model.Player;

public class DealCard extends Card
{
    private double cost, value;
    private String choice1, choice2;

    /**
     * <p>Precondition: icon not null</p>
     * @param type
     * @param message
     * @param cost
     * @param value
     * @param icon
     * @param choise1
     * @param choise2
     */
    public DealCard(String type, String message, double cost, double value, String icon, String choise1, String choise2) 
    {
        super(type, message, icon);
        this.cost = cost;
        this.value = value;
        this.choice1 = choise1;
        this.choice2 = choise2;
    }

    /**
     * @param choise
     * @return Returns an option
     */
    public String GetChoice(int choise)
    {
        if (choise == 1)
            return choice1;
        else if (choise == 2)
            return choice2;
        throw new IllegalArgumentException("Choise must be 1 or 2");
    }

    /**
     * @return Returns the cost of the card
     */
    public double GetCost()
    {
        return cost;
    }

    /**
     * @return Returns the value of the card
     */
    public double GetValue()
    {
        return value;
    }


    /**
     * Adds the value of the card to the player's balance
     * <p>Precondition: <p>Player and Board parameter are not null</p>
     * </p>
     */
    @Override
    public void Action(Board board, Player player) 
    {
        if (player == null || board == null)
            throw new NullPointerException("Board or player is null");
        
        player.SetMoney(player.GetMoney() + this.GetValue());
        player.GetDealCards().remove(this);
    }
    
}
