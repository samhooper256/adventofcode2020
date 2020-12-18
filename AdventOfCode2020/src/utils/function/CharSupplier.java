package utils.function;

import java.util.function.Supplier;

/**
 * @author Sam Hooper
 *
 */
public interface CharSupplier extends Supplier<Character> {
	
	char getAsChar();
	
	@Override
	default Character get() {
		return getAsChar();
	}
	
}
