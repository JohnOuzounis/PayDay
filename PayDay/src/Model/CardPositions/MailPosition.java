package Model.CardPositions;

import Controller.Controller;

public abstract class MailPosition extends CardPosition
{
    MailPosition(String imageName, Controller controller)
    {
        super(imageName, controller);
    }
}
