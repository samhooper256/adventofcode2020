package utils.colls;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * @author Sam Hooper
 *
 */
public final class Colls {
	
	private Colls() {}
	
	public static <T> int count(final Collection<T> collection, final Predicate<? super T> tester) {
		int count = 0;
		for(T item : collection)
			if(tester.test(item))
				count++;
		return count;
	}
	
	/**
	 * Returns the intersection of all the {@link Stream Streams} in the given {@link Iterable} - this is, the {@link Set} of elements
	 * that occur in every {@code Stream}.
	 */
	public static <T> Set<T> intersection(final Iterable<? extends Stream<T>> iterable) {
		return intersection(iterable.iterator());
	}
	
	/**
	 * Returns the intersection of all the {@link Stream Streams} in the given {@link Iterator} - this is, the {@link Set} of elements
	 * that occur in every {@code Stream}.
	 */
	public static <T> Set<T> intersection(final Iterator<? extends Stream<T>> itr) {
		if(!itr.hasNext())
			return Collections.emptySet();
		Set<T> items = new HashSet<>(itr.next().collect(Collectors.toSet()));
		itr.forEachRemaining(stream -> items.retainAll(stream.collect(Collectors.toSet())));
		return items;
	}
	
	/**
	 * Returns the intersection of the {@link IntStream IntStreams} in the given {@link Stream} - that is, returns an {@link IntSet} containing all {@code ints}
	 * that occur in all of the {@code IntStreams} in the given {@code Stream}.
	 */
	public static IntSet intersectionInt(final Stream<? extends IntStream> streamOfIntStreams) {
		return intersectionInt(streamOfIntStreams.iterator());
	}
	
	/**
	 * Returns the intersection of the {@link IntStream IntStreams} in the given {@link Iterable} - that is, returns an {@link IntSet} containing all {@code ints}
	 * that occur in all of the {@code IntStreams} in the given {@code Iterable}.
	 */
	public static IntSet intersectionInt(final Iterable<? extends IntStream> iterable) {
		return intersectionInt(iterable.iterator());
	}
	
	/**
	 * Returns the intersection of the {@link IntStream IntStreams} in the given {@link Iterator} - that is, returns an {@link IntSet} containing all {@code ints}
	 * that occur in all of the {@code IntStreams} in the given {@code Iterator}.
	 */
	public static IntSet intersectionInt(final Iterator<? extends IntStream> itr) {
		if(!itr.hasNext())
			return IntSet.empty();
		IntSet items = IntSet.from(itr.next());
		itr.forEachRemaining(stream -> items.retainAll(IntSet.from(stream)));
		return items;
	}
	
	/**
	 * Returns the last element in the given {@link List}. Throws a {@link NoSuchElementException} if the list {@link List#isEmpty() is empty}.
	 */
	public static <T> T peek(final List<T> list) {
		if(list.isEmpty())
			throw new NoSuchElementException("Cannot peek on empty List");
		return list.get(list.size() - 1);
	}
	
	public static boolean containsAll(Collection<?> collection, Object... items) {
		for(Object item : items)
			if(!collection.contains(item))
				return false;
		return true;
	}
	
	public static boolean containsAny(Collection<?> collection, Object... items) {
		for(Object item : items)
			if(collection.contains(item))
				return true;
		return false;
	}
	
	/** <p>Returns the only element in the given {@link Collection}. If the given {@code Collection} does not have
	 * exactly one elements (that is, if {@code collection.size() != 1}), an {@link IllegalArgumentException} is thrown.</p>*/
	public static <T> T getOnlyElement(Collection<T> collection) {
		if(collection.size() != 1)
			throw new IllegalArgumentException("The given collection does not have a size() of 1");
		return collection.iterator().next();
	}
	
	/**
	 * Returns a copy of the first {@code n} elements from {@code collection} in the {@link Collection} supplied by the given {@link Supplier}.
	 * */
	public static <T, C extends Collection<T>> C firstN(Collection<? extends T> collection, int n, Supplier<C> resultCollectionFactory) {
		C result = resultCollectionFactory.get();
		for(T i : collection) {
			result.add(i);
			if(--n <= 0)
				break;
		}
		return result;
	}
	
}
