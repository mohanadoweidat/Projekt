package Controller;

import Model.Server;
import View.server.ServerView;

import java.io.IOException;

public class ServerController
{
	private Server server;
	private ServerView serverView;

	public ServerController() throws IOException
	{
		 serverView = new ServerView(this);
		 server = new Server(this, 3500);
	}


	public ServerView getServerView()
	{
		return serverView;
	}
}
