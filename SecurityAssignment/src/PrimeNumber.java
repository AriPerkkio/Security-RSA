import java.math.BigInteger;
import java.security.SecureRandom;

public class PrimeNumber {
	
	private BigInteger TWO = new BigInteger("2");
	public int bitCount = 50;

	public static SecureRandom random = new SecureRandom();
	private BigInteger value = new BigInteger(bitCount, random);
	public boolean TESTING = false;
	
	
	public PrimeNumber(){
		if(!TESTING){
			int i = 0;
			do{
				i++;
				System.out.println("Calculating prime number, round #"+i);
				value = null;
				value = new BigInteger(bitCount, random);
			}while(!this.isPrime());
			//System.out.println("Complete with "+value);
		}
	}
	
	// Faster way to calculate if prime, not just square..
	// https://primes.utm.edu/lists/2small/100bit.html
	// One option is to use isProbablePrime(100) - WAY faster
	public boolean isPrime() {
		BigInteger square = sqrt(this.value);
		//System.out.println("#1 "+this.value);
		
		if(this.value.compareTo(TWO)==-1)
			return false;

		if(this.value.remainder(TWO).equals(BigInteger.ZERO))
			return false;

		
		//System.out.println("Starting for loops from 3 to "+square);
		BigInteger i = new BigInteger("3");
		for(i = i.add(BigInteger.ZERO);i.compareTo(square)==-1;i=i.add(TWO)){
			//System.out.println("Loops left: "+sqrt(this.value).subtract(i));
			if(this.value.remainder(i).equals(BigInteger.ZERO)){
				//System.out.println("False at i "+i+"\n\n");
				return false;
			}
		}
		//System.out.println("Done with i: "+i);
		// Others passed -> prime
		return true;
	}
	
	public BigInteger getValue() {
		return value;
	}
	
	// For debug
	public void setValue(BigInteger _value) {
		this.value = _value;
	}
	
	public static BigInteger sqrt(BigInteger x) {
	    BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
	    BigInteger div2 = div;
	    // Loop until we hit the same value twice in a row, or wind
	    // up alternating.
	    for(;;) {
	        BigInteger y = div.add(x.divide(div)).shiftRight(1);
	        if (y.equals(div) || y.equals(div2))
	            return y;
	        div2 = div;
	        div = y;
	    }
	}
	
}
