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

    public Player(Counter turns, int n_of_players /*number of total players in game*/ ){ // constructor for player object
        this.turns = turns;
        this.n_of_players = n_of_players;
        hand = new ArrayList<Card>();
    }

    public int get_n_of_players(){ // get the total number of players in the game 
        return this.n_of_players;
    }

    public void setName(int index){ // sets name for player depending on index 
        this.playerIndex = index;
        this.playerName = "Player"+(index + 1);
    }

    public String get_name(){ // gets name of player
        return this.playerName;
    }

    public Card removeCard(int index){ // removes the specific index of a card object from the players hand 
        return hand.remove(index);
    }

    public void addCard(Card card){ // adds a card to the end of the players hand
        hand.add(card);
    }

    public void setDecks(CardDeck deckPull, CardDeck deckPush){ // takes arguements that are the specified decks each player is supposed to pick from and discard to 
        this.deckPull = deckPull;
        this.deckPush = deckPush;
    }

    public synchronized void pickFromDeck(){ // draws a card from the deck the player is supposed to draw from
        Card card = deckPull.drawCard();
        addCard(card);
        String pickedCard = playerName + " draws a " + card.getDenomination() + " from " + deckPull.getDeckName() + "\n";
        outputToFile(pickedCard); // prints to output file
    }

    public synchronized void addToDeck() { // discards the first card from the hand that is not the players preferred card to the deck they're supposed to discard to 
        for(int i = 0; i < this.hand.size(); i++){
            if(this.hand.get(i).getDenomination() != this.playerIndex + 1) {
                Card cardRemoved = removeCard(i);
                this.deckPush.addCard(cardRemoved); // adds the chosen card to end of the deck
                String discardedCard = playerName + " discards a " + cardRemoved.getDenomination() + " to " + this.deckPush.getDeckName() + "\n";
                outputToFile(discardedCard); // outputs to file
                break;
            }
        }
        String currentHand = playerName + " current hand is " + this.hand.get(0).getDenomination() + " " + this.hand.get(1).getDenomination() + " " + this.hand.get(2).getDenomination() + " " + this.hand.get(3).getDenomination() + "\n";
        outputToFile(currentHand); // outputs to file
    }

    public synchronized void endTurn() { // makes all players wait till they have all caught up to the same point
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
    public void run(){ // main thread run method loops over till a player has won 
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

    public synchronized boolean checkWinner(){ // checks to see if the player has won then outputs true or false
        return getDenomination(0) == getDenomination(1) &&
               getDenomination(1) == getDenomination(2) &&
               getDenomination(2) == getDenomination(3); // checks if all 4 cards are the same
    }

    public void outputToFile(String message) { // outputs to the player files
        try (FileOutputStream output = new FileOutputStream(playerName + "_output.txt", true)) {
            byte[] messageBytes = message.getBytes();
            output.write(messageBytes);
        } catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public int getDenomination(int index){ // gets the value of the card object specified by the index
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