package com.project.fileOperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Read_and_Write {
	
	public static void writeFile (String fileName, byte[] fileBytes) 
			throws FileNotFoundException, IOException {
		
		FileOutputStream writeFile = new FileOutputStream(fileName);
		writeFile.write(fileBytes);
		writeFile.flush();
		writeFile.close();
			
	}
	
	public static byte[] readFile (String fileName)
			throws FileNotFoundException, IOException {
		
		File file = new File(fileName);
		FileInputStream readFile = new FileInputStream(file);
		byte[] fileBytes = new byte[(int) file.length()];
		readFile.read(fileBytes);
		readFile.close();
		
		return fileBytes;
		
	}

}
