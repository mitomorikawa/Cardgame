package cardgame;

import java.util.ArrayList;
public class CardDeck {
    private String deckname;
    private ArrayList<Card> deck;
    public CardDeck(){
        deck = new ArrayList<Card>(); // creates a new deck
    }

    public void setDeckName(int index){ // sets the deck name depending on its index 
        this.deckname = "Deck"+(index + 1);
    }

    public String getDeckName(){ // gets the deck name
        return this.deckname;
    }

    public void addCard(Card card){ // adds a card to the end of a deck 
        deck.add(card); 
    }
    public Card drawCard(){ // draws a card from the first card of a deck
        return deck.remove(0);
    }

    public int getDenomination(int index){ // gets the number of the index of card given
        if(index < 0 || index >= deck.size()){
            return -1;
        }
        return deck.get(index).getDenomination();
    }

    public int size(){ // gets size of card deck
        return deck.size();
    }
}