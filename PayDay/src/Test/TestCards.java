package Test;

import org.junit.Test;

import Controller.Controller;
import Model.Cards.*;
import Model.*;
import Model.CardPositions.BuyerPosition;
import Model.CardPositions.DealPosition;
import Model.CardPositions.JackpotPosition;

public class TestCards 
{
    @Test
    public void testBillCard()
    {
        BillCard billCard = new BillCard("", "", "", 500, "");
        Board board = new Board();
        Player player = new Player();
        player.SetBills(billCard.GetEuro());

        System.out.println(player.GetMoney() + " " + player.GetLoans() + " " + player.GetBills());
        billCard.Action(board, player);
        System.out.println(player.GetMoney() + " " + player.GetLoans() + " " + player.GetBills());
    }

    @Test
    public void testMadMoneyCard()
    {
        Board board = new Board();
        Player player = board.players[0];
        MailCard card = null;

        card = new MadMoneyCard("", "", "", 500, "");
        System.out.println(player.GetMoney());
        card.Action(board, player);
        System.out.println(player.GetMoney());
    }

    @Test
    public void testAdvertisementCard()
    {
        Board board = new Board();
        Player player = board.players[0];
        MailCard card = null;

        card = new AdvertisementCard("", "", "", 500, "");
        System.out.println(player.GetMoney());
        card.Action(board, player);
        System.out.println(player.GetMoney());
    }

    @Test
    public void testCharityCard()
    {
        Board board = new Board();
        Player player = board.players[0];
        MailCard card = null;
        
        board.jackpot.SetCardPosition(new JackpotPosition("Jackpot.png", new Controller(null)));
        
        card = new CharityCard("", "", "", 500, "");
        System.out.println(player.GetMoney() + " " + player.GetLoans());
        card.Action(board, player);
        System.out.println(player.GetMoney() + " " + player.GetLoans());
    }

    @Test
    public void testDealCard()
    {
        Board board = new Board();
        Player player = board.players[0];
        DealCard card = new DealCard("", "", 400, 500, "", "", "");

        System.out.println(player.GetMoney() + " " + player.GetLoans());
        card.Action(board, player);
        System.out.println(player.GetMoney() + " " + player.GetLoans());
    }

    @Test
    public void testMoveCard()
    {
        Board board = new Board();
        Player player = board.players[0];
        MailCard card = null;
        
        board.tiles[8].SetCardPosition(new BuyerPosition("", new Controller(null)));
        
        card = new MoveToBuyerDealCard("", "", "", 500, "");
        System.out.println(player.GetPosition());
        card.Action(board, player);
        System.out.println(player.GetPosition());


        board.tiles[13].SetCardPosition(new DealPosition("", new Controller(null)));

        card = new MoveToBuyerDealCard("", "", "", 500, "");
        System.out.println(player.GetPosition());
        card.Action(board, player);
        System.out.println(player.GetPosition());

        System.out.println(player.GetPosition());
        card.Action(board, player);
        System.out.println(player.GetPosition());
    }

    @Test
    public void testPayNeighborCard()
    {
        Board board = new Board();
        Player player = board.players[0];
        MailCard card = new PayTheNeighborCard("", "", "", 500, "");

        System.out.println(player.GetMoney() + " " + player.GetLoans() + " || " + board.OtherPlayer(player).GetMoney());
        card.Action(board, player);
        System.out.println(player.GetMoney() + " " + player.GetLoans() + " || " + board.OtherPlayer(player).GetMoney());
    }
}
