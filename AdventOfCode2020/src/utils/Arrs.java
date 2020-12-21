package utils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import utils.colls.*;
import utils.function.*;

/**
 * <p>All methods throw a {@link NullPointerException} if a parameter is {@code null}, unless that parameter is explicitly allowed to be {@code null} by the method
 * documentation.</p>
 * @author Sam Hooper
 *
 */
public final class Arrs {
	
	private Arrs() {}
	
	/**
	 * Returns {@code false} if the given object is {@code null}.
	 */
	public static boolean isArray(Object obj) {
		return obj != null && obj.getClass().isArray();
	}
	
	/**
	 * Returns {@code true} if the given object is an array whose component type is a primitive type, {@code false} otherwise.
	 * Returns {@code false} if the given object is {@code null}.
	 */
	public static boolean isPrimitiveArray(Object obj) {
		return isArray(obj) && obj.getClass().componentType().isPrimitive();
	}
	
	/**
	 * Returns {@code true} if the given object is an array whose component type is a reference type, {@code false} otherwise.
	 * Returns {@code false} if the given object is {@code null}.
	 */
	public static boolean isReferenceArray(Object obj) {
		return isArray(obj) && !obj.getClass().componentType().isPrimitive();
	}
	
	/**
	 * Sorts the given array in place (using {@link Arrays#sort(byte[])}) and then returns the given array.
	 */
	public static byte[] sorted(final byte[] arr) {
		Arrays.sort(arr);
		return arr;
	}
	
	/**
	 * Sorts the given array in place (using {@link Arrays#sort(short[])}) and then returns the given array.
	 */
	public static short[] sorted(final short[] arr) {
		Arrays.sort(arr);
		return arr;
	}
	
	/**
	 * Sorts the given array in place (using {@link Arrays#sort(char[])}) and then returns the given array.
	 */
	public static char[] sorted(final char[] arr) {
		Arrays.sort(arr);
		return arr;
	}
	
	/**
	 * Sorts the given array in place (using {@link Arrays#sort(int[])}) and then returns the given array.
	 */
	public static int[] sorted(final int[] arr) {
		Arrays.sort(arr);
		return arr;
	}
	
	/**
	 * Sorts the given array in place (using {@link Arrays#sort(long[])}) and then returns the given array.
	 */
	public static long[] sorted(final long[] arr) {
		Arrays.sort(arr);
		return arr;
	}
	
	/**
	 * Sorts the given array in place (using {@link Arrays#sort(float[])}) and then returns the given array.
	 */
	public static float[] sorted(final float[] arr) {
		Arrays.sort(arr);
		return arr;
	}
	
	/**
	 * Sorts the given array in place (using {@link Arrays#sort(double[])}) and then returns the given array.
	 */
	public static double[] sorted(final double[] arr) {
		Arrays.sort(arr);
		return arr;
	}
	
	/**
	 * <p>Returns the number of elements in {@code arr} that satisfy the given {@link Predicate}.</p>
	 */
	public static <T> int count(T[] arr, Predicate<? super T> tester) {
		int count = 0;
		for(T item : arr)
			if(tester.test(item))
				count++;
		return count;
	}
	
	/**
	 * <p>Returns the number of elements in {@code arr} that satisfy the given {@link BooleanPredicate}.</p>
	 */
	public static int count(boolean[] arr, BooleanPredicate tester) {
		int count = 0;
		for(boolean item : arr)
			if(tester.testBoolean(item))
				count++;
		return count;
	}
	
	/**
	 * <p>Returns the number of elements in {@code arr} that satisfy the given {@link BytePredicate}.</p>
	 */
	public static int count(byte[] arr, BytePredicate tester) {
		int count = 0;
		for(byte item : arr)
			if(tester.testByte(item))
				count++;
		return count;
	}
	
	/**
	 * <p>Returns the number of elements in {@code arr} that satisfy the given {@link ShortPredicate}.</p>
	 */
	public static int count(short[] arr, ShortPredicate tester) {
		int count = 0;
		for(short item : arr)
			if(tester.testShort(item))
				count++;
		return count;
	}
	
