package utils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import utils.colls.*;

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
	 * Sorts the given array in place (using {@link Arrays#sort(int[])}) and then returns the given array.
	 */
	public static int[] sorted(final int[] arr) {
		Arrays.sort(arr);
		return arr;
	}
	
	/**
	 * Returns a sorted copy of the given array. The given array is not modified.
	 */
	public static int[] sortedCopy(final int[] arr) {
		int[] sorted = Arrays.copyOf(arr, arr.length);
		Arrays.sort(sorted);
		return sorted;
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
	 * <p>Returns the number of times {@code val} occurs in {@code arr}.</p>
	 */
	public static int count(int[] arr, int val) {
		return count(arr, x -> x == val);
	}
	
	/** Returns {@code true} for an empty {@code int[]}. */
	public static boolean allMatch(int[] arr, IntPredicate predicate) {
		for(int i : arr)
			if(!predicate.test(i))
				return false;
		return true;
	}
	
	/**
	 * <p>Returns {@code true} if {@code arr} contains {@code item}, {@code false} otherwise. More precisely, returns {@code true} if there
	 * is a value {@code i} such that {@code Objects.equals(arr[i], item)}. {@code item} may be {@code null}, {@code arr} must not.</p>
	 */
	public static <T> boolean contains(final T[] arr, final T item) {
		for(T obj : arr)
			if(Objects.equals(obj, item))
				return true;
		return false;
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
	 * <p>Index {@code i} in the returned array is the sum of the elements from 0 to i (inclusive) in {@code arr}</p>
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
	 * <p>Index {@code i} in the returned array is the sum of the elements from 0 to i (inclusive) in {@code arr}</p>
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
	 * <p>Returns an {@link IntPair} of any two numbers in the given array that sum to {@code target}. If no such two numbers exist,
	 * {@code null} is returned. This method does not modify the given {@code int[]}.</p>
	 */
	public static IntPair sum2ToTarget(final int[] arr, final int target) {
		return sum2ToTarget(sortedCopy(arr), target, 0, arr.length);
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
	
	public static <T> void forEach(T[] arr, Consumer<T> action) {
		for(T item : arr)
			action.accept(item);
	}
	
	public static void forEach(int[] arr, IntConsumer action) {
		for(int item : arr)
			action.accept(item);
	}
	
	public static void forEach(long[] arr, LongConsumer action) {
		for(long item : arr)
			action.accept(item);
	}
	
	public static void forEach(double[] arr, DoubleConsumer action) {
		for(double item : arr)
			action.accept(item);
	}
	
	public static void forEach(boolean[] arr, BooleanConsumer action) {
		for(boolean item : arr)
			action.acceptBoolean(item);
	}
	
	public static void forEach(char[] arr, CharConsumer action) {
		for(char item : arr)
			action.acceptChar(item);
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
	
}
