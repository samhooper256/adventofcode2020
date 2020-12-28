package day9;

import java.util.*;

import utils.*;

/**
 * <p>Correct answers are 14360655 (Part 1) and 1962331 (Part 2).</p>
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final int PREAMBLE_LENGTH = 25;
	
	public static void main(String[] args) {
		long[] input = IO.lines("src/day9/input.txt").mapToLong(Long::parseLong).toArray();
		solvePart1(input);
		solvePart2(input);
	}
	
	private static void solvePart1(final long[] input) {
		System.out.println(firstNon25(input));
	}

	private static long firstNon25(final long[] input) {
		for(int i = PREAMBLE_LENGTH; i < input.length; i++) {
			boolean found = false;
			inner1:
			for(int j = i - PREAMBLE_LENGTH; j < i; j++) {
				for(int k = j + 1; k < i; k++) {
					if(input[j] + input[k] == input[i]) {
						found = true;
						break inner1;
					}
				}
			}
			if(!found)
				return input[i];
		}
		throw new IllegalArgumentException();
	}
	
	private static void solvePart2(final long[] input) {
		LongSummaryStatistics summary = Arrays.stream(contiguousSum(input, firstNon25(input))).summaryStatistics();
		System.out.println(summary.getMin() + summary.getMax());
	}
	
	private static long[] contiguousSum(final long[] input, final long target) {
		final long[] summed = Arrs.summed(input);
		int low = 0, high = 0; //inclusive
		outer:
		for(; low < summed.length; low++) {
			for(high = low; high < summed.length; high++) {
				if(Arrs.sumRangeInclusive(summed, low, high) == target) {
					break outer;
				}
			}
		}
		return Arrays.copyOfRange(input, low, high + 1);
	}
	
}