	/**
	 * <p>Returns the number of elements in {@code arr} that satisfy the given {@link CharPredicate}.</p>
	 */
	public static int count(char[] arr, CharPredicate tester) {
		int count = 0;
		for(char item : arr)
			if(tester.testChar(item))
				count++;
		return count;
	}
	
	/**
	 * <p>Returns the number of elements in {@code arr} that satisfy the given {@link IntPredicate}.</p>
	 */
	public static int count(int[] arr, IntPredicate tester) {
		int count = 0;
		for(int item : arr)
			if(tester.test(item))
				count++;
		return count;
	}
	
	/**
	 * <p>Returns the number of elements in {@code arr} that satisfy the given {@link LongPredicate}.</p>
	 */
	public static int count(long[] arr, LongPredicate tester) {
		int count = 0;
		for(long item : arr)
			if(tester.test(item))
				count++;
		return count;
	}
	
	/**
	 * <p>Returns the number of elements in {@code arr} that satisfy the given {@link FloatPredicate}.</p>
	 */
	public static int count(float[] arr, FloatPredicate tester) {
		int count = 0;
		for(float item : arr)
			if(tester.testFloat(item))
				count++;
		return count;
	}
	
	/**
	 * <p>Returns the number of elements in {@code arr} that satisfy the given {@link DoublePredicate}.</p>
	 */
	public static int count(double[] arr, DoublePredicate tester) {
		int count = 0;
		for(double item : arr)
			if(tester.test(item))
				count++;
		return count;
	}
	
	/**
	 * <p>Returns the number of times {@code val} occurs in {@code arr}.</p>
	 */
	public static int count(boolean[] arr, boolean val) {
		return count(arr, x -> x == val);
	}
	
	/**
	 * <p>Returns the number of times {@code val} occurs in {@code arr}.</p>
	 */
	public static int count(byte[] arr, byte val) {
		return count(arr, x -> x == val);
	}
	
	/**
	 * <p>Returns the number of times {@code val} occurs in {@code arr}.</p>
	 */
	public static int count(short[] arr, short val) {
		return count(arr, x -> x == val);
	}
	
	/**
	 * <p>Returns the number of times {@code val} occurs in {@code arr}.</p>
	 */
	public static int count(char[] arr, char val) {
		return count(arr, x -> x == val);
	}
	
	/**
	 * <p>Returns the number of times {@code val} occurs in {@code arr}.</p>
	 */
	public static int count(int[] arr, int val) {
		return count(arr, x -> x == val);
	}
	
	/**
	 * <p>Returns the number of times {@code val} occurs in {@code arr}.</p>
	 */
	public static int count(long[] arr, long val) {
		return count(arr, x -> x == val);
	}
	
	/**
	 * <p>Returns the number of times {@code val} occurs in {@code arr}.</p>
	 */
	public static int count(float[] arr, float val) {
		return count(arr, x -> x == val);
	}
	
	/**
	 * <p>Returns the number of times {@code val} occurs in {@code arr}.</p>
	 */
	public static int count(double[] arr, double val) {
		return count(arr, x -> x == val);
	}
	
	/** The given array is not modified. */
	public static boolean[] reversed(boolean[] arr) {
		boolean[] reversed = new boolean[arr.length];
		for(int i = 0; i < arr.length; i++)
			reversed[arr.length - i - 1] = arr[i];
		return reversed;
	}
	
	/** The given array is not modified. */
	public static byte[] reversed(byte[] arr) {
		byte[] reversed = new byte[arr.length];
		for(int i = 0; i < arr.length; i++)
			reversed[arr.length - i - 1] = arr[i];
		return reversed;
	}
	
	/** The given array is not modified. */
	public static short[] reversed(short[] arr) {
		short[] reversed = new short[arr.length];
		for(int i = 0; i < arr.length; i++)
			reversed[arr.length - i - 1] = arr[i];
		return reversed;
	}
	
