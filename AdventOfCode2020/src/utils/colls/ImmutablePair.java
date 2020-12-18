package utils.colls;

import java.util.Objects;

/**
 * <p>An immutable pair of objects.</p>
 * @author Sam Hooper
 *
 */
public class ImmutablePair<T1, T2> implements Pair<T1, T2> {
	
	private final T1 first;
	private final T2 second;
	
	public ImmutablePair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public T1 first() {
		return first;
	}
	
	@Override
	public T2 second() {
		return second;
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
		ImmutablePair<?, ?> other = (ImmutablePair<?, ?>) obj;
		return Objects.equals(first, other.first) && Objects.equals(second, other.second);
	}
	
	@Override
	public String toString() {
		return Pair.toString(this);
	}
}
