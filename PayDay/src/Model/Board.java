package Model;

import java.util.LinkedList;

import Model.Cards.DealCard;
import Model.Cards.MailCard;

public class Board 
{
    private static final int MAX_TILES = 31;
    private static final int PLAYERS = 2;

    public Tile[] tiles;
    public Tile start;
    public Tile jackpot;
    public LinkedList<MailCard> mailCards;
    public LinkedList<DealCard> dealCards;
    public Player[] players;

    /**
     * Allocates memory for the players, tiles and card decks
     */
    public Board()
    {
        players = new Player[PLAYERS];
        for (int i = 0; i < players.length; i++)
            players[i] = new Player();


        jackpot = new Tile();
        start = new Tile();
        tiles = new Tile[MAX_TILES];
        for (int i = 0; i < tiles.length; i++)
            tiles[i] = new Tile();
        
        mailCards = new LinkedList<MailCard>();     
        dealCards = new LinkedList<DealCard>();
    }

    /**
     * <p>Precondition: <p>player parameter not null</p></p>
     * @param player
     * @return <p>Returns a player that is not equal to the player parameter</p>
     */
    public Player OtherPlayer(Player player)
    {
        if (player == null)
            throw new NullPointerException("player null");

        return (player.equals(players[0])) ? players[1] : players[0];
    }
}
