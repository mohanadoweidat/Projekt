package Controller;

import Model.Shared.Message;
import Model.Shared.User;
import View.client.LoggedInView;
import View.client.SignUpView;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;

public class ClientController extends Thread {

    private ObjectInputStream inputStream;
    private ObjectOutputStream outStream;
    private Socket s;
    private User user;
    private LoggedInView view;
    private SignUpView signUpView;

    public User getUser() {
        return user;
    }

    public ClientController() throws IOException {
        s = new Socket("localhost", 3500);
        inputStream = new ObjectInputStream(s.getInputStream());
        outStream = new ObjectOutputStream(s.getOutputStream());
        signUpView = new SignUpView(this);
    }

    public ClientController(User u) throws IOException {
        this.user = u;
        s = new Socket("localhost", 3500);
        inputStream = new ObjectInputStream(s.getInputStream());
        outStream = new ObjectOutputStream(s.getOutputStream());
        view = new LoggedInView(this);
        outStream.writeObject(new ServerMessageObject(MessageType.CREATE_USER, user));
        outStream.flush();
        this.start();
    }


    @Override
    public void run() {
        try {
            while (true) {
                if (s.getInputStream().available() < 1) {
                    continue;
                }

                ServerMessageObject messageObject = (ServerMessageObject) inputStream.readObject();
                switch (messageObject.getType()) {
                    case SEND_MESSAGE -> {
                        Message message = (Message) messageObject.getObject();
                        System.out.println(message.getSender().getUsername() + ": " + message.getMessage());
                        user.getReceivedMessages().add(message);
                        view.getClientMessageList().setListData(user.getAllMessageInOrder());
                    }
                    case NEW_USER_CONNECTION -> {
                        HashSet<User> users = (HashSet<User>) messageObject.getObject();
                        //For second constructor --> with user
                        view.setOnlineList(users);
                        //For first constructor
                        //loggedInView().setOnlineList(users);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) throws IOException {
        System.out.println(this.user.getUsername() + "> " + message.getMessage());
        outStream.writeObject(new ServerMessageObject(MessageType.SEND_MESSAGE, message));
        outStream.flush();
        user.getSentMessages().add(message);
        view.getClientMessageList().setListData(user.getAllMessageInOrder());

    }

    //TODO: Kanske flytta alla dessa metoder till  Connection
    // Check if the user is already connected to the server
    public boolean CheckIfUserExists(String username) throws IOException, ClassNotFoundException {
        outStream.writeObject(new ServerMessageObject(MessageType.VERFY_NAME, username));
        outStream.flush();

        while (s.getInputStream().available() == 0) {

        }
        ServerMessageObject serverMessageObject = (ServerMessageObject) inputStream.readObject();
        return (boolean) serverMessageObject.getObject();
    }

    public void disconnect() throws IOException {
        outStream.writeObject(new ServerMessageObject(MessageType.USER_DISCONNECTED, user));
        outStream.flush();

        user.saveUser();
    }


    // It will create a user and send it to the server
    public User createUser(String usernameText, ImageIcon icon) {
        user = new User(usernameText, icon);
        this.setView(new LoggedInView(this));
        try {
            outStream.writeObject(new ServerMessageObject(MessageType.CREATE_USER, user));
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
        return user;
    }


    public LoggedInView getView() {
        return view;
    }

    public void setView(LoggedInView view) {
        this.view = view;
    }
}
