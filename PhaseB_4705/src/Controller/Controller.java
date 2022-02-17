package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;


import Model.Board;
import Model.Player;
import Model.CardPositions.*;
import Model.Cards.*;
import View.GraphicUI;

public class Controller 
{
    private int turn;
    private int duration;
    
    private GraphicUI ui;
    private Board board;

    private int[] endReached; // counter for the times the player pass from the end

    /**
     * Sets up the game and initialises the players and cards
     * @param ui
     */
    public Controller(GraphicUI ui)
    {
        this.ui = ui;
        board = new Board();
        endReached = new int[2];
        
        //init game duration
        Init_Duration();
        endReached[0] = GetGameDuration();
        endReached[1] = GetGameDuration();
        
        //init turn
        Init_Turn();

        //init positions
        Init_Tiles();
        
        //shuffle cards
        Init_DealCards();
        Init_MailCards();

        //init players
        Init_Player(board.players[0]);
        Init_Player(board.players[1]);

        // init task
        SetTask("Start");
    }

    /**
     * This function initialises the player's money bills and loans and his position
     * <p>Precondition: <p>player not null</p></p>
     * @param player
     */
    private void Init_Player(Player player)
    {
        player.SetMoney(3500);
        player.SetBills(0);
        player.SetLoans(0);
        player.SetPosition(0);
        player.SetPawnImageName("pawn"+player.GetId()+".png");
    }

    /**
     * This method initialises the positions of each tile on the board
     */
    private void Init_Tiles()
    {
         int[] numOfType = {8, 6, 5,
                            3, 2, 2,
                            2, 2};
         String[] type = {"MailPosition","BuyerPosition", "DealPosition",
                          "Lottery", "Sweeptakes", "RadioContest",
                          "CasinoNight", "YardSale"};

        java.util.Random rand = new java.util.Random();
        int randomStartDay = rand.nextInt(7)+1;
        boolean insertOne = true;

        for (int i = 0; i < GetBoard().tiles.length-1; i++)
        {
            int index = rand.nextInt(8);
            while (numOfType[index] <= 0)
                index = rand.nextInt(8);

            CardPosition position = null;
            if(numOfType[index] > 0)
            {
                if (type[index].equals("MailPosition"))
                {
                    if(insertOne)
                        position = new OneMailPosition("OneMail.png",this);
                    else
                        position = new TwoMailPosition("TwoMail.png",this);
                    insertOne = !insertOne;
                }
                else if (type[index].equals("DealPosition"))
                {
                    position = new DealPosition("Deal.png",this);
                }
                else if (type[index].equals("BuyerPosition"))
                {
                    position = new BuyerPosition("Buyer.png", this);
                }
                else if (type[index].equals("Lottery"))
                {
                    position = new Lottery("Lottery.png", this);
                }
                else if (type[index].equals("Sweeptakes"))
                {
                    position = new Sweepstakes("Sweepstakes.png", this);
                }
                else if (type[index].equals("RadioContest"))
                {
                    position = new RadioContest("RadioContest.png", this);
                }
                else if (type[index].equals("CasinoNight"))
                {
                    position = new CasinoNight("CasinoNight.png", this);
                }
                else if (type[index].equals("YardSale"))
                {
                    position = new YardSale("Yardsale.png", this);
                }
                GetBoard().tiles[i].SetCardPosition(position);
                GetBoard().tiles[i].GetPosition().SetPosition(i + 1);
                GetBoard().tiles[i].GetPosition().SetDay((i+randomStartDay)%7 + 1);

                numOfType[index]--;
            }
        }
        GetBoard().tiles[GetBoard().tiles.length-1].SetCardPosition(new PayDayPosition("Payday.png", this));
        GetBoard().tiles[GetBoard().tiles.length-1].GetPosition().SetDay((31+randomStartDay)%7 + 1);
        GetBoard().tiles[GetBoard().tiles.length-1].GetPosition().SetPosition(31);

        GetBoard().jackpot.SetCardPosition(new JackpotPosition("Jackpot.png", this));
        GetBoard().jackpot.GetPosition().SetDay(8);

        GetBoard().start.SetCardPosition(new StartPosition("StartIcon.png", this));
        GetBoard().start.GetPosition().SetDay(0);
    }
   
