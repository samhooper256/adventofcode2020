package utils;

import java.util.function.*;

/**
 * Miscellaneous utilities that wouldn't fit in elsewhere.
 * @author Sam Hooper
 *
 */
public final class Misc {
	
	private Misc() {}
	
	/**
	 * Returns {@code true} if {@code predicates} has no elements.
	 */
	public static boolean satisfiesAll(int num, Iterable<IntPredicate> predicates) {
		for(IntPredicate prediate : predicates)
			if(!prediate.test(num))
				return false;
		return true;
	}
	
	/**
	 * Returns {@code false} if {@code predicates} has no elements.
	 */
	public static boolean satisfiesAny(int num, Iterable<IntPredicate> predicates) {
		for(IntPredicate prediate : predicates)
			if(prediate.test(num))
				return true;
		return false;
	}
	
	/**
	 * Returns {@code true} if {@code predicates} has no elements.
	 */
	public static boolean satsifiesNone(int num, Iterable<IntPredicate> predicates) {
		return !satisfiesAny(num, predicates);
	}
	
	/**
	 * Returns {@code true} if {@code predicates} has no elements.
	 */
	public static boolean satisfiesAll(long num, Iterable<LongPredicate> predicates) {
		for(LongPredicate prediate : predicates)
			if(!prediate.test(num))
				return false;
		return true;
	}
	
	/**
	 * Returns {@code false} if {@code predicates} has no elements.
	 */
	public static boolean satisfiesAny(long num, Iterable<LongPredicate> predicates) {
		for(LongPredicate prediate : predicates)
			if(prediate.test(num))
				return true;
		return false;
	}
	
	/**
	 * Returns {@code true} if {@code predicates} has no elements.
	 */
	public static boolean satsifiesNone(long num, Iterable<LongPredicate> predicates) {
		return !satisfiesAny(num, predicates);
	}
	
	/**
	 * Returns {@code true} if {@code predicates} has no elements.
	 */
	public static boolean satisfiesAll(double num, Iterable<DoublePredicate> predicates) {
		for(DoublePredicate prediate : predicates)
			if(!prediate.test(num))
				return false;
		return true;
	}
	
	/**
	 * Returns {@code false} if {@code predicates} has no elements.
	 */
	public static boolean satisfiesAny(double num, Iterable<DoublePredicate> predicates) {
		for(DoublePredicate prediate : predicates)
			if(prediate.test(num))
				return true;
		return false;
	}
	
	/**
	 * Returns {@code true} if {@code predicates} has no elements.
	 */
	public static boolean satsifiesNone(double num, Iterable<DoublePredicate> predicates) {
		return !satisfiesAny(num, predicates);
	}
}
