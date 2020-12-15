package day14;

import java.util.*;

import utils.*;

/**
 * Correct answers are 6513443633260 (Part 1) and 3442819875191 (Part 2).
 * @author Sam Hooper
 *
 */
public class Solution {
	
	public static void main(String[] args) {
		String[] lines = IO.strings("src/day14/input.txt");
		solvePart1(lines);
		solvePart2(lines);
	}
	
	private static void solvePart1(final String[] lines) {
		System.out.println(new Interpreter1(lines).execute().sumMemory());
	}
	
	private static void solvePart2(final String[] lines) {
		System.out.println(new Interpreter2(lines).execute().sumMemory());
	}
	
	private static final long BITS = 36;
	private static final int MEMORY_SIZE = 100_000;
	
	private abstract static class Interpreter {
		protected final String[] input;
		protected final long[] mask;
		
		public Interpreter(final String[] input) {
			this.input = input;
			this.mask = new long[2];
		}
		
		public Interpreter execute() {
			for(String line : input)
				if(line.startsWith("mask"))
					handleMask(line);
				else
					handleAssignment(line);
			return this;
		}
		
		public abstract long sumMemory();
		protected abstract void handleMask(final String maskString);
		protected abstract void handleAssignment(final String assignmentString);
	}
	
	private static class Interpreter1 extends Interpreter {
		
		private final long[] memory;
		
		public Interpreter1(String[] input) {
			super(input);
			this.memory = new long[MEMORY_SIZE];
		}
		
		@Override
		protected void handleMask(final String maskString) {
			String bitString = maskString.substring(maskString.lastIndexOf(' ') + 1);
			long considered = Long.parseLong(bitString.replace('0', '1').replace('X', '0'), 2);
			long maskBits = Long.parseLong(bitString.replace('X', '0'), 2);
			mask[0] = considered;
			mask[1] = maskBits;
		}
		
		@Override
		protected void handleAssignment(final String assignmentString) {
			int address = Integer.parseInt(assignmentString.substring(assignmentString.indexOf('[') + 1, assignmentString.indexOf(']')));
			long value = masked(Long.parseLong(assignmentString.substring(assignmentString.lastIndexOf(' ') + 1)));
			memory[address] = value;
		}
		
		private long masked(long value) {
			return value & ~(mask[1] ^ mask[0]) | mask[1] & mask[0];
		}
		
		@Override
		public long sumMemory() {
			return Arrays.stream(memory).sum();
		}
		
	}
	
	private static class Interpreter2 extends Interpreter {
		
		private final Map<Long, Long> memory;
		
		public Interpreter2(String[] input) {
			super(input);
			this.memory = new HashMap<>();
		}
		
		@Override
		protected void handleMask(final String maskString) {
			String bitString = maskString.substring(maskString.lastIndexOf(' ') + 1);
			mask[0] = Long.parseLong(bitString.replace('0', '1').replace('X', '0'), 2);
			mask[1] = Long.parseLong(bitString.replace('X', '0'), 2);
		}
		
		@Override
		protected void handleAssignment(final String assignmentString) {
			long address = Long.parseLong(assignmentString.substring(assignmentString.indexOf('[') + 1, assignmentString.indexOf(']')));
			long value = Long.parseLong(assignmentString.substring(assignmentString.lastIndexOf(' ') + 1));
			assign(address, value);
		}
		
		private void assign(final long address, final long value) {
			assignHelper(masked(address) & mask[0], value, 0);
		}
		
		private long masked(final long value) {
			return value | mask[1] & mask[0];
		}
		
		//Assumes all floating bits are zero
		private void assignHelper(final long address, final long value, int bitIndex) {
			if(bitIndex == BITS)
				return;
			if(isFloating(bitIndex)) {
				final long ored = Bits.set(address, bitIndex);
				memory.put(address, value);
				memory.put(ored, value);
				assignHelper(ored, value, bitIndex + 1);
			}
			assignHelper(address, value, bitIndex + 1);
		}
		
		/** a bitIndex of 0 is the ones place, 1 is the twos place, etc */
		private boolean isFloating(int bitIndex) {
			return Bits.zeroAt(mask[0], bitIndex);
		}
		
		@Override
		public long sumMemory() {
			return memory.values().stream().mapToLong(Long::longValue).sum();
		}
	}
	
}
