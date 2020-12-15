package day3;

import static utils.Pair.of;

import java.util.Arrays;
import java.util.stream.*;

import utils.*;


/**
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final char TREE = '#';
	private static final int PART1_DELTA_ROW = 1, PART2_DELTA_COL = 3;
	/** The first elements of each {@link IntPair} is delta row, the second is delta col. */
	private static final IntPair[] PART2_SLOPES = {of(1, 1), of(1, 3), of(1, 5), of(1, 7), of(2, 1)};
	
	public static void main(String[] args) {
		final char[][] map = IO.chars("src/day3/input.txt");
		solvePart1(map);
		solvePart2(map);
	}
	
	private static final void solvePart1(final char[][] map) {
		System.out.println(treesOnSlope(map, PART1_DELTA_ROW, PART2_DELTA_COL));
	}
	
	private static int treesOnSlope(final char[][] map, final int deltaRow, final int deltaCol) {
		int treeCount = 0;
		for(int r = 0, c = 0; r < map.length; r += deltaRow, c = (c + deltaCol) % map[0].length)
			if(isTree(map[r][c]))
				treeCount++;
		return treeCount;
	}
	
	private static void solvePart2(final char[][] map) {
		System.out.println(treesOnSlopes(map, PART2_SLOPES).reduce(1, (a, b) -> a * b));
	}
	
	/**
	 * Assumes that the given {@link IntPair IntPairs} are (delta row, delta col).
	 */
	private static LongStream treesOnSlopes(final char[][] map, final IntPair[] slopes) {
		return Arrays.stream(slopes).mapToLong(pair -> treesOnSlope(map, pair.firstInt(), pair.secondInt()));
	}
	
	private static boolean isTree(final char c) {
		return c == TREE;
	}
	
}