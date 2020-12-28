package day24;

import java.util.*;

import java.util.stream.*;

import utils.IO;
import utils.colls.IntPair;

import static utils.colls.IntPair.*;

/**
 * <p>Correct answers are 375 (Part 1) and 3937 (Part 2).</p>
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final int DAYS = 100;
	private static final Map<IntPair, Tile> LOCS = new HashMap<>();
	
	public static void main(String[] args) {
		LOCS.put(IntPair.ORIGIN, new Tile(true));
		String[] lines = IO.lines("src/day24/input.txt").toArray(String[]::new);
		solvePart1(lines);
		solvePart2();
	}
	
	private static void solvePart1(String[] lines) {
		for(String line : lines)
			executeDirs(toDirs(line));
		System.out.println(countBlacks());
	}
	
	private static int countBlacks() {
		return (int) LOCS.values().stream().filter(Tile::isBlack).count();
	}
	
	private static void solvePart2() {
		for(IntPair blackLoc : blackLocs())
			spawnAdj(blackLoc);
		for(int day = 1; day <= DAYS; day++)
			runDay();
		System.out.println(countBlacks());
	}
	
	private static void runDay() {
		List<IntPair> toFlip = new ArrayList<>();
		for(Map.Entry<IntPair, Tile> entry : LOCS.entrySet()) {
			final Tile tile = entry.getValue();
			final IntPair loc = entry.getKey();
			int blackAdj = blackAdj(loc);
			if(tile.isBlack() && (blackAdj == 0 || blackAdj > 2) || tile.isWhite() && blackAdj == 2)
				toFlip.add(loc);
		}
		for(IntPair loc : toFlip) {
			Tile tile = tileAt(loc);
			tile.flip();
			if(tile.isBlack()) //if it is black after flipping, spawn its adjacent ones.
				spawnAdj(loc);
		}
	}
	
	
	private static List<IntPair> blackLocs() {
		return LOCS.entrySet().stream().filter(e -> e.getValue().isBlack()).map(Map.Entry::getKey).collect(Collectors.toList());
	}
	
	/** Returns the number of black tiles that are adjacent to {@code loc}. Will be between {@code 0} and {@code 6} (inclusive).*/
	private static int blackAdj(IntPair loc) {
		return (int) Arrays.stream(adjLocs(loc)).filter(Solution::isBlack).count();
	}
	
	private static IntPair[] adjLocs(IntPair loc) {
		final int first = loc.firstInt(), second = loc.secondInt();
		if(first % 2 == 0)
			return new IntPair[] {of(first - 1, second), of(first - 1, second + 1), of (first, second + 1),
					of(first + 1, second + 1), of(first + 1, second), of(first, second - 1)};
		else
			return new IntPair[] {of(first - 1, second - 1), of(first - 1, second), of(first, second + 1),
					of(first + 1, second), of(first + 1, second - 1), of(first, second - 1)};
	}
	
	/** Returns {@code true} if the {@link Tile} at the given location is white, {@code false} otherwise. This method is preferred to
	 * <pre>{@code tileAt(loc).isWhite()}</pre> because it may be able to to avoid actually creating the {@code Tile} object.*/
	private static boolean isWhite(IntPair loc) {
		Tile tile = LOCS.get(loc);
		return tile == null || tile.isWhite();
	}
	
	private static boolean isBlack(IntPair loc) {
		return !isWhite(loc);
	}
	
	private static void executeDirs(String[] dirs) {
		IntPair currentLoc = IntPair.ORIGIN;
		for(String dir : dirs)
			currentLoc = dirLocOf(currentLoc, dir);
		tileAt(currentLoc).flip();
	}
	
	private static String[] toDirs(String line) {
		return line.split("(?=[sn])|(?<=[ew])");
	}
	
	private static IntPair dirLocOf(IntPair loc, String dir) {
		return switch(dir) {
		case "nw" -> northwestLoc(loc);
		case "ne" -> northeastLoc(loc);
		case "e" -> eastLoc(loc);
		case "se" -> southeastLoc(loc);
		case "sw" -> southwestLoc(loc);
		case "w" -> westLoc(loc);
		default -> throw new IllegalArgumentException();
		};
	}
	
	private static IntPair westLoc(IntPair loc) {
		return IntPair.of(loc.firstInt(), loc.secondInt() - 1);
	}
	
	private static IntPair eastLoc(IntPair loc) {
		return IntPair.of(loc.firstInt(), loc.secondInt() + 1);
	}
	
	private static IntPair northwestLoc(IntPair loc) {
		return IntPair.of(loc.firstInt() - 1, loc.firstInt() % 2 == 0 ? loc.secondInt() : loc.secondInt() - 1);
	}
	
	private static IntPair northeastLoc(IntPair loc) {
		return IntPair.of(loc.firstInt() - 1, loc.firstInt() % 2 == 0 ? loc.secondInt() + 1 : loc.secondInt());
	}
	
	private static IntPair southwestLoc(IntPair loc) {
		return IntPair.of(loc.firstInt() + 1, loc.firstInt() % 2 == 0 ? loc.secondInt() : loc.secondInt() - 1);
	}
	
	private static IntPair southeastLoc(IntPair loc) {
		return IntPair.of(loc.firstInt() + 1, loc.firstInt() % 2 == 0 ? loc.secondInt() + 1: loc.secondInt());
	}
	
	private static Tile tileAt(IntPair loc) {
		spawnIfAbsent(loc);
		return LOCS.get(loc);
	}

	private static void spawnIfAbsent(IntPair loc) {
		LOCS.putIfAbsent(loc, new Tile(true));
	}
	
	private static void spawnAdj(IntPair loc) {
		for(IntPair adj : adjLocs(loc))
			spawnIfAbsent(adj);
	}
}
