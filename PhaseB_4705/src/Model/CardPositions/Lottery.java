package Model.CardPositions;

import Controller.Controller;
import Model.Player;
import View.OptionDialog;

public class Lottery extends CardPosition
{

    /**
     * <p>Precondition: <p>controller not null</p></p>
     * @param imageName
     * @param controller
     */
    public Lottery(String imageName, Controller controller) 
    {
        super(imageName, controller);
    }

    /**
     * Both players choose a number, until one of them is randomly picked a die is rolled, 
     * then 1000€ are added to the player that picked that number
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
        
        Player other = GetController().GetBoard().OtherPlayer(player);

        //get options
        int[] opt = new int[2]; // the options of both players
        opt[0] = PlayerOption(player);
        opt[1] = PlayerOption(other);

        // roll die
        int roll = RollUntilPicked(opt, player);

        //pick winner
        PickWinner(opt, roll, player);


        player.SetTaskDone(true);
    }

    /**
     * <p>Precondition: <p>player and opt not null</p></p>
     * @param opt
     * @param player
     * @return Returns the first roll of dice that matches a player's option
     */
    private int RollUntilPicked(int[] opt, Player player)
    {
        if (player == null || opt == null)
            throw new NullPointerException("Options of player null");

        Player other = GetController().GetBoard().OtherPlayer(player);

        int roll = 0;
        while (roll != opt[0] && roll != opt[1])
        {
            java.util.Random rand = new java.util.Random();
            roll = rand.nextInt(6) + 1;

            String message = "Player"+player.GetId()+" picked "+opt[0] +
                             "\nPlayer"+other.GetId()+" picked "+opt[1] +
                             "\nRoll: "+roll;
            new OptionDialog("Lottery", message, "Ok", null, GetIcon());
        }
        return roll;
    }

    /**
     * Displays options for the player to choose from
     * <p>Precondition: player not null</p>
     * @param player
     * @return <p>Returns the player's option</p>
     */
    private int PlayerOption(Player player)
    {
        if (player == null)
            throw new NullPointerException("player null");

        int option = 0;
        while (option == 0)
        {
            OptionDialog op;
            for (int i = 1; i < 7; i++)
            {
                op = new OptionDialog("Lottery", "Player"+player.GetId()+": pick a number", "Pick", "Next", "dice"+i+".png");
                if (op.GetOption() == 1)
                {
                    option = i;
                    break;
                }
            }
        }
        return option;
    }

    /**
     * Displays the options of both players and the dice roll in a window
     * and announces the winner
     * <p>Precondition: player and opt not null</p>
     * @param opt
     * @param roll
     * @param player
     */
    private void PickWinner(int[] opt, int roll, Player player)
    {
        if (player == null || opt == null)
            throw new NullPointerException("player or options null");
            
        Player other = GetController().GetBoard().OtherPlayer(player);

        String winner = "Player"+player.GetId();
        if (roll == opt[1] && opt[1] != opt[0])
        {
            other.SetMoney(other.GetMoney() + 1000);
            winner = "Player"+other.GetId();
        }
        else
        {
            player.SetMoney(player.GetMoney() + 1000);
        }
        String message = "Winner: " + winner +
                         "\n1000€ have been added to your account";
        new OptionDialog("Lottery", message, "Ok", null, GetIcon());
    }

    /**
     * @return <p>Returns info about the player's task</p>
     */
    public String toString()
    {
        return "Both players pick a number and roll dice until you get one of those";
    }
    
}
