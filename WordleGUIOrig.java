
	package wordle;

	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Font;
	import java.awt.GridLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.List;
	import java.util.Scanner;

	import javax.swing.JButton;
	import javax.swing.JDialog;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import javax.swing.JTextArea;
	import javax.swing.JTextField;
	import javax.swing.SwingConstants;

	public class WordleGUIOrig extends JFrame implements ActionListener,KeyPadClient {
			private JLabel title = new JLabel("WORDLE");
			private JLabel intro = new JLabel("Get 6 chances to guess a 5-letter word.");
			private JTextField guess = new JTextField();
			private JFrame frame = new JFrame();
			private JButton enter = new JButton("ENTER");
			private KeyPad keypad = new KeyPad(this);
			private String[] words = new String[50];
			
			private JTextArea[] displays = new JTextArea[30];
			JTextArea[] squareDisplays = new JTextArea[30]; 
			
			private WordleGame game; 
			int squareIndex = 0;
			int colorIndex = 0; 
			
			private JFrame popUpFrame; 
			private int numberOfGuesses; 

		
		WordleGUIOrig(WordleGame game){
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
			
			title.setFont(new Font("Serif", Font.BOLD,30));
			title.setHorizontalAlignment(JLabel.CENTER);
			 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 frame.setSize(500,500);
			 frame.setLayout(new BorderLayout());
			 frame.setVisible(true);
		   //  keypad = new KeyPad(this);
		     
		     for (int i = 0; i < 30; i++) {
		    	 displays[i] = new JTextArea();
		     }
		     
		     for (int i = 0; i < displays.length; i++) {
		    	 squareDisplays[i] = displays[i];
		     }
		     
		   
		     for (JTextArea display: squareDisplays) {
		    	 display.setFont(new Font("Serif", Font.BOLD,30)); 
		     }
		    
		      //Initial Layout 
		      JPanel northPanel = new JPanel();
		      JPanel centerPanel = new JPanel();
		      JPanel southPanel = new JPanel();
		      JPanel eastPanel = new JPanel();
		      JPanel westPanel = new JPanel();
		      
		      //Center
		      JPanel topCenter = new JPanel(); //boxes
		      JPanel bottomCenter = new JPanel(); //keys
		      
		      //North
		      JPanel titleCenter = new JPanel();
		      JPanel introSouth = new JPanel();
		
		     //Colors to check sizes 
//		      titleCenter.setBackground(Color.red);
//		      southPanel.setBackground(Color.cyan);
//		      eastPanel.setBackground(Color.orange);
//		      westPanel.setBackground(Color.green);
//		      centerPanel.setBackground(Color.yellow);
//		      topCenter.setBackground(Color.pink);
//		      bottomCenter.setBackground(Color.red)
		      
		      southPanel.setPreferredSize(new Dimension(600, 100));

		    
		      northPanel.setPreferredSize(new Dimension(200,100));
		      southPanel.setPreferredSize(new Dimension(50,50));
		      eastPanel.setPreferredSize(new Dimension(500,10));
		      westPanel.setPreferredSize(new Dimension(500,10));
		      topCenter.setPreferredSize(new Dimension(300,400));
		      bottomCenter.setPreferredSize(new Dimension(500,300));
//		      keypad.setBackground(Color.pink);
		          
		      ///NorthPanel Formatting 
		      northPanel.setLayout(new BorderLayout());
		      northPanel.add(titleCenter,BorderLayout.CENTER);
		      northPanel.add(introSouth,BorderLayout.SOUTH);
		      titleCenter.add(title,BorderLayout.CENTER);
		      introSouth.setPreferredSize(new Dimension(40,50));
		      introSouth.add(intro, BorderLayout.SOUTH);
		      
		      //CenterPanel Formatting 
		      centerPanel.setLayout(new BorderLayout());
		      centerPanel.add(topCenter,BorderLayout.NORTH); //boxes
		      centerPanel.add(bottomCenter,BorderLayout.SOUTH); //keypad
		      
		      //TopCenter Formatting 
		      //Squares
		      topCenter.setLayout(new GridLayout(6,2,5,5));
		      topCenter.setPreferredSize(new Dimension(50,400));
		      for (JTextArea display: squareDisplays) {
		    	  display.setSize(new Dimension(50,50));
		    	  topCenter.add(display);
		      }
		      
		      //BottomCenter Formatting
		     // bottomCenter.setLayout(new GridLayout(3,1));
		      bottomCenter.add(guess);
		      guess.setPreferredSize(new Dimension(475,20));
		      enter.setPreferredSize(new Dimension(475,20));
		      bottomCenter.add(keypad);
		      bottomCenter.add(enter);
		     
		      //Frame Formatting
		      frame.add(northPanel,BorderLayout.NORTH);
		      frame.add(centerPanel,BorderLayout.CENTER);
		      frame.add(westPanel,BorderLayout.WEST);
		      frame.add(eastPanel,BorderLayout.EAST);
		      frame.add(southPanel,BorderLayout.SOUTH);
		
		      enter.addActionListener(this);
		      guess.addActionListener(this);
		      
		      //Popup - used Geeks for Geeks
		      JFrame popUpFrame = new JFrame("frame");
		      JPanel popUp = new JPanel();
		      popUpFrame.add(popUp);
		      popUpFrame.setSize(400, 400);
		      
		}
		 @Override
		    public void actionPerformed(ActionEvent e) {
		        String userGuess = guess.getText();
		        String formatGuess = userGuess.substring(0, 5).toUpperCase();
		        char[] charArray = formatGuess.toCharArray();
		        
		        for (int i = 0; i < charArray.length; i++) {
		            char letter = charArray[i];
		            String strLetter = String.valueOf(letter);
		            squareDisplays[squareIndex].append("     " + strLetter);
		            squareIndex++;
		        }

		        String feedback = game.processGuess(formatGuess);
		        char[] colors = feedback.toCharArray();
		        numberOfGuesses++;
		        System.out.println("Feedback: " + feedback);
		        
		        
		        // Define colors
		        final Color GREEN = new Color(106, 170, 100);
		        final Color YELLOW = new Color(232, 205, 85);
		        final Color GRAY = new Color(120, 124, 126);
		   
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
		        if (game.isGameWon() == true) {
		        	JDialog won = new JDialog(popUpFrame, "Congrats!");
		        	JLabel l = new JLabel("Correct! You guessed the word :)",SwingConstants.CENTER);
		        	won.add(l);
		        	won.setLocationRelativeTo(null);
		        	won.setSize(300,200);
		        	won.setVisible(true);
		        	for (int j = 5; j < 0; j--) {
		        		 squareDisplays[colorIndex-j].setBackground(GREEN);
		        	 }
		        }
		        if (numberOfGuesses==6 && game.isGameWon() == false){
		        	JDialog lost = new JDialog(popUpFrame, "Oh no!");
		        	JLabel l = new JLabel("Game over! The word was: " + game.getTargetWord(),SwingConstants.CENTER);
		        	lost.add(l);
		        	lost.setLocationRelativeTo(null);
		        	lost.setSize(300,200);
		        	lost.setVisible(true);
		        }
		        guess.setText("");
		 }
		  public void keypressCallback(String s) {
			    if (s.equals("CLEAR")) {
			      guess.setText("");
			    }
			    else
			      guess.setText(guess.getText() + s);
			  }
	}



