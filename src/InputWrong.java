import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class InputWrong extends JFrame implements ActionListener {
	public static final int width = 200;
	public static final int height = 100;
	
	public InputWrong() {
		super("Wrong");
		setSize(width, height);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(2,1));
		JLabel label = new JLabel("  Did not enter fully¡I");
		add(label);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(this);
		add(okButton);
	}
	 
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
	}
}
