package day20;

import java.util.*;
import java.util.stream.Collectors;

import utils.Grids;
import utils.colls.*;

/**
 * @author Sam Hooper
 *
 */
public class Amalgam {
	
	public static int condense(int row, int col) {
		return row * 100 + col;
	}
	
	public static int[] expand(int condensed) {
		return new int[] {rowFrom(condensed), colFrom(condensed)};
	}

	private static int colFrom(int condensed) {
		return condensed % 100;
	}
	
	public static int rowFrom(int condensed) {
		return condensed / 100;
	}
	
	private final IntSet frontier;
	private final Map<Integer, Tile> used;
	
	public Amalgam(Tile initial) {
		used = new HashMap<>();
		used.put(condense(50, 50), initial);
		frontier = new HashIntSet();
		frontier.add(condense(49, 50));
		frontier.add(condense(50, 49));
		frontier.add(condense(50, 51));
		frontier.add(condense(51, 50));
//		System.out.printf("After Amalgam construction, frontier=%s, used=%s%n", frontier, used);
		
	}
	
	/** {@code true} if the given {@link Tile} could be added and has been, {@code false} if it could not be added. If the
	 * {@code Tile} was added, it may have been rotated or flipped to get into position.*/
	public boolean tryAdd(Tile tile) {
		if(tryAddWithoutTransform(tile))
			return true;
		tile.rotateClockwise();
		if(tryAddWithoutTransform(tile))
			return true;
		tile.rotateClockwise();
		if(tryAddWithoutTransform(tile))
			return true;
		tile.rotateClockwise();
		if(tryAddWithoutTransform(tile))
			return true;
		tile.rotateClockwise();
		tile.flipVertically();
		if(tryAddWithoutTransform(tile))
			return true;
		tile.rotateClockwise();
		if(tryAddWithoutTransform(tile))
			return true;
		tile.rotateCounterclockwise();
		tile.flipVertically();
		tile.flipHorizontally();
		if(tryAddWithoutTransform(tile))
			return true;
		tile.rotateClockwise();
		if(tryAddWithoutTransform(tile))
			return true;
		tile.rotateCounterclockwise();
		tile.flipHorizontally();
		return false;
	}
	
	private boolean tryAddWithoutTransform(Tile tile) {
		for(PrimitiveIterator.OfInt itr = frontier.iterator(); itr.hasNext();) {
			int condensedLoc = itr.nextInt();
			int[] expandedLoc = expand(condensedLoc);
			if(tile.couldBePlaced(fourAdj(expandedLoc))) {
				add(tile, expandedLoc);
				return true;
			}
		}
		return false;
	}
	
	private void add(Tile tile, int[] rowCol) {
		add(tile, rowCol[0], rowCol[1]);
	}
	
	private void add(Tile tile, int row, int col) {
		final int condensed = condense(row, col);
//		System.out.printf("adding %s on (%d, %d) [condensed=%d]; frontier=%s, used=%s%n", tile, row, col, condensed, frontier, used);
		boolean contained = frontier.remove(condensed);
		if(!contained)
			throw new IllegalArgumentException("The given location was not on the frontier of this Amalagam");
		for(int[] delta : Grids.ADJACENT_4) {
			int nr = row + delta[0], nc = col + delta[1];
			addToFrontierIfPossible(condense(nr, nc));
		}
		used.put(condensed, tile);
//		System.out.printf("After add, frontier=%s, used=%s%n", frontier, used);
	}
	
	private void addToFrontierIfPossible(int condensed) {
		if(!used.containsKey(condensed))
			frontier.add(condensed);
	}
	
	private Tile at(int row, int col) {
		return at(condense(row, col));
	}
	
	private Tile at(int condensed) {
		return used.get(condensed);
	}
	
	public Tile[] fourAdj(int[] rowCol) {
		return fourAdj(rowCol[0], rowCol[1]);
	}
	public Tile[] fourAdj(int row, int col) {
		Tile top = at(row - 1, col), right = at(row, col + 1), bottom = at(row + 1, col), left = at(row, col - 1);
		return new Tile[] {top, right, bottom, left};
	}
	
	public Map<Integer, Tile> used() {
		return Collections.unmodifiableMap(used);
	}
	
	public char[][] fullChars() {
		int minCol = minCol(), minRow = minRow(), maxCol = maxCol(), maxRow = maxRow();
		int width = maxCol - minCol + 1, height = maxRow - minRow + 1;
//		System.out.printf("\t\t\t[in fullString] minCol=%d, maxCol=%d, minRow=%d, maxRow=%d, width=%d, height=%d%n", minCol, maxCol, minRow, maxRow, width, height);
		char[][] grid = new char[height * 10][width * 10];
		for(Map.Entry<Integer, Tile> entry : used.entrySet()) {
			int[] expanded = expand(entry.getKey());
//			System.out.printf("\t\t\t[in fullString] placing%s%n", Arrays.toString(expanded));
			expanded[0] = (expanded[0] - minRow) * 10;
			expanded[1] = (expanded[1] - minCol) * 10;
			Grids.copyInto(Grids.chars(entry.getValue().fullString()), grid, expanded[0], expanded[1]);
		}
		return grid;
	}
	
	public String fullString() {
		return Arrays.stream(fullChars()).map(String::new).collect(Collectors.joining("\n"));
	}
	
	public int calcWidth() {
		return maxCol() - minCol();
	}
	
	public int calcHeight() {
		return maxRow() - minRow();
	}
	
	public int minCol() {
		int minCol = Integer.MAX_VALUE;
		for(int condensedLoc : used.keySet()) {
			int col = colFrom(condensedLoc);
			if(col < minCol)
				minCol = col;
		}
		return minCol;
	}
	
	public int maxCol() {
		int maxCol = Integer.MIN_VALUE;
		for(int condensed : used.keySet()) {
			int col = colFrom(condensed);
			if(col > maxCol)
				maxCol = col;
		}
		return maxCol;
	}
	
	public int minRow() {
		int minRow = Integer.MAX_VALUE;
		for(int condensed : used.keySet()) {
			int row = rowFrom(condensed);
			if(row < minRow)
				minRow = row;
		}
		return minRow;
	}
	
	public int maxRow() {
		int maxRow = Integer.MIN_VALUE;
		for(int condensed : used.keySet()) {
			int row = rowFrom(condensed);
			if(row > maxRow)
				maxRow = row;
		}
		return maxRow;
	}
}
