package day18;

import java.math.BigInteger;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import utils.IO;

/**
 * @author Sam Hooper
 *
 */
public class Solution {
	
	private static final Set<String> OPERATORS = Set.of("*","+");
	private static final Map<String, Integer> PRECEDENCE_PART1, PRECEDENCE_PART2;
	
	//Both operators are assumed to be (and are) associative.
	static {
		Map<String, Integer> precedence = new HashMap<>();
		precedence.put("+", 1);
		precedence.put("*", 1);
		PRECEDENCE_PART1 = Collections.unmodifiableMap(precedence);
		precedence = new HashMap<>();
		precedence.put("+", 2);
		precedence.put("*", 1);
		PRECEDENCE_PART2 = Collections.unmodifiableMap(precedence);
	}
	public static void main(String[] args) {
		solvePart1();
		solvePart2();
	}

	private static void solvePart1() {
		solve(Solution::precedenceForPart1Operator);
	}
	
	private static void solvePart2() {
		solve(Solution::precedenceForPart2Operator);
	}
	
	private static void solve(ToIntFunction<String> precedenceFunction) {
		System.out.println(IO.lines("src/day18/input.txt").map(str -> eval(str, precedenceFunction)).reduce(BigInteger.ZERO, BigInteger::add));
	}
	
	private static BigInteger eval(String expression, ToIntFunction<String> precedenceFunction) {
		return evalPostfix(toPostfix(tokenize(expression), precedenceFunction));
	}
	
	private static List<String> tokenize(String expression) {
		return Arrays.stream(expression.replace(" ", "")
				.split("(?=[*+\\(\\)\\d])")).collect(Collectors.toList());
	}
	
	private static List<String> toPostfix(List<String> infixTokens, ToIntFunction<String> precedenceFunction) {
		Stack<String> opStack = new Stack<>(); 
		List<String> postfix = new ArrayList<>();
		for(String token : infixTokens) {
			if(isOperator(token)) {
				int tokenPrecedence = precedenceFunction.applyAsInt(token);
				String peek;
				while(
					!opStack.isEmpty() && !"(".equals(peek = opStack.peek()) &&
					precedenceFunction.applyAsInt(peek) >= tokenPrecedence
				) {
					postfix.add(opStack.pop());
				}
				opStack.push(token);
			}
			else if(isOpenParenthesis(token)) {
				opStack.add(token);
			}
			else if(isCloseParenthesis(token)) {
				while(!isOpenParenthesis(opStack.peek()))
					postfix.add(opStack.pop());
				opStack.pop();
			}
			else { //it's a number
				postfix.add(token);
			}
		}
		while(!opStack.isEmpty())
			postfix.add(opStack.pop());
		return postfix;
	}
	
	private static BigInteger evalPostfix(List<String> postfixTokens) {
		Stack<BigInteger> stack = new Stack<>();
		for(String token : postfixTokens) {
			if(isOperator(token)) {
				BigInteger op2 = stack.pop(), op1 = stack.pop();
				stack.push(switch(token) {
				case "+" -> op1.add(op2);
				case "*" -> op1.multiply(op2);
				default -> throw new UnsupportedOperationException("Unknown operator: \"" + token + "\"");
				});
			}
			else { //it's a number
				stack.push(new BigInteger(token));
			}
		}
		if(stack.size() != 1)
			throw new IllegalArgumentException("Expression is invalid");
		return stack.peek();
	}
	
	private static boolean isOperator(final String token) {
		return OPERATORS.contains(token);
	}
	
	private static boolean isOpenParenthesis(String token) {
		return "(".equals(token);
	}
	
	private static boolean isCloseParenthesis(String token) {
		return ")".equals(token);
	}
	
	private static int precedenceForPart1Operator(String operator) {
		return PRECEDENCE_PART1.get(operator);
	}
	
	private static int precedenceForPart2Operator(String operator) {
		return PRECEDENCE_PART2.get(operator);
	}
}
