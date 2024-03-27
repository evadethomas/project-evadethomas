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
 * @author Eva DeThomas
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2024
 * commit check
 */
//Don't run on driver, run on tests. Find outline window if you lost it.
public class Driver {
	/**
	 * Initializes the classes necessary based on the provided command-line
	 * arguments. This includes (but is not limited to) how to build or search an
	 * inverted index.
	 *
	 * @param args flag/value pairs used to start this program
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// store initial start time
		Instant start = Instant.now();

		System.out.println("Working Directory: " + 	Path.of(".").toAbsolutePath().normalize().getFileName());
		System.out.println("Arguments: " + Arrays.toString(args));
	
		
		InvertedIndex InvIndex = new InvertedIndex();

		ArgumentParser parsedFlags = new ArgumentParser(args);
		boolean hasText = false;
		//Question - can I make these all into different funtions? They're so annoying looking.
		if (parsedFlags.hasFlag("-text")) {
			hasText = true;
			Path path = parsedFlags.getPath("-text");
			try {
				if (path != null) {
					InvertedIndexBuilder builder = new InvertedIndexBuilder();
					builder.checkDirectory(path);
				}
			} catch (IOException e) {
				System.out.println("Unable to build the inverted index from path: " + path);
			}
		}
		
		if (parsedFlags.hasFlag("-index")) {
			Path path = parsedFlags.getPath("-index", Path.of("index.json"));
			try {
				InvIndex.writeInvertedIndex(path);
			} catch (IOException e) {
				System.out.println("Unable to build the inverted index from path: " + path);
			}
		}
		
		if (parsedFlags.hasFlag("-counts")) {
			Path path = parsedFlags.getPath("-counts", Path.of("counts.json"));
			try {
				InvIndex.writeJson(path, hasText);
			} catch (IOException e) {
				System.out.println("Unable to build the inverted index from path: " + path);
			}
		}		
		
		// calculate time elapsed and output
		long elapsed = Duration.between(start, Instant.now()).toMillis();
		double seconds = (double) elapsed / Duration.ofSeconds(1).toMillis();
		System.out.printf("Elapsed: %f seconds%n", seconds);
		
		
	}
	
	

}

/*
 * TODO 
handle warnings
*/
