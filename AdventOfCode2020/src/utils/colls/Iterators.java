package utils.colls;

import java.util.PrimitiveIterator;

/**
 * @author Sam Hooper
 *
 */
public final class Iterators {
	
	private Iterators() {}
	
	public static PrimitiveIterator.OfInt singleton(int value) {
		return new PrimitiveIterator.OfInt() {
			
			boolean hasNext = true;
			@Override
			public boolean hasNext() {
				return hasNext;
			}
			
			@Override
			public int nextInt() {
				hasNext = false;
				return value;
			}
			
		};
	}
	
}
