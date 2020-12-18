package utils.colls;

import java.util.Objects;

/**
 * @author Sam Hooper
 *
 */
public class ImmutableSingleTypePair<T> implements SingleTypePair<T> {
	
	private final T first, second;
	
	public ImmutableSingleTypePair(T first, T second) {
		super();
		this.first = first;
		this.second = second;
	}

	@Override
	public T first() {
		return first;
	}

	@Override
	public T second() {
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
		ImmutableSingleTypePair<?> other = (ImmutableSingleTypePair<?>) obj;
		return Objects.equals(first, other.first) && Objects.equals(second, other.second);
	}

	@Override
	public String toString() {
		return Pair.toString(this);
	}
	
}
