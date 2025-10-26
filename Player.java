package cardgame;

import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public void setName(int index){
        this.playerIndex = index;
        this.playerName = "Player"+(index + 1);
    }

    public String get_name(){
        return this.playerName;
    }

    public Card removeCard(int index){
        return hand.remove(index);
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public void setDecks(CardDeck deckPull, CardDeck deckPush){
        this.deckPull = deckPull;
        this.deckPush = deckPush;
    }

    public synchronized void pickFromDeck(){
        Card card = deckPull.drawCard();
        addCard(card);
        String pickedCard = playerName + " draws a " + card.getDenomination() + " from " + deckPull.getDeckName() + "\n";
        outputToFile(pickedCard);
    }

    public synchronized void addToDeck() {
        for(int i = 0; i < this.hand.size(); i++){
            if(this.hand.get(i).getDenomination() != this.playerIndex + 1) {
                Card cardRemoved = removeCard(i);
                this.deckPush.addCard(cardRemoved);
                String discardedCard = playerName + " discards a " + cardRemoved.getDenomination() + " to " + this.deckPush.getDeckName() + "\n";
                outputToFile(discardedCard);
                break;
            }
        }
        String currentHand = playerName + " current hand is " + this.hand.get(0).getDenomination() + " " + this.hand.get(1).getDenomination() + " " + this.hand.get(2).getDenomination() + " " + this.hand.get(3).getDenomination() + "\n";
        outputToFile(currentHand);
    }

    public synchronized void endTurn() {
        if (get_n_of_players() == 1){
            return;
        }
        synchronized(turns){
        if (turns.addTurn() == get_n_of_players()) {
            turns.resetTurn();
            turns.notifyAll();
        } else {
            try {
                turns.wait();
            } catch (InterruptedException e){
                }
        }
        }
    }

    @Override
    public void run(){
        do{
            pickFromDeck();
            addToDeck();
            endTurn();
            if (checkWinner()){
                turns.endGame();
            }
            endTurn();
        } while(!turns.isGameEnded());
    }

    public synchronized boolean checkWinner(){
        return getDenomination(0) == getDenomination(1) &&
               getDenomination(1) == getDenomination(2) &&
               getDenomination(2) == getDenomination(3); 
    }

    public void outputToFile(String message) {
        try (FileOutputStream output = new FileOutputStream("../txt/" + playerName + "_output.txt", true)) {
            byte[] messageBytes = message.getBytes();
            output.write(messageBytes);
        } catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

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