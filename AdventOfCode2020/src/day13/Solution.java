package day13;

import java.util.Arrays;
import java.util.stream.IntStream;

import utils.*;

/**
 * Correct answers are 2165 (Part 1) and 534035653563227 (Part 2).
 * @author Sam Hooper
 *
 */
public class Solution {
	
	public static void main(String[] args) {
		String[] lines = IO.strings("src/day13/input.txt");
		int time = Integer.parseInt(lines[0]);
		int[] ids = Arrays.stream(lines[1].replace(",x", "").split(",")).mapToInt(Integer::parseInt).toArray();
		solvePart1(time, ids);
		solvePart2(lines[1]);
	}
	
	private static void solvePart1(final int time, final int[] ids) {
		int[] mins = new int[ids.length];
		for(int i = 0; i < ids.length; i++) {
			int id = ids[i];
			if(time % id == 0) {
				System.out.println("0"); return;
			}
			else
				mins[i] = (id * ((time / id) + 1)) - time;
		}
		int minIndex = 0;
		int minMin = mins[0];
		for(int i = 0; i < mins.length; i++) {
			if(mins[i] < minMin) {
				minMin = mins[i];
				minIndex = i;
			}
		}
		System.out.println(minMin * ids[minIndex]);
	}
	
	private static void solvePart2(String input) {
		System.out.println(firstTime(input));
	}
	
	private static long firstTime(String input) {
		int[] arr = Arrays.stream(input.replace("x", "-1").split(",")).mapToInt(Integer::parseInt).toArray();
		long start = 1, lastLCM = 1;
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] > 0) {
				long startOld = start;
				start = lcmShifted(start, -i, lastLCM, arr[i]);
				lastLCM = startOld == 1 ? start : Maths.lcm(lastLCM, arr[i]);
			}
		}
		return start;
	}
	
	
	private static long lcmShifted(long start1, long start2, long mult1, long mult2) {
		while(start1 != start2)
			if(start1 < start2)
				start1 += mult1 * Math.max(1, (start2 - start1) / mult1 - 1);
			else //start1 > start2
				start2 += mult2 * Math.max(1, (start1 - start2) / mult2 - 1);
		return start1;
	}
}
