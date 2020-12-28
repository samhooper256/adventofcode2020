package day5;

import utils.*;

/**
 * <p>Correct answers are 989 (Part 1) and 548 (Part 2).</p>
 * @author Sam Hooper
 *
 */
public class Solution {

	public static void main(String[] args) {
		assert id("FBFBBFFRLR") == 357;
		assert id("BFFFBBFRRR") == 567;
		assert id("FFFBBBFRRR") == 119;
		assert id("BBFFBBFRLL") == 820;
		
		String input = IO.text("src/day5/input.txt");
		solvePart1(input);
		solvePart2(input);
		
	}
	
	private static void solvePart1(String input) {
		System.out.println(input.lines().mapToInt(Solution::id).max().getAsInt());
	}
	
	private static int id(final String pass) {
		return Integer.parseInt(pass.replaceAll("[BR]", "1").replaceAll("[FL]", "0"), 2);
	}
	
	private static void solvePart2(String input) {
		System.out.println(getMyID(input));
	}
	
	private static int getMyID(String input) {
		final boolean[] ids = new boolean[1 << 10];
		input.lines().mapToInt(Solution::id).forEach(i -> ids[i] = true);
		for(int id = 1; id < ids.length - 1; id++)
			if(!ids[id] && ids[id - 1] && ids[id + 1])
				return id;
		throw new IllegalArgumentException("Invalid input");
	}
}
