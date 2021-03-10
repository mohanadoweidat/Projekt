package Starters;

import Controller.ClientController;
import Model.Shared.User;

import java.io.IOException;

public class StartClient3
{
	public static void main(String[] args) throws IOException
	{
		new ClientController();
	/*	User u = User.getUserFromFile();
		if (u == null){
		}else{
			new ClientController(u);
		}*/
	}
}
