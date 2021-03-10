package Controller;

import Model.server.Server;
import View.server.LoggerView;
import View.server.ServerView;

import javax.swing.*;
import java.io.IOException;

public class ServerController
{
	private Server server;
	private ServerView serverView;
	private LoggerView loggerView;


	public ServerController() throws IOException
	{
		serverView = new ServerView(this);
		loggerView = new LoggerView(this);

		server = new Server(this, 3500);
	}


	public ServerView getServerView()
	{
		return serverView;
	}

	public void closeALl()
	{
		serverView.dispose();
		loggerView.dispose();
		loggerView.getQuitButton().dispose();
		server.interrupt();
		server.saveServer();
	}

}
