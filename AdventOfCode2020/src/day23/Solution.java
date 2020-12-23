package day23;

import java.util.*;
import java.util.stream.Collectors;

import utils.Arrs;

/**
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final String INPUT = "685974213";
	
	public static void main(String[] args) {
		int[] nums = INPUT.chars().map(i -> Character.digit(i, 10)).toArray();
		ArrayList<Integer> numsList = Arrays.stream(nums).boxed().collect(Collectors.toCollection(ArrayList::new));
		solvePart1(numsList);
	}

	
	private static void solvePart1(ArrayList<Integer> numsList) {
		int currentCupIndex = 0;
		for(int move = 1; move <= 100; move++) {
			System.out.printf("-- move %d --%n", move);
			System.out.println(numsList.toString().replace(Integer.toString(numsList.get(currentCupIndex)), "(" + numsList.get(currentCupIndex) + ")").replace(",", " "));
			currentCupIndex = doMove(numsList, currentCupIndex);
		}
		System.out.println(numsList);
	}


	/**
	 * Returns the index in {@code nums} of the next current cup.
	 */
	private static int doMove(ArrayList<Integer> nums, final int currentCupIndex) {
		//Setup
		final int currentCup = nums.get(currentCupIndex);
		final int totalCupCount = nums.size();
		//Step 1
		List<Integer> pickup = new ArrayList<>(3);
		if(currentCupIndex == nums.size() - 1) {
			pickup.add(nums.remove(0));
			pickup.add(nums.remove(0));
			pickup.add(nums.remove(0));
		}
		else if(currentCupIndex == nums.size() - 2) {
			pickup.add(nums.remove(nums.size() - 1));
			pickup.add(nums.remove(0));
			pickup.add(nums.remove(0));
		}
		else if(currentCupIndex == nums.size() - 3) {
			pickup.add(nums.remove(nums.size() - 2));
			pickup.add(nums.remove(nums.size() - 1));
			pickup.add(nums.remove(0));
		}
		else {
			for(int i = 1; i <= 3; i++)
				pickup.add(nums.remove(currentCupIndex + 1));
		}
//		for(int i = 1; i <= 3; i++) {
//			final int numsIndex = (currentCupIndex + 1) % nums.size();
//			pickup.add(nums.remove(numsIndex));
//		}
		System.out.printf("pick up: %s%n", pickup.stream().map(String::valueOf).collect(Collectors.joining(", ")));
		//Step 2
		int destinationCup = currentCup == 1 ? totalCupCount : currentCup - 1, destinationCupIndex;
		while((destinationCupIndex = nums.indexOf(destinationCup)) < 0)
			destinationCup = destinationCup == 1 ? totalCupCount : destinationCup - 1;
		System.out.printf("destination: %d%n", destinationCup);
		//Step 3
		nums.addAll(destinationCupIndex + 1, pickup);
		int newCurrentCupIndex = (nums.indexOf(currentCup) + 1) % nums.size();
		return newCurrentCupIndex;
	}
	
}
