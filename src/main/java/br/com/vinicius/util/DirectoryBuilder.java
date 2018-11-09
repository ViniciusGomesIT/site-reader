package br.com.vinicius.util;

import java.io.File;

public class DirectoryBuilder {
	private static final String ROOT_DIRECTORY = "C:";
	private static final String DIRECTORY_SAPARATOR = "\\";
	
	public String createDirectory(String folderName) {		
		String directory = ROOT_DIRECTORY + DIRECTORY_SAPARATOR + folderName;		
		File folder = new File(directory);
		
		if (!folder.exists()) {			
			folder.mkdirs();			
		}
	
		return directory;		
	}	
}