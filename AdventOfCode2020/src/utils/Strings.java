package utils;

/**
 * @author Sam Hooper
 *
 */
public final class Strings {
	
	private Strings() {}
	
	/**
	 * Returns the first {@code char} in the given {@code String} if the {@code String} has {@link String#length() length} {@code 1}; otherwise,
	 * throws an {@link IllegalArgumentException}.
	 */
	public static char toChar(final String str) {
		if(str.length() != 1)
			throw new IllegalArgumentException("str.length() != 1");
		return str.charAt(0);
	}
	
	/**
	 * <p>Returns the number of {@code chars} in the given {@link CharSequence} that {@code == c}.</p>
	 */
	public static int count(final CharSequence str, final char c) {
		int count = 0;
		for(int i = 0; i < str.length(); i++)
			if(str.charAt(i) == c)
				count++;
		return count;
	}
	
	public static <T extends CharSequence> int count(final T str, final CharPredicate predicate) {
		int count = 0;
		for(int i = 0; i < str.length(); i++)
			if(predicate.testChar(str.charAt(i)))
				count++;
		return count;
	}
	
}
