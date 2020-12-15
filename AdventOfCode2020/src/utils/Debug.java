package utils;

/**
 * @author Sam Hooper
 *
 */
public final class Debug {
	
	private Debug() {}
	
	public static void printLines(final char[][] chars) {
		System.out.println(toLines(chars));
	}
	
	public static String toLines(final char[][] chars) {
		StringBuilder sb = new StringBuilder();
		for(char[] arr : chars)
			sb.append(arr).append('\n');
		return sb.toString();
	}
	
}