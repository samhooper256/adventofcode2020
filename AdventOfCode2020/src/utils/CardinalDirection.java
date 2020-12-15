package utils;

/**
 * @author Sam Hooper
 *
 */
public enum CardinalDirection {
	NORTH(-1, 0), SOUTH(1, 0), EAST(0, 1), WEST(0, -1);
	
	private final int dRow, dCol;
	
	private CardinalDirection(int dRow, int dCol) {
		this.dRow = dRow;
		this.dCol = dCol;
	}

	public int dRow() {
		return dRow;
	}

	public int dCol() {
		return dCol;
	}
	
	/**
	 * {@code degrees} may be negative, in which case this {@link CardinalDirection} will be rotated counterclockwise by the absolute value of given
	 * amount of degrees.
	 */
	public CardinalDirection rotatedClockwise(int degrees) {
		if(degrees % 90 != 0)
			throw new IllegalArgumentException("Only degrees in multiples of 90 are supported");
		if(degrees == 0)
			return this;
		if(degrees == 90) {
			return switch(this) {
			case NORTH -> EAST;
			case EAST -> SOUTH;
			case SOUTH -> WEST;
			case WEST -> NORTH;
			default -> throw new UnsupportedOperationException("Unsupported: " + this);
			};
		}
		else if(degrees == -90) {
			return switch(this) {
			case NORTH -> WEST;
			case EAST -> NORTH;
			case SOUTH -> EAST;
			case WEST -> SOUTH;
			default -> throw new UnsupportedOperationException("Unsupported: " + this);
			};
		}
		else if(degrees < 0) {
			return rotatedClockwise(degrees + 90).rotatedClockwise(-90);
		}
		else { //degrees > 0
			return rotatedClockwise(degrees - 90).rotatedClockwise(90);
		}
		
	}
	
	public CardinalDirection rotatedCounterClockwise(int degrees) {
		return rotatedClockwise(-degrees);
	}
	
}
