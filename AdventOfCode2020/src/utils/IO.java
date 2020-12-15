package utils;

import java.io.*;
import java.util.stream.*;

/**
 * <p>Input and output related utilities.</p>
 * @author Sam Hooper
 *
 */
public final class IO {
	
	private IO() {}
	
	/**
	 * Returns a {@link Stream Stream}{@code<String>} containing the lines in the file, as if given by {@link BufferedReader#lines()}.
	 * @param fileName the name of the file as it should be passed to {@link FileReader#FileReader(String)}.
	 * @throws A {@link RuntimeException} if getting the lines fails for any reason.
	 */
	public static Stream<String> lines(final String fileName) {
		try {
			return new BufferedReader(new FileReader(fileName)).lines();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Returns a {@code String[]} containing the {@link #lines(String) lines} of the indicated file.
	 */
	public static String[] strings(final String fileName) {
		return lines(fileName).toArray(String[]::new);
	}
	
	/**
	 * Returns the {@code chars} in the file indicated by {@code fileName}, where each row in the returned array is a line in the file.
	 */
	public static char[][] chars(final String fileName) {
		return lines(fileName).map(String::toCharArray).toArray(char[][]::new);
	}
	
	/**
	 * Returns the entire text of the named file as a {@code String}, using LF (that is, '\n') as its line terminator.
	 */
	public static String text(final String fileName) {
		return text(fileName, "\n");
	}
	
	/**
	 * Returns the entire text of the named file as a {@code String}, using {@code lineTerminator} as its line terminator.
	 */
	public static String text(final String fileName, final String lineTerminator) {
		return lines(fileName).collect(Collectors.joining(lineTerminator));
	}
}
