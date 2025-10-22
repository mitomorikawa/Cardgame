
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
    private Counter turns;
    private int n_of_players;

    public Player(Counter turns, int n_of_players){
        this.turns = turns;
        this.n_of_players = n_of_players;
        hand = new ArrayList<Card>();
    }

    public int get_n_of_players(){
        return this.n_of_players;
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

    public synchronized void endTurn() {
        
        synchronized(turns){
        if (turns.addTurn() == get_n_of_players()) {
            System.out.println("current counter value " + turns.getValue());
            turns.resetTurn();
            System.out.println(playerName + " sleeping...");
            turns.notifyAll();
        } else {
            try {
                System.out.println("current counter value " + turns.getValue());
                System.out.println("waiting");
                turns.wait();
                System.out.println("Notified. New turn begins");
            } catch (InterruptedException e){
                }          
        }
        }
    }

    public synchronized void pickFromDeck(){
        Card card = deckPull.drawCard();
        addCard(card);
        System.out.println(playerName + " picked " + card.getDenomination());
    }

    public synchronized void addToDeck() {
        for(int i = 0; i < this.hand.size(); i++){
            if(this.hand.get(i).getDenomination() != this.playerIndex + 1) {
                this.deckPush.addCard(removeCard(i));
                return;
            }
        }

        if (checkWinner()){
            System.out.println(get_name() + " won");
            turns.end_game();
        }
    }

    @Override
    public void run(){
        do{
            System.out.println("turn begins." + playerName + " hand " + 
            hand.get(0).getDenomination() + " " +  hand.get(1).getDenomination() + " " +
                    hand.get(2).getDenomination() + " " + 
                    hand.get(3).getDenomination() + " "+
                    deckPull.getDeckName() + " " + 
                    deckPull.getDenomination(0) + " " + 
                    deckPull.getDenomination(1) + " " +
                    deckPull.getDenomination(2) + " " + 
                    deckPull.getDenomination(3) + " " );
            pickFromDeck();
            addToDeck();
            endTurn();
            System.out.println("ended turn." + playerName + " hand " + 
            hand.get(0).getDenomination() + " " +  hand.get(1).getDenomination() + " " +
                    hand.get(2).getDenomination() + " " + 
                    hand.get(3).getDenomination() + " "+
                    deckPush.getDeckName() + " " + 
                    deckPush.getDenomination(0) + " " + 
                    deckPush.getDenomination(1) + " " +
                    deckPush.getDenomination(2) + " " + 
                    deckPush.getDenomination(3) + " " );
        } while(!turns.isGameEnded());
    }

    public boolean checkWinner(){
        
        return getDenomination(0) == getDenomination(1) &&
               getDenomination(1) == getDenomination(2) &&
               getDenomination(2) == getDenomination(3);
    }

    public synchronized void displayHand(){
        System.out.println(playerName + " hand " + hand.get(0).getDenomination() + " " +  hand.get(1).getDenomination() + " " +
                    hand.get(2).getDenomination() + " " + hand.get(3).getDenomination() + " ");
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