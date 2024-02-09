package edu.usfca.cs272;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class getJsonFile {
	
	
	public getJsonFile(String[] args) throws IOException {
		ArgumentParser test = new ArgumentParser(args);
		HashMap<String, String> map = test.getMap();
		
		Map<Object, Object> countMap = Collections.emptyMap();
		
		
		for (Entry<String, String> entry : map.entrySet()) {
			
		    String key = entry.getKey();
		    String value = entry.getValue();
		    int finalCount = 0;
		    
		    if (key == "text") {
		    	countMap.put(value, getCount(value));
		    } else if (value == "count") {
		    	
		    }
		    //CITE: guidance on counting without splitting from Stack Overflow
			
		}
 
        
		    
		    
		    FileStemmer stemmer = new FileStemmer();
		    
		   
		    
		}

	private int getCount(String value) {
		File file = new File(value);
		int wordCount = 0;
	    try (Scanner scan = new Scanner(file)) {
	    	while (scan.hasNext()) {
	    		scan.next();
	    		wordCount++;
	    	}
	    	
	    System.out.println(wordCount);
	    
	    } catch (Exception e) {
	    	System.out.println("File not found: " + value);
	    }
	    return wordCount;
	}
		
	
}
