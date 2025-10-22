/*
1. takes input (n_of_players, pack_file_path)
2. convert the string to int, then to Card
3. store Cards in CardDeck obj called pack
4. distribute Cards to players from index 0, 1 by 1
5. 

*/
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

        System.out.println("Please enter the path to the pack file");
        //Scanner inputScanner = new Scanner(System.in);
        //System.out.println("");

    }

    public static void main(String[] args){
        Counter turns = new Counter();
        readFile();

        ArrayList<Integer> pack_int = new ArrayList<Integer>();
        for (int i = 0; i <  2 * n_of_players; i++){
            pack_int.add(1);
            pack_int.add(2);
            pack_int.add(3);
            pack_int.add(4);
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
        
        for (int i = 0; i < n_of_players; i++){
            System.out.println(players.get(i).get_name() + ": " + players.get(i).getDeckPull().getDeckName() + " " + players.get(i).getDeckPush().getDeckName());
        }
        // make files for each of the players
        for (int i = 0; i < players.size(); i++) {
            try (FileOutputStream output = new FileOutputStream(players.get(i).get_name() + "_output.txt", false)) {
            } catch (IOException e){
                System.out.println("Error");
                e.printStackTrace();
            }
        }

        for(int i = 0; i < players.size(); i++){
            players.get(i).start();
        }
    }

/*         // Check for winner
        if (determineWinner() != -1) {
            return;
        } else {
            return;
        }
*/
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
