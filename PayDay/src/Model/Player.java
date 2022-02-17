package Model;

import java.util.LinkedList;

import Model.Cards.BillCard;
import Model.Cards.Card;
import Model.Cards.DealCard;
import Model.Cards.MailCard;


public class Player 
{
    private static int players = 0;

    private double money, loans, bills;
    private int position, id, roll;
    
    private String task;
    private boolean isTaskDone = false, hasPlayed = false;
    private String pawnImageName;

    private LinkedList<DealCard> dealcards;
    private LinkedList<MailCard> mailcards;

    /**
     * creates a new player and allocates memory for his card collections
     */
    public Player()
    {
        dealcards = new LinkedList<DealCard>();
        mailcards = new LinkedList<MailCard>();
        SetId(++players);
    }

    /**
     * @return <p>Returns the player's pawn image name</p>
     */
    public String GetPawnImageName()
    {
        return pawnImageName;
    }

    /**
     * @return <p>Returns the player's id</p>
     */
    public int GetId()
    {
        return id;
    }

    /**
     * @return <p>Returns the player's dice roll</p>
     */
    public int GetRoll()
    {
        return roll;
    }

    /**
     * @return <p>Returns true if the player has made a move</p>
     */
    public boolean HasPlayed()
    {
        return hasPlayed;
    }
    /**
     * @return <p>Returns true if the player's task is done</p>
     */
    public boolean IsTaskDone()
    {
        return isTaskDone;
    }

    /**
     * @return <p>Returns the task of the player</p>
     */
    public String GetTask()
    {
        return task;
    }

    /**
     * @return <p>Returns the player's money</p>
     */
    public double GetMoney()
    {
        return money;
    }   

    /**
     * @return <p>Returns the player's loans</p>
     */
    public double GetLoans()
    {
        return loans;
    }

    /**
     * @return <p>Returns the player's bills</p>
     */
    public double GetBills()
    {
        return bills;
    }

    /**
     * @return <p>Returns the player's position on the board</p>
     */
    public int GetPosition()
    {
        return position;
    }


    /**
     * Sets the player's pawn image
     * <p>Preconditions: <p>a) image parameter must not be null</p>
     * </p>
     * @param image
     */
    public void SetPawnImageName(String imageName)
    {
        if (imageName == null)
            throw new NullPointerException("Image cannot be null");
        pawnImageName = imageName;
    }

    /**
     * Sets the player's id
     * <p>Preconditions: <p>a) id > 0 </p>
     * </p> 
     * @param id
     */
    public void SetId(int id)
    {
        if (id <= 0)
            throw new IllegalArgumentException("Id must be greater than 0");
        this.id = id;
    }

    /**
     * This method generates a random number in range [1,6]
     * and sets the player's roll to the number
     */
    public void RollDice()
    {
        java.util.Random rand = new java.util.Random();
        SetRoll(rand.nextInt(6) +1);
    }

    /**
     * Sets the player's roll
     * <p>Precondition: <p> roll >=0 && roll <=6</p></p>
     * @param roll
     */
    public void SetRoll(int roll)
    {
        if (roll < 0 || roll > 6)
            throw new IllegalArgumentException("roll out of bounds [0,6]");
        this.roll = roll;
    }

    /**
     * Set's the player's hasPlayed field
     * @param hasPlayed
     */
    public void SetHasPlayed(boolean hasPlayed)
    {
        this.hasPlayed = hasPlayed;
    }

    /**
     * Set's the player's isTaskDone field
     * @param isDone
     */
    public void SetTaskDone(boolean isDone)
    {
        this.isTaskDone = isDone;
    }

    /**
     * Set's the player's task
     * @param task
     */
    public void SetTask(String task)
    {
        this.task = task;
    }

    /**
     * Sets the player's money
     * <p>Preconditions: <p>a) money >= 0 </p>
     * </p> 
     * @param money
     */
    public void SetMoney(double money)
    {
        if (money < 0)
            throw new IllegalArgumentException("Money must be greater than or equal to 0");
        this.money = money;
    }

    /**
     * Sets the player's loans
     * <p>Preconditions: <p>a) loans >= 0 </p>
     * </p> 
     * @param loans
     */
    public void SetLoans(double loans)
    {
        if (loans < 0)
            throw new IllegalArgumentException("Loans must be greater than or equal to 0");
        this.loans = loans;
    }

    /**
     * Sets the player's bills
     * <p>Preconditions: <p>a) bills >= 0 </p>
     * </p> 
     * @param bills
     */
    public void SetBills(double bills)
    {
        if (bills < 0)
            throw new IllegalArgumentException("Bills must be greater than or equal to 0");
        this.bills = bills;
    }

    /**
     * Sets the player's position
     * <p>Preconditions: <p>a) position >= 0 && position <= 31 </p>
     * </p> 
     * @param position
     */
    public void SetPosition(int position)
    {
        if (position < 0 && position > 31)
            throw new IllegalArgumentException("Position must be in range [0,31]");
        this.position = position;
    }

    /**
     * Removes a certain amount of money from the player
     * and adds a loan if nesseccary
     * <p>Precondition: <p>amount >=0</p></p>
     * @param amount
     */
    public void Pay(double amount)
    {
        if (amount < 0)
            throw new IllegalArgumentException("amount < 0");
            
        double money = GetMoney();
        double loan = 0;

        if (money - amount < 0)  // he needs a loan
        {
            int x = (int)(amount-money)/1000;
            x = ((amount-money)%1000 != 0) ? x + 1 : x;
            loan = 1000 * x;
        }
        SetMoney(money-amount+loan);
        SetLoans(GetLoans()+loan);

    }

    /**
     * This method add a card to the player's deal cards
     * <p>Preconditions: <p>a) card parameter must be of instance DealCard </p>
     * </p>
     * @param card
     */
    public void AddDealCard(Card card)
    {
        if (card instanceof DealCard)
            dealcards.add((DealCard) card);
        else
            throw new IllegalArgumentException("Card must be of type 'DealCard'");
    }

    /**
     * This method add a card to the player's mail cards
     * <p>Preconditions: <p>a) card parameter must be of instance BillCard </p>
     * </p>
     * @param card
     */
    public void AddMailCard(Card card)
    {
        if (card instanceof BillCard)
            mailcards.add((BillCard) card);
        else
            throw new IllegalArgumentException("Card must be of type 'BillCard'");
    }

    /**
     * @return <p>Returns the player's deal cards</p>
     */
    public LinkedList<DealCard> GetDealCards() 
    {
        return dealcards;
    }

    /**
     * @return <p>Returns the player's mail cards</p>
     */
    public LinkedList<MailCard> GetMailCards() 
    {
        return mailcards;
    }
}
