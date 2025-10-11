
/*
• Player ()
        :empty list
• 
 */

import java.util.ArrayList;

public class Player extends Thread{     
    private String playerName;
    private ArrayList<Card> hand;
    public Player(){
        hand = new ArrayList<Card>();
    }

    public void SetName(int index){
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

    // Debugging method to get denomination of card at index
    public int getDenomination(int index){
        if(index < 0 || index >= hand.size()){
            return -1;
            }
        return hand.get(index).getDenomination();
    }
}