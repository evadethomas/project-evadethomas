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
		
		
		for (Entry<String, String> entry : map.entrySet()) {
			
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
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

	private int getCount(String value) throws IOException {
		Path newFilePath = Paths.get(value);
		ArrayList<String> stems = FileStemmer.listStems(newFilePath);
		System.out.println("stems" + stems);
		
	    return stems.size();
	}
		
	
}
