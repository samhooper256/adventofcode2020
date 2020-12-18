package utils;

import java.util.*;
import java.util.PrimitiveIterator.OfInt;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
/**
 * @author Sam Hooper
 *
 */
public interface IntSet extends Set<Integer> {
	
	IntSet EMPTY = unmodifiable(new HashIntSet(0));
	
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
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfInt iterator();

	boolean add(int val);

	boolean contains(int val);
	
	boolean remove(int val);
	
	default boolean addAll(final IntSet other) {
		boolean changed = false;
		for(PrimitiveIterator.OfInt itr = other.iterator(); itr.hasNext(); )
			changed |= add(itr.nextInt());
		return changed;
	}
	
	default boolean addAll(int... values) {
		boolean changed = false;
		for(int val : values)
			changed |= add(val);
		return changed;
	}
	
	default boolean containsAll(IntSet other) {
		for(OfInt itr = other.iterator(); itr.hasNext();)
			if(!contains(itr.nextInt()))
				return false;
		return true;
	}
	
	default boolean retainAll(final IntSet other) {
		boolean changed = false;
		for(OfInt itr = iterator(); itr.hasNext();) {
			if(!other.contains(itr.nextInt())) {
				itr.remove();
				changed = true;
			}
		}
		return changed;
	}
	
	default boolean removeAll(IntSet other) {
		boolean changed = false;
		for(OfInt itr = other.iterator(); itr.hasNext();)
			changed |= remove(itr.nextInt());
		return changed;
	}
	
	/** The {@code ints} are consumed in the same order as in the {@link #iterator()}. */
	default void forEachInt(IntConsumer action) {
		for(OfInt itr = iterator(); itr.hasNext();)
			action.accept(itr.nextInt());
	}
	
	@Override
	default boolean add(Integer e) {
		return add(e.intValue());
	}
	
	@Override
	default boolean contains(Object o) {
		return o instanceof Integer && contains(((Integer) o).intValue());
	}
	
	@Override
	default boolean addAll(Collection<? extends Integer> c) {
		if(c instanceof IntSet)
			return addAll((IntSet) c);
		boolean changed = false;
		for(Integer i : c) //throws NPE if c is null, as required
			changed |= add(i.intValue()); //throws NPE if i is null, as required
		return changed;
	}
	
	@Override
	default boolean containsAll(Collection<?> c) {
		if(c instanceof IntSet)
			return containsAll((IntSet) c);
		for(Object o : c)
			if(!contains(o))
				return false;
		return true;
	}
	
	@Override
	default boolean retainAll(Collection<?> c) {
		if(c instanceof IntSet)
			return retainAll((IntSet) c);
		boolean changed = false;
		for(Iterator<?> itr = iterator(); itr.hasNext();) {
			if(!c.contains(itr.next())) {
				itr.remove();
				changed = true;
			}
		}
		return changed;
	}
	
	@Override
	default boolean removeAll(Collection<?> c) {
		if(c instanceof IntSet)
			return removeAll((IntSet) c);
		boolean changed = false;
		for(Object o : c)
			changed |= remove(o);
		return changed;
	}
	
	@Override
	default Object[] toArray() {
		return toArray(new Integer[size()]);
	}

	/**
	 * {@inheritDoc} <p><b>Throws a {@link ClassCastException} if {@code T} is not {@code Integer} or a supertype thereof.</b></p>
	 */
	@Override
	@SuppressWarnings("unchecked")
	default <T> T[] toArray(T[] a) {
		if(a.length < size()) {
			T[] arr = (T[]) Arrays.copyOf(a, size(), a.getClass());
			int index = 0;
			for(PrimitiveIterator.OfInt itr = iterator(); itr.hasNext();)
				arr[index++] = (T) itr.next();
			return arr;
		}
		else {
			int index = 0;
			for(PrimitiveIterator.OfInt itr = iterator(); itr.hasNext();)
				a[index++] = (T) itr.next();
			if(index < a.length)
				a[index] = null;
			return a;
		}
	}
	
}
