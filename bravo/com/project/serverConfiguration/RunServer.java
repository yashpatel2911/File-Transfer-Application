package com.project.serverConfiguration;

import java.net.ServerSocket;

public class RunServer {
	
	private ServerSocket ss;
	
	public RunServer() {
	}
	
	public ServerSocket setServerSocket(int port) throws Exception {
		
		ss = new ServerSocket(port);
		
		return ss;
	}

}
