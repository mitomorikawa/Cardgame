
/*
• Player ()
        :empty list
• 
 */

import java.util.ArrayList;

public class Player extends Thread{     
    private String playerName;
    private int playerIndex;
    private ArrayList<Card> hand;
    private CardDeck deckPull;
    private CardDeck deckPush;
    public Player(){
        hand = new ArrayList<Card>();
    }

    public void SetName(int index){
        this.playerIndex = index;
        this.playerName = "Player"+(index + 1);
    }

    public String get_name(){
        return this.playerName;
    }

    public Card removeCard(int index){
        if(index < 0 || index >= hand.size()){
            return null;
        }
        return hand.remove(index);
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public void setDecks(CardDeck deckPull, CardDeck deckPush){
        this.deckPull = deckPull;
        this.deckPush = deckPush;
    }

    @Override
    public void run(){
        
    }

    public void pickFromDeck(){
        Card card = deckPull.drawCard();
        addCard(card);
    }

    public boolean checkWinner(Player player){
        
    }


    
    


    // Debugging method to get denomination of card at index
    public int getDenomination(int index){
        if(index < 0 || index >= hand.size()){
            return -1;
            }
        return hand.get(index).getDenomination();
    }

    public CardDeck getDeckPull(){
        return this.deckPull;
    }
    public CardDeck getDeckPush(){
        return this.deckPush;
    }
}