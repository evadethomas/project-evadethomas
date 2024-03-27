package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * InvertedIndexBuilder provides the methods necessary to create an
 * InvertedIndex, specifically the invertedIndex variable.
 *
 * Given a directory, the constructor calls checkDirectory which then utilized
 * the remaining functions to traverse through the given directory and call
 * methods on InvertedIndex to build the index.
 *
 * @see InvertedIndex
 * @author Eva DeThomas
 */
public class InvertedIndexBuilder {

	/**
	 * @param rootFile this is the root of the directory or a file itself
	 * @throws IOException catches an IO error
	 */
	public static void checkDirectory(Path rootFile) throws IOException {
		if (Files.isDirectory(rootFile) == true) {
			traverseDirectory(rootFile);
		} else {
			processFile(rootFile);
		}

	}

	/**
	 * Helper method for processFile
	 *
	 * @param path the file to be checked
	 * @return whether the file ending is properly formatted or not
	 * @see InvertedIndexBuilder#processFile(Path)
	 */
	static public boolean checkValidFile(Path path) {
		return path.toString().toLowerCase().endsWith(".txt") || path.toString().toLowerCase().endsWith(".text");
	}

	/**
	 * Extracts stems from file, finds the number of the stems to add to countMap.
	 * Then gets the file name, the indexes of each stem using a loop, and adds them
	 * to InvertedIndex.
	 *
	 * @param path takes in a file to be added to the countMap and invertedIndex
	 * @throws IOException to check if IO error occurs Runs trough a file,
	 * @see InvertedIndex
	 * @see InvertedIndex#addWord
	 * @see InvertedIndex#addCount
	 * @see FileStemmer#listStems(Path)
	 *
	 */
	public static void processFile(Path path) throws IOException {
		final ArrayList<String> stems = FileStemmer.listStems(path);
		InvertedIndex.addCount(path.toString(), stems.size());
		int count = 1;
		for (final String stem : stems) {
			InvertedIndex.addWord(stem, path.toString(), count);
			count += 1;
		}
	}

	/**
	 * Recursively runs through a directory and processes each child file by calling
	 * processFile
	 *
	 * @param directory takes in a path directory
	 * @throws IOException catches an IO error
	 * @see InvertedIndexBuilder#processFile(Path)
	 */
	public static void traverseDirectory(Path directory) throws IOException {
		try (DirectoryStream<Path> fileList = Files.newDirectoryStream(directory)) {
			for (final Path path : fileList) {
				if (Files.isDirectory(path)) {
					traverseDirectory(path);
				} else if (checkValidFile(path)) {
					processFile(path);
				}
			}
		}

	}

	/**
	 * Constructor for InvertedIndexBuilder
	 *
	 * @param path the path of the original file provided in the arguments
	 * @throws IOException to catch any IO errors
	 * @see InvertedIndexBuilder
	 * @see InvertedIndexBuilder#checkDirectory(Path)
	 */
	public InvertedIndexBuilder(Path path) throws IOException {
		checkDirectory(path);
	}

}
