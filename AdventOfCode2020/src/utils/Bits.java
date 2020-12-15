package utils;

/**
 * <p>Utilities related to bits (i.e. bitwise operators). "Bit indices" are counted from right (least significant bit or ones place) to left, with
 * the ones place being index {@code 0}.</p>
 * @author Sam Hooper
 *
 */
public final class Bits {
	
	private Bits() {}
	
	public static boolean oneAt(long num, int bitIndex) {
		return !zeroAt(num, bitIndex);
	}
	
	public static boolean zeroAt(long num, int bitIndex) {
		return bitAt(num, bitIndex) == 0L;
	}
	
	public static long bitAt(long num, int bitIndex) {
		return num & onlyBit(bitIndex);
	}
	
	public static long onlyBit(long bitIndex) {
		return onlyBit((int) bitIndex);
	}
	
	public static long onlyBit(int bitIndex) {
		return 1L << bitIndex;
	}
	
	/** Returns a {@code long} with the same value as {@code num} but with the bit at the given index set to {@code 1}.*/
	public static long set(long num, int bitIndex) {
		return num | onlyBit(bitIndex);
	}
	
	/** Returns a {@code long} with the same value as {@code num} but with the bit at the given index cleared (set to {@code 0}). */
	public static long clear(long num, int bitIndex) {
		return num & ~onlyBit(bitIndex);
	}
	
}
