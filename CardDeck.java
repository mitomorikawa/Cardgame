/*
•CardDeck()
    •empty list
•ArrayList [Card] deck
•

 */
import java.util.ArrayList;
public class CardDeck extends Thread{
    private String deckname;
    private ArrayList<Card> deck;
    public CardDeck(){
        deck = new ArrayList<Card>();
    }

    public void setDeckName(int index){
        this.deckname = "Deck"+(index + 1);
    }

    public String getDeckName(){
        return this.deckname;
    }

    public void addCard(Card card){
        deck.add(card);
    }
    public Card drawCard(){
        if(deck.size() == 0){
            return null;
        }
        return deck.remove(0);
    }

    // Debugging method to get denomination of card at index
    public int getDenomination(int index){
        if(index < 0 || index >= deck.size()){
            return -1;
        }
        return deck.get(index).getDenomination();
    }

    // Debugging method to get size of deck
    public int size(){
        return deck.size();
    }
}