package utils.colls;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * @author Sam Hooper
 *
 */
public class LongList implements Iterable<Long> {
	/** stores 0 in unused indices. The used indices are those in the range 0 (inclusive) to size (exclusive). */
	long[] data; 
	/** The number of elements in this {@link LongList}. It is always true that {@code size <= data.length}.*/
	int size;
	
	/** Returns a freshly created {@link LongList} containing exactly {@code numElements} elements, all of which are 0.
	 * The capacity of the returned list is {@code numElements}.*/
	public static LongList zeroList(int numElements) {
		return zeroList(numElements, numElements);
	}
	
	/** Returns a freshly created {@code LongList} containing exactly {@code numElements} elements, all of which are 0.
	 * The capacity of the returned list is {@code capacity}.*/
	public static LongList zeroList(int numElements, int capacity) {
		if(capacity < numElements)
			throw new IllegalArgumentException("capacity < numElements");
		LongList l = new LongList(capacity);
		l.size = numElements;
		return l;
	}
	
	/** Constructs an empty {@link LongList} with a capacity of {@code 10}. */
	public LongList() { this(10); }
	
	/** Be sure that you don't accidentally try to use this constructor to make a single-element {@code LongList} */
	public LongList(int capacity) {
		data = new long[capacity];
	}
	
	/** Creates an {@code LongList} containing the values in the array. The capacity is set to the length of the array. */
	public LongList(long... copyFrom) {
		this(copyFrom, copyFrom.length);
	}
	
	/** Creates an {@code LongList} containing the first {@code capacity} values of {@code copyFrom} with
	 * capacity {@code capacity}. If {@code capacity > copyFrom.length}, then all of the elements of {@code copyFrom}
	 * are added and the size of the list is the number of elements in {@code copyFrom}.
	 */
	public LongList(long[] copyFrom, int capacity) {
		data = Arrays.copyOf(copyFrom, capacity);
		size = Math.min(capacity, copyFrom.length);
	}
	
