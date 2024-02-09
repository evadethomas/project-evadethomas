package edu.usfca.cs272;

public class getJsonFile {
	
	public getJsonFile(String[] args) {
		ArgumentParser test = new ArgumentParser(args);
		
		test.getMap();
		
		System.out.println(test.getMap());
	}
	
}
