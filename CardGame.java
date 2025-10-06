/*
1. takes input (n_of_players, pack_file_path)
2. convert the string to int, then to Card
3. store Cards in CardDeck obj called pack 
4. distribute Cards to players from index 0, 1 by 1
5. 

*/
import java.util.Scanner;
import java.util.ArrayList; 

public class CardGame {
    static int n_of_players;
    static ArrayList<Player> players = new ArrayList<Player>();
    
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
            Player player = new Player();
            players.add(player);
        }
        
        // Distribute cards to players
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < n_of_players; j++){
                Card card = pack.drawCard();
                players.get(j).addCard(card);
            }
        }

        // Check for winner
        
    

    }

    public static boolean checkWinner(Player player){
        return player.getDenomination(0) == player.getDenomination(1) &&
               player.getDenomination(1) == player.getDenomination(2) &&
               player.getDenomination(2) == player.getDenomination(3);
    }

    public static int determineWinner(){
        for (int i = 0; i < n_of_players; i++){
            if (checkWinner(players.get(i))){
                return i;
            }
        }
        return -1; // no winner
    }
}

