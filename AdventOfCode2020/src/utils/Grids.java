package utils;

import static utils.Arrs.inBounds;

import java.util.Arrays;
import java.util.stream.Collectors;

import utils.function.*;

/**
 * <p>All methods in this class accepting (or returning) 2D arrays assume (resp. guarantee) that all rows will have the same length.
 * Additionally, all methods accepting 2D arrays assume they have at least one row (unless stated otherwise).</p>
 * @author Sam Hooper
 *
 */
public final class Grids {
	
	private Grids() {}
	
	/** This array must not be modified. */
	public static final int[][] ADJACENT_8 = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
	
	/** This array must not be modified. */
	public static final int[][] ADJACENT_4 = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
	
	/**
	 * <p>From the starting tile, goes in the direction indicated by {@code deltaRow} and {@code deltaCol} until either:
	 * <ul><li>an element in {@code grid} is found that satisfies the given {@link CharPredicate}, in which case that element is returned, or</li>
	 * <li>the next location would be out of {@link #inBounds(char[][], int, int) bounds}, in which case {@link OptionalChar#empty()} is returned.</li></ul>
	 * The search does <b>NOT</b> include the starting tile.</p>
	 */
	public static OptionalChar deltaFirst(final char[][] grid, final int startRow, final int startCol, final int deltaRow, final int deltaCol, CharPredicate tester) {
		if(deltaRow == 0 && deltaCol == 0)
			throw new IllegalArgumentException("delta col and delta row are both 0");
		int r = startRow +  deltaRow, c = startCol + deltaCol;
		for(; inBounds(grid, r, c); r += deltaRow, c += deltaCol)
			if(tester.test(grid[r][c]))
				return OptionalChar.of(grid[r][c]);
		return OptionalChar.empty();
	}
	
	public static void forEach8Adjacent(final char[][] grid, int row, int col, final CharConsumer consumer) {
		for(int[] adj : ADJACENT_8) {
			int nr = row + adj[0], nc = col + adj[1];
			if(inBounds(grid, nr, nc))
				consumer.accept(grid[nr][nc]);
		}
	}
	
	public static int count8Adjacent(final char[][] grid, int row, int col, final CharPredicate tester) {
		int count = 0;
		for(int[] adj : ADJACENT_8) {
			int nr = row + adj[0], nc = col + adj[1];
			if(inBounds(grid, nr, nc) && tester.test(grid[nr][nc]))
				count++;
		}
		return count;
	}
	
	public static char[] getCol(final char[][] grid, int colIndex) {
		char[] col = new char[grid.length];
		for(int row = 0; row < grid.length; row++)
			col[row] = grid[row][colIndex];
		return col;
	}
	
	public static char[][] chars(final String text) {
		return text.lines().map(String::toCharArray).toArray(char[][]::new);
	}
	
	/** Copies {@code arr} into {@code grid} with its top-left spot at ({@code topLeftRow}, {@code topLeftCol}).*/
	public static void copyInto(char[][] arr, char[][] grid, final int topLeftRow, final int topLeftCol) {
		for(int r = 0; r < arr.length; r++) {
			for(int c = 0; c < arr[r].length; c++) {
				grid[r + topLeftRow][c + topLeftCol] = arr[r][c];
			}
		}
	}
	
	/** Returns a copy. Does not modify the passed {@code char[][]}.*/
	public static char[][] flippedVertically(char[][] grid) {
		char[][] flipped = new char[grid.length][];
		for(int row = 0; row < grid.length; row++) {
			char[] gridRow = grid[grid.length - row - 1];
			flipped[row] = Arrays.copyOf(gridRow, gridRow.length);
		}
		return flipped;
	}
	
	/** Returns a copy. Does not modify the passed {@code char[][]}.*/
	public static char[][] flippedHoriztonally(char[][] grid) {
		final int rowCount = grid.length, colCount = grid[0].length;
		char[][] flipped = new char[rowCount][colCount];
		for(int r = 0; r < rowCount; r++)
			for(int c = 0; c < colCount; c++)
				flipped[r][c] = grid[r][colCount - c - 1];
		return flipped;
	}
	
	/** Returns a copy. Does not modify the passed {@code char[][]}.*/
	public static char[][] rotatedClockwise(char[][] grid) {
		final int rowCount = grid.length, colCount = grid[0].length;
		char[][] rotated = new char[rowCount][colCount];
		for(int r = 0; r < rowCount; r++)
			for(int c = 0; c < colCount; c++)
				rotated[r][c] = grid[rowCount - c - 1][r];
		return rotated;
	}
	
	/** Returns a copy. Does not modify the passed {@code char[][]}.*/
	public static char[][] rotatedCounterclockwise(char[][] grid) {
		final int rowCount = grid.length, colCount = grid[0].length;
		char[][] rotated = new char[rowCount][colCount];
		for(int r = 0; r < rowCount; r++)
			for(int c = 0; c < colCount; c++)
				rotated[r][c] = grid[c][colCount - r - 1];
		return rotated;
	}
	
}
