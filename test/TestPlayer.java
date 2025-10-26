package cardgame.test;

import cardgame.Player;
import cardgame.Counter;
import cardgame.Card;
import cardgame.CardDeck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.lang.Thread;
import java.util.concurrent.atomic.AtomicInteger;

public class TestPlayer{
    private Player player;
    private Counter counter;

    @Before
    public void setUp(){
            counter = new Counter();
    }

    @After
    public void tearDown(){
        player = null;
        counter = null;
    }

    @Test
    public void testAddCard(){
        player = new Player(counter, 4);
        player.addCard(new cardgame.Card(3));
        assertEquals(3, player.getDenomination(0));
    }
    /*remove readCardInHand 
       SetName -> setName
       get_name -> getName
       remove displayHand */

    @Test
    public void testGetDenomination(){
        player = new Player(counter, 4);
        assertEquals(-1, player.getDenomination(0)); // No card at index 0
        player.addCard(new cardgame.Card(5));
        assertEquals(5, player.getDenomination(0));
    }

    @Test
    public void testRemoveCard(){
        player = new Player(counter, 4);
        player.addCard(new cardgame.Card(7));
        Card removedCard = player.removeCard(0);
        assertEquals(7, removedCard.getDenomination());
    }

    @Test
    public void testSetDeckAndGetDeckPullAndGetDeckPush(){
        player = new Player(counter, 4);
        cardgame.CardDeck deck1 = new cardgame.CardDeck();
        cardgame.CardDeck deck2 = new cardgame.CardDeck();
        player.setDecks(deck1, deck2);
        assertEquals(deck1, player.getDeckPull());
        assertEquals(deck2, player.getDeckPush());
    }

    @Test
    public void testPickFromDeck(){
        File f = new File("../txt/Player1_output.txt"); //Prepare a file object to check file output
        if (f.exists()) f.delete(); // Ensure clean state
        player = new Player(counter, 4);
        player.SetName(0);
        CardDeck deck = new CardDeck();
        deck.setDeckName(0);
        deck.addCard(new Card(9));
        player.setDecks(deck, new CardDeck());
        player.pickFromDeck();
        assertEquals(9, player.getDenomination(0)); // Card with denomination 9 should be in hand

        try (Scanner file = new Scanner(new File("../txt/Player1_output.txt"))){
            String line = file.nextLine();

            assertEquals("Player1 draws a 9 from Deck1", line);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testAddToDeck(){
        File f = new File("../txt/Player2_output.txt");
        if (f.exists()) f.delete(); // Ensure clean state
        player = new Player(counter, 4);
        player.SetName(1); // player 2
        CardDeck deckPush = new CardDeck();
        deckPush.setDeckName(2); // deck 3
        player.setDecks(new CardDeck(), deckPush);
        player.addCard(new Card(2)); // before discarding, hand has 2,2,5,4,2
        player.addCard(new Card(2)); 
        player.addCard(new Card(5));
        player.addCard(new Card(4));
        player.addCard(new Card(2));
        player.addToDeck();
        assertEquals(1, deckPush.size()); // Only one card should be added to deckPush
        assertEquals(5, deckPush.getDenomination(0)); // The card with denomination 3 should be in deckPush
        assertEquals(2, player.getDenomination(0)); // The remaining cards in hand should be 2,2,4,2
        assertEquals(2, player.getDenomination(1));
        assertEquals(4, player.getDenomination(2));
        assertEquals(2, player.getDenomination(3)); 
        try (Scanner file = new Scanner(new File("../txt/Player2_output.txt"))){
            String line = file.nextLine();
            assertEquals("Player2 discards a 5 to Deck3", line);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testEndTurn(){
        Player player1 = new Player(counter, 3);
        player1.SetName(0); // Player1
        Player player2 = new Player(counter, 3);
        player2.SetName(1); // Player2
        Player player3 = new Player(counter, 3);
        player3.SetName(2); // Player3
        //  Player1,  2 and 3 runs endTurn in this order
        AtomicInteger finishedThreads = new AtomicInteger(0); // To track finished threads
        Thread t1 = new Thread(() -> {
            player1.endTurn();
            finishedThreads.incrementAndGet();
        });
        Thread t2 = new Thread(() -> {
            player2.endTurn();
            finishedThreads.incrementAndGet();
        });
        Thread t3 = new Thread(() -> {
            player3.endTurn();
            finishedThreads.incrementAndGet();
        });
        t1.start();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){} 
        assertEquals(1, counter.getValue()); // After Player1 ends turn, turn should be 1
        t2.start();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){} 
        assertEquals(2, counter.getValue()); // After Player2 ends turn, turn should be 2
        t3.start();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){} 
        assertEquals(0, counter.getValue()); // After Player3 ends turn, turn should reset to 0
        assertEquals(3, finishedThreads.get()); // All threads should have finished


        Player player4 = new Player(counter, 1);
        player1.SetName(0); // Single player
        finishedThreads.set(0);  // Reset finished threads counter
        Thread t4 = new Thread(() -> {
            player4.endTurn();
            finishedThreads.incrementAndGet();
        });
        t4.start();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){}
        assertEquals(0, counter.getValue()); // With single player, turn should remain 0
        assertEquals(1, finishedThreads.get()); // Thread should have finished

    }
    
