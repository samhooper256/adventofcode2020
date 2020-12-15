package day12;

import utils.*;

/**
 * @author Sam Hooper
 *
 */
public class Solution {
	
	public static void main(String[] args) {
		final String[] instructions = IO.strings("src/day12/input.txt");
		SimpleShip ship = new SimpleShip(0, 0, CardinalDirection.EAST);
		ship.executeInstructions(instructions);
		System.out.println(Basics.manhattanDistance(0, 0, ship.getRow(), ship.getCol()));
		
		WaypointShip wps = new WaypointShip(0, 0, -1, 10);
		wps.executeInstructions(instructions);
		System.out.println(Basics.manhattanDistance(0, 0, wps.getRow(), wps.getCol()));
	}	

}
