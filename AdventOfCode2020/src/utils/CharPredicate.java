package utils;

import java.util.function.Predicate;

/**
 * <p>A {@link Predicate} specialized for the primitive type {@code char}. The functional method is {@link #testChar(char)}.</p>
 * @author Sam Hooper
 *
 */
@FunctionalInterface
public interface CharPredicate extends Predicate<Character> {
	
	public boolean testChar(char c);
	
	/**
	 * Default implementation is equivalent to: <pre><code>return testChar(t);</code></pre>
	 */
	@Override
	default boolean test(Character t) {
		return testChar(t);
	}
	
}
