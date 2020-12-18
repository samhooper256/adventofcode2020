package utils.colls;

import java.util.Objects;

/**
 * <p>An immutable {@link Pair} containing an {@code int} and an {@link Object}. The {@code int} is the {@link #first()} element and the {@code Object} is the
 * {@link #second()}. The {@code int} part can be retrieved as an {@code int} via {@link #intPart()} and {@code Object} part via {@link #objPart()}.</p>
 * @author Sam Hooper
 *
 */
public class IntObjPair<T> implements Pair<Integer, T> {
	
	/**
	 * Returns an {@link IntObjPair} containing the given {@code int} and the given {@link Object}.
	 */
	public static <T> IntObjPair<T> of(final int intPart, final T objPart) {
		return new IntObjPair<>(intPart, objPart);
	}
	
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
	
	@Override
	public int hashCode() {
		return Objects.hash(intPart, objPart);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		IntObjPair<?> other = (IntObjPair<?>) obj;
		return intPart == other.intPart && Objects.equals(objPart, other.objPart);
	}

	@Override
	public String toString() {
		return Pair.toString(this);
	}
	
}
