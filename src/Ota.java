import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Ota extends JFrame implements ActionListener {
	public static final int width = 300;
	public static final int height = 170;
	
	
	public Ota(String versionString) {
		super("New Version");
		setSize(width, height);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		JLabel authorJLabel = new JLabel("<html>" + versionString + "</html>");
		add(authorJLabel , BorderLayout.NORTH);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(this);
		add(okButton, BorderLayout.SOUTH);
	}
 
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
	}
}
