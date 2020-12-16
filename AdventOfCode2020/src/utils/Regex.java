package utils;

import java.util.regex.Pattern;

/**
 * @author Sam Hooper
 *
 */
public final class Regex {
	
	private Regex() {}
	
	public static final Pattern WHITESPACE = Pattern.compile("\\s+");
	public static final Pattern DIGITS = Pattern.compile("\\d+");
	public static final Pattern NON_DIGITS = Pattern.compile("\\D+");
	public static final Pattern COMMA = Pattern.compile(",");
	public static final Pattern INTEGERS = Pattern.compile("-?\\d+");
	
}