	/** The given array is not modified. */
	public static char[] reversed(char[] arr) {
		char[] reversed = new char[arr.length];
		for(int i = 0; i < arr.length; i++)
			reversed[arr.length - i - 1] = arr[i];
		return reversed;
	}
	
	/** The given array is not modified. */
	public static int[] reversed(int[] arr) {
		int[] reversed = new int[arr.length];
		for(int i = 0; i < arr.length; i++)
			reversed[arr.length - i - 1] = arr[i];
		return reversed;
	}
	
	/** The given array is not modified. */
	public static long[] reversed(long[] arr) {
		long[] reversed = new long[arr.length];
		for(int i = 0; i < arr.length; i++)
			reversed[arr.length - i - 1] = arr[i];
		return reversed;
	}
	
	/** The given array is not modified. */
	public static float[] reversed(float[] arr) {
		float[] reversed = new float[arr.length];
		for(int i = 0; i < arr.length; i++)
			reversed[arr.length - i - 1] = arr[i];
		return reversed;
	}
	
	/** The given array is not modified. */
	public static double[] reversed(double[] arr) {
		double[] reversed = new double[arr.length];
		for(int i = 0; i < arr.length; i++)
			reversed[arr.length - i - 1] = arr[i];
		return reversed;
	}
	
	public static void fill2D(final char[][] arr, final char c) {
		Basics.for2D(arr, (row, col) -> arr[row][col] = c);
	}
	
	public static char[][] copy2D(final char[][] arr) {
		char[][] copy = new char[arr.length][];
		for(int row = 0; row < arr.length; row++)
			copy[row] = Arrays.copyOf(arr[row], arr[row].length);
		return copy;
	}
	
	public static int count2D(final char[][] arr, char item) {
		int count = 0;
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				if(arr[i][j] == item)
					count++;
		return count;
	}
	
	/**
	 * <p>Returns {@code true} if {@code arr} contains {@code item}, {@code false} otherwise. More precisely, returns {@code true} if there
	 * is a value {@code i} such that {@code Objects.equals(arr[i], item)}. {@code item} may be {@code null}, {@code arr} must not.</p>
	 */
	public static boolean contains(final Object[] arr, final Object item) {
		return indexOf(arr, item) >= 0;
	}
	
	public static boolean contains(final boolean[] arr, final boolean item) {
		return indexOf(arr, item) >= 0;
	}
	
	public static boolean contains(final byte[] arr, final byte item) {
		return indexOf(arr, item) >= 0;
	}
	
	public static boolean contains(final short[] arr, final short item) {
		return indexOf(arr, item) >= 0;
	}
	
	public static boolean contains(final char[] arr, final char item) {
		return indexOf(arr, item) >= 0;
	}
	
	public static boolean contains(final int[] arr, final int item) {
		return indexOf(arr, item) >= 0;
	}
	
	public static boolean contains(final long[] arr, final long item) {
		return indexOf(arr, item) >= 0;
	}
	
	public static boolean contains(final float[] arr, final float item) {
		return indexOf(arr, item) >= 0;
	}
	
	public static boolean contains(final double[] arr, final double item) {
		return indexOf(arr, item) >= 0;
	}
	
	public static int indexOf(final Object[] arr, final Object item) {
		return indexOf(arr, o -> Objects.equals(item, o));
	}
	
	public static <T> int indexOf(final T[] arr, final Predicate<? super T> predicate) {
		for(int i = 0; i < arr.length; i++)
			if(predicate.test(arr[i]))
				return i;
		return -1;
	}
	
	public static int indexOf(final boolean[] arr, final boolean item) {
		return indexOf(arr, i -> i == item);
	}
	
	public static int indexOf(final boolean[] arr, final BooleanPredicate predicate) {
		for(int i = 0; i < arr.length; i++)
			if(predicate.test(arr[i]))
				return i;
		return -1;
	}
	
	public static int indexOf(final byte[] arr, final byte item) {
		return indexOf(arr, i -> i == item);
	}
	
	public static int indexOf(final byte[] arr, final BytePredicate predicate) {
		for(int i = 0; i < arr.length; i++)
			if(predicate.test(arr[i]))
				return i;
		return -1;
	}
	
