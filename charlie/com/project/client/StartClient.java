package com.project.client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

import com.project.clientConfiguration.ServerDetails;
import com.project.clientOperations.ClientExecution;

public class StartClient {
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("This is Charlie.");
		Socket s1 = new Socket("localhost",3333);
		
		System.out.println("Client started successfully.");
		
		DataInputStream dis = new DataInputStream(s1.getInputStream());
		DataOutputStream dos = new DataOutputStream(s1.getOutputStream());
		Scanner s = new Scanner(System.in);
		
		String command = "";
		
		System.out.print(dis.readUTF());
		
		while(true)
		{
			command = s.nextLine();
			
			if(command.equals(ServerDetails.QUIT)) {
				dos.writeUTF(command);
				System.out.println(dis.readUTF());
				break;
			}
			
			ClientExecution.deCode_command(command, dis, dos);
			System.out.print(ServerDetails.TERMINAL);
		}
		
		s.close();
		dis.close();
		dos.close();
		s1.close();
	}

}
