package wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

public class WordleApp {
    public static void main(String[] args) {

 
    	File fileName = new File("testCrane.txt");
         
        List<String> listWords = new ArrayList<>();
        try (Scanner scanner = new Scanner(fileName)) {
            while (scanner.hasNextLine()) {
                listWords.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        
        String[] words = listWords.toArray(new String[0]);
 
        // Create the game with 6 attempts
        WordleGame game = new WordleGame(words, 6);
        
        WordleGUI gui = new WordleGUI(game);
    	gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
 
    }
}
