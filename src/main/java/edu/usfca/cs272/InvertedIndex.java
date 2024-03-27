package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.TreeSet;

public class InvertedIndex {

	private static TreeMap<String, Integer> countMap;
	private static TreeMap<String, TreeMap<String, TreeSet<Integer>>> invertedIndex;
	private static boolean hasText = true;
	
	public InvertedIndex() {
		invertedIndex = new TreeMap<String, TreeMap<String, TreeSet<Integer>>>();
		countMap = new TreeMap<String, Integer>();
	}
	
	void writeInvertedIndex(Path indexFilePath) throws IOException {
		JsonWriter.writeObjectNested(invertedIndex, indexFilePath);
	}
	
	void writeJson(Path counts, boolean hasText) throws IOException {
		if (hasText == true) {
			JsonWriter.writeObject(countMap, counts);
    	} else {
			JsonWriter.writeObject(new TreeMap<String, Integer>(), counts);
		}
	}
	
	static void addWord(String stem, String location, Integer count) {
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
	
	static void addCount(String path, Integer size) {
		if (size != 0) {
			countMap.put(path, size);
		}
	}

	
	
	
	
}
