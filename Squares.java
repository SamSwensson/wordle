package wordle;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class Squares extends JPanel {
	public Squares() {
		setLayout(new GridLayout(5,6));
	}
}
