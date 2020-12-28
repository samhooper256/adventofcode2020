package utils.colls;

import java.util.*;

/**
 * @author Sam Hooper
 *
 */
public class HashIntSet implements IntSet {
	
	private static class Node implements Iterator<Node> {
		
		private int value;
		private Node next;
		
		public Node(final int value) {
			this(value, null);
		}
		
		public Node(final int value, Node next) {
			this.value = value;
			this.next = next;
		}
		
		/** Returns the {@code int} value of this {@link Node}. */
		public int value() {
			return value;
		}
		
		public void setNext(final Node next) {
			this.next = next;
		}
		
		public void removeNext() {
			if(!hasNext())
				throw new IllegalStateException("Cannot remove next if there is none");
			next = next.next();
		}
		
		/**
		 * Returns the number of {@link Node Nodes} that come after this one, including {@code this}.
		 * {@code lengthInclusive()} is equal to {@code (lengthExclusive() + 1)}.
		 */
		public int lengthInclusive() {
			return lengthExclusive() + 1;
		}
		
		/**
		 * Returns the number of {@link Node Nodes} that come {@link #next() after} this one, excluding {@code this}.
		 * If {@link #hasNext()} is {@code false}, returns {@code 0}.
		 */
		public int lengthExclusive() {
			return hasNext() ? next().lengthInclusive() : 0;
		}
		
		/**
		 * Returns the {@link Node} that comes immediately after this one, or {@code null} if {@code (!hasNext())}.
		 */
		@Override
		public Node next() {
			return next;
		}
		
		@Override
		public boolean hasNext() {
			return next != null;
		}
		
		@Override
		public String toString() {
			return value() + "->" + next();
		}
		
		
		
	}
	
	public static final int DEFAULT_CAPACITY = 10;
	public static final float DEFAULT_LOAD_FACTOR = .75f;
	
	private Node[] buckets;
	private int size;
	private final float loadFactor;
	
	public HashIntSet() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * @throws IllegalArgumentException if {@code (capacity < 0)}.
	 */
	public HashIntSet(final int capacity) {
		this(capacity, DEFAULT_LOAD_FACTOR);
	}
	
	/**
	 * @throws IllegalArgumentException if {@code (capacity < 0)}.
	 */
	public HashIntSet(final int capacity, final float loadFactor) {
		if(capacity < 0)
			throw new IllegalArgumentException("capacity < 0");
		this.buckets = new Node[capacity];
		this.loadFactor = loadFactor;
	}
	
	public HashIntSet(IntSet copyFrom) {
		this((int) (copyFrom.size() * 1.4));
		for(PrimitiveIterator.OfInt itr = copyFrom.iterator(); itr.hasNext();)
			add(itr.next());
	}
	@Override
	public boolean add(final int val) {
		boolean added = add(val, this.buckets);
		if(added) {
			size++;
			if(1f * size / buckets.length > loadFactor)
				expand();
		}
		return added;
	}
	
	private static boolean add(final int val, final Node[] buckets) {
		int index = bucketIndex(val, buckets);
		if(buckets[index] == null) {
			buckets[index] = new Node(val);
			return true;
		}
		else {
			Node node = buckets[index];
			for(;; node = node.next()) {
				if(node.value() == val)
					return false;
				if(!node.hasNext())
					break;
			}
			node.setNext(new Node(val));
			return true;
		}
	}
	
	
	private void expand() {
		Node[] newBuckets = new Node[buckets.length << 1];
		forEachInt(i -> add(i, newBuckets));
		this.buckets = newBuckets;
	}
	
	@Override
	public boolean contains(final int val) {
		return contains(val, bucketFor(val));
	}
	
	private boolean contains(final int val, Node node) {
		for(; node != null; node = node.next())
			if(val == node.value())
				return true;
		return false;
	}
	
	
	private Node bucketFor(final int val) {
		return bucketFor(val, this.buckets);
	}
	
	/** Returns the first {@link Node} in the {@link #buckets bucket} that the given {@code int} value ({@code val}) should be put in. Returns
	 * {@code null} if that bucket is empty.*/
	private static Node bucketFor(final int val, final Node[] buckets) {
		return buckets[bucketIndex(val, buckets)];
	}
	
	/** Returns the index of the {@link #buckets bucket} that the given {@code int} value should be put in. */
	private int bucketIndex(final int val) {
		return bucketIndex(val, this.buckets);
	}
	
	/** Returns the index in {@code buckets} of the bucket that the given {@code int} value should be put in. */
	private static int bucketIndex(final int val, final Node[] buckets) {
		return Math.abs(val) % buckets.length;
	}
	
