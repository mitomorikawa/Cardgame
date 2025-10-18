
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
        System.out.println(turns.getValue());
        if (turns.addTurn() == get_n_of_players()) {
            turns.resetTurn();
            notifyAll();
        } else {
            try {
                System.out.println("waiting");
                wait();
            } catch (InterruptedException e){
                }          
        }
    }

    public synchronized void pickFromDeck(){
        Card card = deckPull.drawCard();
        addCard(card);
    }

    public synchronized void addToDeck() {
        for(int i = 0; i < this.hand.size(); i++){
            if(this.hand.get(i).getDenomination() != this.playerIndex + 1) {
                this.deckPush.addCard(removeCard(i));
            }
        }
        if (checkWinner()){
            turns.end_game();
        }
    }

    @Override
    public void run(){
        do{
            System.out.println("new turn");
            pickFromDeck();
            System.out.println("picked from deck");
            addToDeck();
            System.out.println("added to deck");
            endTurn();
            System.out.println("ended turn");
        } while(!turns.isGameEnded());
    }

    public boolean checkWinner(){
        return getDenomination(0) == getDenomination(1) &&
               getDenomination(1) == getDenomination(2) &&
               getDenomination(2) == getDenomination(3);
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