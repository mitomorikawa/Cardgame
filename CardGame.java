package cardgame;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class CardGame {
    static int n_of_players;
    static ArrayList<Player> players = new ArrayList<Player>();
    static ArrayList<CardDeck> ListCardDecks = new ArrayList<CardDeck>();
    
    public static void readFile(){
        System.out.println("Please enter the number of players");
        Scanner inputScanner = new Scanner(System.in);
        String n_of_players_str = inputScanner.nextLine();
        
        // Ensure n_of_players represents Int

        try {
            n_of_players = Integer.parseInt(n_of_players_str);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer for the number of players.");
            return; // Exit the method if input is invalid
        }
    }

    public static void main(String[] args){
        Counter turns = new Counter();
        readFile();

        ArrayList<Integer> pack_int = new ArrayList<Integer>();
        File file = new File("../txt/pack.txt");

        try (Scanner fileScanner = new Scanner(file)){
        for (int i = 0; i < 8 * n_of_players; i++){
            try {
                
                if (!fileScanner.hasNextLine() && i < 8 * n_of_players - 1){
                    throw new EOFException("The pack file has fewer cards than expected. Generate a correct pack file by running GeneratePack.java with the correct number of players.");
                }
                String cardStr = fileScanner.nextLine();
                int cardInt = Integer.parseInt(cardStr);
                if (cardInt < 0) throw new NumberFormatException("Found a negative integer in the pack file.");
                pack_int.add(cardInt);
            } catch (EOFException e){
                System.err.println(e.getMessage());
                return;
            } catch (NumberFormatException e){
                System.err.println("Provide a valid pack file. " + e.getMessage());
                return;
            }
        }
        } catch (FileNotFoundException e){
            System.err.println("Pack file not found: " + file.getAbsolutePath());
            return;
        }


        CardDeck pack = new CardDeck();
        for (int i = 0; i < pack_int.size(); i++){
            Card card = new Card(pack_int.get(i));
            pack.addCard(card);
        }

        
        for (int i = 0; i < n_of_players; i++){
            Player player = new Player(turns, n_of_players);
            players.add(player);
        }

        for (int i = 0; i < n_of_players; i++){ // making card decks
            CardDeck Card_Deck = new CardDeck();
            ListCardDecks.add(Card_Deck);
        }
        
        // Distribute cards to players
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < n_of_players; j++){
                Card card = pack.drawCard();
                players.get(j).addCard(card);
            }
        }

        // Distribute cards to decks 
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < n_of_players; j++){
                Card card = pack.drawCard();
                ListCardDecks.get(j).addCard(card);
            }
        }

        // Set names for players and decks
        for (int i = 0; i < n_of_players; i++){
            players.get(i).SetName(i);
            ListCardDecks.get(i).setDeckName(i);
        }

        for (int i = 0; i < n_of_players; i++){
            if (i == n_of_players - 1){
                players.get(i).setDecks(ListCardDecks.get(i), ListCardDecks.get(0));
            }
            else{
                players.get(i).setDecks(ListCardDecks.get(i), ListCardDecks.get(i + 1));
            }
            }
            
        //debug
        /*for (int i = 0; i < n_of_players; i++){
            System.out.println(players.get(i).get_name() + ": " + players.get(i).getDeckPull().getDeckName() + " " + players.get(i).getDeckPush().getDeckName());
        }*/

        // make files for each of the players
        for (int i = 0; i < players.size(); i++) {
            try (FileOutputStream output = new FileOutputStream("../txt/" + players.get(i).get_name() + "_output.txt", false)) {
            } catch (IOException e){
                System.out.println("Error");
                e.printStackTrace();
            }
        }

        for (int i = 0; i < players.size(); i++) { // adding initial hand to output file
            String initialHand = players.get(i).get_name() + " initial hand " + players.get(i).readCardInHand(0) + " " + players.get(i).readCardInHand(1) + " " + players.get(i).readCardInHand(2) + " " + players.get(i).readCardInHand(3) + "\n";
            players.get(i).outputToFile(initialHand);
        }

        if (determineWinner() != -1) {
            writeToFiles(determineWinner());
            System.out.println(players.get(determineWinner()).get_name() + " wins");
        } else {
            for(int i = 0; i < players.size(); i++){
                players.get(i).start();
            }
            for(int i = 0; i < players.size(); i++){
                try {
                    players.get(i).join();
                } catch (InterruptedException e) {   
                }
            }
            writeToFiles(determineWinner());
            System.out.println(players.get(determineWinner()).get_name() + " wins");
        }

        for (int i = 0; i < ListCardDecks.size(); i++) {
            String deckContents = ListCardDecks.get(i).getDeckName() + " contents: " + ListCardDecks.get(i).getDenomination(0) + " " + ListCardDecks.get(i).getDenomination(1) + " " + ListCardDecks.get(i).getDenomination(2) + " " + ListCardDecks.get(i).getDenomination(3) + "\n";
            try (FileOutputStream output = new FileOutputStream("../txt/" + ListCardDecks.get(i).getDeckName() + "_output.txt", false)) {
                byte[] deckContentsBytes = deckContents.getBytes();
                output.write(deckContentsBytes);
            } catch (IOException e){
                System.out.println("Error");
                e.printStackTrace();
            }
        }
    }

/*         // Check for winner
        if (determineWinner() != -1) {
            return;
        } else {
            return;
        }
*/
    public static void writeToFiles(int WinnerIndex) {
        for (int i = 0; i < players.size(); i++) {
            if (i == WinnerIndex) {
                String currentName = players.get(i).get_name();
                String finalMessage = currentName + " wins\n" + currentName + " exits\n" + currentName + " final hand: " + players.get(i).readCardInHand(0) + " " + players.get(i).readCardInHand(1) + " " + players.get(i).readCardInHand(2) + " " + players.get(i).readCardInHand(3) + "\n";
                players.get(i).outputToFile(finalMessage);
            } else {
                String currentName = players.get(i).get_name();
                String finalMessage = players.get(WinnerIndex).get_name() + " has informed " + currentName + " that " + players.get(WinnerIndex).get_name() + " has won\n" + currentName + " exits\n" + currentName + " hand: " + players.get(i).readCardInHand(0) + " " + players.get(i).readCardInHand(1) + " " + players.get(i).readCardInHand(2) + " " + players.get(i).readCardInHand(3) + "\n";
                players.get(i).outputToFile(finalMessage);
            }
        }
    }

    public static int determineWinner(){
        for (int i = 0; i < n_of_players; i++){
            if (players.get(i).checkWinner()){
                return i;
            }
        }
        return -1; // no winner
    }

    public void endGame(int winner){
        System.out.println(players.get(winner).get_name() + " wins");
    }
}
