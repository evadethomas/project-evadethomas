package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * Class responsible for running this project based on the provided command-line
 * arguments. See the README for details.
 *
 * This class initializes the necessary classes based on the provided
 * command-line arguments, such as building or searching an inverted index.
 *
 * <p>
 * Usage: java edu.usfca.cs272.Driver [flags]
 * </p>
 *
 * <p>
 * Flags:
 * <ul>
 * <li>-text [file path]: Build an inverted index from the specified text
 * file</li>
 * <li>-index [file path]: Write the inverted index to the specified JSON
 * file</li>
 * <li>-counts [file path]: Write word counts to the specified JSON file</li>
 * </ul>
 * </p>
 *
 * @author Eva DeThomas
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2024
 */
public class Driver {
	/**
	 * Initializes the classes necessary based on the provided command-line
	 * arguments.
	 *
	 * @param args flag/value pairs used to start this program
	 * @throws IOException if an I/O error occurs while building or writing the
	 *                     inverted index
	 */
	public static void main(String[] args) throws IOException { // TODO remove throws 
		// Store initial start time
		final Instant start = Instant.now();

		// Print working directory and command-line arguments
		System.out.println("Working Directory: " + Path.of(".").toAbsolutePath().normalize().getFileName());
		System.out.println("Arguments: " + Arrays.toString(args));

		// Initialize InvertedIndex and ArgumentParser objects
		final InvertedIndex invertedIndex = new InvertedIndex();
		final ArgumentParser parsedFlags = new ArgumentParser(args);
		boolean hasText = false;

		// Build inverted index from text file if specified
		if (parsedFlags.hasFlag("-text")) {
			hasText = true;
			final Path textPath = parsedFlags.getPath("-text");
			try {
				if (textPath != null) {
					// TODO InvertedIndexBuilder.checkDirectory(textPath);
					// Question: Is this bad??
					new InvertedIndexBuilder(textPath); // TODO Maybe?
				}
			} catch (final IOException e) {
				System.out.println("Unable to build the inverted index from path: " + textPath);
			}
		}

		// Write inverted index to JSON file if specified
		if (parsedFlags.hasFlag("-index")) {
			final Path indexPath = parsedFlags.getPath("-index", Path.of("index.json"));
			try {
				invertedIndex.writeInvertedIndex(indexPath);
			} catch (final IOException e) {
				System.out.println("Unable to write the inverted index to path: " + indexPath);
			}
		}

		// Write word counts to JSON file if specified
		if (parsedFlags.hasFlag("-counts")) {
			final Path countsPath = parsedFlags.getPath("-counts", Path.of("counts.json"));
			try {
				invertedIndex.writeCounts(countsPath, hasText);
			} catch (final IOException e) {
				System.out.println("Unable to write the word counts to path: " + countsPath);
			}
		}

		// Calculate and print elapsed time
		final long elapsed = Duration.between(start, Instant.now()).toMillis();
		final double seconds = (double) elapsed / Duration.ofSeconds(1).toMillis();
		System.out.printf("Elapsed: %f seconds%n", seconds);
	}
}

/*
 * TODO 
Description	Resource	Path	Location	Type
Javadoc: Missing comment for private declaration	InvertedIndex.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 32	Java Problem
Javadoc: Missing comment for private declaration	InvertedIndex.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 33	Java Problem
*/
