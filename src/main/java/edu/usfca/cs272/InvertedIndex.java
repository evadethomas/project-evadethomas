package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Represents an inverted index data structure.
 *
 * <p>
 * An inverted index is a data structure used to map each term in a corpus to
 * the documents (or locations) where that term appears. This implementation
 * stores the inverted index in a TreeMap.
 * </p>
 *
 * <p>
 * The inverted index consists of terms (keys) and a mapping to the documents or
 * locations where those terms appear along with their frequency counts.
 *
 * </p>
 *
 * <p>
 * CITE: Chat gpt for help with javadoc comments and Sophie Engle for revisions
 * </p>
 *
 * @author Eva DeThomas
 * @see InvertedIndexBuilder
 */
public class InvertedIndex {
	
	/*
	 * TODO Use the final keyword instead of static
	 */

	private static TreeMap<String, Integer> countMap;
	private static TreeMap<String, TreeMap<String, TreeSet<Integer>>> invertedIndex;

	/**
	 * Adds the file and it's stem size to the countMap
	 *
	 * @param path a filePath that has been stemmed
	 * @param size the amount of stems/words it found
	 */
	public static void addCount(String path, Integer size) { // TODO Make non-static
		if (size != 0) {
			countMap.put(path, size);
		}
	}

	/**
	 * Adds the stemmed word, it's file and its index to the invertedIndex data
	 * structure
	 *
	 * @param stem     the stemmed word found/stemmed in the file
	 * @param location the file it was found in
	 * @param count    the index it was found in
	 */
	public static void addWord(String stem, String location, Integer count) {  // TODO Make non-static
		/*
		 * TODO 
		 * Use computeIfAbsent to initialize the structures...
		 * 

		invertedIndex.putIfAbsent(stem, new TreeMap<>());
		invertedIndex.computeIfAbsent(stem, () -> new TreeMap<>());
		
		Turns into 1 to 3 lines of code.
		 */
		
		
		final TreeMap<String, TreeSet<Integer>> locAndCountMap = invertedIndex.get(stem);
		if (locAndCountMap != null) {
			final TreeSet<Integer> indexes = locAndCountMap.get(location);
			if (indexes != null) {
				indexes.add(count);
				locAndCountMap.put(location, indexes);
			} else {
				final TreeSet<Integer> newIntegers = new TreeSet<Integer>();
				newIntegers.add(count);
				locAndCountMap.put(location, newIntegers);
			}
		} else {
			final TreeSet<Integer> indexes = new TreeSet<Integer>();
			indexes.add(count);
			final TreeMap<String, TreeSet<Integer>> newLocAndCountMap = new TreeMap<String, TreeSet<Integer>>();
			newLocAndCountMap.put(location, indexes);
			invertedIndex.put(stem, newLocAndCountMap);
		}
	}

	/**
	 * Constructor for inverted index, initialize countMap and treeMap
	 */
	public InvertedIndex() {
		invertedIndex = new TreeMap<String, TreeMap<String, TreeSet<Integer>>>();
		countMap = new TreeMap<String, Integer>();
	}

	/**
	 *
	 * Writes the countMap specifically to a json file using JSON writer class
	 *
	 * @param counts  takes in the name of the file to write the count map to
	 * @param hasText takes in whether or not the text flag was present
	 * @throws IOException in case of an IO error
	 * @see JsonWriter#writeObject
	 */
	public void writeCounts(Path counts, boolean hasText) throws IOException { // TODO Remove boolean
		if (hasText == true) {
			JsonWriter.writeObject(countMap, counts); // TODO Only line needed?
		} else {
			JsonWriter.writeObject(new TreeMap<String, Integer>(), counts);
		}
	}

	/**
	 * Actually writes out the data structure to a new file
	 *
	 * @param indexFilePath takes in a the name of the file to write to
	 * @throws IOException to catch IO error
	 * @see JsonWriter#writeObjectNested
	 */
	public void writeInvertedIndex(Path indexFilePath) throws IOException {
		JsonWriter.writeObjectNested(invertedIndex, indexFilePath);
	}
	
	/*
	 * TODO Add more generally useful methods
	 * FileIndex 
	 * WordGroup/WordPrefix
	 * 
	 * Both of those examples store 2 pieces of information, so they have 2 versions
	 * of each method...
	 * 
	 * None of these should break encapsulation or create copies or need loop
	 * 
	 * https://github.com/usf-cs272-spring2024/cs272-lectures/blob/main/src/main/java/edu/usfca/cs272/lectures/inheritance/word/WordPrefix.java
	 * 
	 * https://github.com/usf-cs272-spring2024/cs272-lectures/blob/main/src/main/java/edu/usfca/cs272/lectures/basics/objects/PrefixDemo.java
	 */
}
