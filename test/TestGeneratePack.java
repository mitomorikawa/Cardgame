package cardgame.test;

import cardgame.GeneratePack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Scanner;

/*
1. Test the length of the generated pack file.
2. Test that all card denominations are integers within the expected range.
 */
public class TestGeneratePack {
    @Test
    public void testMain(){
        String[] args = null;
        String input = "17\n"; 
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        GeneratePack.main(args);
        try (Scanner fileScanner = new Scanner(new File("pack.txt"))){
            int length = 0;
            while(fileScanner.hasNextLine()){
                String cardStr = fileScanner.nextLine();
                int cardInt = Integer.parseInt(cardStr);
                assertTrue(cardInt >= 0 && cardInt < 34); // For 17 players, denominations should be between 0 and 33
                length++;
            }

            assertEquals(136, length);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}