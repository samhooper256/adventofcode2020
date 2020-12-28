package day25;

/**
 * <p>Input:
 * <pre>8987316
 *14681524</pre></p>
 * <p>Correct answer is 15217943 (Part 1).</p>
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final long DIV = 20201227;
	private static final long CARD_PUBLIC_KEY = 8987316, DOOR_PUBLIC_KEY = 14681524;
	
	public static void main(String[] args) {
		solvePart1();
	}
	
	private static void solvePart1() {
		long num = 7;
		for(int i = 1; ; i++) {
			num = (num * 7) % DIV;
			if(num == CARD_PUBLIC_KEY) {
				System.out.println(transform(DOOR_PUBLIC_KEY, i + 1));
				return;
			}
		}
	}
	
	private static long transform(final long subjectNum, final long loopSize) {
		long num = subjectNum;
		for(int i = 1; i < loopSize; i++)
			num = (num * subjectNum) % DIV;
		return num;
	}
}
