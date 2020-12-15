package day12;

import static utils.CardinalDirection.EAST;
import static utils.CardinalDirection.NORTH;
import static utils.CardinalDirection.SOUTH;
import static utils.CardinalDirection.WEST;

import utils.*;

/**
 * @author Sam Hooper
 *
 */
public class WaypointShip {
	
	public static class Waypoint {
		private int relativeRow, relativeCol;
		
		public Waypoint(int relRow, int relCol) {
			this.relativeRow = relRow;
			this.relativeCol = relCol;
		}
		
		public int getRelativeRow() {
			return relativeRow;
		}
		
		public int getRelativeCol() {
			return relativeCol;
		}
		
		public void moveRelativeRow(int distance) {
			setRelativeRow(getRelativeRow() + distance);
		}
		
		public void moveRelativeCol(int distance) {
			setRelativeCol(getRelativeCol() + distance);
		}
		
		public void setRelativeRow(int newRelativeRow) {
			this.relativeRow = newRelativeRow;
		}
		
		public void setRelativeCol(int newRelativeCol) {
			this.relativeCol = newRelativeCol;
		}
		
		public void setRelativeLocation(final int relativeRow, int relativeCol) {
			this.relativeRow = relativeRow;
			this.relativeCol = relativeCol;
		}
		
		public void rotateRight(int degrees) {
			if(degrees % 90 != 0)
				throw new UnsupportedOperationException("Only degrees in multiples of 90 are supported");
			if(degrees < 0)
				rotateLeft(-degrees);
			else if(degrees == 0)
				return;
			else if(degrees == 90)
				setRelativeLocation(getRelativeCol(), -getRelativeRow());
			else {
				rotateRight(degrees - 90);
				rotateRight(90);
			}
			
		}
		
		public void rotateLeft(int degrees) {
			if(degrees % 90 != 0)
				throw new UnsupportedOperationException("Only degrees in multiples of 90 are supported");
			if(degrees < 0)
				rotateRight(-degrees);
			else if(degrees == 0)
				return;
			else if(degrees == 90)
				setRelativeLocation(-getRelativeCol(), getRelativeRow());
			else {
				rotateLeft(degrees - 90);
				rotateLeft(90);
			}
		}
		
	}
	
	private int row, col;
	private Waypoint waypoint;
	
	
	public WaypointShip(int startRow, int startCol, int waypointRelativeRow, int waypointRelativeCol) {
		 this.row = startRow;
		 this.col = startCol;
		 this.waypoint = new Waypoint(waypointRelativeRow, waypointRelativeCol);
	}
	
	
	
	public void executeInstructions(String[] instructions) {
		for(String instruction : instructions)
			executeInstruction(instruction);
	}
	
	/** Assumes the instruction is valid. */
	public void executeInstruction(String instruction) {
		int num = Integer.parseInt(instruction.substring(1));
		switch(instruction.charAt(0)) {
		case 'N' -> moveWaypoint(NORTH, num);
		case 'E' -> moveWaypoint(EAST, num);
		case 'S' -> moveWaypoint(SOUTH, num);
		case 'W' -> moveWaypoint(WEST, num);
		case 'L' -> getWaypoint().rotateLeft(num);
		case 'R' -> getWaypoint().rotateRight(num);
		case 'F' -> moveForward(num);
		default -> throw new IllegalArgumentException("Unrecognized instruction: \"" + instruction + "\"");
		}
//		System.out.printf("after execution \"%s\", at (%d, %d) with waypoint relative coords of (%d, %d)%n", instruction, getRow(), getCol(), getWaypoint().getRelativeRow(),
//				getWaypoint().getRelativeCol());
	}
	
	public void moveForward(int times) {
		setRow(getRow() + getWaypoint().getRelativeRow() * times);
		setCol(getCol() + getWaypoint().getRelativeCol() * times);
	}
	
	public void moveWaypoint(CardinalDirection direction, int distance) {
		getWaypoint().moveRelativeRow(direction.dRow() * distance);
		getWaypoint().moveRelativeCol(direction.dCol() * distance);
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Waypoint getWaypoint() {
		return waypoint;
	}
	
	/** Returns the location of this {@link SimpleShip} as an ordered pair of ({@link #getRow() row}, {@link #getCol() col}).*/
	public IntPair location() {
		return Pair.of(getRow(), getCol());
	}
}
