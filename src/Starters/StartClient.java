package Starters;

import Model.Shared.User;

import Controller.ClientController;

import java.awt.*;
import java.io.IOException;

public class StartClient
{
	public static void main(String[] args) throws IOException
	{
		User u = User.getUserFromFile();
		if (u == null){
			new ClientController();
		}else{
			new ClientController(u);
		}
	}
}
