package Model.Shared;

import javax.swing.*;
import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable, Comparable {
    private String message;
    private ImageIcon imageIcon;
    private User sender;
    private User receiver;
    private Date timeStamp;

    public Message(String message, ImageIcon image, User sender, User receiver) {
        this.message = message;
        this.imageIcon = image;
        this.sender = sender;
        this.receiver = receiver;
        this.timeStamp = new Date(System.currentTimeMillis());
    }

    @Override
    public int compareTo(Object o) {
        return this.timeStamp.compareTo(((Message) o).timeStamp);
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ImageIcon getImage() {
        return imageIcon;
    }

    public void setImage(ImageIcon image) {
        this.imageIcon = image;
    }

    @Override
    public String toString() {

        String messageText;
        if (receiver != null)
            messageText = sender.getUsername() + " > " + message;
        else
            messageText = sender.getUsername() + " > " + message + " (Sent to Everyone)";

        return messageText;
    }
}
