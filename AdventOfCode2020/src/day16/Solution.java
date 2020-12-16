package day16;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.Collectors;

import utils.*;

/**
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final Map<String, IntPredicate> validationFunctions = new HashMap<>();
	
	public static void main(String[] args) {
		String[] sections = Pattern.compile("\n\n").splitAsStream(IO.text("src/day16/input.txt")).toArray(String[]::new);
		createValidationFunctions(sections[0]);
		solvePart1(sections[2]);
		solvePart2(sections);
	}
	
	private static void createValidationFunctions(String fieldRules) {
		fieldRules.lines().forEachOrdered(Solution::createValidationFunction);
	}
	
	private static void createValidationFunction(String fieldRule) {
		String[] split = fieldRule.split(": ");
		final String name = split[0];
		int[] nums = Regex.NON_DIGITS.splitAsStream(split[1]).mapToInt(Integer::parseInt).toArray();
		validationFunctions.put(name, i -> Basics.between(i, nums[0], nums[1]) || Basics.between(i, nums[2], nums[3]));
	}
	
	private static boolean isValidForAny(int num) {
		for(IntPredicate function : validationFunctions.values())
			if(function.test(num))
				return true;
		return false;
	}
	
	private static long sumInvalidsOfAllTickets(final String nearbyTicketSection) {
		return nearbyTicketSection.lines().skip(1).mapToLong(Solution::sumInvalidsOfTicket).sum();
	}
	
	private static long sumInvalidsOfTicket(final String ticket) {
		return Regex.COMMA.splitAsStream(ticket).mapToInt(Integer::parseInt).filter(i -> !Solution.isValidForAny(i))
				.sum();
	}
	
	private static void solvePart1(String nearbyTicketSection) {
		long sum = sumInvalidsOfAllTickets(nearbyTicketSection);
		System.out.printf("sum=%d%n", sum);
	}
	
	private static void solvePart2(String[] sections) {
		Map<String, Set<Integer>> indicesSatisfying = new HashMap<>();
		List<int[]> allTicketsAsList = allTickets(sections);
		removeInvalidTickets(allTicketsAsList);
		int[][] allTickets = allTicketsAsList.toArray(int[][]::new);
		for(Map.Entry<String, IntPredicate> entry : validationFunctions.entrySet()) {
			String name = entry.getKey();
			IntPredicate func = entry.getValue();
			HashSet<Integer> set = new HashSet<>(20);
			outer1:
			for(int col = 0; col < allTickets[0].length; col++) {
				for(int row = 0; row < allTickets.length; row++)
					if(!func.test(allTickets[row][col]))
						continue outer1;
				set.add(col);
			}
			indicesSatisfying.put(name, set);
		}
		indicesSatisfying.forEach((name, set) -> System.out.printf("%s : %s%n", name, set));
		
		Map<String, Integer> correctIndices = new HashMap<>();
		outer2:
		while(!indicesSatisfying.isEmpty()) {
			System.out.printf("indicesSatisfying=%s%n", indicesSatisfying);
			for(Iterator<Entry<String, Set<Integer>>> iterator = indicesSatisfying.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, Set<Integer>> entry = iterator.next();
				Set<Integer> set = entry.getValue();
				System.out.printf("\tentry=%s%n", entry);
				if(set.size() == 1) {
					int singleValue = set.iterator().next();
					for(Set<Integer> otherSet : indicesSatisfying.values()) {
						otherSet.remove(singleValue);
					}
					correctIndices.put(entry.getKey(), singleValue);
					iterator.remove();
					continue outer2;
				}
			}
			throw new IllegalStateException("Shouldn't get here");
		}
		System.out.println("correctIndices="+correctIndices);
		int[] myTicket = allTickets[0];
		long product = 1;
		for(var e : correctIndices.entrySet()) {
			if(e.getKey().startsWith("departure"))
				product *= myTicket[e.getValue()];
		}
		System.out.println(product);
	}
	
	private static void removeInvalidTickets(List<int[]> tickets) {
		tickets.removeIf(Solution::isInvalidTicket);
	}
	
	private static boolean isInvalidTicket(int[] ticket) {
		for(int num : ticket)
			if(!isValidForAny(num))
				return true;
		return false;
	}
	
	private static List<int[]> allTickets(String[] sections) {
		List<int[]> tickets = new ArrayList<>();
		int[] myTicket = sections[1].lines().skip(1).map(Solution::ticketFrom).findFirst().get();
		tickets.add(myTicket);
		Collections.addAll(tickets, sections[2].lines().skip(1).map(Solution::ticketFrom).toArray(int[][]::new));
		return tickets;
	}
	
	private static int[] ticketFrom(String lineOfInput) {
		return Regex.COMMA.splitAsStream(lineOfInput).mapToInt(Integer::parseInt).toArray();
	}
}
