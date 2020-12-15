package utils;

import java.util.function.IntFunction;

/**
 * @author Sam Hooper
 *
 */
public interface SingleTypePair<T> extends Pair<T, T> {
	
	public static <T> ImmutableSingleTypePair<T> of(T first, T second) {
		return new ImmutableSingleTypePair<>(first, second);
	}
	
	/**
	 * Returns an {@link ImmutableSingleTypePair} containing the first two elements of {@code arr}, in order. Throws an exception if {@code (arr.length < 2)}.
	 * @param <T>
	 * @param arr
	 * @return
	 */
	public static <T> ImmutableSingleTypePair<T> of(T[] arr) {
		if(arr.length < 2)
			throw new IllegalArgumentException("arr.length < 2");
		return of(arr[0], arr[1]);
	}
	
	@SuppressWarnings("unchecked")
	default <S> S[] toArray(IntFunction<S[]> generator) {
		S[] arr = generator.apply(2);
		arr[0] = (S) first();
		arr[1] = (S) second();
		return arr;
	}
}
