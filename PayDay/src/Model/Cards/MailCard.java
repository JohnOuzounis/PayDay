package Model.Cards;


public abstract class MailCard extends Card
{
    private String choice;
    private double euro;
    

    MailCard(String type, String message, String choise, double euro, String icon)
    {
        super(type, message, icon);
        this.euro = euro;
        this.choice = choise;
    }

    /**
     * @return Returns the value of the card
     */
    public double GetEuro()
    {
        return euro;
    }

    /**
     * @return Returns the option of the card e.g. "Pay x€ to this charity"
     */
    public String GetChoice()
    {
        return choice;
    }
}
