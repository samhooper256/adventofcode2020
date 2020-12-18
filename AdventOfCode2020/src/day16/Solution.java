package day16;

import java.util.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import utils.*;
import utils.colls.*;

/**
 * Correct answers: 20048 (Part 1) and 4810284647569 (Part 2)
 * @author Sam Hooper
 *
 */
public class Solution {
	
	/** The amount of numbers that appear on each ticket. */
	private static final int NUMBERS_ON_TICKET = 20;

	public static void main(String[] args) {
		Solution s = new Solution("src/day16/input.txt");
		s.solvePart1();
		s.solvePart2();
	}
	
	private final Map<String, IntPredicate> validationFunctions = new HashMap<>();
	private final int[][] allTickets; 
	
	private Solution(String filePath) {
		String[] sections = Pattern.compile("\n\n").splitAsStream(IO.text(filePath)).toArray(String[]::new);
		createValidationFunctions(sections[0]);
		allTickets = Stream.concat(
			sections[1].lines().skip(1).map(this::ticketFrom),
			sections[2].lines().skip(1).map(this::ticketFrom)
		).toArray(int[][]::new);
	}
	
	private final int[] myTicket() {
		return allTickets[0];
	}
	
	public final Stream<int[]> nearbyTickets() {
		return Arrays.stream(allTickets, 1, allTickets.length);
	}
	
	private void createValidationFunctions(String fieldRules) {
		fieldRules.lines().forEach(this::createValidationFunction);
	}
	
	private void createValidationFunction(String fieldRule) {
		String[] split = fieldRule.split(": ");
		int[] nums = Parsing.positiveints(split[1]).toArray();
		validationFunctions.put(split[0], i -> Basics.between(i, nums[0], nums[1]) || Basics.between(i, nums[2], nums[3]));
	}
	
	private boolean isValidForAny(int num) {
		return Misc.satisfiesAny(num, validationFunctions.values());
	}
	
	private long sumInvalidsOfAllTickets() {
		return nearbyTickets().mapToLong(this::sumInvalidsOfTicket).sum();
	}
	
	private long sumInvalidsOfTicket(final int[] ticket) {
		return Arrays.stream(ticket).filter(i -> !isValidForAny(i)).sum();
	}
	
	private void solvePart1() {
		System.out.printf("Part 1 = %d%n", sumInvalidsOfAllTickets());
	}
	
	private void solvePart2() {
		Map<String, IntSet> indicesSatisfying = new HashMap<>();
		int[][] validTickets = Arrays.stream(allTickets).filter(this::isValidTicket).toArray(int[][]::new);
		for(Map.Entry<String, IntPredicate> entry : validationFunctions.entrySet()) {
			IntPredicate func = entry.getValue();
			IntSet set = new HashIntSet(NUMBERS_ON_TICKET);
			outer1:
			for(int col = 0; col < validTickets[0].length; col++) {
				for(int row = 0; row < validTickets.length; row++)
					if(!func.test(validTickets[row][col]))
						continue outer1;
				set.add(col);
			}
			indicesSatisfying.put(entry.getKey(), set);
		}
		
		Map<String, Integer> correctIndices = new HashMap<>();
		outer2:
		while(!indicesSatisfying.isEmpty()) {
			for(Iterator<Map.Entry<String, IntSet>> iterator = indicesSatisfying.entrySet().iterator(); iterator.hasNext(); ) {
				Map.Entry<String, IntSet> entry = iterator.next();
				Set<Integer> set = entry.getValue();
				if(set.size() == 1) {
					int singleValue = set.iterator().next();
					for(Set<Integer> otherSet : indicesSatisfying.values())
						otherSet.remove(singleValue);
					correctIndices.put(entry.getKey(), singleValue);
					iterator.remove();
					continue outer2;
				}
			}
			throw new IllegalStateException("Shouldn't get here");
		}
		long product = correctIndices.entrySet().stream().filter(e -> e.getKey().startsWith("departure"))
				.mapToLong(e -> myTicket()[e.getValue()]).reduce(1, (a, b) -> a * b);
		System.out.println("Part 2 = " + product);
	}
	
	private boolean isValidTicket(int[] ticket) {
		return Arrs.allMatch(ticket, this::isValidForAny);
	}
	
	private int[] ticketFrom(String lineOfInput) {
		return Parsing.ints(lineOfInput).toArray();
	}
}
