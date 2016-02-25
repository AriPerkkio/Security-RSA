import java.math.BigInteger;
import java.security.SecureRandom;

public class PrimeNumber{
	
	private final BigInteger TWO = new BigInteger("2");
	public int bitCount;

	public static SecureRandom random = new SecureRandom();
	private BigInteger value = new BigInteger(bitCount, random);
	
	
	public PrimeNumber(int _bitCount){
		bitCount = _bitCount;
		int i = 0;
		do{
			i++;
			//System.out.println("Calculating prime number, round #"+i);
			value = new BigInteger(bitCount, random);
		}while(!this.isPrime());
		//}while(!this.value.isProbablePrime(100)); // This one is from BigInteger library. 30s for 2048 bit primes...
	}
	
	// Faster way to calculate if prime, not just square..
	// https://primes.utm.edu/lists/2small/100bit.html
	// One option is to use isProbablePrime(100) - WAY faster
	public boolean isPrime() {
		BigInteger square = sqrt(this.value);
		
		if(this.value.compareTo(TWO)==-1)
			return false;

		if(this.value.remainder(TWO).equals(BigInteger.ZERO))
			return false;

		//System.out.println("Starting for loops from 3 to "+square);
		for(BigInteger i = new BigInteger("3"); // Above two 
			i.compareTo(square)==-1; // Smaller than square root
			i=i.add(TWO)){ // Skip even numbers 
				if(this.value.remainder(i).equals(BigInteger.ZERO)){
					//System.out.println("False at i "+i+"\n\n");
					return false;
			}
		}
		return true; // Others passed -> prime
	}
	
	public BigInteger getValue() {
		return value;
	}
	
	public static BigInteger sqrt(BigInteger x) {
	    BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
	    BigInteger div2 = div;
	    for(;;) {
	        BigInteger y = div.add(x.divide(div)).shiftRight(1);
	        if (y.equals(div) || y.equals(div2))
	            return y;
	        div2 = div;
	        div = y;
	    }
	}
	
	public static BigInteger factorial(BigInteger n) {
	    BigInteger result = BigInteger.ONE;

	    while (!n.equals(BigInteger.ZERO)) {
	        result = result.multiply(n);
	        n = n.subtract(BigInteger.ONE);
	    }
	    return result;
	}
	
}
