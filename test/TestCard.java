package cardgame.test;

import cardgame.Card;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCard{
    private Card card;

    @Before
    public void setUp(){
        card = new Card(10);
    }

    @After
    public void tearDown(){
        card = null;
    }

    @Test
    public void testGetDenomination(){
        assertEquals(10, card.getDenomination());
    }

}