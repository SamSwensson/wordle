package wordle; 

import java.util.Random;
import java.util.Scanner;
/**
* Represents the Wordle game logic where the player tries to guess a target word within a specified number of attempts.
* The game provides feedback on each guess to help the player get closer to the correct word.
*/
public class WordleGame {
    private String targetWord;
    private String[] wordList;
    private int maxAttempts;
    private int attempts;
    private boolean gameWon;
    /**
     * Constructor to initialize the Wordle game.
     * @param wordList an array of possible words that can be chosen as the target word.
     * @param maxAttempts The maximum number of attempts the player has to guess the word.
     */
    public WordleGame(String[] wordList, int maxAttempts) {
        this.wordList = wordList;
        this.maxAttempts = maxAttempts;
        this.attempts = 0;
        this.gameWon = false;
        chooseRandomWord();
    }
    public boolean isGameWon() {
    	return gameWon;
    }
    public String getTargetWord() {
    	return targetWord;
    }
    /**
     *
     * Chooses a random word from the word list as the target word for the game.
     * This method is called when game is initialized.
     */
    private void chooseRandomWord() {
        Random rand = new Random();
        this.targetWord = wordList[rand.nextInt(wordList.length)];
    }
 
    /**
     * Processes the player's guess and provides feedback on whether the guess is correct,
     * or in the correct position, or not correct at all
     * @param guess The word guessed by the player
     * @return a string indicating the feedback
     */
    public String processGuess(String guess) {
        if (guess.length() != targetWord.length()) {
            return "Invalid guess length!";
        }
        if (!guess.matches("[a-zA-z]+")) {
        	return "Please enter a letter character";
        }
        attempts++;
       if (guess.equalsIgnoreCase(targetWord)) {
           gameWon = true;
           return "Correct! You've guessed the word.";
        }
        // Generate feedback
        String upper = targetWord.toUpperCase();
        
        String feedback = "";
        for (int i = 0; i < upper.length(); i++) {
            if (guess.charAt(i) == upper.charAt(i)) {
                feedback += "G"; // Green for correct position
            } else if (upper.contains(String.valueOf(guess.charAt(i)))) {
                feedback += "Y"; // Yellow for correct but wrong position
            } else {
                feedback += "B"; // Gray for not in the word
            }
        }
        return feedback;
    }
    /**
     * Start the Wordle game, prompting the user for guesses and providing feedback until the game is over.
     */
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Wordle!");
        System.out.println("You have " + maxAttempts + " attempts to guess the word.");
        System.out.println("Feedback: G = Green (Correct), Y = Yellow (Wrong Position), B = Gray (Not in Word)");
        System.out.println("The word has " + targetWord.length() + " letters.\n");
        while (!isGameOver()) {
            System.out.print("Enter your guess: ");
            String guess = scanner.nextLine();
            String feedback = processGuess(guess);
            System.out.println("Feedback: " + feedback);
            if (gameWon) {
                System.out.println("Congratulations! You've guessed the word!");
                break;
            }
        }
        if (!gameWon) {
            System.out.println("Game Over. The word was: " + targetWord);
        }
    }
    /**
     * Checks whether the game is over.
     * The game is over if the player has guessed the word correctly or has used up all attempts.
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameWon || attempts >= maxAttempts;
    }
}