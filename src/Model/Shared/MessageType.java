package Model.Shared;

import java.io.Serializable;

/**
 * This class is used  for communication between the client and the server.
 */
public enum MessageType implements Serializable
{

	VERFY_NAME, CREATE_USER, SEND_MESSAGE, NEW_USER_CONNECTION, USER_DISCONNECTED;



}
