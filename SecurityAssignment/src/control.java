import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class control extends JFrame{
	
    public control(String _text, String panel){
        super(_text);


        switch(panel){
        	case "default":
        		setContentPane(new DrawPane());
        	break;
        	
        	case "encrypt":
        		setContentPane(new DrawEncrypt());
        	break;
        	
        }
        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(400, 400);

        setVisible(true); 
   }

    //create a component that you can actually draw on.
    class DrawPane extends JPanel{
      public void paintComponent(Graphics g){
        //draw on g here e.g.
         g.fillRect(0,0,400,400);
       }
   }
    
    class DrawEncrypt extends JPanel{
        public void paintComponent(Graphics g){
          
           
         }
     }
	
	public static void main(String[] args) {
		
		new control("RSA Keys", "default");
		new control("Encrypt", "encrypt");
		new control("Decrypt", "default");
		
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

















