package Model.Shared;

import javax.swing.*;
import java.io.Serializable;
import java.util.Date;

/**
 * This class represents messages object in the program.
 */
public class Message implements Serializable, Comparable {
    private String message;
    private ImageIcon imageIcon;
    private User sender;
    private User receiver;
    private Date timeStamp;

    /**
     * Constructor.
     * @param message  Message text.
     * @param image The image.
     * @param sender The sender.
     * @param receiver The receiver.
     */
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

    /**
     * Getters and setters.
     */
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

    /**
     * ToString.
     * @return String.
     */
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
