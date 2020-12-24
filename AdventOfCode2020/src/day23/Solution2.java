package day23;

import java.util.stream.IntStream;

/**
 * @author Sam Hooper
 *
 */
public class Solution2 {
	
	private static final int PART_2_MOVES = 10_000_000;
	private static final String INPUT = "685974213";
	private static final int CUP_COUNT = 1_000_000;
	private static final Cup[] CUPS = new Cup[CUP_COUNT + 1];
	private static final class Cup {
		private final int label;
		private Cup next;
		
		public Cup(int label) {
			this(label, null);
		}
		
		public Cup(int label, Cup next) {
			this.label = label;
			this.next = next;
		}
		
		public boolean hasNext() {
			return next != null;
		}
		
		public Cup getNext() {
			return next;
		}
		
		public void setNext(Cup newNext) {
			this.next = newNext;
		}
		
		public int getLabel() {
			return label;
		}
		
		public Cup removeNext3() {
			final Cup next = getNext();
			final Cup ahead3 = getNext().getNext().getNext();
			final Cup ahead3Next = ahead3.getNext();
			ahead3.setNext(null);
			setNext(ahead3Next);
			return next;
		}
		
		public void insertAfter(Cup cups) {
			Cup last = cups.last();
			last.setNext(getNext());
			this.setNext(cups);
		}
		
		public boolean contains(final int label) {
			if(getLabel() == label)
				return true;
			Cup ptr = getNext();
			while(ptr != null) {
				if(ptr.getLabel() == label)
					return true;
				ptr = ptr.getNext();
			}
			return false;
		}
		
		public Cup last() {
			if(!hasNext())
				return this;
			Cup ptr = this;
			while(ptr.hasNext())
				ptr = ptr.next;
			return ptr;
		}
		
	}

	
	public static void main(String[] args) {
		int[] nums = IntStream.concat(INPUT.chars().map(i -> Character.digit(i, 10)), IntStream.rangeClosed(10, 1_000_000)).toArray();
		for(int i = 1; i < CUPS.length; i++)
			CUPS[i] = new Cup(i);
		for(int i = 0; i < nums.length - 1; i++)
			CUPS[nums[i]].setNext(CUPS[nums[i + 1]]);
		CUPS[nums[nums.length - 1]].setNext(CUPS[nums[0]]);
		solve(nums[0], PART_2_MOVES);
	}
	
	private static void solve(final int firstCupLabel, int moves) {
		int currentCupLabel = firstCupLabel;
		for(int move = 1; move <= moves; move++)
			currentCupLabel = doMove(currentCupLabel);
		System.out.printf("%d%n", ((long) CUPS[1].getNext().getLabel()) * CUPS[1].getNext().getNext().getLabel());
	}
	
	private static int doMove(int currentCupLabel) {
		Cup currentCup = cupWithLabel(currentCupLabel);
		Cup next3 = currentCup.removeNext3();
		int destCupLabel = currentCupLabel == 1 ? CUP_COUNT : currentCupLabel - 1;
		while(next3.contains(destCupLabel))
			destCupLabel = destCupLabel == 1 ? CUP_COUNT : destCupLabel - 1;
		Cup destCup = cupWithLabel(destCupLabel);
		destCup.insertAfter(next3);
		return currentCup.getNext().getLabel();
	}

	private static Cup cupWithLabel(int label) {
		return CUPS[label];
	}
}
