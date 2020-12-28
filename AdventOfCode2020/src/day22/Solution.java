package day22;

import java.util.*;
import java.util.stream.Collectors;

import utils.IO;
import utils.colls.*;

/**
 * <p>Correct answers are 30780 (Part 1) and 36621 (Part 2).</p>
 * @author Sam Hooper
 *
 */
public class Solution {
	
	public static void main(String[] args) {
		String[] players = IO.splitOnBlanks("src/day22/input.txt").toArray(String[]::new);
		List<Integer> player1 = players[0].lines().skip(1).map(Integer::parseInt).collect(Collectors.toList());
		List<Integer> player2 = players[1].lines().skip(1).map(Integer::parseInt).collect(Collectors.toList());
		solvePart1(player1, player2);
		solvePart2(player1, player2);
	}
	
	private static void solvePart1(Collection<? extends Integer> p1, Collection<? extends Integer> p2) {
		Combat c = new Combat(p1, p2);
		c.playGame();
		System.out.println(score(c.winnerOrThrow()));
	}
	
	private static void solvePart2(Collection<? extends Integer> p1, Collection<? extends Integer> p2) {
		RecursiveCombat c = new RecursiveCombat(p1, p2);
		c.playGame();
		System.out.println(score(c.winnerOrThrow()));
	}
	
	private static int score(final Queue<Integer> queue) {
		int[] winner = queue.stream().mapToInt(Integer::intValue).toArray();
		int sum = 0;
		for(int i = winner.length - 1; i >= 0; i--)
			sum += winner[winner.length - i - 1] * (i + 1);
		return sum;
	}
	
	private abstract static class CardGame {
		protected final ArrayDeque<Integer> player1, player2;
		
		public CardGame(Collection<? extends Integer> p1, Collection<? extends Integer> p2) {
			player1 = new ArrayDeque<>(p1);
			player2 = new ArrayDeque<>(p2);
		}
		
		public CardGame playGame() {
			while(!isOver())
				playRound();
			return this;
		}
		
		public Queue<Integer> winnerOrThrow() {
			if(!isOver())
				throw new IllegalStateException("Game is still ongoing; there is no winner");
			return player1Won() ? new ArrayDeque<>(player1) : new ArrayDeque<>(player2);
		}
		
		public boolean isOver() {
			return player1Won() || player2Won();
		}
		
		/** Assumes {@link #isOver()} is {@code false} at the time this method is invoked.*/
		public abstract void playRound();
		public abstract boolean player1Won();
		public abstract boolean player2Won();
		
	}
	
	private static class Combat extends CardGame {
		
		public Combat(Collection<? extends Integer> p1, Collection<? extends Integer> p2) {
			super(p1, p2);
		}
		
		@Override
		public void playRound() {
			Integer card1 = player1.remove();
			Integer card2 = player2.remove();
			if(card1 > card2)
				Collections.addAll(player1, card1, card2);
			else
				Collections.addAll(player2, card2, card1);
		}
		
		@Override
		public Queue<Integer> winnerOrThrow() {
			if(player1.isEmpty())
				return new ArrayDeque<>(player2);
			else if(player2.isEmpty())
				return new ArrayDeque<>(player1);
			throw new IllegalStateException("Game is still ongoing; there is no winner");
		}

		@Override
		public boolean player1Won() {
			return player2.isEmpty();
		}
		
		@Override
		public boolean player2Won() {
			return player1.isEmpty();
		}
		
	}
	
	private static class RecursiveCombat extends CardGame  {
		
		private final Set<Pair<ArrayList<Integer>, ArrayList<Integer>>> statesSeen;
		private ArrayDeque<Integer> winner;
		
		public RecursiveCombat(Collection<? extends Integer> p1, Collection<? extends Integer> p2) {
			super(p1, p2);
			statesSeen = new HashSet<>();
			winner = null;
		}
		
		@Override
		public void playRound() {
			if(currentStateHasBeenSeenBefore()) {
				winner = player1;
				return;
			}
			statesSeen.add(Pair.of(new ArrayList<>(player1), new ArrayList<>(player2)));
			int card1 = player1.remove();
			int card2 = player2.remove();
			boolean player1Won;
			if(card1 <= player1.size() && card2 <= player2.size())
				player1Won = new RecursiveCombat(firstN(player1, card1), firstN(player2, card2)).playGame().player1Won();
			else
				player1Won = card1 > card2;
			if(player1Won)
				Collections.addAll(player1, card1, card2);
			else
				Collections.addAll(player2, card2, card1);
			if(player1.isEmpty())
				winner = player2;
			else if(player2.isEmpty())
				winner = player1;
		}
		
		@Override
		public boolean player1Won() {
			return winner == player1;
		}
		
		@Override
		public boolean player2Won() {
			return winner == player2;
		}
		
		private boolean currentStateHasBeenSeenBefore() {
			return statesSeen.contains(Pair.of(new ArrayList<>(player1), new ArrayList<>(player2)));
		}
		
		private static ArrayDeque<Integer> firstN(ArrayDeque<Integer> queue, int n) {
			return Colls.firstN(queue, n, ArrayDeque::new);
		}
	}
}
