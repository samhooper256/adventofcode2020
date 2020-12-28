package day21;

import java.util.*;
import java.util.stream.Collectors;

import utils.*;
import utils.colls.*;

/**
 * <p>Correct answers are 2072 (Part 1) and fdsfpg,jmvxx,lkv,cbzcgvc,kfgln,pqqks,pqrvc,lclnj (Part 2).</p>
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final Map<String, String> ALLERGEN_MAP = new HashMap<>(); //Maps ALLERGEN (in English) to its INGREDIENT (in another language).
	
	public static void main(String[] args) {
		List<Pair<Set<String>, Set<String>>> data = getData();
		final int allergensNeeded = Math.toIntExact(data.stream().map(Pair::second).flatMap(Set::stream).distinct().count());
		Map<String, Set<String>> deductionMap = new HashMap<>();
		outer:
		while(ALLERGEN_MAP.size() != allergensNeeded) {
			for(Pair<Set<String>, Set<String>> pair : data) {
				Set<String> ingredients = pair.first();
				for(Iterator<String> allergenIterator = pair.second().iterator(); allergenIterator.hasNext();) {
					String allergen = allergenIterator.next();
					if(deductionMap.containsKey(allergen)) {
						final Set<String> allergenPossibilities = deductionMap.get(allergen);
						allergenPossibilities.retainAll(ingredients);
						if(allergenPossibilities.size() == 1) {
							ALLERGEN_MAP.put(allergen, Colls.getOnlyElement(allergenPossibilities));
							deductionMap.remove(allergen);
							removeAllergen(allergen, data);
							continue outer;
						}
					}
					else
						deductionMap.put(allergen, new HashSet<>(ingredients));
				}
			}
		}
		System.out.printf("%d%n", data.stream().map(Pair::first).flatMap(Set::stream).count());
		System.out.printf("%s%n", ALLERGEN_MAP.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
				.map(Map.Entry::getValue).collect(Collectors.joining(",")));
	}
	
	/** Assumes that the given {@code allergen} is a key in {@link #ALLERGEN_MAP} */
	private static void removeAllergen(String allergen, List<Pair<Set<String>, Set<String>>> data) {
		String ingredient = ALLERGEN_MAP.get(allergen);
		for(Pair<Set<String>, Set<String>> pair : data) {
			pair.first().removeIf(ingredient::equals);
			pair.second().removeIf(allergen::equals);
		}
	}

	private static List<Pair<Set<String>, Set<String>>> getData() {
		return IO.lines("src/day21/input.txt").map(Solution::getLineData).collect(Collectors.toCollection(ArrayList::new));
	}
	
	private static Pair<Set<String>, Set<String>> getLineData(String line) {
		String[] split = line.substring(0, line.length() - 1).split(" \\(contains ");
		Set<String> ingredients = Regex.WHITESPACE.splitAsStream(split[0]).collect(Collectors.toCollection(HashSet::new));
		Set<String> allergens = Regex.COMMA_OPT_WHITESPACE.splitAsStream(split[1]).collect(Collectors.toCollection(HashSet::new));
		return Pair.of(ingredients, allergens);
	}
	
	
	
}
