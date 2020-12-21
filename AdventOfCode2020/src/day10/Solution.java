package day10;

import java.util.*;
import java.util.stream.*;

import utils.*;

/**
 * @author Sam Hooper
 *
 */
public class Solution {
	
	public static void main(String[] args) {
		List<Integer> adapters = IO.lines("src/day10/input.txt").map(Integer::parseInt).sorted().collect(Collectors.toList());
		adapters.add(0, 0); adapters.add(adapters.get(adapters.size() - 1) + 3);
		int[] adaptersSorted = adapters.stream().mapToInt(Integer::intValue).toArray();
		solvePart1(adaptersSorted);
		solvePart2(adaptersSorted);
	}
	
	private static void solvePart1(final int[] adaptersSorted) {
		final int[] diffs = Arrs.diffs(adaptersSorted);
		System.out.println(Arrs.count(diffs, 1) * Arrs.count(diffs, 3));
	}
	
	private static void solvePart2(final int[] adaptersSorted) {
		Map<Integer, Long> totals = new HashMap<>();
		totals.put(adaptersSorted[adaptersSorted.length - 1], 1L);
		for(int i = adaptersSorted.length - 2; i >= 0; i--) {
			long total = 0L;
			inner:
			for(int j = i + 1; j <= i + 3 && j < adaptersSorted.length; j++) {
				int diff = adaptersSorted[j] - adaptersSorted[i];
				if(diff <= 3)
					total += totals.get(adaptersSorted[j]);
				else break inner;
			}
			totals.put(adaptersSorted[i], total);
		}
		System.out.println(totals.get(0));
	}
	
	
}
