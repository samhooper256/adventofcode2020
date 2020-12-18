package utils.colls;

import java.util.Objects;
import java.util.function.*;
import java.util.stream.*;

/**
 * <p>An immutable {@link Pair} of {@code ints}. The {@link #first()} and {@link #second()} elements can be retrieved as {@code ints} via {@link #firstInt()} and
 * {@link #secondInt()}.</p>
 * @author Sam Hooper
 *
 */
public class IntPair implements SingleTypePair<Integer> {
	
	/**
	 * Returns an {@link IntPair} containing the two {@code ints}.
	 */
	public static IntPair of(final int first, final int second) {
		return new IntPair(first, second);
	}
	
	private final int first, second;
	
	public IntPair(final int first, final int second) {
		this.first = first;
		this.second = second;
	}
	
	public int firstInt() {
		return first;
	}
	
	public int secondInt() {
		return second;
	}

	@Override
	public Integer first() {
		return firstInt();
	}

	@Override
	public Integer second() {
		return secondInt();
	}
	
	public long productAsInt() {
		return firstInt() * secondInt();
	}
	
	public long productAsLong() {
		return ((long) firstInt()) * secondInt();
	}
	
	public int sumAsInt() {
		return firstInt() + secondInt();
	}
	
	public long sumAsLong() {
		return ((long) firstInt()) + secondInt();
	}
	
	public int[] toIntArray() {
		return new int[] {firstInt(), secondInt()};
	}
	
	/** The returned {@link IntStream} is ordered with {@link #first()} followed by {@link #second()}. */
	public IntStream streamInts() {
		return IntStream.of(firstInt(), secondInt());
	}
	
	public IntPair map(final IntUnaryOperator op) {
		return new IntPair(op.applyAsInt(firstInt()), op.applyAsInt(secondInt()));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		IntPair other = (IntPair) obj;
		return first == other.first && second == other.second;
	}
	
	@Override
	public String toString() {
		return Pair.toString(this);
	}
	
}
