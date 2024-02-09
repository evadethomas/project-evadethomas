package edu.usfca.cs272;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class getJsonFile {
	
	
	public getJsonFile(String[] args) throws IOException {
		ArgumentParser test = new ArgumentParser(args);
		HashMap<String, String> map = test.getMap();
		
		HashMap<String, Integer> countMap = new HashMap<String, Integer>();
		
		
		for (Entry<String, String> entry : map.entrySet()) {
			
		    String key = entry.getKey();
		    String value = entry.getValue();
		    int finalCount = 0;
		    
		    if (key == "-text") {
		    	countMap.put(value, getCount(value));
		    	System.out.println(countMap);
		    } else if (key == "-counts") {
		    	Path newFilePath = Paths.get(value);
		    	JsonWriter.writeObject(countMap, newFilePath);
		    	
		    	}
		    	
		    }
		    //CITE: guidance on counting without splitting from Stack Overflow
		    
		   
		    
		}

	private int getCount(String value) {
		File file = new File(value);
		int wordCount = 0;
	    try (Scanner scan = new Scanner(file)) {
	    	while (scan.hasNext()) {
	    		scan.next();
	    		wordCount++;
	    	}
	    	
	    //System.out.println(wordCount);
	    
	    } catch (Exception e) {
	    	System.out.println("File not found: " + value);
	    }
	    return wordCount;
	}
		
	
}
