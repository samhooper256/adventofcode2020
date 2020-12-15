package utils;

/**
 * @author Sam Hooper
 *
 */
public final class Basics {
	
	private Basics() {}

	public static boolean between(final int num, final int minInclusive, final int maxInclusive) {
		return num >= minInclusive && num <= maxInclusive;
	}
	
	/**
	 * Returns {@code true} if {@code num} {@link Maths#isint(String) is an int} between {@code minInclusive} and
	 * {@code maxInclusive}.
	 */
	public static boolean between(final String num, final int minInclusive, final int maxInclusive) {
		return Maths.isint(num) && between(Integer.parseInt(num), minInclusive, maxInclusive);
	}
	
	public static int manhattanDistance(int row1, int col1, int row2, int col2) {
		return Math.abs(row1 - row2) + Math.abs(col1 - col2);
	}
	
}
