package cardgame;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private AtomicInteger turn = new AtomicInteger(0);
    volatile boolean gameEnded = false;
    public int addTurn(){ // increments and returns the number of turns completed 
        return this.turn.incrementAndGet();
    }

    public void resetTurn(){ // resets the turns completed
        turn.set(0);
    }
    public void endGame(){ // sets game ended to true so stops the main game loop
        this.gameEnded = true;
    }
    public boolean isGameEnded(){ // checks to see if game is ended
        return this.gameEnded;
    }
    public int getValue(){ // gets the value of turn 
        return turn.get();
    }
}