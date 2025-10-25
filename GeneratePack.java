import java.io.FileWriter; 
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GeneratePack{
    public static void main(String[] args){
        Random r= new Random();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of players:");
        int n_of_players = scanner.nextInt();
        String path = "pack.txt";
        try (FileWriter writer = new FileWriter(path)){
            for (int i = 0; i < 8 * n_of_players; i++){
                int randomInt = r.nextInt(0, 2*n_of_players-1); // Each card denomination is a random int between 0 and 2*n_of_players-1
                writer.write(Integer.toString(randomInt) + "\n");
            }
            System.out.println("Pack file for " + n_of_players + " players generated at: " + path);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}