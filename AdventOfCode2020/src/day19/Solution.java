package day19;

import java.util.*;

import utils.IO;

/**
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final Node[] NODES;
	private static final String[] RULES, MESSAGES;
	static {
		NODES = new Node[134];
		String[] split = IO.text("src/day19/input.txt").split("\n\n");
		RULES = split[0].lines().toArray(String[]::new);
		MESSAGES = split[1].lines().toArray(String[]::new);
		for(String rule : RULES)
			createRule(rule);
	}
	
	private static void createRule(String rule) {
		String[] cSplit = rule.split(": ");
		int index = Integer.parseInt(cSplit[0]);
		NODES[index] = parseRule(cSplit[1]);
	}
	
	private static Node parseRule(String rule) {
		int quoteIndex = rule.indexOf('"');
		if(quoteIndex >= 0)
			return new Literal(rule.charAt(quoteIndex + 1));
		int pipeIndex = rule.indexOf('|');
		if(pipeIndex < 0)
			return parseSeriesRule(rule);
		return new Pipe(parseSeriesRule(rule.substring(0, pipeIndex - 1)), parseSeriesRule(rule.substring(pipeIndex + 2)));
	}
	
	private static Node parseSeriesRule(String rule) {
		int[] indices = Arrays.stream(rule.split(" ")).mapToInt(Integer::parseInt).toArray();
		return new Series(indices);
	}
	
	public static void main(String[] args) {
		solvePart1();
	}

	private static void solvePart1() {
		int count = 0;
		for(String message : MESSAGES)
			if(NODES[0].matches(message))
				count++;
		System.out.println(count);
	}
	
	private abstract static class Node {
		
		boolean matches(String input) {
			return matchEnd(input, 0) == input.length();
		}
		
		/**Returns {@code -1} if the match is unsuccessful, otherwise returns the index of the character immediately after the last one matched.*/
		abstract int matchEnd(String input, int index);
		
	}
	
	private static class Literal extends Node {
		
		private final char text;
		
		public Literal(char text) {
			this.text = text;
		}
		
		@Override
		int matchEnd(String input, int index) {
			return index < input.length() && input.charAt(index) == text ? index + 1 : -1;
		}
		
	}
	
	private static class Pipe extends Node {
		
		private final Node op1, op2;
		
		public Pipe(Node op1, Node op2) {
			this.op1 = op1;
			this.op2 = op2;
		}
		
		@Override
		int matchEnd(String input, int index) {
			int match1 = op1.matchEnd(input, index);
			return match1 >= 0 ? match1 : op2.matchEnd(input, index);
		}
		
	}
	
	private static class Series extends Node {
		
		private final int[] nodeIndices;
		
		public Series(int... indices) {
			nodeIndices = indices;
		}

		@Override
		int matchEnd(String input, int index) {
			int i = index;
			for(int nodeIndex : nodeIndices) {
				i = NODES[nodeIndex].matchEnd(input, i);
				if(i < 0)
					return -1;
			}
			return i;
		}
		
	}
	
}
