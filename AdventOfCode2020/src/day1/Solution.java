package day1;

import utils.*;

/**
 * Correct answers are 921504 (Part 1) and 195700142 (Part 2)
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final int SUM_TARGET = 2020;

	public static void main(String[] args) {
		int[] expenses = IO.lines("src/day1/input.txt").mapToInt(Integer::parseInt).toArray();
		solvePart1(expenses);
		solvePart2(expenses);
	}

	private static void solvePart1(int[] expenses) {
		System.out.println(Arrs.sum2ToTarget(expenses, SUM_TARGET).productAsLong());
	}
	
	private static void solvePart2(int[] expenses) {
		System.out.println(Arrs.sum3ToTarget(expenses, SUM_TARGET).asLongStream().reduce((a, b) -> a * b).getAsLong());
	}
	
}