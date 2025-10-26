package cardgame.test;

import cardgame.Player;
import cardgame.Counter;
import cardgame.Card;
import cardgame.CardDeck;
import cardgame.CardGame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.FileWriter; 
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class TestCardGame {
    @Test
    public void testMain(){
        String[] args = null;
        String path = "../txt/pack.txt";
        int n_of_players = 3;
        int[] pack = {1,3,1,1,3,1,2,2,3,2,4,4,1,2,3,1,2,3,1,2,3,1,2,3};
        String[] expectedDeckFileLine = {"Deck1 contents: 1 1 1 1", "Deck2 contents: 2 2 2 2", "Deck3 contents: 3 3 3 3"};
        String[][] expectedPlayerFileLine
            = {{"Player1 initial hand 1 1 2 2","Player1 draws a 1 from Deck1","Player1 discards a 2 to Deck2", 
                "Player1 current hand is 1 1 2 1", "Player1 draws a 1 from Deck1","Player1 discards a 2 to Deck2",
                "Player1 current hand is 1 1 1 1", "Player1 wins", "Player1 exits","Player1 final hand: 1 1 1 1"}, 
                {"Player2 initial hand 3 3 2 4","Player2 draws a 2 from Deck2","Player2 discards a 3 to Deck3", 
                "Player2 current hand is 3 2 4 2", "Player2 draws a 2 from Deck2","Player2 discards a 3 to Deck3",
                "Player2 current hand is 2 4 2 2", "Player1 has informed Player2 that Player1 has won", "Player2 exits","Player2 hand: 2 4 2 2"},
                {"Player3 initial hand 1 1 3 4","Player3 draws a 3 from Deck3","Player3 discards a 1 to Deck1", 
                "Player3 current hand is 1 3 4 3", "Player3 draws a 3 from Deck3","Player3 discards a 1 to Deck1",
                "Player3 current hand is 3 4 3 3", "Player1 has informed Player3 that Player1 has won", "Player3 exits","Player3 hand: 3 4 3 3"}};
        try (FileWriter writer = new FileWriter(path)){
            for (int i : pack){
                writer.write(Integer.toString(i) + "\n");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        String input = n_of_players + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        CardGame.main(args);
        for (int i = 0; i < n_of_players; i++){
            try (Scanner deckFile = new Scanner(new File("../txt/Deck"+(i+1)+"_output.txt"))){
                String deckFileLine = deckFile.nextLine();
                assertEquals(expectedDeckFileLine[i], deckFileLine);
            } catch (Exception e){
            e.printStackTrace();
            }
            try (Scanner playerFile = new Scanner(new File("../txt/Player"+(i+1)+"_output.txt"))){
                for (int j = 0; j < expectedPlayerFileLine[i].length; j++){
                    String playerFileLine = playerFile.nextLine();
                    assertEquals(expectedPlayerFileLine[i][j], playerFileLine);
                }
            }catch (Exception e){
            e.printStackTrace();
            }
        }
    }

}