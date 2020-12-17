package day17;

import java.util.Arrays;
import java.util.function.Supplier;

import utils.*;

/**
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final char ACTIVE = '#', INACTIVE = '.';
	private static final int ITERATIONS = 6;
	public static void main(String[] args) {
		char[][] input = IO.chars("src/day17/input.txt");
		solvePart1(input);
		solvePart2(input);
	}
	
	private static void solvePart1(final char[][] input) {
		final int consideredX = input.length, consideredY = input[0].length, consideredZ = 1;
		Supplier<boolean[][][]> emptyFactory = () -> new boolean[consideredX + ITERATIONS * 2][consideredY + ITERATIONS * 2][consideredZ + ITERATIONS * 2];
		boolean[][][] states = emptyFactory.get();
		for(int x = 0; x < input.length; x++) {
			for(int y = 0; y < input[x].length; y++) {
				states[x + ITERATIONS][y + ITERATIONS][ITERATIONS + 1] = isActive(input[x][y]);
			}
		}
		
//		System.out.printf("BEFORE ITRS:%n%s%n%n", Debug.toConciseString(states, '#', '.'));
		for(int iteration = 1; iteration <= ITERATIONS; iteration++) {
			boolean[][][] temp = emptyFactory.get();
			for(int x = 0; x < states.length; x++) {
				for(int y = 0; y < states[x].length; y++) {
					for(int z = 0; z < states[x][y].length; z++) {
						int count = countActiveNeighbors(states, x, y, z);
						if(states[x][y][z]) {
							if(count == 2 || count == 3) {
								temp[x][y][z] = true;
							}
							else {
								temp[x][y][z] = false;
							}
						}
						else {
							if(count == 3) {
								temp[x][y][z] = true;
							}
							else {
								temp[x][y][z] = false;
							}
						}
					}
				}
			}
			states = temp;
//			System.out.printf("AFTER ITR %d (total actives=%d):%n%s%n", iteration, countActives(states), Debug.toConciseString(states, '#', '.'));
		}
		System.out.println(countActives(states));
	}
	
	private static void solvePart2(final char[][] input) {
		final int consideredX = input.length, consideredY = input[0].length, consideredZ = 1, consideredW = 1;
		int EXPANSION = ITERATIONS + 1;
		Supplier<boolean[][][][]> emptyFactory = () -> new boolean[consideredX + EXPANSION * 2][consideredY + EXPANSION * 2]
				[consideredZ + EXPANSION * 2][consideredW + EXPANSION * 2];
		boolean[][][][] states = emptyFactory.get();
		for(int x = 0; x < input.length; x++) {
			for(int y = 0; y < input[x].length; y++) {
				states[x + EXPANSION][y + EXPANSION][EXPANSION + 1][EXPANSION + 1] = isActive(input[x][y]);
			}
		}
		
		for(int iteration = 1; iteration <= ITERATIONS; iteration++) {
			boolean[][][][] temp = emptyFactory.get();
			for(int x = 0; x < states.length; x++) {
				for(int y = 0; y < states[x].length; y++) {
					for(int z = 0; z < states[x][y].length; z++) {
						for(int w = 0; w < states[x][y][z].length; w++) {
							int count = countActiveNeighbors(states, x, y, z, w);
							if(states[x][y][z][w]) {
								if(count == 2 || count == 3) {
									temp[x][y][z][w] = true;
								}
								else {
									temp[x][y][z][w] = false;
								}
							}
							else {
								if(count == 3) {
									temp[x][y][z][w] = true;
								}
								else {
									temp[x][y][z][w] = false;
								}
							}
						}
					}
				}
			}
			states = temp;
			System.out.printf("after itr %d: actives=%s%n", iteration, countActives(states));
		}
		System.out.println(countActives(states));
	}
	
	private static int countActiveNeighbors(boolean[][][] states, int x, int y, int z) {
		int count = 0;
		for(int[] delta : Dimensions.ADJACENT_26) {
			int nx = x + delta[0], ny = y + delta[1], nz = z + delta[2];
			if(inBounds(states, nx, ny, nz) && states[nx][ny][nz])
				count++;
		}
		return count;
	}
	
	private static int countActiveNeighbors(boolean[][][][] states, int x, int y, int z, int w) {
		int count = 0;
		for(int[] delta : Dimensions.ADJACENT_80) {
			int nx = x + delta[0], ny = y + delta[1], nz = z + delta[2], nw = w + delta[3];
			if(inBounds(states, nx, ny, nz, nw) && states[nx][ny][nz][nw])
				count++;
		}
		return count;
	}
	
	private static boolean inBounds(boolean[][][] states, int x, int y, int z) {
		return x >= 0 && x < states.length && y >= 0 && y < states[x].length && z >= 0 && z < states[x][y].length;
	}
	
	private static boolean inBounds(boolean[][][][] states, int x, int y, int z, int w) {
		return x >= 0 && x < states.length && y >= 0 && y < states[x].length && z >= 0 && z < states[x][y].length && w >= 0 && w < states[x][y][z].length;
	}
	
	public static long countActives(boolean[][][] states) {
		long count = 0;
		for(int x = 0; x < states.length; x++) {
			for(int y = 0; y < states[x].length; y++) {
				for(int z = 0; z < states[x][y].length; z++) {
					if(states[x][y][z])
						count++;
				}
			}
		}
		return count;
	}
	
	public static long countActives(boolean[][][][] states) {
		long count = 0;
		for(boolean[][][] arr3d : states)
			count += countActives(arr3d);
		return count;
	}

	public static boolean isActive(char c) {
		return c == ACTIVE;
	}
	
	public static boolean isInactive(char c) {
		return c == INACTIVE;
	}
	
}
