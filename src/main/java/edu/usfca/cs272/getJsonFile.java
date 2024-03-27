package edu.usfca.cs272;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class getJsonFile {
	
	//index
	private static TreeMap<String, Integer> countMap;
	//builder? Do I need this?
	private static HashMap<String, Integer> emptyMap;
	//index
	private static TreeMap<String, TreeMap<String, TreeSet<Integer>>> invertedIndex;
	//builder
	private static boolean hasText = true;
	
	//driver
	public getJsonFile(String[] args) throws IOException {
		
		countMap = new TreeMap<String, Integer>();
		emptyMap = new HashMap<String, Integer>();
		invertedIndex = new TreeMap<String, TreeMap<String, TreeSet<Integer>>>();
			
	}

	//Index
	void writeInvertedIndex(Path indexFilePath) throws IOException {
		JsonWriter.writeObjectNested(invertedIndex, indexFilePath);
	}
	
	//Index
	void writeJson(Path counts) throws IOException {
		if (hasText == true) {
			JsonWriter.writeObject(countMap, counts);
    	} else {
			JsonWriter.writeObject(emptyMap, counts);
		}
	}

	//Builder
	void checkDirectory(Path originalFile) throws IOException {
		
    	if (Files.isDirectory(originalFile) == true) {
    		traverseDirectory(originalFile);
    	} else {
    		processFile(originalFile);
    	}
    	
	}
	//builder
	private static void processFile(Path path) throws IOException  {
		
		ArrayList<String> stems = FileStemmer.listStems(path);
		addCount(path.toString(), stems.size());
		int count = 1;
		for (String stem : stems) {
			addWord(stem, path.toString(), count);
			count += 1;
		}
			
	}
	
	
	//index
	private static void addCount(String path, Integer size) {
		if (size != 0) {
			countMap.put(path, size);
		}
	}
	
	//builder
	private static boolean checkValidFile(Path path) {
		return path.toString().toLowerCase().endsWith(".txt") || path.toString().toLowerCase().endsWith(".text");
	}

	//builder
	private static void traverseDirectory(Path directory) throws IOException {
		try (DirectoryStream<Path> listing = Files.newDirectoryStream(directory)) {
			// use an enhanced-for or for-each loop for efficiency and simplicity??
			for (Path path : listing) {
				if (Files.isDirectory(path)) {
					traverseDirectory(path);
				} else {
					if (checkValidFile(path)) {
						processFile(path);
					}
				}
			}
		}
	}
	//index
	//FIX VAR NAMES!
	private static void addWord(String stem, String location, Integer count) {
		TreeMap<String, TreeSet<Integer>> innerMap = invertedIndex.get(stem);
		if (innerMap != null) {
			
			TreeSet<Integer> integers = innerMap.get(location);
			if (integers != null) {
				integers.add(count);
				innerMap.put(location, integers);
			} else {
				TreeSet<Integer> newIntegers = new TreeSet<Integer>();
				newIntegers.add(count);
				innerMap.put(location, newIntegers);
			}
		} else {
			TreeSet<Integer> integers = new TreeSet<Integer>();
			integers.add(count);
			TreeMap<String, TreeSet<Integer>> fileAndInteger = new TreeMap<String, TreeSet<Integer>>();
			fileAndInteger.put(location, integers);
			invertedIndex.put(stem, fileAndInteger);
		}
	}

	/*
	 * TODO
	 * 
	 * Create 2 new classes instead of this one
	 * 
	 * 1) InvertedIndex data structure class
	 * 
	 * TreeMap<String, Integer> countMap, 
	 * TreeMap<String, TreeMap<String, TreeSet<Integer>>> invertedIndex
	 * 
	 * addCount(String location, Integer count) --> countMap DONE
	 * addWord(String word, String location, Integer position) --> invertedIndex
	 * 
	 * ...eventually (after working again), think about more generally useful methods
	 * 
	 * 2) InvertedIndexBuilder builder class
	 * 

public static void traverseDirectory(Path directory, InvertedIndex index) throws IOException {
   same code except where you used to add to a list, call processFile
} DONE

public static void processFile(Path path, InvertedIndex index) throws IOException {
	ArrayList<String> stems = FileStemmer.listStems(path);
	index.addCount(path.toString(), stems.size()); DONE!!
	
	for each word...
	   index.addWord(...)
	
	 */
		
	
}
