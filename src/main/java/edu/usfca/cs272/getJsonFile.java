package edu.usfca.cs272;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
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
		
		System.out.println(countMap);
		System.out.println("map" + map);
		boolean hasText = false;
		
		for (Entry<String, String> entry : map.entrySet()) {
			
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    if (key == "-text" && value != null) {
		    	
		    	File dirTest = new File(value);
		    	if (dirTest.isDirectory() == true) {
		    		ArrayList<Path> paths = new ArrayList<>();
		    		traverseDirectory(Paths.get(value), countMap, paths);
		    		for(Path path : paths) {
		    			int count  = getCount(path.toString());
		    			if (count != 0) {
		    				countMap.put(path.toString(), count);
		    			}
		    			
		    		}
		    	} else {
		    		int count = getCount(value);
		    		if (count != 0) {
			    		countMap.put(value, count);
			    	}
		    	}
		    	
		    	
		    	
		    	System.out.println(countMap);
		    	hasText = true;
		    } else if (key == "-counts") {
		    		System.out.println("hello");
			    	if (value == null) {
			    		Path currentPath = Paths.get("counts.json");
			    		if (hasText == true) {
			    			JsonWriter.writeObject(countMap, currentPath);
				    	} else {
			    			JsonWriter.writeObject(emptyMap, currentPath);
			    		}
			    	} else {
			    		Path newFilePath = Paths.get(value);
				    	JsonWriter.writeObject(countMap, newFilePath);
			    	}
		    	}
		    	
		    }
		    //CITE: guidance on counting without splitting from Stack Overflow
		    
		   
		    
		}

	private int getCount(String value) throws IOException {
		Path newFilePath = Paths.get(value);
		ArrayList<String> stems = FileStemmer.listStems(newFilePath);
	    return stems.size();
	}
	
	private static void traverseDirectory(Path directory, TreeMap<String, Integer> countMap, ArrayList<Path> paths) throws IOException {
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
					System.out.println(path.toString() + "/");
					System.out.println("path" + path);
					traverseDirectory(path, countMap, paths);
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
