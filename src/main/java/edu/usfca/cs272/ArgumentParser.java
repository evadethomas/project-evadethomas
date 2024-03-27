package edu.usfca.cs272;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HashMap;

public class ArgumentParser {
	/**
	 * Determines whether the argument is a flag. The argument is considered a flag
	 * if it is a dash "-" character followed by any character that is not a digit
	 * or whitespace. For example, "-hello" and "-@world" are considered flags, but
	 * "-10" and "- hello" are not.
	 *
	 * @param arg the argument to test if its a flag
	 * @return {@code true} if the argument is a flag
	 *
	 * @see String#startsWith(String)
	 * @see String#length()
	 * @see String#codePointAt(int)
	 * @see Character#isDigit(int)
	 * @see Character#isWhitespace(int)
	 */

	public static boolean isFlag(String arg) {
		// ensures edge cases are functional, empty arg handled and simple dash handled.
		// returning arg as a flag if the 1st char after the dash is not followed by a
		// digit or whitespace
		return (((arg != null && arg.length() > 1) && arg.startsWith("-"))
				&& (!Character.isDigit(arg.codePointAt(1)) && !Character.isWhitespace(arg.codePointAt(1))));

	}

	/**
	 * Determines whether the argument is a value. Anything that is not a flag is
	 * considered a value.
	 *
	 * @param arg the argument to test if its a value
	 * @return {@code true} if the argument is a value
	 */
	public static boolean isValue(String arg) {
		return !isFlag(arg);
	}

	/**
	 * Stores command-line arguments in flag/value pairs.
	 */
	private final HashMap<String, String> flagValmap;

	/**
	 * Initializes this argument map.
	 */
	public ArgumentParser() {
		this.flagValmap = new HashMap<>();
	}

	/**
	 * Initializes this argument map and then parsers the arguments into flag/value
	 * pairs where possible. Some flags may not have associated values. If a flag is
	 * repeated, its value is overwritten.
	 *
	 * @param args the command line arguments to parse
	 */
	public ArgumentParser(String[] args) {
		this();
		parse(args);
		getInteger("-min");
	}

	/**
	 * Returns the value the specified flag is mapped as an int value, or 0 if
	 * unable to retrieve this mapping (including being unable to convert the value
	 * to an int or if no value exists).
	 *
	 * @param flag the flag whose associated value will be returned
	 * @return the value the specified flag is mapped as an int, or 0 if there is no
	 *         valid mapping
	 *
	 * @see #getInteger(String, int)
	 */
	public int getInteger(String flag) {
		return getInteger(flag, 0);
	}

	/**
	 * Returns the value the specified flag is mapped as an int value, or the backup
	 * value if unable to retrieve this mapping (including being unable to convert
	 * the value to an int or if no value exists).
	 *
	 * @param flag   the flag whose associated value will be returned
	 * @param backup the backup value to return if there is no valid mapping
	 * @return the value the specified flag is mapped as an int, or the backup value
	 *         if there is no valid mapping
	 *
	 * @see Integer#parseInt(String)
	 */
	public int getInteger(String flag, int backup) {
		try {
			final String num = getMap().get(flag);
			return Integer.parseInt(num);
		} catch (NumberFormatException | NullPointerException e) {
			return backup;
		}
	}

	public HashMap<String, String> getMap() {
		return flagValmap;
	}

	/**
	 * Returns the value to which the specified flag is mapped as a {@link Path}, or
	 * {@code null} if unable to retrieve this mapping (including being unable to
	 * convert the value to a {@link Path} or no value exists).
	 *
	 * This method should not throw any exceptions!
	 *
	 * @param flag the flag whose associated value is to be returned
	 * @return the value to which the specified flag is mapped, or {@code null} if
	 *         unable to retrieve this mapping
	 *
	 * @see #getPath(String, Path)
	 */
	public Path getPath(String flag) {
		return getPath(flag, null);
	}

	/**
	 * Returns the value the specified flag is mapped as a {@link Path}, or the
	 * backup value if unable to retrieve this mapping (including being unable to
	 * convert the value to a {@link Path} or if no value exists).
	 *
	 * This method should not throw any exceptions!
	 *
	 * @param flag   the flag whose associated value will be returned
	 * @param backup the backup value to return if there is no valid mapping
	 * @return the value the specified flag is mapped as a {@link Path}, or the
	 *         backup value if there is no valid mapping
	 *
	 * @see Path#of(String, String...)
	 *
	 */

	public Path getPath(String flag, Path backup) {
		// CITE: Sophie's path example files
		try {
			final String val = getMap().get(flag);
			if (val != null) {
				return Path.of(getMap().get(flag));
			}
			return backup;
		} catch (final InvalidPathException e) {
			System.out.println("Invalid path");
			return null;
		}

	}

	/**
	 * Returns the value to which the specified flag is mapped as a {@link String}
	 * or null if there is no mapping.
	 *
	 * @param flag the flag whose associated value is to be returned
	 * @return the value to which the specified flag is mapped or {@code null} if
	 *         there is no mapping
	 */
	public String getString(String flag) {
		return getMap().getOrDefault(flag, null);
	}

	/**
	 * Returns the value to which the specified flag is mapped as a {@link String}
	 * or the backup value if there is no mapping.
	 *
	 * @param flag   the flag whose associated value is to be returned
	 * @param backup the backup value to return if there is no mapping
	 * @return the value to which the specified flag is mapped, or the backup value
	 *         if there is no mapping
	 */
	public String getString(String flag, String backup) {
		if (hasValue(flag)) {
			return getMap().get(flag);
		}
		return backup;
	}

	/**
	 * Determines whether the specified flag exists.
	 *
	 * @param flag the flag check
	 * @return {@code true} if the flag exists
	 */
	public boolean hasFlag(String flag) {
		// CITE: Used official javadoc
		return getMap().containsKey(flag);
	}

	/**
	 * Determines whether the specified flag is mapped to a non-null value.
	 *
	 * @param flag the flag to find
	 * @return {@code true} if the flag is mapped to a non-null value
	 */
	public boolean hasValue(String flag) {
		return getMap().get(flag) != null;
	}

	/**
	 * Returns the number of unique flags.
	 *
	 * @return number of unique flags
	 */
	public int numFlags() {
		return getMap().size();
	}

	/**
	 * Parses the arguments into flag/value pairs where possible. Some flags may not
	 * have associated values. If a flag is repeated, its value will be overwritten.
	 *
	 * @param args the command line arguments to parse
	 */
	public void parse(String[] args) {
		// Handles edge case where args is empty
		if (args.length == 0) {
			return;
		}
		// iterate through arguments
		for (int i = 0; i < args.length - 1; i++) {
			// get the first arg of each "pair" and set to flag var
			if (isFlag(args[i])) {
				final String flag = args[i];
				i++;
				// once a flag is found, increment i to check the next arg. If arg is not a
				// flag, add it to dict. Otherwise add null.
				if (!isFlag(args[i])) {
					getMap().put(flag, args[i]);
				} else {
					getMap().put(flag, null);
					// decrement i if the next arg is a flag to start the loop in the correct place
					// to get another pair
					i--;
				}
			}
		}
		// Handles the edge case where the last arg is a flag with no value
		// corresponding to it
		if (isFlag(args[args.length - 1])) {
			getMap().put(args[args.length - 1], null);
		}
	}

	@Override
	public String toString() {
		return this.getMap().toString();
	}
}
