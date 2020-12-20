package day20;

import java.util.*;
import java.util.stream.Collectors;

import utils.*;

/**
 * @author Sam Hooper
 *
 */
public class Tile {
	
	public static int intFromChars(char[] arr) {
		return Integer.parseInt(new String(arr).replace('#', '1').replace('.', '0'), 2);
	}
	
	private static int flipped(int val) {
		int flip = 0;
		for(int i = 0; i < 10; i++) {
			if(Bits.oneAt(val, 10 - i - 1)) {
				flip = Bits.set(flip, i);
			}
		}
		return flip;
	}
	
	public static String toBorder(int side) {
		final String str = "0000000000" + Integer.toBinaryString(side);
		return str.substring(str.length() - 10).replace('0', '.').replace('1', '#');
	}
	
	private static char[][] transformed(char[][] chars, TileTransformation transformation) {
		return switch(transformation) {
		case FLIP_HORIZONTALLY -> Grids.flippedHoriztonally(chars);
		case FLIP_VERTICALLY -> Grids.flippedVertically(chars);
		case ROTATE_CLOCKWISE -> Grids.rotatedClockwise(chars);
		case ROTATE_COUNTERCLOCKWISE -> Grids.rotatedCounterclockwise(chars);
		default -> throw new UnsupportedOperationException("Unkown transformation: " + transformation);
		};
	}
	
	private final int id;
	private final Stack<TileTransformation> transformations;
	private final char[][] chars;
	private int top, left, bottom, right;
	
	/** Refers to the given {@code char[][]} directly. The {@code char[][]} must not be modified after being passed to this method, except through
	 * this {@link Tile} object. The {@code char[][]} is assumed to be square.*/
	public Tile(final char[][] chars, int id) {
		top = intFromChars(chars[0]);
		bottom = intFromChars(chars[chars.length - 1]);
		left = intFromChars(Grids.getCol(chars, 0));
		right = intFromChars(Grids.getCol(chars, chars[0].length - 1));
		this.id = id;
		this.transformations = new Stack<>();
		this.chars = chars;
	}
	
	public int top() {
		return top;
	}
	
	public int bottom() {
		return bottom;
	}
	
	public int left() {
		return left;
	}
	
	public int right() {
		return right;
	}
	
	public int id() {
		return id;
	}
	
	public void rotateClockwise() {
		int newTop = flipped(left);
		int newRight = top;
		int newBottom = flipped(right);
		int newLeft = bottom;
		top = newTop;
		right = newRight;
		bottom = newBottom;
		left = newLeft;
		transformations.push(TileTransformation.ROTATE_CLOCKWISE);
		reduce();
	}
	
	public void rotateCounterclockwise() {
		int newTop = right;
		int newLeft = flipped(top);
		int newBottom = left;
		int newRight = flipped(bottom);
		top = newTop;
		right = newRight;
		bottom = newBottom;
		left = newLeft;
		transformations.push(TileTransformation.ROTATE_COUNTERCLOCKWISE);
		reduce();
	}
	
	public void flipVertically() {
		int newTop = bottom;
		int newBottom = top;
		int newLeft = flipped(left);
		int newRight = flipped(right);
		top = newTop;
		right = newRight;
		bottom = newBottom;
		left = newLeft;
		transformations.push(TileTransformation.FLIP_VERTICALLY);
		reduce();
	}
	
	public void flipHorizontally() {
		int newLeft = right;
		int newRight = left;
		int newTop = flipped(top);
		int newBottom = flipped(bottom);
		top = newTop;
		right = newRight;
		bottom = newBottom;
		left = newLeft;
		transformations.push(TileTransformation.FLIP_HORIZONTALLY);
		reduce();
	}
	
	private void reduce() {
		boolean reduced = tryReduce();
		while(reduced)
			reduced = tryReduce();
	}
	
