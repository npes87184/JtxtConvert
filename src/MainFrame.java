import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MainFrame extends JFrame implements ActionListener {
	
	public static final int width = 400;
	public static final int height = 200;
	
	JTextField inputField = new JTextField("", 20);
	JTextField outputField = new JTextField("", 20);

	String[] charsetsToBeTested = {"UTF-8"};
	
	public MainFrame() {
		super("JtxtConvert");
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3,1));
		
		JPanel panel1 = new JPanel();
		JLabel inputLabel = new JLabel("Input:");
		JButton srcButton = new JButton("Choose");
		srcButton.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		    	 JFileChooser chooser = new JFileChooser();
		         chooser.setMultiSelectionEnabled(false);
		         chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		         chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		         if (chooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION) {
		              inputField.setText((chooser.getSelectedFile().getAbsolutePath()));
		          }
		     }
		});
		panel1.add(inputLabel);
		panel1.add(inputField);
		panel1.add(srcButton);
		
		JPanel panel2 = new JPanel();
		JLabel outputLabel = new JLabel("Output:");
		JButton outButton = new JButton("Choose");
		outButton.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		    	 JFileChooser chooser = new JFileChooser();
		         chooser.setMultiSelectionEnabled(false);
		         chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		         chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		         if (chooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION) {
		              outputField.setText((chooser.getSelectedFile().getAbsolutePath()));
		          }
		     }
		});
		panel1.add(outputLabel);
		panel1.add(outputField);
		panel1.add(outButton);
		
		add(panel1);
		add(panel2);
		
		JButton startButton = new JButton("START");
		startButton.addActionListener(this);
		
		JButton aboutButton = new JButton("About");
		aboutButton.addActionListener(this);
		
		JPanel panel3 = new JPanel();
		panel3.add(startButton);
		panel3.add(aboutButton);
		add(panel3);
	}
	
	public void actionPerformed(ActionEvent e) {		
		if(e.getActionCommand().equals("START")) {
			if(inputField.getText().equals("") || outputField.getText().equals("")) {
				InputWrong inputWrong = new InputWrong();
				inputWrong.setVisible(true);
			}
			else {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							File inFile = new File(inputField.getText());
							InputStream is = new FileInputStream(inFile);
							String encodeString = "UTF-8";
							boolean first = true;
							String bookName = "out";

						    Charset charset = detectCharset(inFile, charsetsToBeTested);  
						    if (charset == null) {  //maybe GBK
								encodeString = "GBK";
							} else { //UTF-8
								encodeString = "UTF-8";
							}
							InputStreamReader isr = new InputStreamReader(is, encodeString);
							BufferedReader bReader = new BufferedReader(isr);
							bookName = Analysis.StoT(bReader.readLine());
							File outFile = new File(outputField.getText() + "/" + bookName + ".txt");
							OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outFile), "Unicode");
							BufferedWriter bw = new BufferedWriter(osw); 
							String line;
							while((line = bReader.readLine()) != null) {
								if(first) {
									bw.write(bookName);
									first = false;
								}
								bw.write(Analysis.StoT(line));
								bw.newLine();						
							}
							first = true;
							bReader.close();
							bw.close();
	        		 } catch(Exception e){
	        			 	System.out.println("error");
	        		 }
					}
				}).start();
			}
		}
		else if(e.getActionCommand().equals("About")) {
			About about = new About();
			about.setVisible(true);
		}
	}
	
	public Charset detectCharset(File f, String[] charsets) {  
		  
	       Charset charset = null;  
	  
	       // charsets 是我們定義的 編碼 矩陣, 包括 UTF8, BIG5 etc.  
	       for (String charsetName : charsets) {  
	           charset = detectCharset(f, Charset.forName(charsetName));  
	           if (charset != null) {  
	               break;  
	           }  
	       }  
	       System.out.printf("\t[Test] Using '%s' encoding!\n", charset);  
	       return charset;  
	   }  
	  
	   private Charset detectCharset(File f, Charset charset) {  
	       try {  
	           BufferedInputStream input = new BufferedInputStream(new FileInputStream(f));  
	  
	           CharsetDecoder decoder = charset.newDecoder();  
	           decoder.reset();  
	  
	           byte[] buffer = new byte[512];  
	           boolean identified = false;  
	           while ((input.read(buffer) != -1) && (!identified)) {  
	               identified = identify(buffer, decoder);  
	           }  
	  
	           input.close();  
	  
	           if (identified) {  
	               return charset;  
	           } else {  
	               return null;  
	           }  
	  
	       } catch (Exception e) {  
	           return null;  
	       }  
	   }  
	    private boolean identify(byte[] bytes, CharsetDecoder decoder) {  
	        try {  
	            decoder.decode(ByteBuffer.wrap(bytes));  
	        } catch (CharacterCodingException e) {  
	            return false;  
	        }  
	        return true;  
	    }  
}
