import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;

/*****************************************
Assignment 1

RSA is an algorithm for public-key cryptography that is based on 
the presumed difficulty of factoring large integers. 
A user of RSA creates and then publishes the product of two large prime numbers, 
along with an auxiliary value, as their public key. The prime factors must be kept secret.
Anyone can use the public key to encrypt a message, but with currently published methods, 
if the public key is large enough, only someone with knowledge of the prime factors can feasibly decode the message.
In this assignment, you are required to write a program in C++/ JAVA
(any programming language) to implement the RSA algorithm.
Requirements:
1) Generate two large random numbers   (128bits)
	range from −(2^127) to 2^127 − 1
2) Test if the number generated is prime number 
3) Implement RSA Algorithm to generate public and private key
4) Use keys  generated from RSA to Encrypt and Decrypted files and messages
*Friendly  GUI is required. Using exiting library to implement RSA algorithm will deduct 25%

Each student will be required to demo he/she has achieved in this assignment in the Lab. 
Make sure that you upload your assignment (code) in Webcourses before 5pm on 16th, March 2016.
*****************************************/
public class control {
	
	public static void main(String[] args) {
		
		PrimeNumber p = new PrimeNumber();
		PrimeNumber q = new PrimeNumber();
		
		// For development only
		if(p.TESTING){
			p.setValue(new BigInteger("00"));
			q.setValue(new BigInteger("00"));
		}
		
		System.out.println("First prime number: "+p.getValue()+ " with "+p.getValue().toString(2).length()+"bits");
		System.out.println("Second prime number: "+q.getValue()+" with "+q.getValue().toString(2).length()+"bits");
		BigInteger n = q.getValue().multiply(p.getValue());
		System.out.println("N is "+n+ " with "+n.toString(2).length()+"bits");	
		
		//Pick another number e such that e and (p-1)(q-1) are relatively prime.
		BigInteger f = q.getValue().subtract(BigInteger.ONE).multiply
					  (p.getValue().subtract(BigInteger.ONE));
		
		BigInteger e = new BigInteger(f.toString(2).length()-1, PrimeNumber.random);
		BigInteger i = BigInteger.ZERO;
		do{
			e = e.add(BigInteger.ONE);
			i = calcGcd(f, e)[0];
			
		}while(!i.equals(BigInteger.ONE));
	
		System.out.println("Found value for e: "+e+" I: "+i);
		
		BigInteger d = calcGcd(f, e)[1];
		System.out.println("D bit count: "+d.toString(2).length());
		System.out.println("D: "+d);
		System.out.println("ED/F, jakojäännös: "+
		e.multiply(d).subtract(f.multiply((e.multiply(d).divide(f)))));

	
		System.out.println("\n\nFINISHED:\nPublic key: \nN: "+n+" "+n.toString(2).length()+" bits"+
				"\nPublic Exponent: "+e+" "+e.toString(2).length()+" bits");
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
			System.out.println("Cypher: "+encrypted[ii]);
		}
		
		// Decrypt
		System.out.println("Plain text: ");
		for(int ii=0;ii<message.length;ii++){
			decrypted[ii] = encrypted[ii].modPow(d, n);
			System.out.print(""+ ((char) decrypted[ii].intValue()));
		}
		System.exit(0); 
	}
	
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
		System.out.println("GCD("+_x+","+_y+")="+x);
		System.out.println(xx+"("+_x+") - "+yy+"("+_y+")");
		System.out.println((xx.multiply(_x)).add(yy.multiply(_y)));
		BigInteger returnNumber[] = new BigInteger[2];
		returnNumber[0] = x;
		returnNumber[1] = yy;	
		return returnNumber;
	}	
}

















