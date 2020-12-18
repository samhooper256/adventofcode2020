package day6;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import utils.*;
import utils.colls.Colls;

/**
 * @author Sam Hooper
 *
 */
public class Solution {
	
	public static void main(String[] args) {
		System.out.println(Pattern.compile("\n\n").splitAsStream(IO.text("src/day6/input.txt")).mapToInt(Solution::countAnyoneYes).sum());
		System.out.println(Pattern.compile("\n\n").splitAsStream(IO.text("src/day6/input.txt")).mapToInt(Solution::countEveryoneYes).sum());
	}
	
	private static int countAnyoneYes(final String groupData) {
		return (int) groupData.chars().filter(Character::isLetter).distinct().count();
	}
	
	private static int countEveryoneYes(final String groupData) {
		return Colls.intersectionInt(Stream.of(groupData.split("\n")).map(String::chars)).size();
	}
	
}
