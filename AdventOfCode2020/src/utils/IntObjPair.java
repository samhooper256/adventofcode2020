package utils;

/**
 * <p>An immutable {@link Pair} containing an {@code int} and an {@link Object}. The {@code int} is the {@link #first()} element and the {@code Object} is the
 * {@link #second()}. The {@code int} part can be retrieved as an {@code int} via {@link #intPart()} and {@code Object} part via {@link #objPart()}.</p>
 * @author Sam Hooper
 *
 */
public class IntObjPair<T> implements Pair<Integer, T> {
	
	private final int intPart;
	private final T objPart;
	
	public IntObjPair(final int intPart, final T objPart) {
		this.intPart = intPart;
		this.objPart = objPart;
	}
	
	@Override
	public Integer first() {
		return intPart();
	}

	@Override
	public T second() {
		return objPart();
	}
	
	public int intPart() {
		return intPart;
	}
	
	public T objPart() {
		return objPart;
	}
	
}
