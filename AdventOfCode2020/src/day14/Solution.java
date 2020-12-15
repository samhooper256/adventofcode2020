package day14;

import java.util.*;

import utils.IO;

/**
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
		System.out.println(new Interpreter(lines).execute().sumMemory());
	}
	
	private static void solvePart2(final String[] lines) {
		System.out.println(new Interpreter2(lines).execute().sumMemory());
	}
	
	private static final long BITS = 36;
	private static final int MEMORY_SIZE = 100_000;
	
	private static class Interpreter {
		
		private final String[] input;
		private final long[] memory;
		private long[] mask; //mask[0] has 0 bits where the mask was an 'X' and 1 bits where the mask was either a '1' or a '0'.
		
		public Interpreter(String[] input) {
			this.input = input;
			this.memory = new long[MEMORY_SIZE];
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
		
		private void handleMask(final String maskString) {
			String bitString = maskString.substring(maskString.lastIndexOf(' ') + 1);
			long considered = Long.parseLong(bitString.replace('0', '1').replace('X', '0'), 2);
			long maskBits = Long.parseLong(bitString.replace('X', '0'), 2);
			mask[0] = considered;
			mask[1] = maskBits;
		}
		
		private void handleAssignment(final String assignmentString) {
			int address = Integer.parseInt(assignmentString.substring(assignmentString.indexOf('[') + 1, assignmentString.indexOf(']')));
			long value = masked(Long.parseLong(assignmentString.substring(assignmentString.lastIndexOf(' ') + 1)));
			memory[address] = value;
		}
		
		private long masked(final long value) {
			long result = value;
			result &= ~(mask[1] ^ mask[0]);
			result |= mask[1] & mask[0];
			return result;
		}
		
		public long sumMemory() {
			long sum = 0;
			for(long l : memory)
				sum += l;
			return sum;
		}
	}
	
	private static class Interpreter2 {
		
		private final String[] input;
		private final Map<Long, Long> memory;
		private long[] mask;
		
		public Interpreter2(String[] input) {
			this.input = input;
			this.memory = new HashMap<>();
			this.mask = new long[2];
		}
		
		public Interpreter2 execute() {
			for(String line : input)
				if(line.startsWith("mask"))
					handleMask(line);
				else
					handleAssignment(line);
			return this;
		}
		
		private void handleMask(final String maskString) {
//			System.out.printf("[enter] handleMask(%s)%n", maskString);
			String bitString = maskString.substring(maskString.lastIndexOf(' ') + 1);
			long considered = Long.parseLong(bitString.replace('0', '1').replace('X', '0'), 2);
			long maskBits = Long.parseLong(bitString.replace('X', '0'), 2);
			mask[0] = considered;
			mask[1] = maskBits;
//			System.out.printf("mask now = [%s, %s]%n", Long.toBinaryString(mask[0]), Long.toBinaryString(mask[1]));
		}
		
		private void handleAssignment(final String assignmentString) {
			long address = Long.parseLong(assignmentString.substring(assignmentString.indexOf('[') + 1, assignmentString.indexOf(']')));
			long value = Long.parseLong(assignmentString.substring(assignmentString.lastIndexOf(' ') + 1));
			assign(address, value);
		}
		
		private void assign(final long address, final long value) {
			final long masked = masked(address) & mask[0];
//			System.out.printf("assign(address=%s, value=%d (base 10))%n", Long.toBinaryString(address), value);
			assignHelper(masked, value, 0L);
		}
		
		private long masked(final long value) {
			long result = value;
			result |= mask[1] & mask[0];
			return result;
		}
		
		//Assumes all floating bits are zero
		private void assignHelper(final long address, final long value, long bitIndex) {
			if(bitIndex == BITS)
				return;
			
			//assign and pass on
			if(isFloating(bitIndex)) {
//				System.out.printf("FLOATER assign(address=%s, value=%d (base 10), bitIndex=%d (base 10))%n", Long.toBinaryString(address), value, bitIndex);
				memory.put(address, value);
				final long ored = address | onlyBit(bitIndex);
				memory.put(ored, value);
				assignHelper(address, value, bitIndex + 1);
				assignHelper(ored, value, bitIndex + 1);
			}
			else {
				assignHelper(address, value, bitIndex + 1);
			}
		}
		
		/** a bitIndex of 0 is the ones place, 1 is the twos place, etc */
		private boolean isFloating(long bitIndex) {
			return bitAt(mask[0], bitIndex) == 0L;
		}
		
		private long bitAt(long num, long bitIndex) {
			return num & onlyBit(bitIndex);
		}
		
		private long onlyBit(long bitIndex) {
			return 1L << bitIndex;
		}
		
		public long sumMemory() {
//			System.out.println("summing " + memory);
			long sum = 0;
			for(long l : memory.values())
				sum += l;
			return sum;
		}
	}
	
}
