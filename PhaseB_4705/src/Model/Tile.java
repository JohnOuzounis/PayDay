package Model;

import Model.CardPositions.CardPosition;

public class Tile 
{
    private CardPosition position;

    /**
     * @return <p>Returns the position of the tile</p>
     */
    public CardPosition GetPosition()
    {
        return position;
    }

    /**
     * Sets the position of the tile
     * <p>Precondition:<p>a) position parameter is not null</p></p>
     * @param position
     */
    public void SetCardPosition(CardPosition position)
    {
        if (position == null)
            throw new NullPointerException("Null position");
        
        this.position = position;
    }
}
