package day13;

import java.util.Arrays;
import java.util.stream.IntStream;

import utils.IO;

/**
 * @author Sam Hooper
 *
 */
public class Solution {
	
	public static void main(String[] args) {
		String[] lines = IO.strings("src/day13/input.txt");
		int time = Integer.parseInt(lines[0]);
		int[] ids = Arrays.stream(lines[1].replace(",x", "").split(",")).mapToInt(Integer::parseInt).toArray();
		solvePart1(time, ids);	
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
}
