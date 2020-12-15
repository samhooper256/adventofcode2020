package utils;

import java.util.List;

/**
 * @author Sam Hooper
 *
 */
public final class Maths {
	
	public static void main(String[] args) {
	}
	
	private Maths() {}
	
	/**
	 * <p>Does a mathematically correct mod operation. The returned value is always between 0 (inclusive) and {@code modulus} (exclusive), even if
	 * {@code num} is negative. For example, {@code mod(-17, 15)} returns {@code 13}.</p>
	 * @throws IllegalArgumentException if {@code (modulus <= 1)}.
	 */
	public static int mod(int num, int modulus) {
		if(modulus <= 1)
			throw new IllegalArgumentException("modulus <= 1");
		if(num > 0)
			return num % modulus;
		else
			return num % modulus + modulus;
	}
	
	public static boolean areCoprime(int a, int b) {
		return gcd(a, b) == 1;
	}
	public static long gcd(long a, long b) {
		if(a == 0 || b == 0)
			throw new IllegalArgumentException("numbers cannot be zero");
		if(b > a) {
			long temp = a;
			a = b;
			b = temp;
		}
		while(b != 0) {
			long temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}
	
	public static long lcm(long a, long b) {
		return a / gcd(a, b) * b;
	}
	
	public static final int MIN_RADIX = 2, MAX_RADIX = 16;
	public static final String INTEGER_MAX_VALUE_STRING = Integer.toString(Integer.MAX_VALUE);
	public static final String INTEGER_MIN_VALUE_STRING = Integer.toString(Integer.MIN_VALUE);
	private static final List<Character> RADIX_CHARS = List.of('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F');
	
	/**
	 * Returns {@code true} if {@code c} is a digit in base 10 (that is, a number from 0-9), {@code false} otherwise.
	 */
	public static boolean isDigit(final char c) {
		return c >= '0' && c <= '9';
	}
	
	public static boolean isint(final String s) {
		final boolean neg = s.charAt(0) == '-';
		if((neg && s.length() > 11) || (!neg && s.length() > 10) || s.length() == 0)
			return false;
		for(int i = neg ? 1 : 0; i < s.length(); i++)
			if(!isDigit(s.charAt(i)))
				return false;
		
		if(neg && s.length() < 11 || !neg && s.length() < 10)
			return true;
		if(neg)
			return s.compareTo(INTEGER_MIN_VALUE_STRING) <= 0;
		else
			return s.compareTo(INTEGER_MAX_VALUE_STRING) <= 0;
	}
	
	public static boolean isInteger(final String s) {
		return isInteger(s, 10);
	}
	
	/**
	 * {@code radix} must be between {@link #MIN_RADIX} and {@link #MAX_RADIX} (inclusive).
	 * @param s
	 * @param radix
	 * @return
	 */
	public static boolean isInteger(String s, final int radix) {
		if(radix < MIN_RADIX || radix > MAX_RADIX)
			throw new IllegalArgumentException("Radix " + radix + " is unsupported");
		if(s.isEmpty())
			return false;
		int i = s.charAt(0) == '-' ? 1 : 0;
		if(i == s.length())
			return false;
		s = s.toUpperCase();
		List<Character> subList = RADIX_CHARS.subList(0, radix);
		for(int j = i; j < s.length(); j++)
			if(!subList.contains(s.charAt(j)))
				return false;
		return true;
	}
}
