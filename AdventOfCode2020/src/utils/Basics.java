package utils;

import java.util.function.*;

import utils.function.*;
import utils.math.Maths;

/**
 * <p>All of the {@code inBounds} methods assume that the given array is not jagged.</p>
 * @author Sam Hooper
 *
 */
public final class Basics {
	
	private Basics() {}

	public static boolean between(final int num, final int minInclusive, final int maxInclusive) {
		return num >= minInclusive && num <= maxInclusive;
	}
	
	/**
	 * Returns {@code true} if {@code num} {@link Maths#isint(String) is an int} between {@code minInclusive} and
	 * {@code maxInclusive}.
	 */
	public static boolean between(final String num, final int minInclusive, final int maxInclusive) {
		return Maths.isint(num) && between(Integer.parseInt(num), minInclusive, maxInclusive);
	}
	
	public static int manhattanDistance(int row1, int col1, int row2, int col2) {
		return Math.abs(row1 - row2) + Math.abs(col1 - col2);
	}
	
	/** <p>Returns the manhattan distance between the point at ({@code row}, {@code col}) and (0, 0). This method
	 * is equivalent to (but may be more efficient than):
	 * <pre>{@code manhattanDistance(row1, col1, 0, 0)}*/
	public static int manhattanDistance(int row1, int col1) {
		return Math.abs(row1) + Math.abs(col1);
	}
	
	/** Executes the given action on each (row, col) ordered pair of indices in {@code arr}. The action
	 * will be executed from left to right on each row, from the top row to the bottom row. 
	 * */
	public static <T> void for2D(T[][] arr, IntBiConsumer action) {
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				action.accept(i, j);
	}
	
	public static <T> void forEach2D(T[][] arr, Consumer<T> action) {
		for2D(arr, (r, c) -> action.accept(arr[r][c]));
	}
	
	public static void for2D(boolean[][] arr, IntBiConsumer action) {
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				action.accept(i, j);
	}
	
	public static void for2D(byte[][] arr, IntBiConsumer action) {
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				action.accept(i, j);
	}
	
	public static void for2D(short[][] arr, IntBiConsumer action) {
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				action.accept(i, j);
	}
	
	public static void for2D(char[][] arr, IntBiConsumer action) {
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				action.accept(i, j);
	}
	
	public static void for2D(int[][] arr, IntBiConsumer action) {
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				action.accept(i, j);
	}
	
	public static void for2D(long[][] arr, IntBiConsumer action) {
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				action.accept(i, j);
	}
	
	public static void for2D(float[][] arr, IntBiConsumer action) {
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				action.accept(i, j);
	}
	
	public static void for2D(double[][] arr, IntBiConsumer action) {
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				action.accept(i, j);
	}
	
	public static void forEach2D(boolean[][] arr, BooleanConsumer action) {
		for2D(arr, (r, c) -> action.acceptBoolean(arr[r][c]));
	}
	
	public static void forEach2D(byte[][] arr, ByteConsumer action) {
		for2D(arr, (r, c) -> action.acceptByte(arr[r][c]));
	}
	
	public static void forEach2D(short[][] arr, ShortConsumer action) {
		for2D(arr, (r, c) -> action.acceptShort(arr[r][c]));
	}
	
	public static void forEach2D(char[][] arr, CharConsumer action) {
		for2D(arr, (r, c) -> action.acceptChar(arr[r][c]));
	}
	
	public static void forEach2D(int[][] arr, IntConsumer action) {
		for2D(arr, (r, c) -> action.accept(arr[r][c]));
	}
	
	public static void forEach2D(long[][] arr, LongConsumer action) {
		for2D(arr, (r, c) -> action.accept(arr[r][c]));
	}
	
	public static void forEach2D(float[][] arr, FloatConsumer action) {
		for2D(arr, (r, c) -> action.acceptFloat(arr[r][c]));
	}
	
	public static void forEach2D(double[][] arr, DoubleConsumer action) {
		for2D(arr, (r, c) -> action.accept(arr[r][c]));
	}
	
}
