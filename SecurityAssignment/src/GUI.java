import java.math.BigInteger;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Class to hold GUI elements. 
 */

public class GUI extends JFrame{
	
	static RSA myRsa = new RSA(); // Reference to RSA elements class
								  // Used for all the RSA methods
	
	static BigInteger keys[] = new BigInteger[3];
	static BigInteger encryptedMessage[] = new BigInteger[5000];
	static BigInteger encryptedFile[] = new BigInteger[50000000];
	static BigInteger encryptedInputFile[];
	static BigInteger decryptedMessage[] = new BigInteger[5000];
	static BigInteger decryptedFile[] = new BigInteger[50000000];

	// Window #1 for generating keys 	
    static final JButton btnGetKeys = new JButton("Generate keys");
    static final JLabel labelBitcount = new JLabel("Bit count:");
    static final JTextField bitCountText = new JTextField("30");
    static final JTextField publicKeyText = new JTextField("Public key: ");
    static final JTextField exponentText = new JTextField("Exponent: ");
    static final JTextField privateKeyText = new JTextField("Private key:");
	
    // Window #2 for encrypting message	
    static final JButton btnEncrypt = new JButton("Encrypt message");
    static final JTextField publicKeyTextTwo = new JTextField("Public key: ");
    static final JTextField exponentTextTwo = new JTextField("Exponent: ");
    static final JTextField inputMessageText = new JTextField("My message");
    static final JTextField encryptedMessageText = new JTextField("<Encrypted message>");
    static final JButton btnEncryptFile = new JButton("Encrypt file");
    static final JTextField inputFilePathText = new JTextField("src/plaintextfile");
    static final JTextField outputFilePathText = new JTextField("src/encryptedfile");
    
    // Window #3 for decrypting message	    
    static final JButton btnDecrypt = new JButton("Decrypt message");
    static final JTextField privateKeyTextTwo = new JTextField("Private key:");
    static final JTextField publicKeyTextThree = new JTextField("Public key: ");
    static final JTextField inputMessageTextTwo = new JTextField("<Your message>");
    static final JTextField encryptedMessageTextTwo = new JTextField("<Encrypted message>");
    static final JTextField decryptedMessageText = new JTextField("<Encrypted message>");
    static final JButton btnDecryptFile = new JButton("Decrypt file");
    static final JTextField inputFilePathTextTwo = new JTextField("src/encryptedfile");
    static final JTextField outputFilePathTextTwo = new JTextField("src/decryptedfile");
    

    public static class RSAKeys extends JPanel{
    	static RSAKeys instance;

	    public RSAKeys(){
	    	instance = this;
	    	btnGetKeys.addActionListener(new ActionListener(){ // Key Generate keys
    	    	public void actionPerformed(ActionEvent e) {
    	    		publicKeyText.setBounds(0, 160, 0, 30);
    	    		publicKeyText.setSize(400, 30);
    	    		RSAKeys.instance.add(publicKeyText);
    	    			    	    
    	    	    exponentText.setBounds(0, 200, 0, 30);
    	    	    exponentText.setSize(400, 30);
    	    	    RSAKeys.instance.add(exponentText);
    	    	    
    	    	    privateKeyText.setBounds(0, 240, 0, 30);
    	    	    privateKeyText.setSize(400, 30);
    	    	    RSAKeys.instance.add(privateKeyText);
    	    		
    	    		int bitCount = Integer.parseInt(bitCountText.getText());
    	    		PrimeNumber p = new PrimeNumber(bitCount);
    	    		PrimeNumber q = new PrimeNumber(bitCount);
    	    		keys = myRsa.calcKeys(p, q);	

    	    		publicKeyText.setText("Public key ("+keys[0].toString(2).length()+" bits) : "+keys[0]);
    	    		exponentText.setText("Exponent ("+keys[1].toString(2).length()+" bits): "+keys[1]);
    	    		privateKeyText.setText("Private key ("+keys[2].toString(2).length()+" bits) : "+keys[2]);
    	    		
    	    		publicKeyTextTwo.setBounds(0, 160, 0, 30);
    	    		publicKeyTextTwo.setSize(400, 30);
    	    		publicKeyTextTwo.setText("Public key ("+keys[0].toString(2).length()+" bits) : "+keys[0]);
    	    		Encrypt.instance.add(publicKeyTextTwo);
    	    		
    	    		exponentTextTwo.setBounds(0, 200, 0, 30);
    	    		exponentTextTwo.setSize(400, 30);
    	    		exponentTextTwo.setText("Exponent ("+keys[1].toString(2).length()+" bits): "+keys[1]);
    	    		Encrypt.instance.add(exponentTextTwo);
    	    		
    	    		inputMessageText.setBounds(0, 240, 0, 30);
    	    		inputMessageText.setSize(400, 30);
    	    		Encrypt.instance.add(inputMessageText);
    	    		
    	    		inputFilePathText.setBounds(0, 400, 0, 30);
    	    		inputFilePathText.setSize(400, 30);
    	    		inputFilePathText.setText("src/plaintextfile");
    	    		Encrypt.instance.add(inputFilePathText);
    	    		
    	    		outputFilePathText.setBounds(0, 440, 0, 30);
    	    		outputFilePathText.setSize(400, 30);
    	    		outputFilePathText.setText("src/encryptedfile");
    	    		Encrypt.instance.add(outputFilePathText);
    	    		
    	    		privateKeyTextTwo.setBounds(0, 160, 0, 30);
    	    		privateKeyTextTwo.setSize(400, 30);
    	    		privateKeyTextTwo.setText("Private key ("+keys[2].toString(2).length()+" bits) : "+keys[2]);
    	    		Decrypt.instance.add(privateKeyTextTwo);
    	    		
    	    		publicKeyTextThree.setBounds(0, 200, 0, 30);
    	    		publicKeyTextThree.setSize(400, 30);
    	    		publicKeyTextThree.setText("Public key ("+keys[0].toString(2).length()+" bits) : "+keys[0]);
    	    		Decrypt.instance.add(publicKeyTextThree);
    	    		
    	    	}});
  			RSAKeys.instance.add(bitCountText);
  			RSAKeys.instance.add(labelBitcount);
    	    this.add(btnGetKeys);
    
	    }
	    
