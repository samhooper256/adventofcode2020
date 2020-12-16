package utils;

import java.util.regex.MatchResult;
import java.util.stream.IntStream;

public final class Parsing {
	
	private Parsing() {}
	
	/** <p>Returns the {@code ints} in the given {@link String}. The "{@code ints} in the {@code String}" are integers in
	 * the {@code String} that are between {@link Integer#MIN_VALUE} and {@link Integer#MAX_VALUE} and are separated by
	 * one or more  non-digit characters. The returned {@link IntStream} is ordered.</p>
	 * 
	 * <h1>Examples:</h1>
	 * <ul>
	 * <li><code>ints("12, 3, abc, 4x5")</code> returns <code>[12, 3, 4, 5]</code></li>
	 * <li><code>ints("1 123456789123456789123456789 3")</code> returns <code>[1, 3]</code></li>
	 * </ul>
	 * */
	public static IntStream ints(String input) {
		return Regex.INTEGERS.matcher(input).results().map(MatchResult::group).filter(Maths::isint).mapToInt(Integer::parseInt);
	}
}
