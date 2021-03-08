package Controller;

import Model.Shared.User;
import View.client.SignUpView;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ClientController
{
	private SignUpView loginView;
	private Socket s;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outStream;


	public ClientController()
	{
		loginView = new SignUpView(this);
		try
		{
			s = new Socket("localhost", 3500);
			inputStream = new ObjectInputStream(s.getInputStream());
			outStream = new ObjectOutputStream(s.getOutputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// Check if the user is already connected to the server
	public boolean CheckIfUserExists(String username) throws IOException
	{
		outStream.writeObject(new ServerMessageObject(MessageType.VERFY_NAME, username));
		outStream.flush();
		return inputStream.readBoolean();
	}

	public User createUser(String usernameText, ImageIcon icon)
	{

		User user = new User(usernameText, icon);
		try
		{
			outStream.writeObject(new ServerMessageObject(MessageType.CREATE_USER, user));
			outStream.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return user;
	}
}