      @Override
      public void paintComponent(Graphics g){  	    
    	  	btnGetKeys.setBounds(100,60,100,30);
  			btnGetKeys.setSize(new Dimension(200, 50));
  			bitCountText.setBounds(100,0,100,20);	
  			labelBitcount.setBounds(20,0,200,20);
      }
    }
    
    static class Encrypt extends JPanel{
    
    	static Encrypt instance;
    	public Encrypt(){
    		instance = this;	
    		btnEncrypt.addActionListener(new ActionListener(){ // Key Encrypt message
    	    	public void actionPerformed(ActionEvent e) {
    	    		encryptedMessageText.setBounds(0, 280, 0, 30);
    	    		encryptedMessageText.setSize(400, 30);
    	    		encryptedMessage = myRsa.encrypt(inputMessageText.getText(), keys[0], keys[1]);
    	    		String encryptedMessageString = "";
    	    		for(int i=0;i<encryptedMessage.length;i++){
    	    			encryptedMessageString += (char) encryptedMessage[i].intValue();
    	    		}
    	    		encryptedMessageText.setText(encryptedMessageString);
    	    	    Encrypt.instance.add(encryptedMessageText);
    	    	    
    	    		encryptedMessageTextTwo.setBounds(0, 240, 0, 30);
    	    		encryptedMessageTextTwo.setSize(400, 30);
    	    		encryptedMessageTextTwo.setText(encryptedMessageString);
    	    	    Decrypt.instance.add(encryptedMessageTextTwo);
    	    	}
    	    }); 
    		btnEncryptFile.addActionListener(new ActionListener(){ // Key Encrypt File
    	    	public void actionPerformed(ActionEvent e) {
    	    		String inputFileString = "";
    	    		// Read in file content
    	    		try {
    	    			FileReader inputFile = new FileReader(inputFilePathText.getText());
    	    			Scanner in = new Scanner(inputFile);
    	    			while(in.hasNext())
    	    				inputFileString = inputFileString + in.next()+ " ";
        	    		inputFile.close();
        	    		in.close();
    	    		} catch (FileNotFoundException ee) {
    	    			// TODO Auto-generated catch block
    	    			ee.printStackTrace();
    	    			System.exit(0);
    	    		} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    	    		
    	    		// Encrypt content
    	    		encryptedFile = myRsa.encrypt(inputFileString, keys[0], keys[1]);
    	    		String encryptedFileString = "";
    	    		for(int i=0;i<encryptedFile.length;i++){
    	    			encryptedFileString += encryptedFile[i] + ",";
    	    		}
    	    		encryptedFileString += "0";
    	    		// Write encrypted content to file
    				PrintWriter printer;
    				File outputFile  = new File(outputFilePathText.getText());
    				try {
    					printer = new PrintWriter(outputFile);
    					printer.write(encryptedFileString);
        				printer.flush();	
        				printer.close();
    				} catch (FileNotFoundException ee) {
    					// TODO Auto-generated catch block
    					ee.printStackTrace();
    				}
    				
    	    		inputFilePathTextTwo.setBounds(0, 400, 0, 30);
    	    		inputFilePathTextTwo.setSize(400, 30);
    	    		inputFilePathTextTwo.setText("src/encryptedfile");
    	    		Decrypt.instance.add(inputFilePathTextTwo);
    	    		outputFilePathTextTwo.setBounds(0, 440, 0, 30);
    	    		outputFilePathTextTwo.setSize(400, 30);
    	    		outputFilePathTextTwo.setText("src/decryptedfile");
    	    		Decrypt.instance.add(outputFilePathTextTwo);
    	    		
    	    	}
    	    });
    	    this.add(btnEncryptFile);
    	    this.add(btnEncrypt);  
    	}
    	
