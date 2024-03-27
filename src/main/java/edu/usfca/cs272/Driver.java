package edu.usfca.cs272;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

// TODO
//ADD DOCUMENTATION/comments
//Add javadoc warnings to eclipse
//RENAME VARS
//"Save actions - remove unused imports"
//Save actions white space

/**
 * Class responsible for running this project based on the provided command-line
 * arguments. See the README for details.
 *
 * This class initializes the necessary classes based on the provided command-line
 * arguments, such as building or searching an inverted index.
 * 
 * <p>
 * Usage: java edu.usfca.cs272.Driver [flags]
 * </p>
 * 
 * <p>
 * Flags:
 * <ul>
 *   <li>-text [file path]: Build an inverted index from the specified text file</li>
 *   <li>-index [file path]: Write the inverted index to the specified JSON file</li>
 *   <li>-counts [file path]: Write word counts to the specified JSON file</li>
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
     * @throws IOException if an I/O error occurs while building or writing the inverted index
     */
    public static void main(String[] args) throws IOException {
        // Store initial start time
        Instant start = Instant.now();

        // Print working directory and command-line arguments
        System.out.println("Working Directory: " + Path.of(".").toAbsolutePath().normalize().getFileName());
        System.out.println("Arguments: " + Arrays.toString(args));

        // Initialize InvertedIndex and ArgumentParser objects
        InvertedIndex invertedIndex = new InvertedIndex();
        ArgumentParser parsedFlags = new ArgumentParser(args);
        boolean hasText = false;

        // Build inverted index from text file if specified
        if (parsedFlags.hasFlag("-text")) {
            hasText = true;
            Path textPath = parsedFlags.getPath("-text");
            try {
                if (textPath != null) {
                    // Question: Is this bad??
                    new InvertedIndexBuilder(textPath);
                }
            } catch (IOException e) {
                System.out.println("Unable to build the inverted index from path: " + textPath);
            }
        }

        // Write inverted index to JSON file if specified
        if (parsedFlags.hasFlag("-index")) {
            Path indexPath = parsedFlags.getPath("-index", Path.of("index.json"));
            try {
                invertedIndex.writeInvertedIndex(indexPath);
            } catch (IOException e) {
                System.out.println("Unable to write the inverted index to path: " + indexPath);
            }
        }

        // Write word counts to JSON file if specified
        if (parsedFlags.hasFlag("-counts")) {
            Path countsPath = parsedFlags.getPath("-counts", Path.of("counts.json"));
            try {
                invertedIndex.writeJson(countsPath, hasText);
            } catch (IOException e) {
                System.out.println("Unable to write the word counts to path: " + countsPath);
            }
        }

        // Calculate and print elapsed time
        long elapsed = Duration.between(start, Instant.now()).toMillis();
        double seconds = (double) elapsed / Duration.ofSeconds(1).toMillis();
        System.out.printf("Elapsed: %f seconds%n", seconds);
    }
}
