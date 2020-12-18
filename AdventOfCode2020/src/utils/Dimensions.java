package utils;

import java.util.function.*;

import utils.function.*;

/**
 * Utilities for operating on higher dimensional things (such as arrays).
 * @author Sam Hooper
 *
 */
public final class Dimensions {
	
	private Dimensions() {}
	
	public static final int[][] ADJACENT_26 = {
			{-1,-1,-1},
			{-1,-1, 0},
			{-1,-1, 1},
			{-1, 0,-1},
			{-1, 0, 0},
			{-1, 0, 1},
			{-1, 1,-1},
			{-1, 1, 0},
			{-1, 1, 1},
			{ 0,-1,-1},
			{ 0,-1, 0},
			{ 0,-1, 1},
			{ 0, 0,-1},
			{ 0, 0, 1},
			{ 0, 1,-1},
			{ 0, 1, 0},
			{ 0, 1, 1},
			{ 1,-1,-1},
			{ 1,-1, 0},
			{ 1,-1, 1},
			{ 1, 0,-1},
			{ 1, 0, 0},
			{ 1, 0, 1},
			{ 1, 1,-1},
			{ 1, 1, 0},
			{ 1, 1, 1}
	};
	
	public static final int[][] ADJACENT_80 = makeAdjacent80();
	
	private static int[][] makeAdjacent80() {
		final int[][] adj = new int[80][];
		int index = 0;
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				for(int z = -1; z <= 1; z++) {
					for(int w = -1; w <= 1; w++) {
						if(x == 0 && y == 0 && z == 0 && w == 0)
							continue;
						adj[index++] = new int[] {x, y, z, w};
					}
				}
			}
		}
		return adj;
	}
	
	/**
	 * <p>Performs the given action on each element in the array. If the array has more than one dimension, the elements are the things that have the element type
	 * (as opposed to the component type) of the array. For example, there are 12 elements in a {@code new boolean[3][4]} that are all of type {@code boolean}.<p>
	 * <p>If the element type is a primitive type, the given {@link Consumer} must be specialized for that primitive type (For example, passing a {@code boolean[]} and
	 * a {@code Consumer<Boolean>} will cause an exception. You should instead pass a {@link BooleanConsumer}). The primitive specialized method of the consumer
	 * will be used.<p>
	 * @throws IllegalArgumentException if the element type of the given array is not a reference type nor one of:
	 * <ul><li><code>boolean</code></li><li><code>char</code></li></ul>
	 * @throws ClassCastException if any of the following are true:
	 * <ul>
	 * <li>the given array is a {@code boolean[]} and {@code consumer} is not a {@link BooleanConsumer}.</li>
	 * <li>the given array is a {@code char[]} and {@code consumer} is not a {@link CharConsumer}.</li>
	 * </ul>
	 */
	@SuppressWarnings("unchecked")
	public static <T> void forEach(Object arr, Consumer<T> consumer) {
		if(arr instanceof Object[]) {
			Object[] asArr = (Object[]) arr;
			for(Object o : asArr)
				if(Arrs.isArray(o))
					forEach(o, consumer);
				else
					consumer.accept((T) o);
		}
		else if(arr instanceof boolean[]) {
			boolean[] asArr = (boolean[]) arr;
			BooleanConsumer asBC = (BooleanConsumer) consumer;
			for(boolean b : asArr)
				asBC.accept(b);
		}
		else if(arr instanceof char[]) {
			char[] asArr = (char[]) arr;
			CharConsumer asCC = (CharConsumer) consumer;
			for(char c : asArr)
				asCC.accept(c);
		}
		else {			
			throw new IllegalArgumentException("Given array does not have a reference type, boolean, or char as its element type.");
		}
	}
	
	/** Assumes the element type of the given array is {@code boolean}. The body of this method is equivalent to (but may be more efficient than):
	 * <pre><code>forEach(arr, consumer);</code></pre>
	 */
	public static void forEachBoolean(Object arr, BooleanConsumer consumer) {
		if(arr instanceof boolean[])
			Arrs.forEach((boolean[]) arr, consumer);
		else if(arr instanceof boolean[][])
			forEachBoolean((boolean[][]) arr, consumer);
		else if(arr instanceof boolean[][][])
			forEachBoolean((boolean[][][]) arr, consumer);
		else if(arr instanceof boolean[][][][])
			forEachBoolean((boolean[][][][]) arr, consumer);
		else
			forEach(arr, consumer);
	}
	
	public static void forEachBoolean(boolean[][] arr, BooleanConsumer consumer) {
		for(boolean[] elem : arr)
			Arrs.forEach(elem, consumer);
	}

	public static void forEachBoolean(boolean[][][] arr, BooleanConsumer consumer) {
		for(boolean[][] elem : arr)
			forEachBoolean(elem, consumer);
	}
	
	public static void forEachBoolean(boolean[][][][] arr, BooleanConsumer consumer) {
		for(boolean[][][] elem : arr)
			forEachBoolean(elem, consumer);
	}
	
	/** Assumes the element type of the given array is {@code char}. The body of this method is equivalent to (but may be more efficient than):
	 * <pre><code>forEach(arr, consumer);</code></pre>
	 */
	public static void forEachChar(Object arr, CharConsumer consumer) {
		if(arr instanceof char[])
			Arrs.forEach((char[]) arr, consumer);
		else if(arr instanceof char[][])
			forEachChar((char[][]) arr, consumer);
		else if(arr instanceof char[][][])
			forEachChar((char[][][]) arr, consumer);
		else if(arr instanceof char[][][][])
			forEachChar((char[][][][]) arr, consumer);
		else
			forEach(arr, consumer);
	}
	
	public static void forEachChar(char[][] arr, CharConsumer consumer) {
		for(char[] elem : arr)
			Arrs.forEach(elem, consumer);
	}

	public static void forEachChar(char[][][] arr, CharConsumer consumer) {
		for(char[][] elem : arr)
			forEachChar(elem, consumer);
	}
	
	public static void forEachChar(char[][][][] arr, CharConsumer consumer) {
		for(char[][][] elem : arr)
			forEachChar(elem, consumer);
	}
	
	/**
	 * The element type of the given array must be a reference type.
	 */
	@SuppressWarnings("unchecked")
	public static <T> int countSatisfying(Object arr, Predicate<T> predicate) {
		int[] count = {0};
		forEach(arr, x -> {
			if(predicate.test((T) x))
				count[0]++;
		});
		return count[0];
	}
	
	/**
	 * The element type of the given array must be {@code boolean}.
	 */
	public static int countSatisfyingBooleans(Object arr, BooleanPredicate predicate) {
		int[] count = {0};
		forEachBoolean(arr, x -> {
			if(predicate.test(x))
				count[0]++;
		});
		return count[0];
	}
	
	/**
	 * The element type of the given array must be {@code char}.
	 */
	public static int countSatisfyingChars(Object arr, CharPredicate predicate) {
		int[] count = {0};
		forEachChar(arr, x -> {
			if(predicate.test(x))
				count[0]++;
		});
		return count[0];
	}
}
