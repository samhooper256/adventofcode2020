package day2;

import java.util.stream.IntStream;

import utils.*;
import utils.colls.*;

/**
 * Correct answers are 638 (Part 1) and 699 (Part 2).
 * @author Sam Hooper
 *
 */
public class Solution {
	
	public static void main(String[] args) {
		solvePart1();
		solvePart2();
	}

	private static void solvePart1() {
		System.out.println(IO.lines("src/day2/input.txt").filter(Solution::isValidPasswordForPart1).count());
	}
	
	/**
	 * Returns an {@link IntPair} containing the {@code int} before the dash and the {@code int} after the dash, in that order.
	 */
	private static IntPair dashNums(String dashPart) {
		int dash = dashPart.indexOf('-');
		return IntPair.of(Integer.parseInt(dashPart.substring(0, dash)), Integer.parseInt(dashPart.substring(dash + 1)));
	}
	
	
	
	private static boolean isValidPasswordForPart1(String line) {
		String[] split = line.split(":? ");
		IntPair dashNums = dashNums(split[0]);
		return Basics.between(Strings.count(split[2], Strings.toChar(split[1])), dashNums.firstInt(), dashNums.secondInt());
	}
	
	private static void solvePart2() {
		System.out.println(IO.lines("src/day2/input.txt").filter(Solution::isValidPasswordForPart2).count());
	}
	
	private static boolean isValidPasswordForPart2(String line) {
		String[] split = line.split(":? ");
		char c = Strings.toChar(split[1]);
		IntPair dashNums = dashNums(split[0]);
		return split[2].charAt(dashNums.firstInt() - 1) == c ^ split[2].charAt(dashNums.secondInt() - 1) == c;
	}
}
