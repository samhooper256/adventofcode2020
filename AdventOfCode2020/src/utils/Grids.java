package utils;

/**
 * <p>All methods in this class accepting (or returning) 2D arrays assume (or guarantee) that all rows will have the same length.</p>
 * @author Sam Hooper
 *
 */
public final class Grids {
	
	private Grids() {}
	
	/** This array must not be modified. */
	public static final int[][] ADJACENT_8 = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
	
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
	
	public static int count(final char[][] grid, final CharPredicate tester) {
		int count = 0;
		for(int r = 0; r < grid.length; r++)
			for(int c = 0; c < grid[r].length; c++)
				if(tester.test(grid[r][c]))
					count++;
		return count;
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
	
	/**
	 * Assumes all rows have the same length.
	 */
	public static boolean inBounds(final char[][] grid, int row, int col) {
		return row >= 0 && row < grid.length && col >= 0 && col < grid[row].length;
	}
}
