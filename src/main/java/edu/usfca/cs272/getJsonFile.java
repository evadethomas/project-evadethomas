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

/*
 * TODO Clean up
 */

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
		
		//boolean hasIndex = false;
		
		String text = map.get("-text");
		String counts = map.get("-counts");
		String index = map.get("-index");
		
		
		
		if (text != null) {
			checkDirectory(Paths.get(text));
		} else {
			hasText = false;
		}
		
		if (counts != null) {
			writeJson(Paths.get(counts));
		} else {
			writeJson(Paths.get("counts.json"));
		}
		
		if (index != null) {
			writeInvertedIndex(Paths.get(index));
		} else {
			writeInvertedIndex(Paths.get("index.json"));
		}
	}
		
		
		//FOR TOMORROW EVA: SIMPLIFY THIS DOWN AND GET IT WORKING SO ITS EASIER TO TRANSFER
//		
//		for (Entry<String, String> entry : map.entrySet()) {
//			
//		    String key = entry.getKey();
//		    String value = entry.getValue();
//		    
//		    if (key == "-text" && value != null) {
//		    	
//		    	File dirTest = new File(value);
//		    	if (dirTest.isDirectory() == true) {
//		    		
//		    		ArrayList<Path> paths = new ArrayList<>();
//		    		
//		    		traverseDirectory(Paths.get(value), paths);
//		    		
//		    		for(Path path : paths) {
//		    			
//		    			processFile(path, countMap, invertedIndex);	
//		    			
//		    		}
//		    		
//		    	} else {
//		    		Path path = Paths.get(value);
//		    		
//		    		processFile(path, countMap, invertedIndex);
//		    	}
//		    	
//		    	hasText = true;
//		    } else if (key == "-counts") {
//		    	
//			    	if (value == null) {
//			    		
//			    		Path currentPath = Paths.get("counts.json");
//			    		
//			    		if (hasText == true) {
//			    			JsonWriter.writeObject(countMap, currentPath);
//			    			//System.out.println("invertedIndex: " + invertedIndex);
//				    	} else {
//			    			JsonWriter.writeObject(emptyMap, currentPath);
//			    		}
//			    		
//			    	} else {
//			    		Path newFilePath = Paths.get(value);
//				    	JsonWriter.writeObject(countMap, newFilePath);
//				    	//System.out.println("invertedIndex: " + invertedIndex);
//			    	}
//			    	
//		    } else if (key == "-index") {
//		    	
//		    	hasIndex = true;
//		    	System.out.println("is true");
//		    	
//		    }
		    
	    	
//		    	
//		    } 
//		
//		
//		
//	}
//		



//		String indexFileName = map.get("-index");
//	    
//	    if (indexFileName == null) {
//	    	if (hasIndex == true) {
//		    	Path defaultIndexPath = Paths.get("index.json");
//		    	JsonWriter.writeObjectNested(invertedIndex, defaultIndexPath);
//	    	}
//	    } else {
//	    	
//		    	Path indexPath = Paths.get(indexFileName);
//		    	JsonWriter.writeObjectNested(invertedIndex, indexPath);
//	    }
//		
//	}
	
	
//}

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
		
		if (checkValidFile(path) != true) {
			return;
		}
		
		ArrayList<String> stems = FileStemmer.listStems(path);
	
		if (stems.size() != 0) {
			countMap.put(path.toString(), stems.size());
		}
		
		int count = 1;
		 
		for (String stem : stems) {
			// TODO Move this logic into the inverted index add method
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
	
	private static boolean checkValidFile(Path path) {
		return (path.toString().toLowerCase().endsWith(".txt") || path.toString().toLowerCase().endsWith(".text")) && Files.isRegularFile(path);
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
						processFile(path);
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
	 * addCount(String location, Integer count) --> countMap
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