	private boolean tryReduce() {
		if(transformations.size() <= 1)
			return false;
		if(transformations.peek() == TileTransformation.FLIP_VERTICALLY) {
			if(transformations.get(transformations.size() - 2) == TileTransformation.FLIP_VERTICALLY) {
				transformations.pop();
				transformations.pop();
				return true;
			}
			return false;
		}
		else if(transformations.peek() == TileTransformation.FLIP_HORIZONTALLY) {
			if(transformations.get(transformations.size() - 2) == TileTransformation.FLIP_HORIZONTALLY) {
				transformations.pop();
				transformations.pop();
				return true;
			}
			return false;
		}
		else if(transformations.peek() == TileTransformation.ROTATE_CLOCKWISE) {
			if(transformations.get(transformations.size() - 2) == TileTransformation.ROTATE_COUNTERCLOCKWISE) {
				transformations.pop();
				transformations.pop();
				return true;
			}
			else {
				int topcs = 0;
				for(int i = 1; i <= 4; i++)
					if(transformations.size() - i >= 0 && transformations.get(transformations.size() - i) == TileTransformation.ROTATE_CLOCKWISE)
						topcs++;
				if(topcs == 3) {
					for(int i = 0; i < 3; i++)
						transformations.pop();
					transformations.push(TileTransformation.ROTATE_COUNTERCLOCKWISE);
					return true;
				}
				else if(topcs == 4) {
					for(int i = 0; i < 4; i++)
						transformations.pop();
					return true;
				}
				return false;
			}
		}
		else if(transformations.peek() == TileTransformation.ROTATE_COUNTERCLOCKWISE) {
			if(transformations.get(transformations.size() - 2) == TileTransformation.ROTATE_CLOCKWISE) {
				transformations.pop();
				transformations.pop();
				return true;
			}
			else {
				int topccs = 0;
				for(int i = 1; i <= 4; i++)
					if(transformations.size() - i >= 0 && transformations.get(transformations.size() - i) == TileTransformation.ROTATE_COUNTERCLOCKWISE)
						topccs++;
				if(topccs == 3) {
					for(int i = 0; i < 3; i++)
						transformations.pop();
					transformations.push(TileTransformation.ROTATE_CLOCKWISE);
					return true;
				}
				else if(topccs == 4) {
					for(int i = 0; i < 4; i++)
						transformations.pop();
					return true;
				}
				return false;
			}
		}
		return false;

	}
	
	/** The given {@code Tile[]} must contain four elements: the {@link Tile} above this one, the {@code Tile} to the right of this one,
	 * the {@code Tile} to the bottom of this one, and the {@code Tile} to the left of this one, <b>in that order.<b> If there is no tile
	 * in a particular direction (or the caller doesn't care if {@code this} and the {@code Tile} at that location are compatible), that element
	 * may be {@code null}.*/
	public boolean couldBePlaced(Tile[] fourAdj) {
		if(fourAdj[0] != null && !couldBeBelow(fourAdj[0]))
			return false;
		if(fourAdj[1] != null && !couldBeLeftOf(fourAdj[1]))
			return false;
		if(fourAdj[2] != null && !couldBeAbove(fourAdj[2]))
			return false;
		if(fourAdj[3] != null && !couldBeRightOf(fourAdj[3]))
			return false;
		return true;
	}
	
	public boolean couldBeBelow(Tile tile) {
		return tile.bottom() == this.top();
	}
	
	public boolean couldBeLeftOf(Tile tile) {
		return tile.left() == this.right();
	}
	
	public boolean couldBeAbove(Tile tile) {
		return tile.top() == this.bottom();
	}
	
	public boolean couldBeRightOf(Tile tile) {
		return tile.right() == this.left();
	}

	public String fullString() {
		return Arrays.stream(fullChars()).map(String::new).collect(Collectors.joining("\n"));
	}
	
	public char[][] fullChars() {
		char[][] grid = chars;
		for(TileTransformation transformation : transformations)
			grid = transformed(grid, transformation);
		return grid;
	}
	
	
	
	@Override
	public String toString() {
		return String.format("Tile#%d%s", id(), transformations.isEmpty() ? "" : transformations);
	}
	
	public List<TileTransformation> transformationsUnmodifiable() {
		return Collections.unmodifiableList(transformations);
	}
	
}
