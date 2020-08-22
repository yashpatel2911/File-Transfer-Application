package com.project.clientOperations;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.project.clientConfiguration.ServerDetails;
import com.project.fileOperations.IsFile;
import com.project.fileOperations.Read_and_Write;

public class ClientExecution {
	
	static String command;
	
	public static void deCode_command(String command, 
			DataInputStream dis, DataOutputStream dos) throws IOException {
		
		String[] command_parts = command.split(" ");
		
		if (command_parts[0].equals(ServerDetails.GET)) {
			dos.writeUTF(command);
			dos.flush();
			getFile(command_parts[1], dis, dos);
		}
		else if(command_parts[0].equals(ServerDetails.PUT)) {
			putFile(command_parts[0], command_parts[1], dis ,dos);
		}
		else {
			System.out.println(ServerDetails.INVALID_COMMAND);
		}
		
	}
	
	public static void getFile(String fileName, 
			DataInputStream dis, DataOutputStream dos) throws IOException {
		
		String statusCode = dis.readUTF();
		
		if(statusCode.equals(ServerDetails.OK)) {
			
			while(IsFile.checkFile(fileName)) {
				
				System.out.println(fileName + ServerDetails.FILE_EXITS);
				System.out.println(ServerDetails.FILE_NAME);
				
				Scanner sc = new Scanner(System.in);
				fileName = sc.nextLine();
				sc.close();
				
			}
				
			byte[] fileBytes = new byte[dis.readInt()];
			dis.readFully(fileBytes);
			
			Read_and_Write.writeFile(fileName, fileBytes);
			
			System.out.println(ServerDetails.WRITE);
			
		}
		
		
		else {
			
			System.out.println(ServerDetails.FILE_NOT_EXITS_SERVER);
			
		}
		
	}
	
	public static void putFile(String command , String fileName, DataInputStream dis,DataOutputStream dos) throws IOException{
		
		boolean condition = IsFile.checkFile(fileName);
		
		if(condition) {
			
			dos.writeUTF(command + ' ' +fileName);
			dos.flush();
			
			String statusCode = dis.readUTF();
			BufferedReader bur = new BufferedReader(new InputStreamReader(System.in));
			
			while(statusCode.equals(ServerDetails.EXISTS)){
				System.out.println(dis.readUTF());
				
				System.out.print("Enter New File Name:");
				String f = bur.readLine();
				dos.writeUTF(f);
				
				statusCode = dis.readUTF();
			
			}
			

			byte[] fileBytes = Read_and_Write.readFile(fileName);
			
			dos.writeInt(fileBytes.length);
			dos.write(fileBytes);
			dos.flush();
			
			System.out.println("File uploaded Successfully.");
			
		}
		else {
			System.out.println(ServerDetails.FILE_NOT_EXITS);
		}
	}

}