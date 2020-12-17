package utils;

/**
 * A possibly mutable ordered pair of elements.
 * @author Sam Hooper
 *
 */
public interface Pair<T1, T2> {
	
	default Object[] toArray() {
		return new Object[] {first(), second()};
	}
	
	/**
	 * Returns an {@link ImmutablePair} containing the two elements.
	 */
	public static <T1, T2> ImmutablePair<T1, T2> of(final T1 first, final T2 second) {
		return new ImmutablePair<>(first, second);
	}
	
	/**
	 * Returns an {@link IntPair} containing the two {@code ints}.
	 */
	public static IntPair of(final int first, final int second) {
		return new IntPair(first, second);
	}
	
	/**
	 * Returns an {@link LongPair} containing the two {@code longs}.
	 */
	public static LongPair of(final long first, final long second) {
		return new LongPair(first, second);
	}
	
	/**
	 * Returns an {@link IntObjPair} containing the given {@code int} and the given {@link Object}.
	 */
	public static <T> IntObjPair<T> of(final int intPart, final T objPart) {
		return new IntObjPair<>(intPart, objPart);
	}
	
	T1 first();
	
	T2 second();
	
	/** Returns a {@code String} whose value is equivalent to {@code (first().toString() + second.toString())}.
	 */
	default String concatenated() {
		return first().toString() + second().toString();
	}
	
}
