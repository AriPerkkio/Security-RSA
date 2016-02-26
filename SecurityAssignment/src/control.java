import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class control extends JFrame{
	
    static final JButton btnGetKeys = new JButton("Generate keys");
    static final JTextField publicKeyText = new JTextField("Public key: ");
    static final JTextField exponentText = new JTextField("Exponent: ");
    static final JTextField privateKeyText = new JTextField("Private key:");
	
    static final JButton btnEncrypt = new JButton("Encrypt message");
    static final JTextField inputMessage = new JTextField("Write your message");
    
    static final JButton btnDecrypt = new JButton("Decrypt message");

    

    public static class RSAKeys extends JPanel{
    	
	    static RSAKeys instance;

	    public RSAKeys(){
	    	instance = this;
	    }
	    
      @Override
      public void paintComponent(Graphics g){  	        	    
    	    btnGetKeys.addActionListener(new ActionListener(){
    	    	public void actionPerformed(ActionEvent e) {
    	    		publicKeyText.setBounds(0, 160, 0, 30);
    	    		publicKeyText.setSize(400, 30);
    	    		RSAKeys.instance.add(publicKeyText);
    	    		
    	    	    
    	    	    exponentText.setBounds(0, 200, 0, 30);
    	    	    exponentText.setSize(400, 30);
    	    	    RSAKeys.instance.add(exponentText);
    	    	    
    	    	    privateKeyText.setBounds(0, 250, 0, 30);
    	    	    privateKeyText.setSize(400, 30);
    	    	    RSAKeys.instance.add(privateKeyText);
    	    		
    	    		int bitCount = 30;
    	    		PrimeNumber p = new PrimeNumber(bitCount);
    	    		PrimeNumber q = new PrimeNumber(bitCount);
    	    		BigInteger keys[] = calcKeys(p, q);	
    	    		publicKeyText.setText("Public key ("+keys[0].toString(2).length()+" bits) : "+keys[0]);
    	    		exponentText.setText("Exponent ("+keys[1].toString(2).length()+" bits): "+keys[1]);
    	    		privateKeyText.setText("Private key ("+keys[2].toString(2).length()+" bits) : "+keys[2]);
    	    	}});
    	    this.add(btnGetKeys);
       }
    }
    
    static class Encrypt extends JPanel{
    
    	static Encrypt instance;
    	public Encrypt(){
    		instance = this;
    		
    	}
    	
    	@Override
        public void paintComponent(Graphics g){
    	    
    		
    		btnEncrypt.setBounds(100,60,100,30);
    	    btnEncrypt.setSize(new Dimension(200, 50));
    	    this.add(btnEncrypt);
    	    
        }
    }
    class Decrypt extends JPanel{
    	@Override
        public void paintComponent(Graphics g){
        	btnDecrypt.setBounds(100,60,100,30);
        	btnDecrypt.setSize(new Dimension(200, 50));
    	    this.add(btnDecrypt);
        }        
    }
	
    public control(String _text, String panel){
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
        setSize(400, 400);
        setVisible(true); 
   }
	
	public static void main(String[] args) {
		
		JFrame rsa = new control("RSA Keys", "rsakeys");
		final JFrame encrypt = new control("Encrypt", "encrypt");
		final JFrame decrypt = new control("Decrypt", "decrypt");
		rsa.setSize(400, 400);
		encrypt.setSize(400,400);
		decrypt.setSize(400,400);
		encrypt.move(500,50);
		decrypt.move(1000, 50);
		
		rsa.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	             System.exit(0);
	          }        
	       });    
	 	/*
		long startTime = System.nanoTime();
		String secretMessage = "This is my secret message which will be encrypted.";
		int bitCount = 32;
		
		// Generate two 128-bit prime numbers
		PrimeNumber p = new PrimeNumber(bitCount);
		PrimeNumber q = new PrimeNumber(bitCount);
		
		// keys[0]: Public, keys[1]: Exponent, keys[2]: Private
		BigInteger keys[] = calcKeys(p, q);	
		// Encrypt message using public key and exponent
		BigInteger encryptedMessage[] = encrypt(secretMessage, keys[0], keys[1]);
		// Decrypt message using public key and private key
		BigInteger decryptedMessage[] = decrypt(encryptedMessage, keys[0], keys[2]);
		
		System.out.println("\nEncrypted message:");
		for(int i=0;i<encryptedMessage.length;i++){
			System.out.print(""+(char) encryptedMessage[i].intValue());
		}
		System.out.println("\nDecrypted message:");		
		for(int i=0;i<decryptedMessage.length;i++){
			System.out.print(""+ ((char) decryptedMessage[i].intValue()));			
		}
		

		System.out.println("\n\nFINISHED:\nPublic key, "
				+keys[0].toString(2).length()+" bits"+
				"\nN: "+keys[0]+
				"\nPublic Exponent, "+keys[1].toString(2).length()+" bits"+"\nE:"+keys[1]);
		System.out.println("Private key, "+ keys[2].toString(2).length()+" bits"+
				" \nD:"+keys[2] );	
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000000;  
		System.out.println("\n\nDuration: "+duration+" s");
		//System.exit(0); 
	*/
	}
	
	// Returns: [0] = gcd - [1] = factor for y
	public static BigInteger[] calcGcd(BigInteger _x, BigInteger _y){
		BigInteger x = _x;
		BigInteger y = _y;
		BigInteger factor = BigInteger.ONE; 
		BigInteger leftOver = BigInteger.ONE;
		BigInteger z = BigInteger.ONE;
		BigInteger xx = BigInteger.ONE;
		BigInteger yy = BigInteger.ZERO;
		BigInteger v = BigInteger.ONE;
		BigInteger u = BigInteger.ZERO;
		
		while(!y.equals(BigInteger.ZERO)){
			factor = x.divide(y); 
			leftOver = x.subtract(factor.multiply(y));			
			//System.out.println(x+" = "+factor+"("+y+") + "+leftOver);
			x = y;
			y = leftOver;			
			z = u;
			u = xx.subtract(factor.multiply(u));
			xx = z;
			z = v;
			v = yy.subtract(factor.multiply(v));
			yy = z;
			
		}
		//System.out.println("GCD("+_x+","+_y+")="+x);
		//System.out.println(xx+"("+_x+") - "+yy+"("+_y+")");
		//System.out.println((xx.multiply(_x)).add(yy.multiply(_y)));
		BigInteger returnNumber[] = new BigInteger[2];
		
		returnNumber[0] = x;
		// Avoid using negative private keys
		if(yy.compareTo(BigInteger.ZERO)==-1)
			returnNumber[1] = _x.add(yy);
		else
			returnNumber[1] = yy;	
		return returnNumber;
	}
	
	public static BigInteger[] calcKeys(PrimeNumber _p, PrimeNumber _q){
		BigInteger returnKeys[] = new BigInteger[3];
		
		// Calculate n = pq
		BigInteger n = _q.getValue().multiply(_p.getValue());
			
		// Pick another number e such that e and (p-1)(q-1) are relatively prime.
		// F = (p-1)(q-1)
		BigInteger f = _q.getValue().subtract(BigInteger.ONE).multiply
							  (_p.getValue().subtract(BigInteger.ONE));		
		BigInteger e = new BigInteger(f.toString(2).length()-1, PrimeNumber.random);
		BigInteger i = BigInteger.ZERO;
		do{
				e = e.add(BigInteger.ONE);
				i = calcGcd(f, e)[0];		
		}while(!i.equals(BigInteger.ONE));
			
		// Calculate d such that ed = 1 mod (p-1)(q-1)
		BigInteger d = calcGcd(f, e)[1];
		
		returnKeys[0] = n; // Public key
		returnKeys[1] = e; // Public exponent
		returnKeys[2] = d; // Private key
		
		return returnKeys;
	}
	
	public static BigInteger[] encrypt(String _message, BigInteger publicKey, BigInteger publicExponent){
		
		BigInteger encrypted[] = new BigInteger[_message.length()];
		int message[] = new int[_message.length()];
		
		for(int i=0;i<_message.length();i++)
			message[i] = ((int) _message.charAt(i));
		
		for(int i=0;i<message.length;i++){
			encrypted[i] = new BigInteger(""+message[i]).modPow(publicExponent, publicKey);
		}	
		return encrypted;
	}
	
	public static BigInteger[] decrypt(BigInteger[] _encryptedMessage, BigInteger publicKey, BigInteger privateKey){
		
		BigInteger decrypted[] = new BigInteger[_encryptedMessage.length];

		for(int i=0;i<_encryptedMessage.length;i++){
			decrypted[i] = _encryptedMessage[i].modPow(privateKey, publicKey);
		}
		return decrypted;	
	}
	
}

















