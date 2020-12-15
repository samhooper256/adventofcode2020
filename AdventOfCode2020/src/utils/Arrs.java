package utils;

import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

/**
 * <p>All methods throw a {@link NullPointerException} if a parameter is {@code null}, unless that parameter is explicitly allowed to be {@code null} by the method
 * documentation.</p>
 * @author Sam Hooper
 *
 */
public final class Arrs {
	
	private Arrs() {}
	
	public static int[] sorted(final int[] arr) {
		Arrays.sort(arr);
		return arr;
	}
	
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
	 * <p>Index {@code i} in the returned array is the value of the elements from 0 to i (inclusive) in {@code arr}</p>
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
	 * <p>Index {@code i} in the returned array is the value of the elements from 0 to i (inclusive) in {@code arr}</p>
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
		return sum2ToTargetSorted(sortedCopy(arr), target, 0, arr.length);
	}
	
	public static IntPair sum2ToTargetSorted(final int[] sortedArr, final int target, final int startInclusive, final int endExclusive) {
		return sum2ToTargetSortedAsIndices(sortedArr, target, startInclusive, endExclusive).map(x -> sortedArr[x]);
	}
	
	/**
	 * <p>Returns an {@link IntPair} of two indices {@code i1} and {@code i2} in {@code sortedArr} such that
	 * {@code (sortedArr[i1] + sortedArr[i2] == target)}. If no such two numbers exist, returns {@code null}.</p>
	 * <p>This method assumes the given array is sorted in non-decreasing order. If this is not the case, <b>all
	 * behavior is undefined</b>.</p>
	 */
	public static IntPair sum2ToTargetSortedAsIndices(final int[] sortedArr, final int target, final int startInclusive, final int endExclusive) {
		int l = startInclusive, r = endExclusive - 1;
		int sum;
		while(l < r && (sum = sortedArr[l] + sortedArr[r]) != target)
			if(sum < target)
				l++;
			else
				r--;
		return l >= r ? null : Pair.of(l, r);
	}
	
	/**
	 * <p>Returns, in no specific order, an array of three {@code ints} from {@code arr} that sum to {@code target},
	 * or {@code null} if no such trio of {@code ints} exists.
	 * The returned array, if not {@code null}, is guaranteed to have length 3.</p>
	 */
	public static IntStream sum3ToTarget(final int[] arr, final int target) {
		return sum3ToTargetSorted(sortedCopy(arr), target);
	}
	
	/**
	 * <p>Returns, in no specific order, an array of three {@code ints} from {@code arr} that sum to {@code target},
	 * or {@code null} if no such trio of {@code ints} exists.
	 * The returned array, if not {@code null}, is guaranteed to have length 3.</p>
	 * <p>This method assumes the given array is sorted in non-decreasing order. If this is not the case, <b>all
	 * behavior is undefined</b>.</p>
	 */
	public static IntStream sum3ToTargetSorted(final int[] sortedArr, final int target) {
		for(int i = 0; i < sortedArr.length - 2; i++) {
			IntPair pair = sum2ToTargetSorted(sortedArr, target - sortedArr[i], i + 1, sortedArr.length);
			if(pair != null)
				return IntStream.of(sortedArr[i], pair.firstInt(), pair.secondInt());
		}
		return null;
	}
	
}
