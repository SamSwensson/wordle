package wordle;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JPanel;


public class KeyPad extends JPanel implements ActionListener{
  private final static int NBUTTONS = 27;
  private KeyPadClient kpc;  
  private JButton buttons[];
  private String labels[] =  // An array of button labels
                { "Q","W","E","R","T","Y","U","I","O","P",
                  "A","S","D","F","G","H","J","K","L",
                  "Z","X","C","V","B","N","M","CLEAR"};
  public KeyPad(KeyPadClient kpc) {
	setLayout(new GridLayout(3,9,1,1));
	setPreferredSize(new Dimension(600, 200)); 
    JPanel top = new JPanel();
    add(top);
    JPanel middle = new JPanel();
    add(middle);
   JPanel bottom = new JPanel();
   add(bottom);
 
   
   
    this.kpc = kpc;
    buttons = new JButton[NBUTTONS];         // Create the array
    for(int k = 0; k < 10; k++) { // For each button
      buttons[k] = new JButton(labels[k]);   //  Create a button
      buttons[k].addActionListener(this);     //  and a listener
      top.add(buttons[k]);                    // and add it to panel
    }
    for (int i = 10;i<19; i++) {
    	buttons[i] = new JButton(labels[i]);
    	buttons[i].addActionListener(this);
    	middle.add(buttons[i]);
    }
    for(int l = 19; l <NBUTTONS; l++) {
    	buttons[l] = new JButton(labels[l]);
    	buttons[l].addActionListener(this);
    	bottom.add(buttons[l]);
    }
    // for
  } // KeyPad()
  
  public void actionPerformed(ActionEvent e) {
    String keylabel = ((JButton)e.getSource()).getText();
    kpc.keypressCallback(keylabel);
  } // actionPerformed()
} // KeyPad