package Model.CardPositions;

import Controller.Controller;
import Model.Player;
import Model.Cards.BillCard;
import Model.Cards.MailCard;
import View.OptionDialog;

public class PayDayPosition extends CardPosition
{

    /**
     * <p>Precondition: <p>controller not null</p></p>
     * @param imageName
     * @param controller
     */
    public PayDayPosition(String imageName, Controller controller) 
    {
		super(imageName, controller);
	}


    /**
     * This method: <p>a) adds 3500€ to the player's balance</p>
     *              <p>b) pays all player's bill cards and puts the back in the deck</p>
     *              <p>c) pays 10% of the player's loans</p>
     *              <p>d) the player may pay the rest of his loans</p>
     *              <p>e) the player must dispose all his cards and cannot play again if the last month is played</p>
     *              <p>f) the player goes back to the start position if the last month is not played</p>
     * 
     * <p>Preconditions: <p>a) Player parameter is not null</p>
     *                   <p>b) if player's balance is less than or equal to 0 he gets a loan</p>
     *                   <p>c) the loan paid by the player must be a multiple of 1000</p>
     * <\p>
     * <p>Postcondition:<p>Player's task is done at the end of the method call</p>
     * </p> 
     */
	@Override
    public void performAction(Player player) 
    {
        if (player == null)
            throw new NullPointerException("player is null");   

        //a) get paid 3500
        player.SetMoney(player.GetMoney() + 3500);
        new OptionDialog("Payday", "You got paid 3500€", "Ok", null, GetIcon());

        //b) pay bills
        for (MailCard card : player.GetMailCards())
        {
            ((BillCard)card).Action(GetController().GetBoard(), player);
            player.GetMailCards().remove(card);
            new OptionDialog("Bill", "You paid this bill", "Ok", null, card.GetImage());
            GetController().GetBoard().mailCards.add(0, card);
        }

        //c) pay 10% of loans
        if (player.GetLoans() > 0)
        {
            double pay = (player.GetLoans() * 10)/100;
            player.Pay(pay);
            player.SetLoans(player.GetLoans() - pay);
            new OptionDialog("Loans", "You paid 10%of your loans", "Ok", null, GetIcon());
        }

        //d) pay the rest of loans
        if (player.GetLoans() > 0)
        {
            OptionDialog op = new OptionDialog("Loans", "Do you wish to pay the rest of your loans?", "Yes", "No", GetIcon());
            if (op.GetOption() == 1)
            {
                double loans = player.GetLoans();
                player.SetLoans(0);
                player.Pay(loans);
            }
        }


        GetController().GetPlayerEndReached()[GetController().GetTurn()-1]--;
        GetController().SetGameDuration();

        if (GetController().HasFinished(GetController().GetTurn())) // player finished
        {
            //e) dispose cards
            for (int i = 0; i < player.GetDealCards().size(); i++)
            {
                player.GetDealCards().remove();
            }
            for (int i = 0; i < player.GetMailCards().size(); i++)
            {
                player.GetMailCards().remove();
            }
            new OptionDialog("Finished", "You finished!\nYour cards have been removed", "Ok", null, GetIcon());
            //GetController().SetNextTurn();
        }
        else
        {
            //f) reset position
            player.SetPosition(0);
        }

        if (GetController().IsGameOver())
        {
            new OptionDialog("Winner", GetController().GetWinner(), "Ok", null, GetIcon());
        }
        player.SetTaskDone(true);
    }
    
    /**
     * @return <p>Returns info about the player's task</p>
     */
    public String toString()
    {
        return "PayDay";
    }
}
