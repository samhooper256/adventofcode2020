package utils;

import java.util.StringJoiner;

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
	
	public static String toConciseString(final boolean[][][] bools, char trueChar, char falseChar) {
		StringJoiner sb = new StringJoiner("\n\n");
		for(boolean[][] boolArr : bools)
			sb.add(toConciseString(boolArr, trueChar, falseChar));
		return sb.toString();
	}
	
	public static String toConciseString(final boolean[][] bools, char trueChar, char falseChar) {
		StringJoiner j = new StringJoiner("\n");
		for(boolean[] boolArr : bools)
			j.add(toConciseString(boolArr, trueChar, falseChar));
		return j.toString();
	}
	
	public static String toConciseString(final boolean[] bools, char trueChar, char falseChar) {
		StringBuilder sb = new StringBuilder();
		for(boolean b : bools)
			sb.append(toChar(b, trueChar, falseChar));
		return sb.toString();
	}
	
	public static String toConciseString(boolean bool) {
		return bool ? "T" : "F";
	}
	
	public static char toChar(boolean bool) {
		return toChar(bool, '#', '.');
	}
	
	public static char toChar(boolean bool, char trueChar, char falseChar) {
		return bool ? trueChar : falseChar;
	}
	
}