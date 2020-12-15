package utils;

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
	
	
}