    /**
     * This method ramdomly initialises the turn to 1 or 2
     * <p>Postcondition:<p>turn is 1 or 2</p></p>
     */
    private void Init_Turn()
    {
        java.util.Random rand = new java.util.Random();
        turn = rand.nextInt(2) + 1;

        assert (turn == 1 || turn == 2);
    }

    /**
     * This method ramdomly initialises the duration to 1, 2 or 3
     * <p>Postcondition:<p>duration is 1, 2 or 3</p></p>
     */
    private void Init_Duration()
    {
        java.util.Random rand = new java.util.Random();

        this.duration = rand.nextInt(3)+1;

        assert (duration >=1 && duration <=3);
    }

    /**
     * This method opens a file and initialises the mail cards
     */
    private void Init_MailCards()
    {
        String source = "src/resources/mailCards_greeklish.csv";
        String[][] mailcards = ReadFile(source);

        for (int i = 0; i < 48; i++)
        {
            MailCard card = null;
            if (mailcards[i][1].equals("Advertisement"))
                card = new AdvertisementCard(mailcards[i][1],
                    mailcards[i][2],
                    mailcards[i][3],
                    Double.parseDouble(mailcards[i][4]),
                    mailcards[i][5]);
            else if (mailcards[i][1].equals("Bill"))
                card = new BillCard(mailcards[i][1],
                    mailcards[i][2],
                    mailcards[i][3],
                    Double.parseDouble(mailcards[i][4]),
                    mailcards[i][5]);
            else if (mailcards[i][1].equals("Charity"))
                card = new CharityCard(mailcards[i][1],
                    mailcards[i][2],
                    mailcards[i][3],
                    Double.parseDouble(mailcards[i][4]),
                    mailcards[i][5]);
            else if (mailcards[i][1].equals("PayTheNeighbor"))
                card = new PayTheNeighborCard(mailcards[i][1],
                    mailcards[i][2],
                    mailcards[i][3],
                    Double.parseDouble(mailcards[i][4]),
                    mailcards[i][5]);
            else if (mailcards[i][1].equals("MadMoney"))
                card = new MadMoneyCard(mailcards[i][1],
                    mailcards[i][2],
                    mailcards[i][3],
                    Double.parseDouble(mailcards[i][4]),
                    mailcards[i][5]);
            else if (mailcards[i][1].equals("MoveToDealBuyer"))
                card = new MoveToBuyerDealCard(mailcards[i][1],
                    mailcards[i][2],
                    mailcards[i][3],
                    Double.parseDouble(mailcards[i][4]),
                    mailcards[i][5]);
            if (card == null)
               throw new NullPointerException("Uninitialised card");
            GetBoard().mailCards.add(card);
        }

        Shuffle(GetBoard().mailCards);
    }

    /**
     * This method opens a file and initialises the deal cards
     */
    private void Init_DealCards()
    {
        String source = "src/resources/dealCards_greeklish.csv";
        String[][] dealcards = ReadFile(source);

        for (int i = 0; dealcards[i][1] != null; i++)
        {
            DealCard card = new DealCard(dealcards[i][1],
                dealcards[i][2],
                Double.parseDouble(dealcards[i][3]),
                Double.parseDouble(dealcards[i][4]),
                dealcards[i][5],
                dealcards[i][6],
                dealcards[i][7]);
            GetBoard().dealCards.add(card);
        }

        Shuffle(GetBoard().dealCards);
    }
    
