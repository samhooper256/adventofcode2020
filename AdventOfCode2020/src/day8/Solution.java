package day8;

import java.util.Arrays;

import utils.*;

/**
 * Correct answers are 1610 (Part 1) and 1703 (Part 2).
 * @author Sam Hooper
 *
 */
public class Solution {
	
	public static void main(String[] args) {
		Solver s = new Solver(IO.strings("src/day8/input.txt"));
		s.solvePart1();
		s.solvePart2();
	}
	
	private static class Solver {
		
		private final String[] input;
		private long accumulator = 0;
		
		public Solver(final String[] input) {
			this.input = Arrays.copyOf(input, input.length);
		}
		
		public void solvePart1() {
			System.out.println(getResult().second());
		}
		
		/** The {@link Boolean} in the {@link Pair} is {@code true} if the program terminated, {@code false} otherwise. */
		private Pair<Boolean, Long> getResult() {
			accumulator = 0;
			int nextInstruction = 0;
			IntSet seen = new HashIntSet(100);
			while(!seen.contains(nextInstruction)) {
				if(nextInstruction >= input.length)
					return Pair.of(true, accumulator);
				seen.add(nextInstruction);
				nextInstruction = exec(nextInstruction);
			}
			return Pair.of(false, accumulator);
		}
		
		public void solvePart2() {
			terminatingValueAfterBeingFixed();
		}

		private long terminatingValueAfterBeingFixed() {
			for(int i = 0; i < input.length; i++) {
				final String line = input[i];
				if(line.charAt(0) != 'n' && line.charAt(0) != 'j')
					continue;
				input[i] = (line.startsWith("j") ? "nop" : "jmp") + input[i].substring(3);
				Pair<Boolean, Long> res = getResult();
				input[i] = line;
				if(res.first())
					return res.second();
			}
			throw new IllegalStateException("Could not find the correct instruction to modify");
		}
		
		/**
		 * Returns the index in {@link #input} of the next instruction to run.
		 */
		private int exec(final int instruction) {
			final String[] split = input[instruction].split(" ", 2);
			return switch(split[0]) {
				case "nop" -> instruction + 1;
				case "acc" -> {
					accumulator += Integer.parseInt(split[1]);
					yield instruction + 1;
				}
				case "jmp" -> instruction + Integer.parseInt(split[1]);
				default -> throw new IllegalStateException("Unknown instruction: \"" + input[instruction] + "\"");
			};
		}
	}
}
