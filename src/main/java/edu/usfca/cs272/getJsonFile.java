package edu.usfca.cs272;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class getJsonFile {
	
	
	private static TreeMap<String, Integer> countMap;
	
	private static HashMap<String, Integer> emptyMap;
	
	private static TreeMap<String, TreeMap<String, TreeSet<Integer>>> invertedIndex;
	
	private static boolean hasText = true;
	
	public getJsonFile(String[] args) throws IOException {
		
		countMap = new TreeMap<String, Integer>();
		emptyMap = new HashMap<String, Integer>();
		invertedIndex = new TreeMap<String, TreeMap<String, TreeSet<Integer>>>();
		
		ArgumentParser test = new ArgumentParser(args);
		HashMap<String, String> map = test.getMap();
		
		String text = map.get("-text");
		String counts = map.get("-counts");
		String index = map.get("-index");
		
		boolean containsCounts = map.containsKey("-counts");
		hasText = map.containsKey("-text");
		boolean containsIndex = map.containsKey("-index");
		
		if (text != null) {
			checkDirectory(Paths.get(text));
		}
		
		if (counts != null) {
			writeJson(Paths.get(counts));
		} else if (containsCounts == true) {
			writeJson(Paths.get("counts.json"));
		}
		
		if (index != null) {
			writeInvertedIndex(Paths.get(index));
		} else if (containsIndex != false) {
			writeInvertedIndex(Paths.get("index.json"));
		}
	}

	private void writeInvertedIndex(Path indexFilePath) throws IOException {
		JsonWriter.writeObjectNested(invertedIndex, indexFilePath);
	}
	
	private void writeJson(Path counts) throws IOException {
		if (hasText == true) {
			JsonWriter.writeObject(countMap, counts);
    	} else {
			JsonWriter.writeObject(emptyMap, counts);
		}
	}

	private void checkDirectory(Path originalFile) throws IOException {
		
    	if (Files.isDirectory(originalFile) == true) {
    		traverseDirectory(originalFile);
    	} else {
    		processFile(originalFile);
    	}
    	
	} 

	private static void processFile(Path path) throws IOException  {
		
		ArrayList<String> stems = FileStemmer.listStems(path);
		
		addCount(path.toString(), stems.size());
		
		int count = 1;
		 
		for (String stem : stems) {
			// TODO Move this logic into the inverted index add method
			
			//All of this can b
			TreeMap<String, TreeSet<Integer>> innerMap = invertedIndex.get(stem);
			if (innerMap != null) {
				
				TreeSet<Integer> integers = innerMap.get(path.toString());
				if (integers != null) {
					integers.add(count);
					innerMap.put(path.toString(), integers);
				} else {
					TreeSet<Integer> newIntegers = new TreeSet<Integer>();
					newIntegers.add(count);
					innerMap.put(path.toString(), newIntegers);
				}
			} else {
				TreeSet<Integer> integers = new TreeSet<Integer>();
				integers.add(count);
				TreeMap<String, TreeSet<Integer>> fileAndInteger = new TreeMap<String, TreeSet<Integer>>();
				fileAndInteger.put(path.toString(), integers);
				invertedIndex.put(stem, fileAndInteger);
				
			}
			count += 1;
		}
		
		
		
	}
	
	
	
	private static void addCount(String path, Integer size) {
		if (size != 0) {
			countMap.put(path, size);
		}
	}
	
	private static boolean checkValidFile(Path path) {
		return path.toString().toLowerCase().endsWith(".txt") || path.toString().toLowerCase().endsWith(".text");
	}


	private static void traverseDirectory(Path directory) throws IOException {
		/*
		 * The try-with-resources block makes sure we close the directory stream when
		 * done, to make sure there aren't any issues later when accessing this
		 * directory.
		 *
		 * Note, however, we are still not catching any exceptions. This type of try
		 * block does not have to be accompanied with a catch block. (You should,
		 * however, do something about the exception.)
		 */
		try (DirectoryStream<Path> listing = Files.newDirectoryStream(directory)) {
			// use an enhanced-for or for-each loop for efficiency and simplicity
			for (Path path : listing) {
				if (Files.isDirectory(path)) {
					traverseDirectory(path);
				} else {
					if (checkValidFile(path)) {
						processFile(path);
					}
					
				    //use getCounts and add continuously
				}
				// note the duplicated logic above with traverse()!
				// avoid the duplicated code by just calling the traverse() method
				// (requires designing it just right to make this possible)
			}
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
}

public static void processFile(Path path, InvertedIndex index) throws IOException {
	ArrayList<String> stems = FileStemmer.listStems(path);
	index.addCount(path.toString(), stems.size());
	
	for each word...
	   index.addWord(...)
	
	 */
		
	
}
