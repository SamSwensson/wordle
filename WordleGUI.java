package wordle;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.*;
 
import javax.swing.*;
 
 
/**
* WordleGUI class provides the graphical user interface for a Wordle game.
* It extends JFrame Class and implements ActionListener and KeyPadClient interfaces.
* This class sets up the game layout, handles user input and game feedback,
* and interacts with the WordleGame class.
*/
public class WordleGUI extends JFrame implements ActionListener, KeyPadClient {
    
	private JLabel title = new JLabel("WORDLE");
    private JLabel intro = new JLabel("Get 6 chances to guess a 5-letter word.");
    private JTextField guess = new JTextField();
    private JButton enter = new JButton("ENTER");
    private KeyPad keypad = new KeyPad(this);
    private String[] words;
    
    private JTextArea[] squareDisplays = new JTextArea[30];
    private WordleGame game; // The game logic
    int squareIndex = 0; // Index for displaying letter
    int colorIndex = 0; // Index for coloring feedback squares
    
    private JFrame popUpFrame; // Frame for pop-up messages
    private int numberOfGuesses; // Number of guesses made so far
 
    
    /**
     * Constructor
     * Sets up the game UI, initializes the word list, and configures the frame
     * @param game the instance of the WordleGame used for game logic
     */
    public WordleGUI(WordleGame game) {
    	    	
    	this.game = game;
    	File fileName = new File("FiveLetterWords.txt");
    	
    	List<String> listWords = new ArrayList<>();
        try (Scanner scanner = new Scanner(fileName)) {
            while (scanner.hasNextLine()) {
                listWords.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    	
        words = listWords.toArray(new String[0]);
        
        // Frame setup
        setTitle("Wordle Game");
        title.setHorizontalAlignment(JLabel.CENTER);
        intro.setHorizontalAlignment(JLabel.CENTER);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);
        keypad = new KeyPad(this);
        
        // Font for title and intro
        title.setFont(new Font("Serif", Font.BOLD, 30));
        
        // Initialize the display areas (5x6 grid)
        for (int i = 0; i < 30; i++) {
            squareDisplays[i] = new JTextArea(1, 5);
            squareDisplays[i].setEditable(false);
            squareDisplays[i].setFont(new Font("Serif", Font.BOLD, 30));
            squareDisplays[i].setBackground(Color.WHITE);
        }
 
        // Layout setup
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel();
        
 
        
        JPanel topCenter = new JPanel(new GridLayout(6, 5, 5, 5));  // 6x5 grid for letters
        topCenter.setPreferredSize(new Dimension(800,500));
        topCenter.setBorder(BorderFactory.createEmptyBorder(20, 500, 20, 500));
        JPanel bottomCenter = new JPanel(new FlowLayout());  // Keypad and guess bar
        bottomCenter.setBorder(BorderFactory.createEmptyBorder(20, 300, 10, 300));
        
        northPanel.setPreferredSize(new Dimension(600, 100));
        southPanel.setPreferredSize(new Dimension(600, 100));
        
        // North panel setup (Title and intro)
        northPanel.add(title, BorderLayout.CENTER);
        JPanel introPanel = new JPanel();
       // introPanel.add(intro);
        northPanel.add(intro, BorderLayout.SOUTH);
        
        // Center panel setup (Top for word grid, Bottom for keypad)
        centerPanel.add(topCenter, BorderLayout.NORTH);
        centerPanel.add(bottomCenter, BorderLayout.SOUTH);
        bottomCenter.setPreferredSize(new Dimension(0,200));
 
        // Add each JTextArea (5x6 grid for letters) to topCenter
        for (JTextArea display : squareDisplays) {
            topCenter.add(display);
        }
 
        // Bottom panel setup (Keypad + Guess + Enter button)
        
        guess.setPreferredSize(new Dimension(540,20));
        enter.setPreferredSize(new Dimension(540,20));
        keypad.setPreferredSize(new Dimension(1000,100));
        bottomCenter.add(guess);
        bottomCenter.add(keypad, BorderLayout.CENTER);
        bottomCenter.add(enter, BorderLayout.SOUTH);
         
        // Add panels to frame
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
 
        // Listeners for Enter button and guess field
        enter.addActionListener(this);
        guess.addActionListener(this);
        
      //Popup - used Geeks for Geeks
        JFrame popUpFrame = new JFrame("frame");
	    JPanel popUp = new JPanel();
	    popUpFrame.add(popUp);
	    popUpFrame.setSize(800, 800);
    }
    
    /**
     * Handles actions performed on the enter button or the guess field.
     * Processes the user's guess, updates the display, and shows feedback.
     *
     * @param e The ActionEvent triggered when a button or field is interacted with.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	final Color GREEN = new Color(106, 170, 100);
        final Color YELLOW = new Color(232, 205, 85);
        final Color GRAY = new Color(120, 124, 126);
        String formatGuess = null;
        
        String userGuess = guess.getText();
        
        if (userGuess.matches("[a-zA-z]+")) {
        	formatGuess = userGuess.substring(0, 5).toUpperCase();
        }
      
        char[] charArray = formatGuess.toCharArray();
        
        for (int i = 0; i < charArray.length; i++) {
            char letter = charArray[i];
            String strLetter = String.valueOf(letter);
            squareDisplays[squareIndex].append("     " + strLetter);
            squareIndex++;
        }
 
        // Process the guess and get feedback
        String feedback = game.processGuess(formatGuess);
        char[] colors = feedback.toCharArray();
        numberOfGuesses++;
        System.out.println("Feedback: " + feedback);
        
        // Show popup if the game is won or lost
        if (game.isGameWon() == true) {
        	JDialog won = new JDialog(popUpFrame, "Congrats!");
        	JLabel l = new JLabel("CORRECT! YOU GUESSED THE WORD! ◝(ᵔᗜᵔ)◜",SwingConstants.CENTER);
        	l.setFont(new Font("Serif", Font.BOLD, 20));
        	won.add(l);
        	won.setLocationRelativeTo(null);
        	won.setLocation(500,150);
        	won.setSize(600,600);
        	won.setVisible(true);	
        }
        if (numberOfGuesses==6 && game.isGameWon() == false){
        	JDialog lost = new JDialog(popUpFrame, "Oh no!");
        	lost.setFont(new Font("Serif", Font.BOLD, 30));
        	JLabel l2 = new JLabel("GAME OVER! THE WORD WAS: " + game.getTargetWord(),SwingConstants.CENTER);
        	lost.add(l2);
        	lost.setLocationRelativeTo(null);
        	lost.setLocation(500,150);
        	lost.setSize(600,600);
        	lost.setVisible(true);
        }
        
        // Update the display colors based on feedback
        for (int j = 0; j < feedback.length(); j++) {
            if (colors[j] == 'G') {
                squareDisplays[colorIndex].setBackground(GREEN);
            } else if (colors[j] == 'Y') {
                squareDisplays[colorIndex].setBackground(YELLOW);
            } else if (colors[j] == 'B') {
                squareDisplays[colorIndex].setBackground(GRAY);
            }
            colorIndex++;
        }
        
        // Clear the guess field
        guess.setText("");
}
    /**
     * Callback method for the keypad to handle key presses.
     * Adds the pressed key to the guess field or clears it if "CLEAR" is pressed.
     *
     * @param s The key pressed (either a letter or "CLEAR").
     */
  public void keypressCallback(String s) {
	    if (s.equals("CLEAR")) {
	      guess.setText("");
	    }
	    else
	      guess.setText(guess.getText() + s);
	  }
}
 