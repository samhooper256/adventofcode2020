package utils.colls;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * <p>A possibly mutable ordered pair of elements.</p>
 * @author Sam Hooper
 *
 */
public interface Pair<T1, T2> {
	
	/**
	 * Returns an {@link Pair} containing the two elements.
	 */
	public static <T1, T2> Pair<T1, T2> of(final T1 first, final T2 second) {
		return new ImmutablePair<>(first, second);
	}
	
	/**
	 * Returns a {@code String} representation of the given {@link Pair} that is equivalent to the one mandated
	 * by {@link Pair#toString()}. This method does not invoke {@code pair.toString()}.
	 * @return
	 */
	public static String toString(Pair<?, ?> pair) {
		return "(" + pair.first() + ", " + pair.second() + ")";
	}
	
	T1 first();
	
	T2 second();
	
	default Object[] toArray() {
		return toArray(Object[]::new);
	}
	
	@SuppressWarnings("unchecked")
	default <S> S[] toArray(IntFunction<S[]> generator) {
		S[] arr = generator.apply(2);
		arr[0] = (S) first();
		arr[1] = (S) second();
		return arr;
	}
	
	/** The returned {@link Collection} is unmodifiable.*/
	@SuppressWarnings("unchecked")
	default <T> Collection<T> toCollection() {
		return List.of((T) first(), (T) second());
	}
	
	@SuppressWarnings("unchecked")
	default <T, C extends Collection<T>> C toCollection(Supplier<C> collectionFactory) {
		C collection = collectionFactory.get();
		collection.add((T) first());
		collection.add((T) second());
		return collection;
	}
	
	/** The returned {@link Stream} is ordered with {@link #first()} followed by {@link #second()}. */
	@SuppressWarnings("unchecked")
	default <T> Stream<T> stream() {
		return Stream.of((T) first(), (T) second());
	}
	
	default <R> R reduce(BiFunction<T1, T2, R> function) {
		return function.apply(first(), second());
	}
	
	/**
	 * Returns a {@code String} that {@link String#equals(Object) equals}:<br>
	 * {@code ("(" + first().toString() + ", " + second().toString() + ")")}
	 * @return
	 */
	@Override
	String toString();
	
	
	
}
