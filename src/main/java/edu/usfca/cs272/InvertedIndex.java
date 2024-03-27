package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.TreeSet;

public class InvertedIndex {

	private static TreeMap<String, Integer> countMap;
	private static TreeMap<String, TreeMap<String, TreeSet<Integer>>> invertedIndex;
	
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
		TreeMap<String, TreeSet<Integer>> locAndCountMap = invertedIndex.get(stem);
		if (locAndCountMap != null) {
			TreeSet<Integer> indexes = locAndCountMap.get(location);
			if (indexes != null) {
				indexes.add(count);
				locAndCountMap.put(location, indexes);
			} else {
				TreeSet<Integer> newIntegers = new TreeSet<Integer>();
				newIntegers.add(count);
				locAndCountMap.put(location, newIntegers);
			}
		} else {
			TreeSet<Integer> indexes = new TreeSet<Integer>();
			indexes.add(count);
			TreeMap<String, TreeSet<Integer>> newLocAndCountMap = new TreeMap<String, TreeSet<Integer>>();
			newLocAndCountMap.put(location, indexes);
			invertedIndex.put(stem, newLocAndCountMap);
		}
	}
	
	static void addCount(String path, Integer size) {
		if (size != 0) {
			countMap.put(path, size);
		}
	}
}
