package utils;

import java.util.Objects;

/**
 * <p>An immutable {@link Pair} of {@code longs}. The {@link #first()} and {@link #second()} elements can be retrieved as {@code longs} via {@link #firstLong()} and
 * {@link #secondLong()}.</p>
 * @author Sam Hooper
 *
 */
public class LongPair implements SingleTypePair<Long> {
	
	private final long first, second;
	
	public LongPair(final long first, final long second) {
		this.first = first;
		this.second = second;
	}
	
	public long firstLong() {
		return first;
	}
	
	public long secondLong() {
		return second;
	}

	@Override
	public Long first() {
		return firstLong();
	}

	@Override
	public Long second() {
		return secondLong();
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
		LongPair other = (LongPair) obj;
		return first == other.first && second == other.second;
	}
	
	@Override
	public String toString() {
		return "(" + firstLong() + ", " + secondLong() + ")";
	}
}