    /**
     * This method opens the specified file and reads its contents
     * @param fileName : The name of the file to be opened
     * @return <p>Returns an array containing info about cards of type "DealCard" or "MailCard".
     *            The array may contain null positions at the end</p>
     */
    private String[][] ReadFile(String fileName)
    {
        String[][] cards = new String[48][8];
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));

            int index = 0;
            String line = reader.readLine();
            while ((line = reader.readLine()) != null)
            {
                cards[index++] = line.split(",");
            }
        }
        catch (Exception e)
        {
            System.err.println(e);
        }

        return cards;
    }

    
    /**
     * This method randomises the items in a list
     * @param cards : the items to be shuffled
     */
    public void Shuffle(List<?> cards)
    {
        java.util.Collections.shuffle(cards);
    }
    
    /**
     * Moves a player to the designated position, 
     * sets his task
     * <p>Preconditions: <p>a) player != null</p>
     *                   <p>b) newPosition >=0</p>
     * </p>
     * @param player
     * @param newPosition
     */
    public void MovePlayer(Player player, int newPosition)
    {
        if (player == null || newPosition < 0)
            throw new IllegalArgumentException("player null or new position < 0");

        // move player
        int pos = (newPosition >= 31) ? 31 : newPosition;
        player.SetPosition(pos);
        
        //set task
        String task = (pos == 0) ? GetBoard().start.GetPosition().toString() : GetBoard().tiles[pos-1].GetPosition().toString();
        SetTask(task);
    }


    /**
     * @return Returns true if both player have finished
     */
    public boolean IsGameOver()
    {
       return (HasFinished(GetTurn()) && HasFinished(GetNextTurn()));
    }


    /**
     * <p>Precondition: <p>playerTurn is 1 or 2</p></p>
     * @param playerTurn the turn of the player currently playing
     * @return returns true if the player has pass x times from the end
     */
    public boolean HasFinished(int playerTurn)
    {
        if (playerTurn != 1 && playerTurn != 2)
            throw new IllegalArgumentException("turn must be 1 or 2");
        return (GetPlayerEndReached()[playerTurn-1] <= 0);
    }

    /**
     * @return <p>Returns the current turn (1 or 2)</p>
     */
    public int GetTurn()
    {
        return turn;    
    }

    /**
     * @return <p>Returns the next turn</p>
     */
    public int GetNextTurn()
    {
        return (GetTurn() % 2) +1;
    }

    /**
     * Sets the next turn.
     */
    public void SetNextTurn()
    {
        turn = GetNextTurn();
        if (GetPlayerEndReached()[turn-1] <= 0)
        {
            turn = GetNextTurn();
        }
    }

    /**
     * @return <p>Returns the task of the player currently playing</p>
     */
    public String GetTask()
    {
        if (GetBoard().players[GetTurn()-1].IsTaskDone())
            GetBoard().players[GetTurn()-1].SetTask("Done");
            
        return GetBoard().players[GetTurn()-1].GetTask();
    }

    /**
     * Sets the task of the player currently playing
     * @param task
     */
    public void SetTask(String task)
    {
        GetBoard().players[GetTurn()-1].SetTask(task);
        GetBoard().players[GetTurn()-1].SetTaskDone(false);
    }

    /**
     * @return <p>Returns the game duration</p>
     */
    public int GetGameDuration()
    {
        return duration;
    }

    /**
     * Sets the game duration to the minimum amount of times
     * a player has to pass from the end
     */
    public void SetGameDuration()
    {
        this.duration = Math.min(GetPlayerEndReached()[0], GetPlayerEndReached()[1]);
    }

    /**
     * @return Returns a string with both player names and their money
     * along with a message about the winner
     */
    public String GetWinner()
    {
        Player player1 = GetBoard().players[0];
        Player player2 = GetBoard().players[1];

        double money1 = player1.GetMoney() - player1.GetLoans() - player1.GetBills();
        double money2 = player2.GetMoney() - player2.GetLoans() - player2.GetBills();

        String info1 = "Player"+player1.GetId() + " has: " + money1 + "€";
        String info2 = "Player"+player2.GetId() + " has: " + money2 + "€";

        String winner;
        if (money1 > money2)
            winner = "Player"+player1.GetId() + " has won!!";
        else if (money2 > money1)
            winner = "Player"+player2.GetId() + " has won!!";
        else
            winner = "Tie!" + "Nobody won :("; 
        return info1 + "\n" + info2 + "\n" + winner;
    }

    /**
     * @return <p>Returns the controller's board</p>
     */
    public Board GetBoard()
    {
        return board;
    }

    /**
     * @return <p>Returns the controller's UI</p>
     */
    public GraphicUI GetUI()
    {
        return ui;
    }
 

    public int[] GetPlayerEndReached()
    {
        return endReached;
    }

    /**
     * @return <p>Returns information regarding the game duration, player's turn as well as the current task</p>
     */
    public String toString()
    {
        return  "Info Box\n" +
                GetGameDuration() + " Months Left\n" +
                "Turn: " + ((GetTurn() == 1) ? "Player1\n" : "Player2\n") +
                "Task: " + GetTask();
    }
}
