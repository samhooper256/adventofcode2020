package utils.math;

import java.math.*;

/**
 * @author Sam Hooper
 *
 */
public class ChineseRemainderTheorem {
	
	/** 
	 * <p>Let <i>N</i> denote the product of all n<sub>i</sub>. This method uses the Chinese Remainder Theorem to find the unique integer <i>n</i>, 0 &leq; n &lt; <i>N</i>,
	 * that has a remainder of r<sub>i</sub> when divided by n<sub>i</sub> for all 0 &leq; i &lt; {@code n.length}.
	 * Assumes all n<sub>i</sub> are pairwise coprime and that 0 &leq; r<sub>i</sub> &lt; n<sub>i</sub>.</p>
	 * 
	 * @throws IllegalArgumentException if {@code (n.length != r.length)}.
	 * @throws ArithmeticException if the result cannot be stored in a {@code long}.
	 * */
	public static long crt(long[] n, long[] r) {
		if(n.length != r.length)
			throw new IllegalArgumentException("n.length != r.length");
		long lcm = n[0], crt = r[0];
		for(int i = 1; i < n.length; i++) {
			crt = crt(lcm, n[i], crt, r[i]);
			lcm *= n[i];
		}
		return crt;
	}
	
	
	/** 
	 * <p>Uses the Chinese Remainder Theorem to find the unique integer <i>n</i>, 0 &leq; n &lt; {@code n1} * {@code n2}, that has a remainder
	 * of {@code r1} when divided by {@code n1} and a remainder of {@code r2} when divided by {@code n2}.
	 * Assumes {@code n1} and {@code n2} are coprime, that {@code n1 > 0} and {@code n2 > 0}, and that 0 &leq; r<sub>i</sub> &lt; n<sub>i</sub>.</p>
	 * 
	 * @throws ArithmeticException if the result cannot be stored in a {@code long}.
	 * */
	public static long crt(long n1, long n2, long r1, long r2) {
		long[] bezoutCoefficients = bezoutCoefficients(n1, n2);
		final BigInteger bigN1 = BigInteger.valueOf(n1);
		BigInteger x = BigInteger.valueOf(r1).add(BigInteger.valueOf(r2 - r1).multiply(bigN1).multiply(BigInteger.valueOf(bezoutCoefficients[0])));
		BigInteger lcm = bigN1.multiply(BigInteger.valueOf(n2)); //Since n1 and n2 are coprime, their LCM will be the same as their product.
		return x.mod(lcm).longValueExact();
	}
	
	/**
	 * <p>Uses the Extended Euclidean Algorithm to find a pair of Bezout Coefficients for the given two numbers, which are assumed to both be positive. </b>
	 * */
	public static long[] bezoutCoefficients(long a, long b) {
		long prevRemainder = a, currRemainder = b, prevCo1 = 1, currCo1 = 0, prevCo2 = 0, currCo2 = 1; //"Co1" and "Co2" stand for the 1st and 2nd Bezout Coefficients.
		while(true) {
			final long quotient = prevRemainder / currRemainder, nextRemainder = prevRemainder - quotient * currRemainder;
			if(nextRemainder == 0)
				return new long[] {currCo1, currCo2};
			final long nextCo1 = prevCo1 - quotient * currCo1;
			final long nextCo2 = prevCo2 - quotient * currCo2;
			prevRemainder = currRemainder;
			currRemainder = nextRemainder;
			prevCo1 = currCo1;
			currCo1 = nextCo1;
			prevCo2 = currCo2;
			currCo2 = nextCo2;
		}
	}
	
}
