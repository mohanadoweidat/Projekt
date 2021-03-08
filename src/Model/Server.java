package Model;

import Controller.ServerController;
import Controller.ServerMessageObject;
import Model.Shared.Message;
import Model.Shared.SynchronizedHashSet;
import Model.Shared.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server extends Thread
{
	private ServerController controller;
	//Client controller socket
	private Socket ccSocket;
	private SynchronizedHashSet<Message> unsentMessages;


	private ServerSocket serverSocket;
	private int port;

	public static Set<User> onlineUsers = new HashSet<User>();


	public Server(ServerController controller, int port) throws IOException
	{
		this.controller = controller;
		this.port = port;
		serverSocket = new ServerSocket(port);
		controller.getServerView().serverMessageBoardAppend("Message Server running..");
		controller.getServerView().serverMessageBoardAppend("Vi kör på port:" + port);
		controller.getServerView().serverMessageBoardAppend("------------------------------------");
		controller.getServerView().serverMessageBoardAppend("Waiting for the clients...");
		this.ccSocket = serverSocket.accept();
		new ClientControllerHandler(ccSocket);
	}


	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				Socket socket = serverSocket.accept();
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

		}
	}

	private class ClientControllerHandler extends Thread
	{
		Socket socket;
		ObjectInputStream ois;
		ObjectOutputStream oos;

		public ClientControllerHandler(Socket socket)
		{
			this.socket = socket;

			try
			{
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());

			}
			catch (Exception e)
			{

			}

		}

		@Override
		public void run()
		{
			while (!this.isInterrupted())
			{
				if (!socket.isConnected())
				{
					this.interrupt();
				}
				try
				{
					ServerMessageObject o = (ServerMessageObject) ois.readObject();
					switch (o.getType())
					{
						case CREATE_USER:
							User user = (User) o.getObject();
							onlineUsers.add(user);
							break;
						case VERFY_NAME:
							String uName = (String) o.getObject();
							for (User uc : onlineUsers)
							{
								if (uc.getUsername().equals(uName))
								{
									oos.writeBoolean(true);
								}
							}
							oos.writeBoolean(false);
							break;
						default:

					}
				}
				catch (java.io.IOException | java.lang.ClassNotFoundException e)
				{
					e.printStackTrace();
				}

			}
		}
	}
}
