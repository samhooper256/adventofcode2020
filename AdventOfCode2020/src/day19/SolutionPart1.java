package day19;

import java.util.*;
import java.util.regex.Pattern;

import utils.*;

/**
 * @author Sam Hooper
 *
 */
public class SolutionPart1 {
	
	private static final int NUM_RULES = 134;
	private static final Map<Integer, String> REGEX_MAP = new HashMap<>();
	private static final String[] SPLIT = IO.text("src/day19/input.txt").split("\n\n"), RULES = new String[NUM_RULES];
	
	static {
		for(String[] ruleSplit : SPLIT[0].lines().map(s -> s.split(": ")).toArray(String[][]::new))
			RULES[Integer.parseInt(ruleSplit[0])] = ruleSplit[1];
	}
	
	public static void main(String[] args) {
		Pattern pat = Pattern.compile(getRegex(0));
		System.out.println(SPLIT[1].lines().filter(line -> pat.matcher(line).matches()).count());
	}
	
	private static String getRegex(int index) {
		if(!REGEX_MAP.containsKey(index)) {
			final String rule = RULES[index];
			if(rule.contains("\""))
				REGEX_MAP.put(index, rule.substring(1, 2));
			else {
				String[] split = rule.split(" ");
				StringBuilder reg = new StringBuilder("(?:");
				for(int i = 0; i < split.length; i++)
					reg.append(split[i].equals("|") ? "|" : getRegex(Integer.parseInt(split[i])));
				REGEX_MAP.put(index, reg.append(")").toString());
			}
		}
		return REGEX_MAP.get(index);
	}
	
}
