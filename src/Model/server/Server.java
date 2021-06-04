package Model.server;

import Model.Shared.MessageType;
import Controller.ServerController;
import Model.Shared.ServerMessageObject;
import Model.Shared.Message;
import Model.Shared.SynchronizedHashSet;
import Model.Shared.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Server extends Thread
{
	private ServerController controller;
	private SynchronizedHashSet<Message> unsentMessages;
	private HashSet<Logg> logs;
	private ServerSocket serverSocket;
	private int port;
	public static Map<User, ClientControllerHandler> onlineUsers = new HashMap();


	/**
	 * Constructor.
	 * @param controller Controller.
	 * @param port Port number.
	 * @throws IOException
	 */
	public Server(ServerController controller, int port) throws IOException
	{
		this.controller = controller;
		this.port = port;
		serverSocket = new ServerSocket(port);
		logs = new HashSet<>();

		this.unsentMessages = new SynchronizedHashSet<>();
		controller.getServerView().serverMessageBoardAppend("Message Server running..");
		controller.getServerView().serverMessageBoardAppend("Vi kör på port:" + port);
		controller.getServerView().serverMessageBoardAppend("------------------------------------");
		controller.getServerView().serverMessageBoardAppend("Waiting for the clients...");

		loadServerFromFile();
		this.start();
	}


	/**
	 * This function will wait for clients to connect to the server.
	 */
	@Override
	public void run()
	{
		while (!this.isInterrupted())
		{
			try
			{
				System.out.println("Looking for client!");
				Socket socket = serverSocket.accept();
				System.out.println("NEW CONNECTION FOUND");
				new ClientControllerHandler(socket);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * This function will load serverFile from the pc.
	 */
	public void loadServerFromFile()
	{
		File dirr = new File("C:\\server_chat");
		if (!dirr.exists())
		{
			dirr.mkdirs();

		}
		else
		{
			File obj = new File(dirr, "info.txt");
			if (obj.exists())
			{
				try
				{
					FileInputStream fi = new FileInputStream(obj);
					ObjectInputStream oi = new ObjectInputStream(fi);
					HashSet<Logg> logs = (HashSet<Logg>) oi.readObject();
					SynchronizedHashSet<Message> unsentMessages = (SynchronizedHashSet<Message>) oi.readObject();
					this.setLogs(logs);
					this.setUnsentMessages(unsentMessages);
					this.controller.getLoggerView().updateLoggList(logs);
				}
				catch (IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This function will save the server with the logs to the pc.
	 */
	public void saveServer()
	{
		File dirr = new File("C:\\server_chat");
		if (!dirr.exists())
		{
			dirr.mkdir();
		}
		try
		{
			FileOutputStream fileOut = new FileOutputStream("C:\\server_chat\\info.txt");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(logs);
			objectOut.writeObject(unsentMessages);

			objectOut.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}


	/**
	 * Getter for getLogs.
	 * @return HashSet<Logg>
	 */
	public HashSet<Logg> getLogs()
	{
		return this.logs;
	}

	/**
	 * Setter for HashSet<Logg> logs.
	 * @param logs the logs.
	 */
	public void setLogs(HashSet<Logg> logs)
	{
		this.logs = logs;
	}

	/**
	 * Getter for SynchronizedHashSet<Message>
	 * @return SynchronizedHashSet<Message>
	 */
	public SynchronizedHashSet<Message> getUnsentMessages()
	{
		return this.unsentMessages;
	}

	/**
	 * Setter for SynchronizedHashSet<Message>
	 * @param messages SynchronizedHashSet<Message>
	 */
	public void setUnsentMessages(SynchronizedHashSet<Message> messages)
	{
		this.unsentMessages = messages;
	}

	/**
	 * This class will handle the client.
	 */
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
			this.start();
		}

		/**
		 * This function is for updating the logs and saving it, and connecting the users to the server and saving
		 * unsent messages. Checking if, the username already exist in the server.
		 * Disconnect the  user and send messages to other users.
		 */
		@Override
		public void run()
		{
			System.out.println("Running client socket");
			while (!this.isInterrupted())
			{
				if (!socket.isConnected())
				{
					this.interrupt();
				}
				try
				{
					if (socket.getInputStream().available() == 0)
					{
						continue;
					}
					ServerMessageObject o = (ServerMessageObject) ois.readObject();
					Logg logg = new Logg(o);
					logs.add(logg);
					controller.getLoggerView().updateLoggList(logs);
					switch (o.getType())
					{
						case CREATE_USER:
							User user = (User) o.getObject();
							onlineUsers.put(user, this);
							controller.getServerView().addUserToList(user.getUsername());
							controller.getServerView().serverMessageBoardAppend("New user connected: " + user.getUsername());
							updateClients(new ServerMessageObject(MessageType.NEW_USER_CONNECTION, new HashSet<>(onlineUsers.keySet())));
							readUnsentMessage(user);
							break;
						case VERFY_NAME:
							String uName = (String) o.getObject();
							boolean bool = false;
							for (User uc : onlineUsers.keySet())
							{
								if (uc.getUsername().equals(uName))
								{
									bool = true;
								}
							}
							oos.writeObject(new ServerMessageObject(MessageType.VERFY_NAME, bool));
							oos.flush();
							break;
						case USER_DISCONNECTED:
							User u = (User) o.getObject();
							onlineUsers.remove(u);
							controller.getServerView().removeUserFromList(u.getUsername());
							controller.getServerView().serverMessageBoardAppend("User disconnected: " + u.getUsername());
							updateClients(new ServerMessageObject(MessageType.NEW_USER_CONNECTION,
									new HashSet<>(onlineUsers.keySet())));
						case SEND_MESSAGE:
							Message message = (Message) o.getObject();
							if (message.getReceiver() == null)
							{
								updateClients(o);
								continue;
							}
							User reciver = getUserByName(message.getReceiver().getUsername());
							ClientControllerHandler reciverHandler = onlineUsers.get(reciver);

							if (reciver == null)
							{
								controller.getServerView().serverMessageBoardAppend("User: " + message.getReceiver().getUsername() + " is not currently online saving message");
								unsentMessages.add(message);
							}
							else
							{
								reciverHandler.update(o);
							}
					}
				}
				catch (java.io.IOException | java.lang.ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}

		/**
		 * This function is for updating server objects.
		 * @param object Server message object.
		 */
		public void update(ServerMessageObject object)
		{
			try
			{
				System.out.println(object.getType());
				oos.writeObject(object);
				oos.flush();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * This function is for reading unsent messages for offline users.
	 * @param user the user.
	 */
	public void readUnsentMessage(User user)
	{
		for (Message value : unsentMessages.getValues())
		{
			if (value.getReceiver().getUsername().equals(user.getUsername()))
			{
				onlineUsers.get(user).update(new ServerMessageObject(MessageType.SEND_MESSAGE, value));
				unsentMessages.remove(value);
			}
		}
	}

	/**
	 * This function is for updating the online users.
	 * @param object server message object.
	 */
	public void updateClients(ServerMessageObject object)
	{
		for (User user : onlineUsers.keySet())
		{
			ClientControllerHandler cch = onlineUsers.get(user);
			cch.update(object);
		}
	}

	/**
	 * This function is for getting a specific user by name.
	 * @param userName the user username.
	 * @return User object.
	 */
	public User getUserByName(String userName)
	{
		for (User u : onlineUsers.keySet())
		{
			if (userName.equals(u.getUsername()))
			{
				return u;
			}
		}
		return null;
	}
}


