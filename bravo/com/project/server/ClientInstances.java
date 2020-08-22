package com.project.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.project.serverConfiguration.ServerDetails;
import com.project.serverOperations.ServerExecution;

public class ClientInstances extends Thread {
	
	private Thread t;
	
	Socket s;
	
	ClientInstances(Socket s) {
		this.s = s;
	}
	
	public void run() {
		
		DataInputStream dis;
		DataOutputStream dos;
		try {
			String IPAddress = s.getInetAddress().toString();
			int port = s.getPort();
			
			System.out.println("The client connected from "+ IPAddress +":"+ port);
			dis = new DataInputStream(this.s.getInputStream());
			dos = new DataOutputStream(this.s.getOutputStream());
			
			dos.writeUTF(ServerDetails.MESSAGE + ServerDetails.COMMAND_LIST +ServerDetails.TERMINAL);
			dos.flush();
			
			String command="";
			while(true)
			{
				command = dis.readUTF();
				
				if(command.equals(ServerDetails.QUIT))
				{
					System.out.println("The client disconnected from "+ s.getInetAddress() +":"+s.getPort());
					dos.writeUTF("Good Bye Charlie. See you soon.");
					dos.flush();
					break;
				}
				
				command += " " + IPAddress + " " + port;
				
				ServerExecution.deCode_Command(command, dis, dos);
			}
			
			dis.close();
			dos.close();
			this.s.close();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

}
