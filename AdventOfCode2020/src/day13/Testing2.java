package day13;

import java.util.*;

import utils.Maths;

/**
 * @author Sam Hooper
 *
 */
public class Testing2 {
	static final String[] tests = {"5,7","7,13,x,x,59,x,31,19", "17,x,13,19", "67,7,59,61", "67,x,7,59,61", "67,7,x,59,61", "1789,37,47,1889","29,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,433,x,x,x,x,x,x,x,x,x,x,x,x,13,17,x,x,x,x,19,x,x,x,23,x,x,x,x,x,x,x,977,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,41"};
	static final long[] testResults = {20L, 1068781L, 3417L, 754018L, 779210, 1261476, 1202161486L};
	
	public static void main(String[] args) {
		System.out.println(lcmShifted(0, -1, 5, 7));
		System.out.println(solve(tests[0]));
		System.out.println(solve(tests[1]));
		for(int i = 0; i < testResults.length; i++) {
			final long sol = solve(tests[i]);
			if(sol != testResults[i]) {
				System.out.printf("ERROR, was: %d, should have been: %d", sol, testResults[i]); System.exit(-1);
			}
		}
		System.out.printf("===============TESTS SUCCEEDED===========%n%n");
		System.out.println(solve(tests[tests.length - 1]));
	}
	
	private static long solve(String input) {
		int[] arr = Arrays.stream(input.replace("x", "-1").split(",")).mapToInt(Integer::parseInt).toArray();
		long start = 1;
		long lastLCM = 1;
		System.out.printf("arr=%s%n", Arrays.toString(arr));
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] > 0) {
				long startOld = start;
				start = lcmShifted(start, -i, lastLCM, arr[i]);
				lastLCM = startOld == 1 ? start : Maths.lcm(lastLCM, arr[i]);
				System.out.printf("\tstartOld=%d start=%d, lastLCM=%d%n", startOld, start, lastLCM);
			}
		}
		return start;
	}
	
	
	private static long lcmShifted(long start1, long start2, long mult1, long mult2) {
		System.out.printf("lcmS(%d, %d, %d, %d)%n", start1,start2,mult1,mult2);
		while(start1 != start2) {
			if(start1 < start2)
				start1 += mult1 * Math.max(1, (start2 - start1) / mult1 - 1);
			else //start1 > start2
				start2 += mult2 * Math.max(1, (start1 - start2) / mult2 - 1);
		}
		return start1;
	}
}