    	@Override
        public void paintComponent(Graphics g){
    	    
    		btnEncrypt.setBounds(100,60,100,30);
    	    btnEncrypt.setSize(new Dimension(200, 50));
  
    	    btnEncryptFile.setBounds(100,340,100,30);
    	    btnEncryptFile.setSize(new Dimension(200, 50)); 	

        }
    }
    static class Decrypt extends JPanel{
    	
    	static Decrypt instance;
    	public Decrypt(){
    		instance = this;
        	btnDecrypt.addActionListener(new ActionListener(){ // Key Decrypt message
     	    	public void actionPerformed(ActionEvent e) {
     	    		decryptedMessageText.setBounds(0, 280, 0, 30);
     	    		decryptedMessageText.setSize(400, 30);
     	    		decryptedMessage = myRsa.decrypt(encryptedMessage, keys[0], keys[2]);
     	    		String decryptedMessageString = "";
     	    		for(int i=0;i<decryptedMessage.length;i++){
     	    			decryptedMessageString+= ((char) decryptedMessage[i].intValue());			
     	    		}
     	    		decryptedMessageText.setText(decryptedMessageString);
     	    		Decrypt.instance.add(decryptedMessageText);
     	    	}
     	    });
        	btnDecryptFile.addActionListener(new ActionListener(){ // Key Decrypt File
     	    	public void actionPerformed(ActionEvent e) { 
        			String encryptedFileString = "";
    	    		// Read in file content
    	    		try {
    	    			FileReader inputFile = new FileReader(inputFilePathTextTwo.getText());
    	    			Scanner in = new Scanner(inputFile);
    	    			while(in.hasNext()){
    	    				encryptedFileString = encryptedFileString + in.next();
    	    			}
        	    		inputFile.close();
        	    		in.close();
    	    		} catch (FileNotFoundException ee) {
    	    			// TODO Auto-generated catch block
    	    			ee.printStackTrace();
    	    			System.exit(0);
    	    		} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	    			
    	    		String messageParts[] = encryptedFileString.split(",");
    	    		encryptedInputFile = new BigInteger[messageParts.length];
    	    		
	    			for(int i=0;i<messageParts.length;i++)
		    				encryptedInputFile[i] = new BigInteger(messageParts[i]);
	    			
	    			// Decrypt content
     	    		decryptedFile = myRsa.decrypt(encryptedInputFile, keys[0], keys[2]);
     	    		
     	    		String decryptedMessageString = "";
     	    		for(int i=0;i<decryptedFile.length-1;i++) // Lengt-1 since last part is 0
     	    			decryptedMessageString+= ((char) decryptedFile[i].intValue());			

     	    		// Write decrypted content to file
    				PrintWriter printer;
    				File outputFile  = new File(outputFilePathTextTwo.getText());
    				try {
    					printer = new PrintWriter(outputFile);
    					printer.write(decryptedMessageString);
        				printer.flush();	
        				printer.close();
    				} catch (FileNotFoundException ee) {
    					// TODO Auto-generated catch block
    					ee.printStackTrace();
    				}	
     	    	}
     	    	});
    	    this.add(btnDecrypt);
    	    this.add(btnDecryptFile);
    	}
    	@Override
        public void paintComponent(Graphics g){
        	btnDecrypt.setBounds(100,60,100,30);
        	btnDecrypt.setSize(new Dimension(200, 50));
  
    	    btnDecryptFile.setBounds(100,340,100,30);
    	    btnDecryptFile.setSize(new Dimension(200, 50));

        }        
    }
	
    public GUI(String _text, String panel){
        super(_text); // Set title

        switch(panel){
        	case "rsakeys":
        		setContentPane(new RSAKeys());
        	break;
        	
        	case "encrypt":
        		setContentPane(new Encrypt());
        	break;
        	
        	case "decrypt":
        		setContentPane(new Decrypt());
        	break;
        	
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 800);
        setVisible(true); 
   }
	
	public static void main(String[] args) {
		
		JFrame rsa = new GUI("RSA Keys", "rsakeys");
		final JFrame encrypt = new GUI("Encrypt", "encrypt");
		final JFrame decrypt = new GUI("Decrypt", "decrypt");
		rsa.setSize(400, 800);
		encrypt.setSize(400,800);
		decrypt.setSize(400,800);
		encrypt.move(500,50);
		decrypt.move(1000, 50);
		
		rsa.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	             System.exit(0);
	          }        
	       });    
	}
}







