package Model.Cards;

import Model.Board;
import Model.Player;

public abstract class Card 
{
     private String imageIcon;
     private String message;
     private String type;
     
     /**
      * Sets up a card
      * <p>Precondition: icon not null</p>
      * @param type
      * @param message
      * @param icon
      */
     Card(String type, String message, String icon)
     {
          if (icon == null)
               throw new NullPointerException("icon null");

          this.type = type;
          this.message = message;
          this.imageIcon = icon;
     }

     /**
      * @return Returns the type of this card
      */
     public String GetType()
     {
          return type;
     }

     /**
      * @return Returns the name of the image
      */
     public String GetImage()
     {
          return imageIcon;
     }

     /**
      * @return Returns the message of the card
      */
     public String GetMessage()
     {
          return message;
     }
     
     public abstract void Action(Board board, Player player);
}
