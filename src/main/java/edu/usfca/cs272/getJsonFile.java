package edu.usfca.cs272;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeSet;


public class getJsonFile {
	
	
	public getJsonFile(String[] args) throws IOException {
		
		ArgumentParser test = new ArgumentParser(args);
		HashMap<String, String> map = test.getMap();
		
		HashMap<String, Integer> countMap = new HashMap<String, Integer>();
		
		System.out.println(countMap);
		System.out.println("map" + map);
		boolean hasText = false;
		
		for (Entry<String, String> entry : map.entrySet()) {
			
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    if (key == "-text") {
		    	int count = getCount(value);
		    	if (count != 0) {
		    		countMap.put(value, count);
		    	}
		    	System.out.println(countMap);
		    	hasText = true;
		    } else if (key == "-counts") {
		    	
		    	if (hasText == true) {
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
		System.out.println("stems" + stems);
		System.out.println(stems.size());
	    return stems.size();
	}
		
	
}
