package day23;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Sam Hooper
 *
 */
public class SolutionPart1 {
	
	private static final String INPUT = "685974213";
	
	public static void solve() {
		int[] nums = INPUT.chars().map(i -> Character.digit(i, 10)).toArray();
		ArrayList<Integer> numsList = Arrays.stream(nums).boxed().collect(Collectors.toCollection(ArrayList::new));
		solvePart1(numsList);
	}

	
	private static void solvePart1(ArrayList<Integer> numsList) {
		int currentCupIndex = 0;
		for(int move = 1; move <= 100; move++)
			currentCupIndex = doMove(numsList, currentCupIndex);
		int oneIndex = numsList.indexOf(1);
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i < numsList.size(); i++)
			sb.append(numsList.get((oneIndex + i) % numsList.size()));
		System.out.println(sb);
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
		int dist = totalCupCount - currentCupIndex;
		if(dist <= 3) {
			for(int i = dist - 1; i >= 1; i--)
				pickup.add(nums.remove(nums.size() - i));
			for(int i = dist; i <= 3; i++)
				pickup.add(nums.remove(0));
		}
		else
			for(int i = 1; i <= 3; i++)
				pickup.add(nums.remove(currentCupIndex + 1));
		//Step 2
		int destinationCup = currentCup == 1 ? totalCupCount : currentCup - 1, destinationCupIndex;
		while((destinationCupIndex = nums.indexOf(destinationCup)) < 0)
			destinationCup = destinationCup == 1 ? totalCupCount : destinationCup - 1;
		//Step 3
		nums.addAll(destinationCupIndex + 1, pickup);
		int newCurrentCupIndex = (nums.indexOf(currentCup) + 1) % nums.size();
		return newCurrentCupIndex;
	}
	
}
