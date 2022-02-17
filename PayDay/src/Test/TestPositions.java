package Test;

import Model.CardPositions.*;
import Model.Cards.BillCard;
import View.PlayerPanel;
import Model.*;

import javax.swing.JFrame;
import java.awt.*;

import org.junit.Test;

import Controller.Controller;


public class TestPositions 
{
    @Test
	public void testCasinoNight()
	{
		CasinoNight casinoNight = new CasinoNight("CasinoNight.png",new Controller(null));
        Player player = new Player();
        
        for (int i = 0; i < 10; i++)
        {
            player.RollDice();
            System.out.println("Player money before: " + player.GetMoney());
            System.out.println("Player loan before: " + player.GetLoans());
            casinoNight.performAction(player);
            System.out.println("Player money after: " + player.GetMoney());
            System.out.println("Player loan after: " + player.GetLoans());
            System.out.println("\n\n");
        }
        System.exit(0);
	}  
    
    @Test
    public void testYardSale()
    {
        CardPosition position = null;
        Controller ctrl = new Controller(null);
        Player player = ctrl.GetBoard().players[0];

        JFrame frame = new JFrame();
        PlayerPanel panel = new PlayerPanel(player, ctrl);

        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        position = new YardSale("Yardsale.png", ctrl);
        
        position.performAction(player);
        while(frame.isActive());
    }

    @Test
    public void testTwoMail()
    {
        CardPosition position = null;
        Controller ctrl = new Controller(null);
        Player player = ctrl.GetBoard().players[0];

        position = new TwoMailPosition("TwoMail.png", ctrl);
        
        System.out.println(player.GetMoney());
        position.performAction(player);
        System.out.println(player.GetMoney());
    }

    @Test
    public void testSweepstakes()
    {
        CardPosition position = null;
        Controller ctrl = new Controller(null);
        Player player = ctrl.GetBoard().players[ctrl.GetTurn()-1];

        JFrame frame = new JFrame();
        PlayerPanel panel = new PlayerPanel(player, ctrl);

        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        
        position = new Sweepstakes("Sweepstakes.png", ctrl);
        position.performAction(player);
        while(frame.isActive());
    }

    @Test
    public void testRadioContest()
    {
        CardPosition position = null;
        Controller ctrl = new Controller(null);
        Player player = ctrl.GetBoard().players[0];

        JFrame frame = new JFrame();
        PlayerPanel panel = new PlayerPanel(player, ctrl);
        PlayerPanel panel2 = new PlayerPanel(ctrl.GetBoard().OtherPlayer(player), ctrl);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.WEST);
        frame.add(panel2, BorderLayout.EAST);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        position = new RadioContest("RadioContest.png", ctrl);
        
        position.performAction(player);
        while(frame.isActive());
    }

    @Test
    public void testPayDay()
    {
        CardPosition position = null;
        Controller ctrl = new Controller(null);
        Player player = ctrl.GetBoard().players[0];

        player.AddMailCard(new BillCard("", "Test", "choise", 500, "enfia.jpg"));
        player.SetBills(500);
        
        player.SetLoans(4000);
        
        position = new PayDayPosition("PayDay.png", ctrl);
        
        System.out.println(player.GetMoney()+ " " + player.GetBills() + " " + player.GetLoans());
        position.performAction(player);
        System.out.println(player.GetMoney()+ " " + player.GetBills() + " " + player.GetLoans());
    }

    @Test
    public void testOneMail()
    {
        CardPosition position = null;
        Controller ctrl = new Controller(null);
        Player player = ctrl.GetBoard().players[0];
        
        position = new OneMailPosition("OneMail.png", ctrl);
        
        System.out.println(player.GetMoney()+ " " + player.GetBills() + " " + player.GetLoans());
        position.performAction(player);
        System.out.println(player.GetMoney()+ " " + player.GetBills() + " " + player.GetLoans());
    }

    @Test
    public void testLottery()
    {
        CardPosition position = null;
        Controller ctrl = new Controller(null);
        Player player = ctrl.GetBoard().players[0];
        
        position = new Lottery("Lottery.png", ctrl);
        
        System.out.println(player.GetMoney() + " " + ctrl.GetBoard().OtherPlayer(player).GetMoney());
        position.performAction(player);
        System.out.println(player.GetMoney() + " " + ctrl.GetBoard().OtherPlayer(player).GetMoney());
    }

    @Test
    public void testJackpot()
    {
        CardPosition position = null;
        Controller ctrl = new Controller(null);
        Player player = ctrl.GetBoard().players[0];
        
        position = new JackpotPosition("Jackpot.png", ctrl);
        ((JackpotPosition)position).SetJackpot(3054);

        System.out.println(player.GetMoney());
        position.performAction(player);
        System.out.println(player.GetMoney());
    }
    
    @Test
    public void testDeal()
    {
        CardPosition position = null;
        Controller ctrl = new Controller(null);
        Player player = ctrl.GetBoard().players[0];
        
        position = new DealPosition("Deal.png", ctrl);
        
        System.out.println(player.GetMoney() + " " + player.GetDealCards().size());
        position.performAction(player);
        System.out.println(player.GetMoney() + " " + player.GetDealCards().size());
    }

    @Test
    public void testBuyer()
    {
        CardPosition position = null;
        Controller ctrl = new Controller(null);
        Player player = ctrl.GetBoard().players[0];
        
        position = new BuyerPosition("Buyer.png", ctrl);
        
        System.out.println(player.GetMoney() + " " + player.GetDealCards().size());
        (new DealPosition("Deal.png", ctrl)).performAction(player);
        System.out.println(player.GetMoney() + " " + player.GetDealCards().size());
        position.performAction(player);
        System.out.println(player.GetMoney() + " " + player.GetDealCards().size());
    }
}
