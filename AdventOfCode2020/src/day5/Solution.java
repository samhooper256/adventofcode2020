package day5;

import utils.*;

/**
 * @author Sam Hooper
 *
 */
public class Solution {

	public static void main(String[] args) {
		assert id("FBFBBFFRLR") == 357;
		assert id("BFFFBBFRRR") == 567;
		assert id("FFFBBBFRRR") == 119;
		assert id("BBFFBBFRLL") == 820;
		
		solvePart1();
		solvePart2();
		
	}
	
	private static void solvePart1() {
		System.out.println(IO.lines("src/day5/input.txt").mapToInt(Solution::id).max().getAsInt());
	}
	
	private static int id(final String pass) {
		return Integer.parseInt(pass.replaceAll("[BR]", "1").replaceAll("[FL]", "0"), 2);
	}
	
	private static void solvePart2() {
		System.out.println(getMyID());
	}
	
	private static int getMyID() {
		final boolean[] ids = new boolean[1 << 10];
		IO.lines("src/day5/input.txt").mapToInt(Solution::id).forEach(i -> ids[i] = true);
		for(int id = 1; id < ids.length - 1; id++)
			if(!ids[id] && ids[id - 1] && ids[id + 1])
				return id;
		throw new IllegalArgumentException("Invalid input");
	}
}
