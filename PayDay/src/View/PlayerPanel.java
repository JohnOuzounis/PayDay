package View;

import java.awt.Color;
import java.net.URL;

import javax.swing.*;

import Controller.Controller;
import Model.Player;
import Model.CardPositions.CardPosition;
import Model.CardPositions.DealPosition;
import Model.CardPositions.JackpotPosition;
import Model.CardPositions.MailPosition;
import Model.Cards.DealCard;

import java.awt.*;


/**
 * This class represents a Player in UI
 * <p>It displays important information about the player,
 * such as his balance, his roll of the dice, etc.
 * It also contains buttons for the player to perform actions</p>
 */
public class PlayerPanel extends JPanel
{
    private Player player;

    private Controller controller;

    private JLabel diceImage;
    private URL imageURL;
    
    private JLabel playerText;
    public JLabel moneyText, loanText, billsText;

    JButton rollDieButton, myDealCardsButton;
    JButton getLoanButton, endTurnButton;

    /**
     * Sets up a panel for a player
     * <p>Preconditions: <p>a) player and controller parameters are not null</p></p>
     * @param player
     * @param controller
     */
    public PlayerPanel(Player player, Controller controller)
    {
        if (player == null || controller == null)
            throw new NullPointerException("controller or player are null");

        // SET PLAYER
        SetPlayer(player);

        // SET CONTROLLER
        SetController(controller);

        // FOR PANEL
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new GridLayout(9,1));

        // FOR LABELS
        playerText = new JLabel("Player"+ this.GetPlayer().GetId());
        moneyText = new JLabel("Money: "+ this.GetPlayer().GetMoney());
        loanText = new JLabel("Loan: " + this.GetPlayer().GetLoans());
        billsText = new JLabel("Bills: " + this.GetPlayer().GetBills());

