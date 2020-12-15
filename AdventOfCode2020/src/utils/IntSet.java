package utils;

import java.util.*;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;
/**
 * @author Sam Hooper
 *
 */
public interface IntSet extends Set<Integer> {
	
	IntSet EMPTY = unmodifiable(new HashIntSet(1));
	
	public static IntSet unmodifiable(final IntSet set) {
		return new IntSet() {

			@Override
			public int size() {
				return set.size();
			}

			@Override
			public boolean isEmpty() {
				return set.isEmpty();
			}

			@Override
			public boolean contains(Object o) {
				return set.contains(o);
			}

			@Override
			public Object[] toArray() {
				return set.toArray();
			}

			@Override
			public <T> T[] toArray(T[] a) {
				return set.toArray(a);
			}

			@Override
			public boolean add(Integer e) {
				throw new UnsupportedOperationException("This IntSet is unmodifiable");
			}

			@Override
			public boolean remove(Object o) {
				throw new UnsupportedOperationException("This IntSet is unmodifiable");
			}

			@Override
			public boolean containsAll(Collection<?> c) {
				return set.containsAll(c);
			}

			@Override
			public boolean addAll(Collection<? extends Integer> c) {
				throw new UnsupportedOperationException("This IntSet is unmodifiable");
			}

			@Override
			public boolean retainAll(Collection<?> c) {
				throw new UnsupportedOperationException("This IntSet is unmodifiable");
			}

			@Override
			public boolean removeAll(Collection<?> c) {
				throw new UnsupportedOperationException("This IntSet is unmodifiable");
			}

			@Override
			public void clear() {
				throw new UnsupportedOperationException("This IntSet is unmodifiable");
			}

			@Override
			public OfInt iterator() {
				return set.iterator();
			}

			@Override
			public boolean add(int val) {
				throw new UnsupportedOperationException("This IntSet is unmodifiable");
			}

			@Override
			public boolean addAll(IntSet other) {
				throw new UnsupportedOperationException("This IntSet is unmodifiable");
			}

			@Override
			public boolean contains(int val) {
				return set.contains(val);
			}

			@Override
			public boolean remove(int val) {
				throw new UnsupportedOperationException("This IntSet is unmodifiable");
			}
			
		};
	}
	
	public static IntSet empty() {
		return EMPTY;
	}
	
	public static IntSet from(final IntStream stream) {
		return stream.collect(HashIntSet::new, HashIntSet::add, HashIntSet::addAll);
	}
	
	public static HashIntSet createHash(final int capacity) {
		return createHash(capacity, HashIntSet.DEFAULT_LOAD_FACTOR);
	}
	
	public static HashIntSet createHash(final int capacity, final float loadFactor) {
		return new HashIntSet(capacity, loadFactor);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimitiveIterator.OfInt iterator();

	boolean add(int val);

	boolean addAll(IntSet other);

	boolean contains(int val);
	
	boolean remove(int val);
	
	default void retainAll(final IntSet other) {
		for(OfInt itr = iterator(); itr.hasNext();) {
			final int next = itr.nextInt();
			if(!other.contains(next))
				itr.remove();
		}
	}
	
}
