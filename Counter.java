package cardgame;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private AtomicInteger turn = new AtomicInteger(0);
    volatile boolean gameEnded = false;
    public int addTurn(){
        return this.turn.incrementAndGet();
    }

    public void resetTurn(){
        turn.set(0);
    }
    public void endGame(){
        this.gameEnded = true;
    }
    public boolean isGameEnded(){
        return this.gameEnded;
    }
    public int getValue(){
        return turn.get();
    }
}