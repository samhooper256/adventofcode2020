package day11;

import java.util.Arrays;
import java.util.function.UnaryOperator;

import utils.*;
import utils.function.OptionalChar;

/**
 * Correct answers are 2211 (Part 1) and 1995 (Part 2).
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final char FLOOR = '.', EMPTY = 'L', OCCUPIED = '#';
	
	public static void main(String[] args) {
		char[][] map = IO.chars("src/day11/input.txt");
		solvePart1(map);
		solvePart2(map);
	}

	
	private static void solvePart1(char[][] map) {
		System.out.println(Dimensions.countSatisfyingChars(firstDiff(map, Solution::applied_1), Solution::isOccupied));
	}
	
	private static void solvePart2(char[][] map) {
		System.out.println(Dimensions.countSatisfyingChars(firstDiff(map, Solution::applied_2), Solution::isOccupied));
	}
	
	private static char[][] firstDiff(char[][] map, UnaryOperator<char[][]> applyFunction) {
		while(true) {
			char[][] applied = applyFunction.apply(map);
			if(Arrays.deepEquals(applied, map))
				return applied;
			map = applied;
		}
	}
	
	private static char[][] applied_1(char[][] map) {
		char[][] copy = new char[map.length][map[0].length];
		for(int r = 0; r < map.length; r++) {
			for(int c = 0; c < map[r].length; c++) {
				int occ = adjacentOccupied(map, r, c);
				if(isEmpty(map[r][c]))
					if(occ == 0)
						copy[r][c] = OCCUPIED;
					else
						copy[r][c] = EMPTY;
				else if(isOccupied(map[r][c]))
					if(occ >= 4)
						copy[r][c] = EMPTY;
					else
						copy[r][c] = OCCUPIED;
				else
					copy[r][c] = FLOOR;
			}
		}
		return copy;
	}
	
	
	
	private static char[][] applied_2(char[][] map) {
		char[][] copy = new char[map.length][map[0].length];
		for(int r = 0; r < map.length; r++) {
			for(int c = 0; c < map[r].length; c++) {
				int occ = visibleOccupied(map, r, c);
				if(isEmpty(map[r][c]))
					if(occ == 0)
						copy[r][c] = OCCUPIED;
					else
						copy[r][c] = EMPTY;
				else if(isOccupied(map[r][c]))
					if(occ >= 5)
						copy[r][c] = EMPTY;
					else
						copy[r][c] = OCCUPIED;
				else
					copy[r][c] = FLOOR;
			}
		}
		return copy;
	}
	
	private static int adjacentOccupied(final char[][] map, int row, int col) {
		return Grids.count8Adjacent(map, row, col, Solution::isOccupied);
	}
	
	private static int visibleOccupied(final char[][] map, int row, int col) {
		int count = 0;
		for(int[] adj : Grids.ADJACENT_8) {
			final OptionalChar seat = Grids.deltaFirst(map, row, col, adj[0], adj[1], Solution::isSeat);
			if(seat.isPresent() && isOccupied(seat.getAsChar()))
				count++;
		}
		return count;
	}
	
	private static boolean isSeat(final char c) {
		return isOccupied(c) || isEmpty(c);
	}
	
	private static boolean isOccupied(final char c) {
		return c == OCCUPIED;
	}
	
	private static boolean isEmpty(final char c) {
		return c == EMPTY;
	}
	
	private static boolean isFloor(final char c) {
		return c == FLOOR;
	}
	
}
