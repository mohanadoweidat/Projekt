package Starters;

import Controller.ServerController;

import java.awt.*;
import java.io.IOException;

public class StartServer
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					ServerController serverController = new ServerController();

				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
