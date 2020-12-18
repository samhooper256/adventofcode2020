package utils;

import java.util.regex.MatchResult;
import java.util.stream.*;

import utils.math.Maths;

public final class Parsing {
	
	private Parsing() {}
	
	/** <p>Returns the {@code ints} in the given {@link String}. The "{@code ints} in the {@code String}" are integers in
	 * the {@code String} that are between {@link Integer#MIN_VALUE} and {@link Integer#MAX_VALUE} (inclusive) and are separated by
	 * one or more non-digit (and non-negative sign) characters. The returned {@link IntStream} is ordered.</p>
	 * 
	 * <h1>Examples:</h1>
	 * <ul>
	 * <li><code>ints("12, 3, abc, -4x5")</code> returns <code>[12, 3, -4, 5]</code></li>
	 * <li><code>ints("1 9223372036854775807 -3")</code> returns <code>[1, -3]</code></li>
	 * </ul>
	 * */
	public static IntStream ints(String input) {
		return Regex.INTEGERS.matcher(input).results().map(MatchResult::group).filter(Maths::isint).mapToInt(Integer::parseInt);
	}
	
	/** <p>Returns the positive {@code ints} in the given {@link String}. The "positive {@code ints} in the {@code String}" are integers in
	 * the {@code String} that are between {@code 0} and {@link Integer#MAX_VALUE} (inclusive) and are separated by
	 * one or more non-digit characters. The returned {@link IntStream} is ordered.</p>
	 * 
	 * <h1>Examples:</h1>
	 * <ul>
	 * <li><code>ints("12, 3, abc, -4x5")</code> returns <code>[12, 3, 4, 5]</code></li>
	 * <li><code>ints("1 9223372036854775807 -3")</code> returns <code>[1, 3]</code></li>
	 * </ul>
	 * */
	public static IntStream positiveints(String input) {
		return Regex.DIGITS.matcher(input).results().map(MatchResult::group).filter(Maths::isint).mapToInt(Integer::parseInt);
	}
	
	/** <p>Returns the {@code longs} in the given {@link String}. The "{@code longs} in the {@code String}" are integers in
	 * the {@code String} that are between {@link Long#MIN_VALUE} and {@link Long#MAX_VALUE} (inclusive) and are separated by
	 * one or more non-digit (and non-negative sign) characters. The returned {@link LongStream} is ordered.</p>
	 * 
	 * <h1>Examples:</h1>
	 * <ul>
	 * <li><code>ints("12, 3, abc, -4x5")</code> returns <code>[12, 3, -4, 5]</code></li>
	 * <li><code>ints("1 9223372036854775807 -3")</code> returns <code>[1, 9223372036854775807, -3]</code></li>
	 * </ul>
	 * */
	public static LongStream longs(String input) {
		return Regex.INTEGERS.matcher(input).results().map(MatchResult::group).filter(Maths::islong).mapToLong(Long::parseLong);
	}
	
	/** <p>Returns the positive {@code longs} in the given {@link String}. The "positive {@code longs} in the {@code String}" are integers in
	 * the {@code String} that are between {@code 0} and {@link Long#MAX_VALUE} (inclusive) and are separated by
	 * one or more non-digit characters. The returned {@link LongStream} is ordered.</p>
	 * 
	 * <h1>Examples:</h1>
	 * <ul>
	 * <li><code>ints("12, 3, abc, -4x5")</code> returns <code>[12, 3, 4, 5]</code></li>
	 * <li><code>ints("1 9223372036854775807 -3")</code> returns <code>[1, 9223372036854775807, 3]</code></li>
	 * </ul>
	 * */
	public static LongStream positivelongs(String input) {
		return Regex.DIGITS.matcher(input).results().map(MatchResult::group).filter(Maths::islong).mapToLong(Long::parseLong);
	}
	
}
