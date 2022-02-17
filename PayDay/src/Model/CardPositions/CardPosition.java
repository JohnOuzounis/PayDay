package Model.CardPositions;


import Controller.Controller;
import Model.Player;
import View.OptionDialog;

public abstract class CardPosition
{
    private String icon;

    private int position;
    private String day;

    private Controller controller;


    /**
     * <p>Precondition: <p>controller not null</p></p>
     * @param imageName
     * @param controller
     */
    CardPosition(String imageName, Controller controller)
    {
        if (controller == null)
            throw new NullPointerException("controller is null");

        this.SetIcon(imageName);
        SetController(controller);
    }

    /**
     * Sets the day of the position
     * <p>Precondition: <p> day >= 0 && day <= 8<><>
     * @param day
     */
    public void SetDay(int day)
    {
        if (day < 0 || day > 8)
            throw new IllegalArgumentException("day out of bounds [0,8]");

        if (day == 0)
            this.day = "Start";
        else if (day == 1)
            this.day = "Monday";
        else if (day == 2)
            this.day = "Tuesday";
        else if (day == 3)
            this.day = "Wednesday";
        else if (day == 4)
            this.day = "Thursday";
        else if (day == 5)
            this.day = "Friday";
        else if (day == 6)
            this.day = "Saturday";
        else if (day == 7)
            this.day = "Sunday";
        else 
            this.day = "Jackpot";
    }

    /**
     * @return Returns the day of the week of the position
     */
    public String GetDay()
    {
        return day;
    }

    /**
     * Sets the day of the month of the position
     * @param position
     */
    public void SetPosition(int position)
    {
        this.position = position;
    }

    /**
     * @return Returns the day of the month of the position
     */
    public int GetPosition()
    {
        return position;
    }

    /**
     * @return Returns the name of the image file
     */
    public String GetIcon()
    {
        return icon;
    }

    /**
     * sets the name of the image
     * @param icon
     */
    public void SetIcon(String icon)
    {
        this.icon = icon;
    }

    /**
     * Sets the controller
     * <p>Precondition: <p>controller not null</p></p>
     * @param controller
     */
    public void SetController(Controller controller)
    {
        this.controller = controller;
    }

    /**
     * @return Returns the controller of this position
     */
    public Controller GetController()
    {
        return controller;
    }

    /**
     * This function forces the player to do something
     * based on the position he is now
     */
    public abstract void performAction(Player player);

    /**
     * Performs a special action based on the day of the week of the position
     * <p>Precpndition: player not null</p>
     * @param player
     */
    public void specialPerformAction(Player player)
    {
        if (player == null)
            throw new NullPointerException("player is null");

        if (GetDay().equals("Thursday"))
        {
            //crypro currency
            OptionDialog a = new OptionDialog("Thursday-Rise in the value of cryptos", "Do you want to bet 300€ in crypto?",
                             "Bet 300€", "No, thanks", "crypto.jpg");
            if (a.GetOption() == 1)
            {
                java.util.Random random = new java.util.Random();
                int roll = random.nextInt(6)+1;
                if (roll <= 2)
                {
                    new OptionDialog("Thursday-Rise in the value of cryptos", 
                                     "Yikes...you just lost 300€ :(", "Oh..ok", null, "crypto.jpg");
                    player.Pay(300);
                }
                else if (roll <= 4)
                {
                    new OptionDialog("Thursday-Rise in the value of cryptos", 
                                     "Prices remained stable. You won your 300€ back", "Ok", null, "crypto.jpg");
                }
                else
                {
                    new OptionDialog("Thursday-Rise in the value of cryptos", 
                                     "Crypto-currency rised!! You won 600€", "Ok", null, "crypto.jpg");
                }
            }
        }
        else if (GetDay().equals("Sunday"))
        {
            //football
            String image = "nba.jpg";
            String title = "Sunday Football Game";
            String text = "Would you like to bet 500€ on a foolball game?";

            OptionDialog op = new OptionDialog(title, text, "Yes", "No", image);
            if (op.GetOption() == 1)
            {
                //bet
                double bet = 500;

                // pick a team
                String playerOption = "";
                int option = 0;
                while (option != 1)
                {
                    String[] team = {"home", "tie", "guest"};
                    for (int i = 0; i < 3; i++)
                    {
                        text = "Would you like to bet on " + team[i];
                        op = new OptionDialog(title, text, "Pick", "Next", image);
                        if (op.GetOption() == 1)
                        {
                            option = op.GetOption();
                            playerOption = team[i];
                            break;
                        }
                    }
                }

                //roll dice
                java.util.Random random = new java.util.Random();
                int roll = random.nextInt(6)+1;
                if ((playerOption.equals("home") && (roll == 1 || roll == 2))
                  ||(playerOption.equals("tie")  && (roll == 3 || roll == 4))
                  ||(playerOption.equals("guest") &&(roll == 5 || roll == 6)))
                {
                    text = "Congrats! You won " + 2*bet + "€";
                    player.SetMoney(player.GetMoney()+2*bet);
                }
                else
                {
                    text = "Unfortunately you lost";
                    player.Pay(bet);
                }
                op = new OptionDialog(title, text, "Ok", null, image);
            }
        }
    }
}
