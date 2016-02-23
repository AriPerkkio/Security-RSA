import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;

public class control {
	
	public static void main(String[] args) {
		
		long startTime = System.nanoTime();
		String secretMessage = "This is my secret message which will be encrypted.";
		
		// Generate two 128-bit prime numbers
		PrimeNumber p = new PrimeNumber();
		PrimeNumber q = new PrimeNumber();
		
		BigInteger keys[] = calcKeys(p, q);
		
		System.out.println("\n\nFINISHED:\nPublic key, "
				+keys[0].toString(2).length()+" bits"+
				"\nN: "+keys[0]+
				"\nPublic Exponent, "+keys[1].toString(2).length()+" bits"+"\nE:"+keys[1]);
		System.out.println("Private key, "+ keys[2].toString(2).length()+" bits"+
				" \nD:"+keys[2] );		

		BigInteger encryptedMessage[] = encrypt(secretMessage, keys[0], keys[1]);
		
		BigInteger decrypted[] = new BigInteger[encryptedMessage.length];
		
		// Decrypt
		System.out.println("\nPlain text: ");
		for(int ii=0;ii<encryptedMessage.length;ii++){
			decrypted[ii] = encryptedMessage[ii].modPow(keys[2], keys[0]);
			System.out.print(""+ ((char) decrypted[ii].intValue()));
		}
		
		long endTime = System.nanoTime();

		long duration = (endTime - startTime)/1000000000;  
		System.out.println("\n\nDuration: "+duration+" s");
		System.exit(0); 
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
		
		System.out.println("\nEncrypted message: ");
		for(int i=0;i<message.length;i++){
			encrypted[i] = new BigInteger(""+message[i]).modPow(publicExponent, publicKey);
			System.out.print(""+(char) encrypted[i].intValue());
		}	
		return encrypted;
	}
	
}

















