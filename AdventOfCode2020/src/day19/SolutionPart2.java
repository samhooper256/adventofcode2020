package day19;

import java.util.*;

import utils.*;
import utils.colls.*;

/**
 * @author Sam Hooper
 *
 */
public class SolutionPart2 {
	
	private static final Node[] NODES;
	private static final String[] RULES, MESSAGES;
	static {
		NODES = new Node[134];
		String[] split = IO.text("src/day19/input.txt").split("\n\n");
		RULES = split[0].lines().toArray(String[]::new);
		MESSAGES = split[1].lines().toArray(String[]::new);
		for(String rule : RULES)
			createRule(rule);
		NODES[8] = new Node8();
		NODES[11] = new Node11();
	}
	
	private static void createRule(String rule) {
		String[] cSplit = rule.split(": ");
		int index = Integer.parseInt(cSplit[0]);
		if(index == 8 || index == 11) {
			return;
		}
		else {
			NODES[index] = parseRule(cSplit[1]);
		}
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
			if(NODES[0].matches(message)) {
				System.out.println("Matched: \"" + message + "\"");
				count++;
			}
		System.out.println(count);
	}
	
	private abstract static class Node {
		
		boolean matches(String input) {
			return matchEnd(input, 0).contains(input.length());
		}
		
		/**Returns an empty {@link IntSet} if the match is unsuccessful, otherwise returns the indices of the character(s) immediately after the last one matched
		 * for any valid match route.*/
		abstract IntSet matchEnd(String input, int index);
		
	}
	
	private static class Literal extends Node {
		
		private final char text;
		
		public Literal(char text) {
			this.text = text;
		}
		
		@Override
		IntSet matchEnd(String input, int index) {
			return index < input.length() && input.charAt(index) == text ? IntSet.singleton(index + 1) : IntSet.EMPTY;
		}
		
	}
	
	private static class Pipe extends Node {
		
		private final Node op1, op2;
		
		public Pipe(Node op1, Node op2) {
			this.op1 = op1;
			this.op2 = op2;
		}
		
		@Override
		IntSet matchEnd(String input, int index) {
			IntSet match1 = op1.matchEnd(input, index);
			IntSet match2 = op2.matchEnd(input, index);
			if(match1.isEmpty())
				return match2;
			else if(match2.isEmpty())
				return match1;
			return IntSet.union(match1, match2);
		}
		
	}
	
	private static class Series extends Node {
		
		private final int[] nodeIndices;
		
		public Series(int... indices) {
			nodeIndices = indices;
		}

		@Override
		IntSet matchEnd(String input, int index) {
			return matchEnd(input, 0, IntSet.singleton(index));
		}
		
		private IntSet matchEnd(String input, int nodeIndex, IntSet strIndexSet) {
//			System.out.printf("\t[enter] matchEnd(input=\"%s\", nodeIndex=%d, strIndexSet=%s)%n", input, nodeIndex, strIndexSet);
			if(strIndexSet.isEmpty())
				return strIndexSet;
			IntSet res = new HashIntSet();
			for(PrimitiveIterator.OfInt itr = strIndexSet.iterator(); itr.hasNext(); )
				res.addAll(NODES[nodeIndices[nodeIndex]].matchEnd(input, itr.nextInt()));
			if(nodeIndex == nodeIndices.length - 1)
				return res;
			return matchEnd(input, nodeIndex + 1, res);
			
		}
		
	}
	
	private static class Node8 extends Node {
		
		private final Node repeated;
		public Node8() {
			repeated = NODES[42];
		}
		@Override
		IntSet matchEnd(String input, int index) {
			IntSet allDest = new HashIntSet(repeated.matchEnd(input, index));
			IntSet temp = new HashIntSet();
			boolean added = false;
			do {
				for(PrimitiveIterator.OfInt itr = allDest.iterator(); itr.hasNext(); ) {
					int dest = itr.nextInt();
					IntSet newDests = repeated.matchEnd(input, dest);
					temp.addAll(newDests);
				}
				added = allDest.addAll(temp);
			}
			while(added);
			
			return allDest;
		
		}
	}
	
	private static class Node11 extends Node {
		
		private final Node left, right;
		
		public Node11() {
			left = NODES[42];
			right = NODES[31];
		}
		
		@Override
		IntSet matchEnd(String input, int index) {
			IntSet valids = new HashIntSet();
			IntSet leftEnds = new HashIntSet();
			leftEnds = left.matchEnd(input, index);
			int rightTimes = 1;
			while(!leftEnds.isEmpty()) {
				for(PrimitiveIterator.OfInt itr = leftEnds.iterator(); itr.hasNext(); ) {
					int leftEnd = itr.nextInt();
					valids.addAll(rightMatchEnds(input, leftEnd, rightTimes));
				}
				IntSet newLeftEnds = new HashIntSet();
				for(PrimitiveIterator.OfInt itr = leftEnds.iterator(); itr.hasNext(); ) {
					int leftEnd = itr.nextInt();
					newLeftEnds.addAll(left.matchEnd(input, leftEnd));
				}
				newLeftEnds.removeAll(leftEnds);
				leftEnds = newLeftEnds;
				rightTimes++;
			}
			return valids;
		}
		
		private IntSet rightMatchEnds(String input, int index, int times) {
			IntSet rightEnds = right.matchEnd(input, index);
			if(times == 1) return rightEnds;
			for(int i = 1; i < times; i++) {
				if(rightEnds.isEmpty())
					return rightEnds;
				IntSet newRightEnds = new HashIntSet();
				for(PrimitiveIterator.OfInt itr = rightEnds.iterator(); itr.hasNext(); ) {
					int rightEnd = itr.nextInt();
					newRightEnds.addAll(right.matchEnd(input, rightEnd));
				}
				newRightEnds.removeAll(rightEnds);
				rightEnds = newRightEnds;
			}
			return rightEnds;
		}
		
		
	}
	
	
}
