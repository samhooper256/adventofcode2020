package day6;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import utils.*;

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
	
	/*
	private static int countEveryoneYes(final String groupData) {
		final int[] yesCount = new int['z' - 'a' + 1];
		groupData.chars().filter(Character::isLetter)
		.forEach(c -> {
			yesCount[c - 'a']++;
		});
		
		int groupCount = Strings.count(groupData, '\n') + 1;
		int total = 0;
		for(int cnt : yesCount)
			if(cnt == groupCount)
				total += 1;
		return total;
		
	}
	*/
}
