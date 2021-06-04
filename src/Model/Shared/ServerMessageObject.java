package Model.Shared;

import java.io.Serializable;

/**
 * This class is used  for communication between the client and the server.
 */
public class ServerMessageObject implements Serializable
{
	private MessageType type;
	private Object object;

	public ServerMessageObject(MessageType type, Object object)
	{
		this.type = type;
		this.object = object;
	}


	public MessageType getType()
	{
		return type;
	}
	public Object getObject()
	{
		return object;
	}
	public void setType(MessageType type)
	{
		this.type = type;
	}
	public void setObject(Object object)
	{
		this.object = object;
	}

	@Override
	public String toString()
	{
		return object.toString() + " - " + type.name();
	}
}
