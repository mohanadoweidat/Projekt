package Model;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable
{

    private HashMap<String, Icon>  userList = new HashMap<>();


	private String username;
	private Icon icon;

	public User(){

	}
	public User(String username, Icon icon){
		this.username = username;
		this.icon  = icon;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public Icon getIcon()
	{
		return icon;
	}

	public void setIcon(Icon icon)
	{
		this.icon = icon;
	}

	public HashMap<String, Icon> getUserList()
	{
		return userList;
	}
}
