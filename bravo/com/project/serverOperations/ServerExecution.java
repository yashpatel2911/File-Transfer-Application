package com.project.serverOperations;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.project.serverConfiguration.ServerDetails;
import com.project.fileOperations.*;

public class ServerExecution {
	
	public static void deCode_Command (String command, 
			DataInputStream dis, DataOutputStream dos) throws IOException {
		
		String[] command_parts = command.split(" ");
		int length = command_parts.length;
		
		System.out.println(command_parts[length-2]+":" + command_parts[length-1]+ "    " 
						 + command_parts[0].toUpperCase() + "    " + command_parts[1] );
		
		if (command_parts[0].equals(ServerDetails.GET)) {
			get_Operation(command_parts[1], dos);
		}
		
		else if (command_parts[0].equals(ServerDetails.PUT)) {
			put_Operation(command_parts[1], dis, dos);
		}
		else {
			dos.writeUTF("Please Enter One of this command.\n"+ ServerDetails.COMMAND_LIST);
			dos.flush();
		}
	
	}
	
	public static void get_Operation (String fileName, DataOutputStream dos) throws IOException {
		
		if (IsFile.checkFile(fileName)) {
			
			dos.writeUTF(ServerDetails.OK);
			dos.flush();
			
			byte[] fileBytes = Read_and_Write.readFile(fileName);
			
			dos.writeInt(fileBytes.length);
			dos.flush();
			
			dos.write(fileBytes);
			dos.flush();
			
		}
		
		else {
			dos.writeUTF(ServerDetails.NOT_EXISTS);
			dos.flush();
		}
		
	}
	
	public static void put_Operation (String fileName, 
			DataInputStream dis, DataOutputStream dos) throws IOException {
		
		boolean condition = IsFile.checkFile(fileName);
		while(condition) {
			
			dos.writeUTF(ServerDetails.EXISTS);
			dos.flush();
			
			dos.writeUTF(ServerDetails.FILE_NAME);
			dos.flush();
			
			fileName = dis.readUTF();
			
			condition = IsFile.checkFile(fileName);
		}
		
		dos.writeUTF(ServerDetails.OK);
		dos.flush();
		
		byte[] fileBytes = new byte[dis.readInt()];
		dis.readFully(fileBytes);
		 
		Read_and_Write.writeFile(fileName, fileBytes);
		
	}

}