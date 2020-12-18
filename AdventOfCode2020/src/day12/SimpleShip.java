package day12;

import utils.*;
import utils.colls.*;

import static utils.CardinalDirection.*;
/**
 * @author Sam Hooper
 *
 */
public class SimpleShip {
	
	private int row, col;
	private CardinalDirection facing;
	
	public SimpleShip(int startRow, int startCol, CardinalDirection startFacing) {
		this.row = startRow;
		this.col = startCol;
		this.facing = startFacing;
	}
	
	public void executeInstructions(String[] instructions) {
		for(String instruction : instructions)
			executeInstruction(instruction);
	}
	
	/** Assumes the instruction is valid. */
	public void executeInstruction(String instruction) {
		int num = Integer.parseInt(instruction.substring(1));
		switch(instruction.charAt(0)) {
		case 'N' -> move(NORTH, num);
		case 'E' -> move(EAST, num);
		case 'S' -> move(SOUTH, num);
		case 'W' -> move(WEST, num);
		case 'L' -> turnLeft(num);
		case 'R' -> turnRight(num);
		case 'F' -> moveForward(num);
		default -> throw new IllegalArgumentException("Unrecognized instruction: \"" + instruction + "\"");
		}
	}
	
	public void moveForward(int distance) {
		setRow(getRow() + getFacing().dRow() * distance);
		setCol(getCol() + getFacing().dCol() * distance);
	}
	
	/**
	 * Turns this {@link SimpleShip} right (that is, rotates it clockwise by) {@code degrees}, which may be negative.
	 */
	public void turnRight(int degrees) {
		setFacing(getFacing().rotatedClockwise(degrees));
	}
	
	/**
	 * Turns this {@link SimpleShip} left (that is, rotates it counter-clockwise by) {@code degrees}, which may be negative.
	 */
	public void turnLeft(int degrees) {
		setFacing(getFacing().rotatedCounterClockwise(degrees));
	}
	
	public void move(CardinalDirection direction, int distance) {
		setRow(getRow() + direction.dRow() * distance);
		setCol(getCol() + direction.dCol() * distance);
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

	public CardinalDirection getFacing() {
		return facing;
	}

	public void setFacing(CardinalDirection direction) {
		this.facing = direction;
	}
	
	/** Returns the location of this {@link SimpleShip} as an ordered pair of ({@link #getRow() row}, {@link #getCol() col}).*/
	public IntPair location() {
		return IntPair.of(getRow(), getCol());
	}
	
	
}
