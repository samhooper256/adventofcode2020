package day20;

import java.util.*;

import utils.*;
import utils.colls.*;

/**
 * Correct answers are 64802175715999 (Part 1) and 2146 (Part 2).
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final Map<Integer, Tile> TILE_MAP = new HashMap<>();
	private static final Map<Integer, char[][]> TILE_CHAR_MAP = new HashMap<>();
	private static final char[][] MONSTER = {"                  # ".toCharArray(), "#    ##    ##    ###".toCharArray(), " #  #  #  #  #  #   ".toCharArray()};
	public static void main(String[] args) {
		String[] tileTexts = IO.splitOnBlanks("src/day20/input.txt").toArray(String[]::new);
		for(String tileText : tileTexts)
			parseTile(tileText);
		solvePart1();
	}

	private static void solvePart1() {
		Queue<Tile> queue = new ArrayDeque<>(TILE_MAP.values());
		Amalgam amalgam = new Amalgam(queue.remove());
		while(!queue.isEmpty()) {
			Tile rem = queue.remove();
			if(!amalgam.tryAdd(rem))
				queue.add(rem);
		}
//		System.out.printf("FULL:%n%s%n", amalgam.fullString());
//		amalgam.used().forEach((condensed, tile) -> {
//			System.out.printf("%s : %s%n", Arrays.toString(Amalgam.expand(condensed)), tile);
//		});
		Tile[][] tiles = amalgam.tilesArray();
		System.out.printf("%d%n", ((long) tiles[0][0].id()) * tiles[0][tiles[0].length - 1].id() *
				tiles[tiles.length - 1][0].id() * tiles[tiles.length - 1][tiles[0].length - 1].id());
		solvePart2(amalgam.fullChars());
	}
	
	private static void solvePart2(char[][] chars) {
		char[][] noBorders = new char[chars.length][chars[0].length];
		for(int i = 0; i < noBorders.length; i++) {
			for(int j = 0; j < noBorders[i].length; j++) {
				int imod = i % 10, jmod = j % 10;
				if(imod == 0 || imod == 9 || jmod == 0 || jmod == 9)
					noBorders[i][j] = ' ';
				else
					noBorders[i][j] = chars[i][j];
			}
		}
//		System.out.printf("NO BORDERS:%n");
//		Debug.printLines(noBorders);
		char[][] condensed = Arrays.stream(noBorders).map(String::new)
				.filter(s -> !s.isBlank())
				.map(s -> s.replace(" ", "")).map(String::toCharArray).toArray(char[][]::new);
//		System.out.printf("CONDENSED:%n");
//		Debug.printLines(condensed);
		System.out.println(getRoughness(condensed));
	}

	private static int getRoughness(char[][] condensed) {
		int seen1 = -1, seen2 = -1;
		for(char[][] img : allPos(condensed)) {
			int roughness = roughness(img);
			if(seen1 == -1)
				seen1 = roughness;
			else if(seen2 == -1)
				seen2 = roughness;
			else if(seen1 == seen2) {
				if(seen1 != roughness)
					return roughness;
			}
			else {
				return roughness == seen1 ? seen2 : seen1;
			}
		}
		throw new IllegalStateException();
	}
	
	private static char[][][] allPos(char[][] img) {
		char[][][] allPos = new char[8][][];
		allPos[0] = img;
		allPos[1] = Grids.rotatedClockwise(allPos[0]);
		allPos[2] = Grids.rotatedClockwise(allPos[1]);
		allPos[3] = Grids.rotatedClockwise(allPos[2]);
		allPos[4] = Grids.flippedVertically(img);
		allPos[5] = Grids.flippedHoriztonally(img);
		allPos[6] = Grids.rotatedClockwise(allPos[4]);
		allPos[7] = Grids.rotatedClockwise(allPos[5]);
		return allPos;
	}
	
	private static int roughness(char[][] img) {
		markMonsters(img);
		return Arrs.count2D(img, '#');
	}
	
	private static void markMonsters(char[][] img) {
		for(int i = 0; i < img.length; i++) {
			for(int j = 0; j < img[i].length; j++) {
				markMonster(img, i, j);
			}
		}
	}
	
	private static void markMonster(char[][] img, int topLeftRow, int topLeftCol) {
		if(topLeftRow + MONSTER.length >= img.length || topLeftCol + MONSTER[0].length >= img[0].length)
			return;
		for(int r = 0; r < MONSTER.length; r++) {
			for(int c = 0; c < MONSTER[r].length; c++) {
				if(MONSTER[r][c] == ' ')
					continue;
				int imgRow = r + topLeftRow, imgCol = c + topLeftCol;
				if(img[imgRow][imgCol] != '#')
					return;
			}
		}
		for(int r = 0; r < MONSTER.length; r++) {
			for(int c = 0; c < MONSTER[r].length; c++) {
				if(MONSTER[r][c] == ' ')
					continue;
				int imgRow = r + topLeftRow, imgCol = c + topLeftCol;
				img[imgRow][imgCol] = 'O';
			}
		}
	}
	
	private static void parseTile(String tileText) {
		String[] split = tileText.split(":\n");
		int id = Integer.parseInt(split[0].substring(5));
		final char[][] tileChars = Grids.chars(split[1]);
		TILE_MAP.put(id, new Tile(tileChars, id));
		TILE_CHAR_MAP.put(id, tileChars);
	}
	
}