	/** Clears the {@link #buckets bucket} at {@code bucketIndex}. Updates {@link #size} accordingly. */
	private void clearBucket(final int bucketIndex) {
		Node bucket = buckets[bucketIndex];
		clearBucket(bucketIndex, buckets);
		size -= bucket.lengthInclusive();
	}
	
	/** Sets {@code buckets[bucketIndex]} to {@code null}.*/
	private static void clearBucket(final int bucketIndex, final Node[] buckets) {
		buckets[bucketIndex] = null;
	}
	
	@Override
	public boolean remove(final int val) {
		int bucketIndex = bucketIndex(val);
		Node bucket = buckets[bucketIndex];
		if(bucket == null)
			return false;
		if(bucket.value() == val) {
			buckets[bucketIndex] = bucket.next();
			size--;
			return true;
		}
		else {
			for(Node temp = bucket.next(); bucket.hasNext(); bucket = bucket.next(), temp = bucket.next()) {
				if(temp.value() == val) {
					bucket.removeNext();
					size--;
					return true;
				}
			}
			return false;
		}
	}
	
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * {@inheritDoc}
	 * <b>All behavior of the returned {@link Iterator} is undefined if this {@link HashIntSet} is modified in any way after this method is invoked,
	 * except through the iterator's own methods.</b>
	 */
	@Override
	public PrimitiveIterator.OfInt iterator() {
		return isEmpty() ? emptyIterator() : nonEmptyIterator();
	}

	private PrimitiveIterator.OfInt nonEmptyIterator() {
		return new PrimitiveIterator.OfInt() {
			//TODO better implementation? hasNext is costly, and next basically does the same thing as hasNext()
			/**
			 * the bucket index of {@code last}, or {@code -1} if {@code (last == null)}.
			 */
			int bucketIndex = -1;
			/**
			 * The last node seen (it contains the {@link Node#value() value} returned by the most recent call to next()).
			 */
			Node last = null;
			@Override
			public boolean hasNext() {
				if(last == null)
					return true; //We know that this IntSet it not empty.
				if(last.hasNext())
					return true;
				for(int i = bucketIndex + 1; i < buckets.length; i++)
					if(buckets[i] != null)
						return true;
				return false;
			}
			
			@Override
			public int nextInt() {
				if(last == null) {
					bucketIndex = 0;
					while(bucketIndex < buckets.length && buckets[bucketIndex] == null)
						bucketIndex++;
					last = buckets[bucketIndex]; //Don't need to check if out of bounds and throw exception b/c we know this IntSet is not empty.
					return last.value();
				}
				else {
					if(last.hasNext())
						return (last = last.next()).value();
					bucketIndex++;
					while(bucketIndex < buckets.length && buckets[bucketIndex] == null)
						bucketIndex++;
					if(bucketIndex == buckets.length)
						throw new NoSuchElementException();
					last = buckets[bucketIndex];
					return last.value();
				}
			}

			@Override
			public void remove() {
				if(last == null)
					throw new NoSuchElementException();
				if(buckets[bucketIndex] == last) {
					buckets[bucketIndex] = last.next();
					size--;
				}
				else {
					Node temp = buckets[bucketIndex];
					while(temp.hasNext()) {
						if(temp.next() == last) {
							temp.removeNext();
							size--;
							return;
						}
						temp = temp.next();
					}
					assert false : "Shouldn't happen";
				}
			}
			
		};
	}

	private PrimitiveIterator.OfInt emptyIterator() {
		return new PrimitiveIterator.OfInt() {
			
			@Override
			public boolean hasNext() {
				return false;
			}
			
			@Override
			public int nextInt() {
				throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new NoSuchElementException();
			}
			
		};
	}

	@Override
	public String toString() {
		StringJoiner j = new StringJoiner(", ", "[", "]");
		for(Integer i : this)
			j.add(i.toString());
		return j.toString();
	}

	@Override
	public boolean remove(Object o) {
		return o instanceof Integer && remove(((Integer) o).intValue());
	}

	@Override
	public void clear() {
		for(int i = 0; i < buckets.length; i++)
			buckets[i] = null;
		size = 0;
	}

	@Override
	public int hashCode() {
		int sum = 0;
		for(PrimitiveIterator.OfInt itr = iterator(); itr.hasNext(); )
			sum += itr.nextInt();
		return sum;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof IntSet))
			return false;
		IntSet set = (IntSet) o;
		return size() == set.size() && containsAll(set);
	}
	
	
	
}
