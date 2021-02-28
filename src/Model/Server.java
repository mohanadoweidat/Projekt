package Model;

import Controller.ClientController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread
{
	private int serverPort;
	public int getServerPort()
	{
		return serverPort;
	}

	private ArrayList<ServerWorker> workersList = new ArrayList<>();
	public ArrayList<ServerWorker> getWorkersList()
	{
		return workersList;
	}

	private ClientController clientController;
	public ClientController getClientController()
	{
		return clientController;
	}

	public Server(ClientController clientController, int serverPort){
		this.clientController = clientController;
		this.serverPort = serverPort;
	}


	@Override
	public void run(){
		try
		{
			ServerSocket serverSocket = new ServerSocket(serverPort);
			System.out.println("Waiting for clients request..");
			while (true){
				try (Socket clientSocket = serverSocket.accept()) {
					System.out.println("Taking request from:" + clientSocket);
					ServerWorker worker = new ServerWorker(this, clientSocket);
					workersList.add(worker);
					worker.start();
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	public void removeWorker(ServerWorker serverWorker) {
		workersList.remove(serverWorker);
	}

}
