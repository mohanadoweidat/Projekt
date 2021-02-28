package Controller;

import Model.User;
import View.FirstView;

import java.io.*;

public class ClientController
{
	private FirstView firstView;
	private User user;

	public ClientController() throws FileNotFoundException
	{
		firstView = new FirstView(this);
		user = new User();
	}

	public User getUser()
	{
		return user;
	}



	//Check if username exist in the file when signing up
	public boolean checkUserExist( String username) throws IOException
	{
		boolean found = false;
		if (!username.equalsIgnoreCase("")){
			found = readFromFile(username);
			if (found){
				found = true;
			}
		}
		return found;
	}
	// Sava username and image path to the txt.file (user)
	public void writeToFile(String username,String path) throws IOException
	{
		File file = new File("user.txt");
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

		bufferedWriter.write("\n");
		bufferedWriter.write(username);
		bufferedWriter.write("\n");
		bufferedWriter.write(path);
		bufferedWriter.flush();
		bufferedWriter.close();
	}
	// read username and image path to the txt.file (user)
	public boolean readFromFile(String username) throws IOException
	{
		boolean found = false;
		File file = new File("user.txt");
		BufferedReader bufferedReader = new BufferedReader(new FileReader (file));
		String  line;
		while ((line = bufferedReader.readLine()) != null){
			if (line.equalsIgnoreCase(username)){
				found = true;
			}
		}
		return found;
	}
}
