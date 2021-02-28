package Model;

import java.io.IOException;
import java.net.Socket;

public class ServerWorker extends Thread
{
	private Server server;
	private Socket clientSocket;
	public ServerWorker(Server server, Socket clientSocket)
	{
		this.server = server;
		this.clientSocket = clientSocket;
	}


	@Override
	public void run(){
		 handleClientSocket();
	}

	private void handleClientSocket(){

	}
}
