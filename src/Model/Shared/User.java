package Model.Shared;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class User extends Thread implements Serializable {
    @Serial
    private static final long serialVersionUID = 3178179156010420956L;
    private UUID uuid;
    private Set<User> contacts;
    private List<Message> sentMessages;
    private List<Message> receivedMessages;
    private String username;
    //private Icon profilePicture;
    private ImageIcon profilePicture;
    private boolean isLoggedIn;


    public User(String username, ImageIcon profilePicture) {
        this.receivedMessages = new ArrayList<>();
        this.sentMessages = new ArrayList<>();
        this.contacts = new HashSet<>();
        uuid = UUID.randomUUID();
        this.username = username;
        this.profilePicture = profilePicture;
        isLoggedIn = false;
    }

    public static User getUserFromFile() {
        File dirr = new File("C:\\client_chat");

        if (!dirr.exists()) {
            return null;
        } else {
            File obj = new File(dirr, "info.txt");
            if (obj.exists()) {
                try {
                    FileInputStream fi = new FileInputStream(obj);
                    ObjectInputStream oi = new ObjectInputStream(fi);

                    return (User) oi.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }

        }
        return null;
    }


    public List<Message> getMessagesFromUserInOrder(User user) {
        List<Message> messages = new ArrayList<>();
        for (Message message : getReceivedMessages()) {
            if (message.getSender() == user) {
                messages.add(message);
            }
        }
        for (Message message : getSentMessages()) {
            if (message.getReceiver() == user) {
                messages.add(message);
            }
        }

        Collections.sort(messages);

        return messages;
    }

    public Message[] getAllMessageInOrder() {
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(receivedMessages);
        Collections.reverse(allMessages);
        Message[] array = new Message[allMessages.size()];

        allMessages.toArray(array);

        return array;
    }


    public void saveUser() {
        File dirr = new File("C:\\client_chat");

        if (!dirr.exists()) {
            dirr.mkdir();
        }
        try {
            FileOutputStream fileOut = new FileOutputStream("C:\\client_chat\\info.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addContact(User user) {
        contacts.add(user);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Set<User> getContacts() {
        return contacts;
    }

    public void setContacts(Set<User> contacts) {
        this.contacts = contacts;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ImageIcon getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ImageIcon profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
