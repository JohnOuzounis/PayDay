package Test;

import Model.Cards.*;
import Model.*;
import Controller.*;

import org.junit.Test;

public class MyTest 
{

	@Test
	public void testController()
	{
		Controller controller = null;	
		try 
		{
			controller = new Controller(null);
		} catch (Exception e) 
		{
			e.printStackTrace();
			System.exit(0);
		}
        
        for (Card card : controller.GetBoard().mailCards)
        {
            if (card == null)
                continue;
            System.out.println(card.GetType() + " " + card.GetImage());
        }
        for (Card card : controller.GetBoard().dealCards)
        {
            if (card == null)
                continue;
            System.out.println(card.GetType() + " " + card.GetImage());
        }
	}

	@Test
	public void testControllerInit()
	{
		try
		{
			Controller ctrl = new Controller(null);
			for (int i = 0; i < 1000; i++)
			{
				System.out.println(ctrl.GetTurn() + "  " + ctrl.GetGameDuration());
				ctrl = new Controller(null);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testPlayerPay()
	{
		Player player = new Player();

		// start with 0
		System.out.println(player.GetMoney());
		player.Pay(5420);
		System.err.println(player.GetMoney());
		System.err.println(player.GetLoans());

		// start with some pay less
		System.out.println(player.GetMoney());
		player.Pay(200);
		System.err.println(player.GetMoney());
		System.err.println(player.GetLoans());

		// start with some pay more
		System.out.println(player.GetMoney());
		player.Pay(10000);
		System.err.println(player.GetMoney());
		System.err.println(player.GetLoans());
	}

	@Test
	public void testOtherPlayer()
	{
		Board board = new Board();

		System.out.println(board.OtherPlayer(board.players[0]).GetId());
		System.out.println(board.OtherPlayer(board.players[1]).GetId());
	}

	@Test
	public void testPlayerRollDice()
	{
		Player player = new Player();
		
		for (int i = 0; i < 1000; i++)
		{
			player.RollDice();
			System.out.println(player.GetRoll());
		}
	}
}