	public static int indexOf(final short[] arr, final short item) {
		return indexOf(arr, i -> i == item);
	}
	
	public static int indexOf(final short[] arr, final ShortPredicate predicate) {
		for(int i = 0; i < arr.length; i++)
			if(predicate.test(arr[i]))
				return i;
		return -1;
	}
	
	public static int indexOf(final char[] arr, final char item) {
		return indexOf(arr, i -> i == item);
	}
	
	public static int indexOf(final char[] arr, final CharPredicate predicate) {
		for(int i = 0; i < arr.length; i++)
			if(predicate.test(arr[i]))
				return i;
		return -1;
	}
	
	public static int indexOf(final int[] arr, final int item) {
		return indexOf(arr, i -> i == item);
	}
	
	public static int indexOf(final int[] arr, final IntPredicate predicate) {
		for(int i = 0; i < arr.length; i++)
			if(predicate.test(arr[i]))
				return i;
		return -1;
	}
	
	public static int indexOf(final long[] arr, final long item) {
		return indexOf(arr, i -> i == item);
	}
	
	public static int indexOf(final long[] arr, final LongPredicate predicate) {
		for(int i = 0; i < arr.length; i++)
			if(predicate.test(arr[i]))
				return i;
		return -1;
	}
	
	public static int indexOf(final float[] arr, final float item) {
		return indexOf(arr, i -> i == item);
	}
	
	public static int indexOf(final float[] arr, final FloatPredicate predicate) {
		for(int i = 0; i < arr.length; i++)
			if(predicate.test(arr[i]))
				return i;
		return -1;
	}
	
	public static int indexOf(final double[] arr, final double item) {
		return indexOf(arr, i -> i == item);
	}
	
	public static int indexOf(final double[] arr, final DoublePredicate predicate) {
		for(int i = 0; i < arr.length; i++)
			if(predicate.test(arr[i]))
				return i;
		return -1;
	}
	
	/**
	 * The given array may have a primitive or reference component type.
	 * @throws IllegalArgumentException if {@code arr} is not an array, or if the number of indices given does not equal the number of dimensions in the given array.
	 */
	public static boolean inBounds(Object arr, int... indices) {
		Objects.requireNonNull(arr);
		if(!Arrs.isArray(arr))
			throw new IllegalArgumentException("The given Object is not an array");
		return inBounds(0, arr, indices);
		
	}
	
	/** Assumes {@code arr} is an array. */
	private static boolean inBounds(int startIndexInIndices, Object arr, int... indices) { //params are ordered strangely to avoid ambiguous method invocations.
		if(startIndexInIndices >= indices.length)
			throw new IllegalArgumentException("Number of indices does not match the number of dimensions of the array");
		int index = indices[startIndexInIndices];
		if(index < 0)
			return false;
		int length = Array.getLength(arr);
		if(index >= length)
			return false;
		Class<?> component = arr.getClass().componentType();
		if(component.isArray()) {
			Object[] asArr = (Object[]) arr;
			return inBounds(startIndexInIndices + 1, asArr[index], indices);
		}
		else {
			if(startIndexInIndices != indices.length - 1)
				throw new IllegalArgumentException("Number of indices does not match the number of dimensions of the array");
			return true;
		}
	}
	
	public static boolean inBounds(boolean[] arr, int index) {
		return index >= 0 && index < arr.length;
	}
	
	public static boolean inBounds(boolean[][] arr, int index1, int index2) {
		return index1 >= 0 && index1 < arr.length && inBounds(arr[index1], index2);
	}
	
	public static boolean inBounds(boolean[][][] arr, int index1, int index2, int index3) {
		return index1 >= 0 && index1 < arr.length && inBounds(arr[index1], index2, index3);
	}
	
	public static boolean inBounds(boolean[][][][] arr, int index1, int index2, int index3, int index4) {
		return index1 >= 0 && index1 < arr.length && inBounds(arr[index1], index2, index3, index4);
	}
	
	public static boolean inBounds(char[] arr, int index) {
		return index >= 0 && index < arr.length;
	}
	
