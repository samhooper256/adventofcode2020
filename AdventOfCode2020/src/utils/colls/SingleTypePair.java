package utils.colls;

import java.util.function.*;

/**
 * @author Sam Hooper
 *
 */
public interface SingleTypePair<T> extends Pair<T, T> {
	
	public static <T> SingleTypePair<T> of(T first, T second) {
		return new ImmutableSingleTypePair<>(first, second);
	}
	
	/**
	 * Returns an {@link ImmutableSingleTypePair} containing the first two elements of {@code arr}, in order. Throws an exception if {@code (arr.length < 2)}.
	 * @param <T>
	 * @param arr
	 * @return
	 */
	@SafeVarargs
	public static <T> SingleTypePair<T> of(T... arr) {
		if(arr.length < 2)
			throw new IllegalArgumentException("arr.length < 2");
		return of(arr[0], arr[1]);
	}
	
	default T combine(BinaryOperator<T> combiner) {
		return combiner.apply(first(), second());
	}
	
}