        // FOR DIE BUTTON
        rollDieButton = new JButton("Roll Dice");
        imageURL = this.getClass().getClassLoader().getResource("resources/images/dice2.png");
        diceImage = new JLabel(new ImageIcon(imageURL));
        rollDieButton.addActionListener(
            /**
             * This function generates a random number in range [1,6] and moves the player
             * this many tile's forward.It also updates the UI
             * <p>Preconditions: <p>a) It must be the player's turn to play in order for him to press the button</p>
             *                   <p>b) The player cannot move more than once at a time</p>
             * </p>
             */
            (event) -> {

                try
                {
                    //roll dice
                    this.GetPlayer().RollDice();

                    if (this.GetPlayer().GetId() != this.GetController().GetTurn())
                        throw new Exception("It's not the player's turn to play");

                    if (this.GetPlayer().HasPlayed())
                        throw new Exception("the player has played already");

                    if (this.GetPlayer().GetRoll() == 6)
                    {
                        ((JackpotPosition)GetController().GetBoard().jackpot.GetPosition()).performAction(player);
                    }

                    // move player
                    int newPosition = this.GetPlayer().GetPosition() + this.GetPlayer().GetRoll();
                    this.GetController().MovePlayer(player, newPosition);
                    this.GetPlayer().SetHasPlayed(true);
                    
                    // update ui
                    imageURL = this.getClass().getClassLoader().getResource("resources/images/dice"+this.GetPlayer().GetRoll()+".png");
                    diceImage.setIcon(new ImageIcon(imageURL));
                    this.GetController().GetUI().UpdateUI();

                    // perform actions
                    CardPosition position = GetController().GetBoard().tiles[player.GetPosition()-1].GetPosition();
                    position.specialPerformAction(player);
                        
                    if (!((position instanceof DealPosition) || (position instanceof MailPosition)))
                        position.performAction(player);
                    
                    // update ui
                    this.GetController().GetUI().UpdateUI();
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        );

        // FOR DEAL BUTTON
        myDealCardsButton = new JButton("My Deal Cards");
        myDealCardsButton.addActionListener(
            /**
             * This function displayes the player's deal cards in a new window
             * <p>Preconditions: <p>a) It must be the player's turn to play in order for him to press the button</p>
             * </p>
             */
            (event) -> {
                try
                {
                    if (this.GetPlayer().GetId() != this.GetController().GetTurn())
                        throw new Exception("It's not the player's turn to play");

                    int index = controller.GetTurn()-1;
                    for (DealCard dealcard : controller.GetBoard().players[index].GetDealCards())
                    {
                        String message = dealcard.GetMessage() + "\nCost: " + dealcard.GetCost() + "\nValue: " + dealcard.GetValue();
                        new OptionDialog(dealcard.GetType(), message, "Ok", null, dealcard.GetImage());
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        );

        // FOR LOAN BUTTON
        getLoanButton = new JButton("Get Loan");
        getLoanButton.addActionListener(
            /**
             * This method adds 1000€ to the player's loans and money. It also updates the UI
             * <p>Preconditions: <p>a) It must be the player's turn to play in order for him to press the button</p>
             *                   <p>b) The player must not have finished his task</p>
             *                   <p>c) The game must not be over</p>
             * </p>
             */
            (event) -> {
                try
                {
                    if (this.GetPlayer().GetId() != this.GetController().GetTurn())
                        throw new Exception("It's not the player's turn to play");
                    
                    if (this.GetPlayer().IsTaskDone())
                        throw new Exception("The player doesn't need a loan");
            
                    if (this.GetController().IsGameOver())
                        throw new Exception("The game is over, player can't get a loan");
                        
                    int add = 1000;
                    this.GetPlayer().SetLoans(this.GetPlayer().GetLoans() + add);
                    this.GetPlayer().SetMoney(this.GetPlayer().GetMoney() + add);

                    //update ui
                    this.GetController().GetUI().UpdateUI();

                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        );

        // FOR ENDTURN BUTTON
        endTurnButton = new JButton("End Turn");
        endTurnButton.addActionListener(
            /**
             * This function ends the player's turn and sets the next turn.
             * <p>Preconditions: <p>a) It must be the player's turn to play in order for him to press the button</p>
             *                   <p>b) The player must have finished his task</p>
             *                   <p>c) The player must not have finished the game to play again/p>
             * </p>
             * <p>Postcondition <p>a) The player can play again if the game isn't over</p></p>
             */
            (event) -> {
                try
                {
                    if (this.GetPlayer().GetId() != this.GetController().GetTurn())
                        throw new Exception("It's not the player's turn to play");

                    if (!this.GetPlayer().IsTaskDone())
                        throw new Exception("The player has not finished his task");
                    
                        this.GetPlayer().SetTaskDone(false);
                        this.GetController().SetNextTurn();
                        this.GetController().SetTask((GetController().IsGameOver()) ? "Game Over" : "Play");
                        this.GetController().GetUI().UpdateUI();

                    if (GetController().HasFinished(GetController().GetTurn()))
                        throw new Exception("the player cannot play again");
                        
                    this.GetPlayer().SetHasPlayed(false);
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        );

        // ADD COMPONENTS
        this.add(playerText);
        this.add(moneyText);
        this.add(loanText);
        this.add(billsText);
        this.add(diceImage);
        this.add(rollDieButton);
        this.add(myDealCardsButton);
        this.add(getLoanButton);
        this.add(endTurnButton);
    }

    /**
     * Updates the player's labels
     * @param money
     * @param loans
     * @param bills
     */
    public void UpdatePlayerUI(double money, double loans, double bills)
    {
        moneyText.setText("Money: " + money);
        loanText.setText("Loans: " + loans);
        billsText.setText("Bills: " + bills);
    }

    /**
     * <p>Preconditions: <p>a) controller parameter must not be null</p>
     * </p>
     * @param controller
     */
    private void SetController(Controller controller)
    {
        if (controller == null)
            throw new NullPointerException("Controller is null");
        this.controller = controller;
    }

    public Controller GetController()
    {
        return this.controller;
    }

    /**
     * <p>Preconditions: <p>a) player parameter must not be null</p>
     * </p>
     * @param player
     */
    private void SetPlayer(Player player)
    {
        if (player == null)
            throw new NullPointerException("Player is null");
        this.player = player;
    }
    
    public Player GetPlayer()
    {
        return this.player;
    }
}
