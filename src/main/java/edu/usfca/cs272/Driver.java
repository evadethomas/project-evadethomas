package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;

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

		/* TODO 
		ArgumentParser parser = new ArgumentParser(args);
		InvertedIndex index = new InvertedIndex();
		
		if (parser.hasFlag("-text")) {
			Path path = parser.getPath("-text");
			
			try {
				1 or 2 lines of code
			}
			catch ( ) {
				System.out.println("Unable to build the inverted index from path: " + path);
			}
		}
		
		if (parser.hasFlag("-index")) {
			Path path = parser.getPath("-index", Path.of("index.json"));
			
			try {
				JsonWriter.writeObjectNested(invertedIndex, path);
			}
			catch ( ) {
				System.out.println("Unable to build the inverted index from path: " + path);
			}
		}
		*/
		
		
		
//		try {
			//See Json class
			getJsonFile files = new getJsonFile(args);
//		} catch (IOException e) {
//			System.out.println("Problem with getting the json files. See getJsonFile class.");
//		}
		
		// calculate time elapsed and output
		long elapsed = Duration.between(start, Instant.now()).toMillis();
		double seconds = (double) elapsed / Duration.ofSeconds(1).toMillis();
		System.out.printf("Elapsed: %f seconds%n", seconds);
		
		
	}

}

/*
 * TODO 
Description	Resource	Path	Location	Type
Javadoc: Missing comment for private declaration	getJsonFile.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 110	Java Problem
Javadoc: Missing comment for private declaration	getJsonFile.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 155	Java Problem
Javadoc: Missing comment for public declaration	ArgumentParser.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 5	Java Problem
Javadoc: Missing comment for public declaration	ArgumentParser.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 243	Java Problem
Javadoc: Missing comment for public declaration	FileStemmer.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 22	Java Problem
Javadoc: Missing comment for public declaration	FileStemmer.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 23	Java Problem
Javadoc: Missing comment for public declaration	JsonWriter.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 363	Java Problem
Javadoc: Missing comment for public declaration	getJsonFile.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 22	Java Problem
Javadoc: Missing comment for public declaration	getJsonFile.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 24	Java Problem
The import java.io.BufferedReader is never used	getJsonFile.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 3	Java Problem
The import java.io.FileReader is never used	getJsonFile.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 5	Java Problem
The import java.nio.file.FileSystems is never used	getJsonFile.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 8	Java Problem
The import java.util.Collection is never used	getJsonFile.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 13	Java Problem
The import java.util.HashMap is never used	Driver.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 8	Java Problem
The import java.util.Map is never used	getJsonFile.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 15	Java Problem
The import java.util.Scanner is never used	getJsonFile.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 17	Java Problem
The import java.util.TreeMap is never used	JsonWriter.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 15	Java Problem
The import java.util.TreeSet is never used	JsonWriter.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 16	Java Problem
The value of the local variable files is not used	Driver.java	/SearchEngine/src/main/java/edu/usfca/cs272	line 37	Java Problem
*/
