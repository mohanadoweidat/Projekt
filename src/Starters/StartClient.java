package Starters;

import Controller.ClientController;

import java.awt.*;

public class StartClient
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(() -> {
			new ClientController();
			new ClientController();
		});
	}
}
