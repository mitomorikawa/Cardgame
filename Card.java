package cardgame;

public class Card {
    private int denomination;
    public Card(int denomination){ // constructor for card object
        this.denomination = denomination;
    }
    public int getDenomination(){ // returns the number of the card object
        return denomination;
    }
}