package utils;

/**
 * @author Sam Hooper
 *
 */
public final class Dimensions {
	
	private Dimensions() {}
	
	public static final int[][] ADJACENT_26 = {
			{-1,-1,-1},
			{-1,-1, 0},
			{-1,-1, 1},
			{-1, 0,-1},
			{-1, 0, 0},
			{-1, 0, 1},
			{-1, 1,-1},
			{-1, 1, 0},
			{-1, 1, 1},
			{ 0,-1,-1},
			{ 0,-1, 0},
			{ 0,-1, 1},
			{ 0, 0,-1},
			{ 0, 0, 1},
			{ 0, 1,-1},
			{ 0, 1, 0},
			{ 0, 1, 1},
			{ 1,-1,-1},
			{ 1,-1, 0},
			{ 1,-1, 1},
			{ 1, 0,-1},
			{ 1, 0, 0},
			{ 1, 0, 1},
			{ 1, 1,-1},
			{ 1, 1, 0},
			{ 1, 1, 1}
	};
	
	public static final int[][] ADJACENT_80 = makeAdjacent80();
	
	private static int[][] makeAdjacent80() {
		final int[][] adj = new int[80][];
		int index = 0;
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				for(int z = -1; z <= 1; z++) {
					for(int w = -1; w <= 1; w++) {
						if(x == 0 && y == 0 && z == 0 && w == 0)
							continue;
						adj[index++] = new int[] {x, y, z, w};
					}
				}
			}
		}
		return adj;
	}
	
	
}
