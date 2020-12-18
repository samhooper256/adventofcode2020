package day15;

import java.util.*;

import utils.colls.IntList;

/**
 * Input: "18,11,9,0,5,1"
 * 
 * The second part runs in about 4-6 seconds on my machine.
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final String INPUT = "18,11,9,0,5,1";
	private static final int PART1_N = 2020;
	private static final int PART2_N = 30000000;
	
	public static void main(String[] args) {
		IntList startingNumbers = new IntList(Arrays.stream(INPUT.split(",")).mapToInt(Integer::parseInt).toArray());
		solvePart1(startingNumbers);
		solvePart2(startingNumbers);
	}
	
	private static void solvePart1(IntList firstNumbersSpoken) {
		System.out.println(nthSpoken(firstNumbersSpoken, PART1_N));
	}
	
	private static void solvePart2(IntList firstNumbersSpoken) {
		System.out.println(nthSpoken(firstNumbersSpoken, PART2_N));
	}
	
	//O(n)
	private static int nthSpoken(IntList nums, int n) {
		Map<Integer, Integer> lastSpoken = new HashMap<>();
		for(int i = 0; i < nums.size() - 1; i++)
			lastSpoken.put(nums.get(i), i);
		while(nums.size() < n) {
			Integer last = nums.peek();
			final Integer index = lastSpoken.get(last);
			lastSpoken.put(last, nums.size() - 1);
			if(index != null)
				nums.add(nums.size() - index - 1);
			else
				nums.add(0);
		}
		return nums.peek();
	}
}
