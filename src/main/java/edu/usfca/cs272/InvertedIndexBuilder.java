package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class InvertedIndexBuilder {

	public static void checkDirectory(Path rootFile) throws IOException {
		if (Files.isDirectory(rootFile) == true) {
			traverseDirectory(rootFile);
		} else {
			processFile(rootFile);
		}

	}

	static public boolean checkValidFile(Path path) {
		return path.toString().toLowerCase().endsWith(".txt") || path.toString().toLowerCase().endsWith(".text");
	}

	public static void processFile(Path path) throws IOException {
		final ArrayList<String> stems = FileStemmer.listStems(path);
		InvertedIndex.addCount(path.toString(), stems.size());
		int count = 1;
		for (final String stem : stems) {
			InvertedIndex.addWord(stem, path.toString(), count);
			count += 1;
		}
	}

	public static void traverseDirectory(Path directory) throws IOException {
		try (DirectoryStream<Path> fileList = Files.newDirectoryStream(directory)) {
			for (final Path path : fileList) {
				if (Files.isDirectory(path)) {
					traverseDirectory(path);
				} else if (checkValidFile(path)) {
					processFile(path);
				}
			}
		}

	}

	public InvertedIndexBuilder(Path path) throws IOException {
		checkDirectory(path);
	}

}