	public static boolean inBounds(char[][] arr, int index1, int index2) {
		return index1 >= 0 && index1 < arr.length && inBounds(arr[index1], index2);
	}
	
	public static boolean inBounds(char[][][] arr, int index1, int index2, int index3) {
		return index1 >= 0 && index1 < arr.length && inBounds(arr[index1], index2, index3);
	}
	
	public static boolean inBounds(char[][][][] arr, int index1, int index2, int index3, int index4) {
		return index1 >= 0 && index1 < arr.length && inBounds(arr[index1], index2, index3, index4);
	}
	
	public static boolean inBounds(int[] arr, int index) {
		return index >= 0 && index < arr.length;
	}
	
	public static boolean inBounds(int[][] arr, int index1, int index2) {
		return index1 >= 0 && index1 < arr.length && inBounds(arr[index1], index2);
	}
	
	public static boolean inBounds(int[][][] arr, int index1, int index2, int index3) {
		return index1 >= 0 && index1 < arr.length && inBounds(arr[index1], index2, index3);
	}
	
	public static boolean inBounds(int[][][][] arr, int index1, int index2, int index3, int index4) {
		return index1 >= 0 && index1 < arr.length && inBounds(arr[index1], index2, index3, index4);
	}

	public static <T> void forEach(T[] arr, Consumer<T> action) {
		for(T item : arr)
			action.accept(item);
	}
	
	public static void forEach(boolean[] arr, BooleanConsumer action) {
		for(boolean item : arr)
			action.acceptBoolean(item);
	}
	
	public static void forEach(byte[] arr, ByteConsumer action) {
		for(byte item : arr)
			action.acceptByte(item);
	}
	
	public static void forEach(short[] arr, ShortConsumer action) {
		for(short item : arr)
			action.acceptShort(item);
	}
	
	public static void forEach(char[] arr, CharConsumer action) {
		for(char item : arr)
			action.acceptChar(item);
	}
	
	public static void forEach(int[] arr, IntConsumer action) {
		for(int item : arr)
			action.accept(item);
	}

	public static void forEach(long[] arr, LongConsumer action) {
		for(long item : arr)
			action.accept(item);
	}
	
	public static void forEach(float[] arr, FloatConsumer action) {
		for(float item : arr)
			action.acceptFloat(item);
	}

	public static void forEach(double[] arr, DoubleConsumer action) {
		for(double item : arr)
			action.accept(item);
	}
	
	/** Returns {@code true} for a zero-length array. */
	public static boolean allMatch(boolean[] arr, BooleanPredicate predicate) {
		for(boolean b : arr)
			if(!predicate.testBoolean(b))
				return false;
		return true;
	}
	
	/** Returns {@code true} for a zero-length array. */
	public static boolean allMatch(byte[] arr, BytePredicate predicate) {
		for(byte b : arr)
			if(!predicate.testByte(b))
				return false;
		return true;
	}
	
	/** Returns {@code true} for a zero-length array. */
	public static boolean allMatch(short[] arr, ShortPredicate predicate) {
		for(short s : arr)
			if(!predicate.testShort(s))
				return false;
		return true;
	}
	
	/** Returns {@code true} for a zero-length array. */
	public static boolean allMatch(int[] arr, IntPredicate predicate) {
		for(int i : arr)
			if(!predicate.test(i))
				return false;
		return true;
	}
	
	/** Returns {@code true} for a zero-length array. */
	public static boolean allMatch(long[] arr, LongPredicate predicate) {
		for(long l : arr)
			if(!predicate.test(l))
				return false;
		return true;
	}
	
	/** Returns {@code true} for a zero-length array. */
	public static boolean allMatch(float[] arr, FloatPredicate predicate) {
		for(float f : arr)
			if(!predicate.testFloat(f))
				return false;
		return true;
	}
	
	/** Returns {@code true} for a zero-length array. */
	public static boolean allMatch(double[] arr, DoublePredicate predicate) {
		for(double d : arr)
			if(!predicate.test(d))
				return false;
		return true;
	}
	
