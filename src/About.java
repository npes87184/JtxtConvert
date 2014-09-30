import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class About extends JFrame implements ActionListener {
	
	public static final int width = 200;
	public static final int height = 120;
	
	JLabel authorJLabel = new JLabel("Author:npes87184");
	public About() {
		super("About");
		setSize(width, height);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(3,1));
		add(authorJLabel);
		JLabel versionJLabel = new JLabel("Version:1.0");
		add(versionJLabel);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(this);
		add(okButton);
	}
 
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
	}
}
