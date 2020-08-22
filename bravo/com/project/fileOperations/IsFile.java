package com.project.fileOperations;

import java.io.File;

public class IsFile {
	
	static File f;
	
	public static boolean checkFile(String fileName) {
		
		boolean isFile = false;
		
		f = new File(fileName);
		
		isFile = f.exists();
		
		return isFile;
	}

}