    @Test
    public void testRun(){
        // 1. player 1 wins in round 1
        // 2. player 1 wins in round 2
        // 3. multiple winners -> first one to notify wins
        Counter counter1 = new Counter();
        Player player1_1 = new Player(counter1, 3);
        player1_1.SetName(0);
        player1_1.addCard(new Card(1));
        player1_1.addCard(new Card(1));
        player1_1.addCard(new Card(1));
        player1_1.addCard(new Card(2)); 
        Player player1_2 = new Player(counter1, 3);
        player1_2.SetName(1);
        player1_2.addCard(new Card(3));
        player1_2.addCard(new Card(2));
        player1_2.addCard(new Card(3));
        player1_2.addCard(new Card(4));
        Player player1_3 = new Player(counter1, 3);
        player1_3.SetName(2);
        player1_3.addCard(new Card(1));
        player1_3.addCard(new Card(2));
        player1_3.addCard(new Card(3));
        player1_3.addCard(new Card(4));
        CardDeck deck1_1 = new CardDeck();
        deck1_1.setDeckName(0);
        deck1_1.addCard(new Card(1));
        deck1_1.addCard(new Card(1));
        deck1_1.addCard(new Card(1));
        deck1_1.addCard(new Card(1));
        CardDeck deck1_2 = new CardDeck();
        deck1_2.setDeckName(1);
        deck1_2.addCard(new Card(2));
        deck1_2.addCard(new Card(2));
        deck1_2.addCard(new Card(2));
        deck1_2.addCard(new Card(2));
        CardDeck deck1_3 = new CardDeck();
        deck1_3.setDeckName(2);
        deck1_3.addCard(new Card(3));
        deck1_3.addCard(new Card(3));
        deck1_3.addCard(new Card(3));
        deck1_3.addCard(new Card(3));
        player1_1.setDecks(deck1_1, deck1_2);
        player1_2.setDecks(deck1_2, deck1_3);
        player1_3.setDecks(deck1_3, deck1_1);

        player1_1.start();
        player1_2.start();
        player1_3.start();
        try{
            player1_1.join();
            player1_2.join();
            player1_3.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        assertEquals(1, player1_1.getDenomination(0));
        assertEquals(1, player1_1.getDenomination(1));
        assertEquals(1, player1_1.getDenomination(2));
        assertEquals(1, player1_1.getDenomination(3)); // player 1 should have 4 of denomination 1
        assertEquals(2, player1_2.getDenomination(0));
        assertEquals(3, player1_2.getDenomination(1));
        assertEquals(4, player1_2.getDenomination(2));
        assertEquals(2, player1_2.getDenomination(3));
        assertEquals(2, player1_3.getDenomination(0));
        assertEquals(3, player1_3.getDenomination(1));
        assertEquals(4, player1_3.getDenomination(2));
        assertEquals(3, player1_3.getDenomination(3)); // 
        assertEquals(1, deck1_1.getDenomination(0));
        assertEquals(1, deck1_1.getDenomination(1));
        assertEquals(1, deck1_1.getDenomination(2));
        assertEquals(1, deck1_1.getDenomination(3));
        assertEquals(2, deck1_2.getDenomination(0));
        assertEquals(2, deck1_2.getDenomination(1));
        assertEquals(2, deck1_2.getDenomination(2));
        assertEquals(2, deck1_2.getDenomination(3));
        assertEquals(3, deck1_3.getDenomination(0));
        assertEquals(3, deck1_3.getDenomination(1));
        assertEquals(3, deck1_3.getDenomination(2));
        assertEquals(3, deck1_3.getDenomination(3)); 

        Counter counter2 = new Counter();
        Player player2_1 = new Player(counter2, 3);
        player2_1.SetName(0);
        player2_1.addCard(new Card(1));
        player2_1.addCard(new Card(1));
        player2_1.addCard(new Card(2));
        player2_1.addCard(new Card(2)); 
        Player player2_2 = new Player(counter2, 3);
        player2_2.SetName(1);
        player2_2.addCard(new Card(3));
        player2_2.addCard(new Card(3));
        player2_2.addCard(new Card(2));
        player2_2.addCard(new Card(4));
        Player player2_3 = new Player(counter2, 3);
        player2_3.SetName(2);
        player2_3.addCard(new Card(1));
        player2_3.addCard(new Card(1));
        player2_3.addCard(new Card(3));
        player2_3.addCard(new Card(4));
        CardDeck deck2_1 = new CardDeck();
        deck2_1.setDeckName(0);
        deck2_1.addCard(new Card(1));
        deck2_1.addCard(new Card(1));
        deck2_1.addCard(new Card(1));
        deck2_1.addCard(new Card(1));
        CardDeck deck2_2 = new CardDeck();
        deck2_2.setDeckName(1);
        deck2_2.addCard(new Card(2));
        deck2_2.addCard(new Card(2));
        deck2_2.addCard(new Card(2));
        deck2_2.addCard(new Card(2));
        CardDeck deck2_3 = new CardDeck();
        deck2_3.setDeckName(2);
        deck2_3.addCard(new Card(3));
        deck2_3.addCard(new Card(3));
        deck2_3.addCard(new Card(3));
        deck2_3.addCard(new Card(3));
        player2_1.setDecks(deck2_1, deck2_2);
        player2_2.setDecks(deck2_2, deck2_3);
        player2_3.setDecks(deck2_3, deck2_1);

        player2_1.start();
        player2_2.start();
        player2_3.start();
        try{
            player2_1.join();
            player2_2.join();
            player2_3.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        assertEquals(1, player2_1.getDenomination(0));
        assertEquals(1, player2_1.getDenomination(1));
        assertEquals(1, player2_1.getDenomination(2));
        assertEquals(1, player2_1.getDenomination(3)); // player 1 should have 4 of denomination 1
        assertEquals(2, player2_2.getDenomination(0));
        assertEquals(4, player2_2.getDenomination(1));
        assertEquals(2, player2_2.getDenomination(2));
        assertEquals(2, player2_2.getDenomination(3));
        assertEquals(3, player2_3.getDenomination(0));
        assertEquals(4, player2_3.getDenomination(1));
        assertEquals(3, player2_3.getDenomination(2));
        assertEquals(3, player2_3.getDenomination(3)); // player 3 should have 1,2,3,4
        assertEquals(1, deck2_1.getDenomination(0));
        assertEquals(1, deck2_1.getDenomination(1));
        assertEquals(1, deck2_1.getDenomination(2));
        assertEquals(1, deck2_1.getDenomination(3));
        assertEquals(2, deck2_2.getDenomination(0));
        assertEquals(2, deck2_2.getDenomination(1));
        assertEquals(2, deck2_2.getDenomination(2));
        assertEquals(2, deck2_2.getDenomination(3));
        assertEquals(3, deck2_3.getDenomination(0));
        assertEquals(3, deck2_3.getDenomination(1));
        assertEquals(3, deck2_3.getDenomination(2));
        assertEquals(3, deck2_3.getDenomination(3)); 
    }

    @Test
    public void testOutputToFile(){
        player = new Player(counter, 4);
        player.SetName(3);
        File f = new File("../txt/Player4_output.txt");
        if (f.exists()) f.delete(); // Ensure clean state
        player.outputToFile("TestOutput\n");
        player.outputToFile("TestOutput2\n"); // Write 2 lines to a blank file "../txt/Player4_output.txt"
        try (Scanner file = new Scanner(new File("../txt/Player4_output.txt"))){
            String line = file.nextLine();
            assertEquals("TestOutput", line);
            line = file.nextLine();
            assertEquals("TestOutput2", line);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckWinner(){
        player = new Player(counter, 4);
        player.addCard(new Card(1));
        player.addCard(new Card(1));
        player.addCard(new Card(1));
        player.addCard(new Card(1));
        assertTrue(player.checkWinner());

        player.removeCard(0);
        player.addCard(new Card(2));
        assertFalse(player.checkWinner());
    }
}