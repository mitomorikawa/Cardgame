package cardgame.test;

import cardgame.Counter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCounter{
    private Counter counter;

    @Before
    public void setUp(){
        counter = new Counter();
    }

    @After
    public void tearDown(){
        counter = null;
    }

    @Test
    public void testAddTurn(){
        assertEquals(1, counter.addTurn());
        assertEquals(2, counter.addTurn());
        assertEquals(3, counter.addTurn());
    }

    @Test
    public void testResetTurn(){
        counter.addTurn();
        counter.addTurn();
        counter.resetTurn();
        assertEquals(0, counter.getValue());
    }

    @Test
    public void testEndGameAndIsGameEnded(){
        assertFalse(counter.isGameEnded());
        counter.endGame();
        assertTrue(counter.isGameEnded());
    }

    @Test
    public void testGetValue(){
        counter.addTurn();
        counter.addTurn();
        assertEquals(2, counter.getValue());
    }
}