	/**
	 * <p>Returns the <i>diffs</i> of the given array. That is, returns an array with length {@code (arr.length - 1)} where index {@code i} in the returned
	 * array stores {@code (arr[i + 1] - arr[i])}.</p>
	 * @throws IllegalArgumentException if {@code (arr.length == 0)}.
	 */
	public static int[] diffs(final int[] arr) {
		if(arr.length == 0)
			throw new IllegalArgumentException("arr.length == 0");
		int[] diffs = new int[arr.length - 1];
		for(int i = 0; i < diffs.length; i++)
			diffs[i] = arr[i + 1] - arr[i];
		return diffs;
	}

	/**
	 * <p>Index {@code i} in the returned array is the sum of the elements from {@code 0} to {@code i} (inclusive) in the given array.</p>
	 */
	public static int[] summed(final int[] arr) {
		if(arr.length == 0)
			return new int[0];
		int[] summed = new int[arr.length];
		summed[0] = arr[0];
		for(int i = 1; i < arr.length; i++)
			summed[i] = summed[i - 1] + arr[i];
		return summed;
	}

	/**
	 * <p>Index {@code i} in the returned array is the sum of the elements from {@code 0} to {@code i} (inclusive) in the given array.</p>
	 */
	public static long[] summed(final long[] arr) {
		if(arr.length == 0)
			return new long[0];
		long[] summed = new long[arr.length];
		summed[0] = arr[0];
		for(int i = 1; i < arr.length; i++)
			summed[i] = summed[i - 1] + arr[i];
		return summed;
	}

	/**
	 * <p>Given the {@link #summed(long[]) summed} form of the array <i>A</i>, returns the sum of the values in <i>A</i> from {@code low} to {@code high},
	 * inclusive.</p>
	 */
	public static long sumRangeInclusive(final long[] summed, final int low, final int high) {
		return summed[high] - (low == 0 ? 0L : summed[low - 1]);
	}

	/**
	 * <p>Returns an {@link IntPair} of any two numbers in the given array that sum to {@code target}. If no such two numbers exist,
	 * {@code null} is returned. This method does not modify the given {@code int[]}.</p>
	 */
	public static IntPair sum2ToTarget(final int[] arr, final int target) {
		return sum2ToTarget(arr, target, 0, arr.length);
	}

	/**
	 * <p>Returns an {@link IntPair} of any two numbers in the given array that sum to {@code target}. If no such two numbers exist,
	 * {@code null} is returned. This method does not modify the given {@code int[]}.</p>
	 */
	public static IntPair sum2ToTarget(final int[] arr, final int target, final int startInclusive, final int endExclusive) {
		IntSet set = new HashIntSet(arr.length);
		for(int i = startInclusive; i < endExclusive; i++)
			set.add(arr[i]);
		for(int i = startInclusive; i < endExclusive; i++)
			if(set.contains(target - arr[i]))
				return IntPair.of(arr[i], target - arr[i]);
		return null;
	}

	/**
	 * <p>Returns an {@link IntStream} containing, in no specific order, three {@code ints} from {@code arr} that sum to {@code target},
	 * or {@code null} if no such trio exists. The three {@code ints} will be from unique indices.</p>
	 */
	public static IntStream sum3ToTarget(final int[] arr, final int target) {
		return sum3ToTarget(arr, target, 0, arr.length);
	}

	/**
	 * <p>Returns an {@link IntStream} containing, in no specific order, three {@code ints} that appear between indices {@code startInclusive}
	 * and {@code endExclusive} in {@code arr} that sum to {@code target},
	 * or {@code null} if no such trio exists. The three {@code ints} will be from unique indices.</p>
	 */
	public static IntStream sum3ToTarget(final int[] arr, final int target, int startInclusive, int endExclusive) {
		for(int i = startInclusive; i < endExclusive; i++) {
			IntPair pair = sum2ToTarget(arr, target - arr[i], i + 1, endExclusive);
			if(pair != null)
				return IntStream.of(arr[i], pair.firstInt(), pair.secondInt());
		}
		return null;
	}
	
}