	public LongList(LongList copyFrom) {
		data = Arrays.copyOf(copyFrom.data, copyFrom.data.length);
		size = copyFrom.size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	/** Adds the given item to this list without checking if the internal array has enough capacity. May throw an
	 * {@link IndexOutOfBoundsException}.
	 */
	public void addSafe(long item) {
		data[size++] = item;
	}
	
	/** Adds the given item to this list, automatically expanding this list's capacity if needed. */
	public void add(long item) {
		add(size, item);
	}
	
	/** Adds the given item to this list at the given index (such that, after this call, {@code get(index) == item}),
	 * automatically expanding this list's capacity if needed. */
	public void add(final int index, final long item) {
		if(index > size)
			throw new IllegalArgumentException("index > size");
		if(size == data.length)
			doubleCapacity();
		pushForwardSafeKeepingValue(index);
		data[index] = item;
		size++;
	}
	
	/** <p>Shifts all elements starting from index (inclusive) to size (exclusive) one index to the right. Does not check for capacity.
	 * After this method is called, {@code data[index]} will still hold the same value as before. <b>THIS METHOD DOES NOT
	 * INCREASE {@link #size}.</b></p>*/
	private void pushForwardSafeKeepingValue(int index) {
		for(int i = size; i > index;)
			data[index] = data[--index];
	}
	
	public void set(int index, long item) {
		if(index >= size)
				throw new IllegalArgumentException("index >= size");
		setSafe(index, item);
	}
	
	/** Sets the element at given index to the given item, without performing bounds checks. This method allows the
	 * caller to set the value of any index in {@code data}, even if it that index is greater than or equal to {@link #size}.
	 * Does not change {@code size}.*/
	private void setSafe(final int index, final long item) {
		data[index] = item;
	}
	
	/** Removes the first (lowest index) occurrence of {@code item}. Returns false if {@code item} was not present,
	 * true otherwise. */
	public boolean remove(long item) {
		for(int i = 0; i < size; i++) {
			if(data[i] == item) {
				removeByIndexSafe(i);
				return true;
			}
		}
		return false;
	}
	
	/** Removes and returns the element at {@code index}.
	 * throws {@link ArrayIndexOutOfBoundsException} if {@code index} is negative or greater than or equal to the
	 * size of this {@code LongList}. */
	public long removeByIndex(int index) {
		if(index >= size)
			throw new ArrayIndexOutOfBoundsException("index >= size");
		return removeByIndexSafe(index);
	}
	
	/**
	 * Removes and returns the element at index {@code index} in this {@link LongList}. No bounds checking is done on {@code index}.
	 */
	public long removeByIndexSafe(final int index) {
		final long value = data[index]; 
		for(int j = index; j < size - 1; j++)
			data[j] = data[j + 1];
		size--;
		data[size] = 0;
		return value;
	}
	
	/** Removes and returns the last (highest index/rightmost) element in this list. Throws {@link NoSuchElementException} if
	 * the list is empty.*/
	public long  pop() {
		if(size == 0)
			throw new NoSuchElementException();
		return popSafe();
	}
	
	/** Removes and returns the last (highest index/rightmost) element in this list. Assumes the list is not empty. */
	public long popSafe() {
		long value = data[--size];
		data[size] = 0;
		return value;
	}
	
	/** Returns the last (highest index/rightmost) element in this list. Throws {@link NoSuchElementException} if the list is empty */
	public long peek() {
		if(size == 0)
			throw new NoSuchElementException();
		return peekSafe();
	}
	
	/** Returns the last (highest index/rightmost) element in this list. Assumes the list is not empty.*/
	public long peekSafe() {
		return data[size - 1];
	}
	
	public boolean contains(long item) {
		for(int i = 0; i < size; i++)
			if(data[i] == item)
				return true;
		return false;
	}
	
	/** Returns the item at the given index. Throws {@link ArrayIndexOutOfBoundsException} if {@code index} is negative
	 * or greater than or equal to {@code size}.*/
	public long get(int index) {
		return data[index];
	}
	
	public int size() {
		return size;
	}
	
	public void clear() {
		Arrays.fill(data, 0, size, 0L);
		size = 0;
	}
	
	public void doubleCapacity() {
		data = Arrays.copyOf(data, data.length << 1);
	}
	
	public int getCapcity() {
		return data.length;
	}
	
	/** Sets this {@code LongList}'s capacity to {@code minCapacity} if {@code minCapacity} is greater than its current capacity.
	 * Has no effect otherwise.*/
	public void ensureCapacity(int minCapacity) {
		if(minCapacity > data.length)
			data = Arrays.copyOf(data, minCapacity);
	}
	
	public void forEach(LongConsumer consumer) {
		for(int i = 0; i < size; i++)
			consumer.accept(data[i]);
	}
	
	public LongStream stream() {
		return Arrays.stream(data, 0, size);
	}
	
	/**
	 * Note that it will likely be more efficient to iterate over the elements of this {@code LongList} without using
	 * the iterator, as it will not require boxing to {@link Long}. The returned iterator makes no guarantees about
	 * its behavior if elements are added or removed from this list after it has been created, except by its on
	 * remove method.
	 */
	@Override
	public java.util.Iterator<Long> iterator() {
		return new Itr();
	}
	
	private class Itr implements Iterator<Long> {
		int index; //index of the NEXT element to be returned by next(). Zero by default.
		boolean canRemove;
		@Override
		public boolean hasNext() {
			return index != size;
		}

		@Override
		public Long next() {
			if(index >= size)
				throw new NoSuchElementException();
			canRemove = true;
			return data[index++];
		}

		@Override
		public void remove() {
			if(!canRemove)
				throw new IllegalStateException();
			canRemove = false;
			removeByIndex(--index);
		}
		
	}
	
	public PrimitiveIterator.OfLong primitiveIterator() {
		return new PrimitiveIterator.OfLong() {
			int index; //index of the NEXT element to be returned by next(). Zero by default.
			boolean canRemove;
			
			@Override
			public boolean hasNext() {
				return index != size;
			}

			@Override
			public long nextLong() {
				if(index >= size)
					throw new NoSuchElementException();
				canRemove = true;
				return data[index++];
			}

			@Override
			public void remove() {
				if(!canRemove)
					throw new IllegalStateException();
				canRemove = false;
				removeByIndex(--index);
			}
		};
	}
	
	@Override
	public String toString() {
		StringJoiner j = new StringJoiner(", ", "[", "]");
		for(int i = 0; i < size; i++)
			j.add(Long.toString(data[i]));
		return j.toString();
	}
	
	/** Utility methods */
	
	public long sum() {
		long s = 0;
		for(int i = 0; i < size; i++)
			s += data[i];
		return s;
	}
}
