package edu.usfca.cs272;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;


public class getJsonFile {
	
	public getJsonFile(String[] args) throws IOException {
		
		ArgumentParser test = new ArgumentParser(args);
		HashMap<String, String> map = test.getMap();
		
		TreeMap<String, Integer> countMap = new TreeMap<String, Integer>();
		
		HashMap<String, Integer> emptyMap = new HashMap<String, Integer>();
		
		TreeMap<String, TreeMap<String, TreeSet<Integer>>> invertedIndex = new TreeMap<String, TreeMap<String, TreeSet<Integer>>>();
		
		
		boolean hasText = false;
		boolean hasIndex = false;
		
		for (Entry<String, String> entry : map.entrySet()) {
			
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    if (key == "-text" && value != null) {
		    	
		    	File dirTest = new File(value);
		    	if (dirTest.isDirectory() == true) {
		    		
		    		ArrayList<Path> paths = new ArrayList<>();
		    		
		    		traverseDirectory(Paths.get(value), paths);
		    		
		    		for(Path path : paths) {
		    			
		    			processFile(path, countMap, invertedIndex);	
		    			
		    		}
		    		
		    	} else {
		    		Path path = Paths.get(value);
		    		
		    		processFile(path, countMap, invertedIndex);
		    	}
		    	
		    	hasText = true;
		    } else if (key == "-counts") {
		    	
			    	if (value == null) {
			    		
			    		Path currentPath = Paths.get("counts.json");
			    		
			    		if (hasText == true) {
			    			JsonWriter.writeObject(countMap, currentPath);
			    			//System.out.println("invertedIndex: " + invertedIndex);
				    	} else {
			    			JsonWriter.writeObject(emptyMap, currentPath);
			    		}
			    		
			    	} else {
			    		Path newFilePath = Paths.get(value);
				    	JsonWriter.writeObject(countMap, newFilePath);
				    	//System.out.println("invertedIndex: " + invertedIndex);
			    	}
			    	
		    } else if (key == "-index") {
		    	
		    	hasIndex = true;
		    	System.out.println("is true");
		    	
		    }
		    
	    	
		    	
		    } 
		String indexFileName = map.get("-index");
	    
	    if (indexFileName == null) {
	    	if (hasIndex == true) {
		    	Path defaultIndexPath = Paths.get("index.json");
		    	JsonWriter.writeObjectNested(invertedIndex, defaultIndexPath);
	    	}
	    } else {
	    	
		    	Path indexPath = Paths.get(indexFileName);
		    	JsonWriter.writeObjectNested(invertedIndex, indexPath);
	    }
		
	}
	
	private void processFile(Path path, TreeMap<String, Integer> countMap, TreeMap<String, TreeMap<String, TreeSet<Integer>>> invertedIndex) throws IOException {
		
		ArrayList<String> stems = FileStemmer.listStems(path);
		if (stems.size() != 0) {
			countMap.put(path.toString(), stems.size());
		}
		
		int count = 1;
		//System.out.println(stems);
		 
		for (String stem : stems) {
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
				
				
				
				
				
				//System.out.println(integers);
			} else {
				//System.out.println(stem);
				TreeSet<Integer> integers = new TreeSet<Integer>();
				integers.add(count);
				TreeMap<String, TreeSet<Integer>> fileAndInteger = new TreeMap<String, TreeSet<Integer>>();
				fileAndInteger.put(path.toString(), integers);
				invertedIndex.put(stem, fileAndInteger);
				
			}
			count += 1;
		}
		
		
		
	}
	
	private static void traverseDirectory(Path directory, ArrayList<Path> paths) throws IOException {
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
					
					traverseDirectory(path, paths);
					
				} else {
					if ((path.toString().toLowerCase().endsWith(".txt") || path.toString().toLowerCase().endsWith(".text")) && Files.isRegularFile(path)) {
						paths.add(path);
					}
				    //use getCounts and add continuously
				}
				// note the duplicated logic above with traverse()!
				// avoid the duplicated code by just calling the traverse() method
				// (requires designing it just right to make this possible)
			}
		}
		
	}

		
	
}
