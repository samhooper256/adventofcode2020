package day7;

import java.math.BigInteger;
import java.util.*;

import utils.*;

/**
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final String TARGET_COLOR = "shiny gold";

	public static void main(String[] args) {
		solvePart1();
		solvePart2();
	}
	
	private static void solvePart1() {
		List<String[]> edges = getEdges();
		Map<String, Collection<String>> radj = Graphs.radjMapDirected(edges, Graphs.inferNodes(edges));
		System.out.println(dfs(radj, TARGET_COLOR).size());
	}
	
	/**
	 * Does not include {@code key} in the DFS.
	 */
	private static Set<String> dfs(final Map<String, Collection<String>> map, final String key) {
		return dfs(map, key, new HashSet<>());
	}
	
	private static Set<String> dfs(final Map<String, Collection<String>> map, final String key, final Set<String> found) {
		if(!map.containsKey(key))
			return found;
		for(String s : map.get(key)) {
			found.add(s);
			dfs(map, s, found);
		}
		return found;
	}
	
	private static List<String[]> getEdges() {
		List<String[]> edges = new ArrayList<>();
		IO.lines("src/day7/input.txt").forEach(line -> {
			if(line.endsWith("no other bags."))
				return;
			String[] cSplit = line.split(" bags contain ");
			String left = cSplit[0];
			for(String splitHalf : cSplit[1].split(", ")) {
				String right = splitHalf.substring(splitHalf.indexOf(' ') + 1, splitHalf.lastIndexOf(" bag"));
				edges.add(new String[] {left, right});
			}
		});
		return edges;
	}
	
	private static void solvePart2() {
		System.out.println(countContaining(getEdgeMap(), TARGET_COLOR));
	}
	
	private static long countContaining(final Map<String, List<IntObjPair<String>>> edgeMap, String color) {
		Map<String, Long> counts = new HashMap<>();
		for(String s : edgeMap.keySet())
			counts.put(s, -1L);
		return countContaining(edgeMap, color, counts);
	}

	private static long countContaining(final Map<String, List<IntObjPair<String>>> edgeMap, String color, Map<String, Long> counts) {
		if(counts.get(color) >= 0)
			return counts.get(color);
		long sum = 0;
		for(IntObjPair<String> data : edgeMap.get(color))
			sum += data.intPart() * (countContaining(edgeMap, data.objPart(), counts) + 1);
		counts.put(color, sum);
		return sum;
	}
	
	private static Map<String, List<IntObjPair<String>>> getEdgeMap() {
		Map<String, List<IntObjPair<String>>> map = new HashMap<>();
		IO.lines("src/day7/input.txt").forEach(line -> {
			String[] cSplit = line.split(" bags contain ");
			String left = cSplit[0];
			if(line.endsWith("no other bags.")) {
				map.put(left, Collections.emptyList());
			}
			else {
				final String[] rSplit = cSplit[1].split(", ");
				final ArrayList<IntObjPair<String>> list = new ArrayList<>(rSplit.length);
				for(String splitHalf : rSplit) {
					int sIndex = splitHalf.indexOf(' ');
					String color = splitHalf.substring(sIndex + 1, splitHalf.lastIndexOf(" bag"));
					list.add(Pair.of(Integer.parseInt(splitHalf.substring(0, sIndex)), color));
				}
				map.put(left, list);
			}
		});
		return map;
	}
}
