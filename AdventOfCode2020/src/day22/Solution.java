package day22;

import java.util.*;
import java.util.stream.Collectors;

import utils.IO;
import utils.colls.Pair;

/**
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
	
	private static class Combat {
		
		private final ArrayDeque<Integer> player1, player2;
		int round = 1; //the next round to be played
		
		public Combat(Collection<? extends Integer> p1, Collection<? extends Integer> p2) {
			player1 = new ArrayDeque<>(p1);
			player2 = new ArrayDeque<>(p2);
		}
		
		public void playGame() {
			while(!isOver())
				playRound();
		}
		
		public void playRound() {
			if(isOver())
				throw new IllegalStateException("Cannot play round if the game is over");
			round++;
			Integer card1 = player1.remove();
			Integer card2 = player2.remove();
			if(card1 > card2) {
				player1.add(card1);
				player1.add(card2);
			}
			else {
				player2.add(card2);
				player2.add(card1);
			}
		}
		
		public boolean isOver() {
			return player1.isEmpty() || player2.isEmpty();
		}
		
		public Queue<Integer> winnerOrThrow() {
			if(player1.isEmpty())
				return new ArrayDeque<>(player2);
			else if(player2.isEmpty())
				return new ArrayDeque<>(player1);
			throw new IllegalStateException("Game is still ongoing; there is no winner");
		}
	}
	
	private static class RecursiveCombat {
		
		private final Set<Pair<ArrayList<Integer>, ArrayList<Integer>>> statesSeen;
		private final ArrayDeque<Integer> player1, player2; //the head of the queue is the top of the deck
		private int winner;
		int round = 1; //the next round to be played
		
		public RecursiveCombat(Collection<? extends Integer> p1, Collection<? extends Integer> p2) {
//			System.out.printf("REC COMB%n");
			player1 = new ArrayDeque<>(p1);
			player2 = new ArrayDeque<>(p2);
			statesSeen = new HashSet<>();
			winner = -1;
		}
		
		public void playGame() {
			while(!isOver())
				playRound();
			
		}
		
		public void playRound() {
			if(isOver())
				throw new IllegalStateException("Cannot play round if the game is over");
			if(currentStateHasBeenSeenBefore()) {
				winner = 1;
				return;
			}
			statesSeen.add(Pair.of(new ArrayList<>(player1), new ArrayList<>(player2)));
//			System.out.printf("-- Round %d (Game #%h) --%n", round, this);
			round++;
//			System.out.printf("Player 1's deck: %s%n", player1.stream().map(String::valueOf).collect(Collectors.joining(", ")));
//			System.out.printf("Player 2's deck: %s%n", player2.stream().map(String::valueOf).collect(Collectors.joining(", ")));
			int card1 = player1.remove();
			int card2 = player2.remove();
//			System.out.printf("Player 1 plays: %d%nPlayer 2 plays: %d%n", card1, card2);
			if(card1 <= player1.size() && card2 <= player2.size()) {
				RecursiveCombat c = new RecursiveCombat(firstN(player1, card1), firstN(player2, card2));
				c.playGame();
				if(c.player1Won()) {
					player1.add(card1);
					player1.add(card2);
				}
				else {
					player2.add(card2);
					player2.add(card1);
				}
			}
			else {
				if(card1 > card2) {
					player1.add(card1);
					player1.add(card2);
				}
				else {
					player2.add(card2);
					player2.add(card1);
				}
			}
			
		}
		
		public boolean isOver() {
			return player1Won() || player2Won();
		}
		
		public Queue<Integer> winnerOrThrow() {
			if(!isOver())
				throw new IllegalStateException("Game is still ongoing; there is no winner");
			return player1Won() ? new ArrayDeque<>(player1) : new ArrayDeque<>(player2);
		}
		
		public boolean player1Won() {
			return winner == 1 || player2.isEmpty();
		}
		
		public boolean player2Won() {
			return winner == 2 || player1.isEmpty();
		}
		
		private boolean currentStateHasBeenSeenBefore() {
			return statesSeen.contains(Pair.of(new ArrayList<>(player1), new ArrayList<>(player2)));
		}
		
		private static ArrayDeque<Integer> firstN(ArrayDeque<Integer> queue, int n) {
			ArrayDeque<Integer> result = new ArrayDeque<>();
			for(Integer i : queue) {
				result.add(i);
				if(--n <= 0)
					break;
			}
			return result;
		}
	}
}
