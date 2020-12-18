package day4;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.*;
import utils.colls.Colls;

/**
 * @author Sam Hooper
 *
 */
public class Solution {

	private static final Map<String, Predicate<? super String>> VALIDATION_FUNCTIONS;
	
	private static final int MIN_BIRTH_YEAR = 1920;
	private static final int MAX_BIRTH_YEAR = 2002;
	private static final int MIN_ISSUE_YEAR = 2010;
	private static final int MAX_ISSUE_YEAR = 2020;
	private static final int MIN_EXPIRATION_YEAR = 2020;
	private static final int MAX_EXPIRATION_YEAR = 2030;
	private static final int MIN_HEIGHT_CM = 150;
	private static final int MAX_HEIGHT_CM = 193;
	private static final int MIN_HEIGHT_IN = 59;
	private static final int MAX_HEIGHT_IN = 76;
	private static final Pattern HAIR_COLOR_PATTERN = Pattern.compile("#[0-9a-f]{6}");
	private static final Set<String> EYE_COLORS = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
	private static final Pattern PASSPORT_ID_PATTERN = Pattern.compile("[0-9]{9}");
	
	static {
		final Map<String, Predicate<? super String>> valFuncs = new HashMap<>();
		valFuncs.put("byr", s -> Basics.between(s, MIN_BIRTH_YEAR, MAX_BIRTH_YEAR));
		valFuncs.put("iyr", s -> Basics.between(s, MIN_ISSUE_YEAR, MAX_ISSUE_YEAR));
		valFuncs.put("eyr", s -> Basics.between(s, MIN_EXPIRATION_YEAR, MAX_EXPIRATION_YEAR));
		valFuncs.put("hgt", Solution::isValidHeight);
		valFuncs.put("hcl", s -> HAIR_COLOR_PATTERN.matcher(s).matches());
		valFuncs.put("ecl", EYE_COLORS::contains);
		valFuncs.put("pid", s -> PASSPORT_ID_PATTERN.matcher(s).matches());
		VALIDATION_FUNCTIONS = Collections.unmodifiableMap(valFuncs);
	}
	
	public static void main(String[] args) {
		final List<Map<String, String>> passports = Pattern.compile("\n\n").splitAsStream(IO.text("src/day4/input.txt")).map(Solution::fields).collect(Collectors.toList());
		solvePart1(passports);
		solvePart2(passports);
	}
	
	private static void solvePart1(final List<Map<String, String>> passports) {
		System.out.println(Colls.count(passports, Solution::hasAllRequiredFields));
	}
	
	private static void solvePart2(final List<Map<String, String>> passports) {
		System.out.println(Colls.count(passports, Solution::isValidPassport));
	}
	
	private static boolean isValidHeight(final String s) {
		return 	s.endsWith("cm") && Basics.between(s.substring(0, s.lastIndexOf('c')), MIN_HEIGHT_CM, MAX_HEIGHT_CM) ||
				s.endsWith("in") && Basics.between(s.substring(0, s.lastIndexOf('i')), MIN_HEIGHT_IN, MAX_HEIGHT_IN);
	}
	
	private static Map<String, String> fields(final String passportData) {
		final Map<String, String> map = new HashMap<>();
		for(String keyValuePair : Regex.WHITESPACE.split(passportData)) {
			int colon = keyValuePair.indexOf(':');
			map.put(keyValuePair.substring(0, colon), keyValuePair.substring(colon + 1));
		}
		return map;
	}
	
	private static boolean hasAllRequiredFields(final Map<String, String> passport) {
		return passport.keySet().containsAll(requiredFields());
	}

	private static Set<String> requiredFields() {
		return VALIDATION_FUNCTIONS.keySet();
	}
	
	private static boolean isRequiredField(final String fieldName) {
		return VALIDATION_FUNCTIONS.containsKey(fieldName);
	}

	private static boolean isValidPassport(final Map<String, String> passport) {
		return hasAllRequiredFields(passport) && allRequiredFieldsAreValid(passport);
	}
	
	private static boolean allRequiredFieldsAreValid(final Map<String, String> fields) {
		for(Map.Entry<String, String> e : fields.entrySet())
			if(isRequiredField(e.getKey()))
				if(!isValidField(e))
					return false;
		return true;
	}
	
	/** Assumes the field {@link #isRequiredField(String) is required}.*/
	private static boolean isValidField(final Map.Entry<String, String> field) {
		return VALIDATION_FUNCTIONS.get(field.getKey()).test(field.getValue());
	}
}
