package cardgame.test; 

import cardgame.Card;
import cardgame.CardDeck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCardDeck {
    private CardDeck cardDeck;

    @Before
    public void setUp() {
        cardDeck = new CardDeck();
    }

    @After
    public void tearDown() {
        cardDeck = null;
    }

    @Test
    public void testSetDeckNameAndGetDeckName(){
        cardDeck.setDeckName(1);
        assertEquals("Deck2", cardDeck.getDeckName());;
    }

    @Test
    public void testAddCard(){
        Card card = new Card(6);
        cardDeck.addCard(card);
        assertEquals(6, cardDeck.getDenomination(0));
    }

    @Test
    public void testDrawCard(){
        Card card1 = new Card(4);
        Card card2 = new Card(9);
        cardDeck.addCard(card1);
        cardDeck.addCard(card2);
        Card drawnCard1 = cardDeck.drawCard();
        assertEquals(4, drawnCard1.getDenomination());
        assertEquals(1, cardDeck.size());
        Card drawnCard2 = cardDeck.drawCard();
        assertEquals(9, drawnCard2.getDenomination());
        assertEquals(0, cardDeck.size());
        Card drawnCard3 = cardDeck.drawCard();
        assertNull(drawnCard3); // Test drawing from empty deck
    }

    @Test
    public void testGetDenomination(){
        cardDeck.addCard(new Card(7));
        cardDeck.addCard(new Card(10));
        assertEquals(-1, cardDeck.getDenomination(-1)); // Invalid index
        assertEquals(-1, cardDeck.getDenomination(2));  // Invalid index
        assertEquals(7, cardDeck.getDenomination(0));  
        assertEquals(10, cardDeck.getDenomination(1));
    }

    @Test
    public void testDeckSize(){
        assertEquals(0, cardDeck.size());
        cardDeck.addCard(new Card(2));
        cardDeck.addCard(new Card(5));
        assertEquals(2, cardDeck.size());
    }

}