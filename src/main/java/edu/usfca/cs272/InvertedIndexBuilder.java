package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class InvertedIndexBuilder {
	
	
	public void checkDirectory(Path originalFile) throws IOException {
    	if (Files.isDirectory(originalFile) == true) {
    		traverseDirectory(originalFile);
    	} else {
    		processFile(originalFile);
    	}
    	
	}
	
	public static void traverseDirectory(Path directory) throws IOException {
		try (DirectoryStream<Path> listing = Files.newDirectoryStream(directory)) {
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
	
	static public boolean checkValidFile(Path path) {
		return path.toString().toLowerCase().endsWith(".txt") || path.toString().toLowerCase().endsWith(".text");
	}
	
	public static void processFile(Path path) throws IOException  {
		ArrayList<String> stems = FileStemmer.listStems(path);
		InvertedIndex.addCount(path.toString(), stems.size());
		int count = 1;
		for (String stem : stems) {
			InvertedIndex.addWord(stem, path.toString(), count);
			count += 1;
		}
			
	}
	
	
}
