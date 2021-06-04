package Model.server;

import Model.Shared.ServerMessageObject;

import java.io.Serializable;
import java.util.Date;

/**
 * This class is for Logg.
 */
public class Logg implements Comparable, Serializable
{
	private Date date;
	private ServerMessageObject serverMessageObject;

	public Logg(ServerMessageObject smo)
	{
		this.serverMessageObject = smo;
		this.date = new Date(System.currentTimeMillis());
	}

	public Date getDate()
	{
		return date;
	}

	public ServerMessageObject getSmo()
	{
		return serverMessageObject;
	}

	@Override
	public int compareTo(Object o)
	{
		Logg val = (Logg) o;
		return date.compareTo(val.date);
	}
	public String toString(){
		return date.toString() + ": " + serverMessageObject.toString();
	}
}