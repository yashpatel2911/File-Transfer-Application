package com.project.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.project.serverConfiguration.*;

public class StartServer {

	static ServerSocket s;
	static Socket s1;
	static DataInputStream dis;
	static DataOutputStream dos;
	static String message;
	
	public static void main(String[] args) throws Exception {
		
		System.out.println(ServerDetails.BRAVO);
		RunServer r = new RunServer();
		s = r.setServerSocket(ServerDetails.PORT);
		
		System.out.println("Server Stared Successfully");
		
		while(true)
		{
			Socket s1 = s.accept();
			new ClientInstances(s1).start();
		}

	}

}
