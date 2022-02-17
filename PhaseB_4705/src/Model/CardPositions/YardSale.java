package Model.CardPositions;

import Controller.Controller;
import Model.Player;
import Model.Cards.DealCard;
import View.OptionDialog;

public class YardSale extends CardPosition implements Runnable
{
    private Player player;

    /**
     * <p>Precondition: <p>controller not null</p></p>
     * @param imageName
     * @param controller
     */
    public YardSale(String imageName, Controller controller) 
    {
        super(imageName, controller);
    }


    /**
     * This method allows the player to roll his dice,
     * removes 100*X from his balance and adds a deal card to his collection
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
        
        this.player = player;
        new Thread(this).start();
    }

    /**
     * @return <p>Returns info about the player's task</p>
     */
    public String toString()
    {
        return "Roll dice, pay 100*X and get a deal card";
    }


    @Override
    public void run() 
    {
        player.SetRoll(0);
        int roll = 0;
        while (player.GetRoll() == 0)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            roll = player.GetRoll();
        }
        player.Pay(100 * roll);
        new OptionDialog("Yardsale", "Your roll:"+roll+"\nYou paid: "+100*roll, "Ok", null, GetIcon());
        
        int size = GetController().GetBoard().dealCards.size();
        DealCard card = GetController().GetBoard().dealCards.remove(size -1);
        player.AddDealCard(card);

        String message = "Your card:\n"+card.GetMessage()+"\nCost: "+card.GetCost()+"\nValue: "+card.GetValue();
        new OptionDialog(card.GetType(), message, "Ok", null, card.GetImage());

        player.SetTaskDone(true);  
        if (GetController().GetUI() != null)
            GetController().GetUI().UpdateUI(); 
    }
    
}
