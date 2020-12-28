package day12;

import utils.*;

/**
 * <p>Correct answers are 1319 (Part 1) and 62434 (Part 2).</p>
 * @author Sam Hooper
 *
 */
public class Solution {
	
	public static void main(String[] args) {
		final String[] instructions = IO.strings("src/day12/input.txt");
		solvePart1(instructions);
		solvePart2(instructions);
	}

	private static void solvePart1(final String[] instructions) {
		SimpleShip ship = new SimpleShip(0, 0, CardinalDirection.EAST);
		ship.executeInstructions(instructions);
		System.out.println(Basics.manhattanDistance(ship.getRow(), ship.getCol()));
	}	
	
	private static void solvePart2(final String[] instructions) {
		WaypointShip wps = new WaypointShip(0, 0, -1, 10);
		wps.executeInstructions(instructions);
		System.out.println(Basics.manhattanDistance(wps.getRow(), wps.getCol()));
	}

}
