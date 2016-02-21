import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;

public class control {
	
	public static void main(String[] args) {
		
		// Generate two 128-bit prime numbers
		PrimeNumber p = new PrimeNumber();
		PrimeNumber q = new PrimeNumber();
		
		// Calculate n = pq
		BigInteger n = q.getValue().multiply(p.getValue());
		
		// Pick another number e such that e and (p-1)(q-1) are relatively prime.
		// F = (p-1)(q-1)
		BigInteger f = q.getValue().subtract(BigInteger.ONE).multiply
					  (p.getValue().subtract(BigInteger.ONE));		
		BigInteger e = new BigInteger(f.toString(2).length()-1, PrimeNumber.random);
		BigInteger i = BigInteger.ZERO;
		do{
			e = e.add(BigInteger.ONE);
			i = calcGcd(f, e)[0];
			
		}while(!i.equals(BigInteger.ONE));
	
		// Calculate d such that ed = 1 mod (p-1)(q-1)
		BigInteger d = calcGcd(f, e)[1];
		
		System.out.println("\n\nFINISHED:\nPublic key, "
				+n.toString(2).length()+" bits"+
				"\nN: "+n+
				"\nPublic Exponent, "+e.toString(2).length()+" bits"+"\nE:"+e);
		System.out.println("Private key: \nD:"+d + " "+
		d.toString(2).length()+" bits");
		
		
		// Encrypt message m via c = m^e mod n
		// Decrypt the ciphertext c via m = cd mod n

		int message[] = new int[]{
				(int) (new Character('M')),
				(int) (new Character('y')),
				(int) (new Character(' ')),
				(int) (new Character('M')),
				(int) (new Character('e')),
				(int) (new Character('s')),
				(int) (new Character('s')),
				(int) (new Character('a')),
				(int) (new Character('g')),
				(int) (new Character('e'))};

		BigInteger encrypted[] = new BigInteger[message.length];
		BigInteger decrypted[] = new BigInteger[message.length];

		System.out.println("\nEncrypted message: ");
		for(int ii=0;ii<message.length;ii++){
			encrypted[ii] = new BigInteger(""+message[ii]).modPow(e, n);
			System.out.print(""+(char) encrypted[ii].intValue());
		}
		
		// Decrypt
		System.out.println("\nPlain text: ");
		for(int ii=0;ii<message.length;ii++){
			decrypted[ii] = encrypted[ii].modPow(d, n);
			System.out.print(""+ ((char) decrypted[ii].intValue()));
		}
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
	
}

















