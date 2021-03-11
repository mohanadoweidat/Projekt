package View.client;

import Controller.ClientController;
import Model.Shared.Message;
import Model.Shared.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class LoggedInView extends JFrame {
    private JTextField clientTypingBoard;
    private JList<Message> clientMessageList;

    public JList<Message> getClientMessageList() {
        return clientMessageList;
    }

    private JList<User> clientActiveUsersList;

    private JList contactJList;


    private JRadioButton oneToNBtn;
    private JRadioButton broadcastBtn;
    private JRadioButton contactListBtn;
    private JButton uploadBtn;
    private JButton clientEndSessionButton;

    private JLabel user_icon;
    private JLabel user_id;


    private DefaultListModel<User> listModel;
    private DefaultListModel<User> contactListModel;
    private JFileChooser fileChooser;

    private ClientController clientController;

    private User user;
    private ImageIcon MessageImage;


    public LoggedInView(ClientController clientController) {
        this.clientController = clientController;
        user = clientController.getUser();
        init();
    }

    //TODO: Just for debug --> delete later!
    public static void main(String[] args) {
        new LoggedInView(null);
    }


    public void init() {
        //Frame
        //setTitle("Client Frame-" + user.getUsername());
        setTitle("Messenger - Logged in as: " + user.getUsername());
        setBounds(100, 100, 926, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        this.setResizable(false);


        //Components

        //clientMessageList
        clientMessageList = new JList<>();
        clientMessageList.setCellRenderer(new MessageBoxRenderer());
        clientMessageList.setBounds(12, 63, 530, 457);
        getContentPane().add(clientMessageList);


        //user_icon
        user_icon = new JLabel();
        ImageIcon icon;
        icon = user.getProfilePicture();
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        user_icon.setIcon(icon);
        user_icon.setHorizontalAlignment(SwingConstants.LEFT);
        user_icon.setBounds(12, 5, 50, 50);
        getContentPane().add(user_icon);

        //user_id
        user_id = new JLabel(user.getUsername());
        user_id.setHorizontalAlignment(SwingConstants.LEADING);
        user_id.setFont(new Font("Verdana", Font.BOLD, 10));
        user_id.setBounds(90, 12, 70, 50);
        getContentPane().add(user_id);

        //clientTypingBoard
        clientTypingBoard = new JTextField();
        clientTypingBoard.setHorizontalAlignment(SwingConstants.LEFT);
        clientTypingBoard.setBounds(12, 533, 470, 70);
        getContentPane().add(clientTypingBoard);
        clientTypingBoard.setColumns(10);


        //Send button
        JButton clientSendMsgBtn = new JButton("Skicka");
        clientSendMsgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textAreaMessage = clientTypingBoard.getText();
                ImageIcon selectedImg = MessageImage;

                if (selectedImg != null || !textAreaMessage.equalsIgnoreCase("")) {
                    if (clientActiveUsersList.getSelectedValue() != null && oneToNBtn.isSelected()) {
                        sendMessageLogic(textAreaMessage, selectedImg);
                    } else if (broadcastBtn.isSelected()) {
                        sendMessageLogic(textAreaMessage, selectedImg);
                    } else if (contactJList.getSelectedValue() != null && contactListBtn.isSelected()) {
                        sendMessageLogic(textAreaMessage, selectedImg);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please choose one receiver from the list!", "OBS!",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please write a message or upload a picture!", "OBS!",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        clientSendMsgBtn.setBounds(625, 533, 115, 70);
        getContentPane().add(clientSendMsgBtn);


        //clientActiveUsersList
        clientActiveUsersList = new JList<>();
        clientActiveUsersList.setCellRenderer(new UserBoxRenderer());
        listModel = new DefaultListModel<>();
        clientActiveUsersList.setModel(listModel);
        clientActiveUsersList.setToolTipText("Online användare:");
        clientActiveUsersList.setBounds(554, 63, 350, 460);
        getContentPane().add(clientActiveUsersList);
        clientActiveUsersList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    User selectedUser = clientActiveUsersList.getSelectedValue();
                    user.getContacts().add(selectedUser);
                    contactJList.setListData(user.getContacts().toArray());
                }
            }
        });

        //contactJList
        contactJList = new JList();
        contactListModel = new DefaultListModel<>();
        contactJList.setModel(contactListModel);
        contactJList.setToolTipText("Kontakter:");
        contactJList.setBounds(554, 63, 350, 460);
        contactJList.setCellRenderer(new UserBoxRenderer());
        contactJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    User selectedUser = (User) contactJList.getSelectedValue();
                    user.getContacts().remove(selectedUser);
                    contactJList.setListData(user.getContacts().toArray());
                }
            }
        });
        getContentPane().add(contactJList);


        //Exit button
        clientEndSessionButton = new JButton("Avsluta");
        clientEndSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientController.disconnect();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                dispose();
            }
        });
        clientEndSessionButton.setBounds(755, 533, 115, 70);
        getContentPane().add(clientEndSessionButton);

        //Upload pic button
        uploadBtn = new JButton("Bild");
        uploadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                int option = fileChooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File pic = fileChooser.getSelectedFile();
                    MessageImage = new ImageIcon(pic.getPath());
                }
            }
        });
        uploadBtn.setBounds(490, 533, 120, 70);
        getContentPane().add(uploadBtn);
        JLabel activeUserLabel = new JLabel("Online:");
        activeUserLabel.setHorizontalAlignment(SwingConstants.LEFT);
        activeUserLabel.setBounds(559, 43, 95, 16);
        getContentPane().add(activeUserLabel);

        //oneToNBtn button
        oneToNBtn = new JRadioButton("PM");
        oneToNBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientActiveUsersList.setVisible(true);
                clientActiveUsersList.setEnabled(true);
            }
        });
        oneToNBtn.setSelected(true);
        oneToNBtn.setBounds(600, 24, 100, 20);
        getContentPane().add(oneToNBtn);


        //broadcastBtn
        broadcastBtn = new JRadioButton("Broadcast");
        broadcastBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelectedUser();
                clientActiveUsersList.setEnabled(false);

            }
        });
        broadcastBtn.setBounds(700, 24, 100, 20);
        getContentPane().add(broadcastBtn);


        //contactListBtn
        contactListBtn = new JRadioButton("Kontakter");
        contactListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientActiveUsersList.setVisible(false);
                contactJList.setVisible(true);
            }
        });
        contactListBtn.setBounds(800, 24, 100, 20);
        getContentPane().add(contactListBtn);


        ButtonGroup btngrp = new ButtonGroup();
        btngrp.add(oneToNBtn);
        btngrp.add(broadcastBtn);
        btngrp.add(contactListBtn);
        setVisible(true);

    }


    public void clearSelectedUser() {
        clientActiveUsersList.clearSelection();
    }

    public void addUserToOnlineList(User user) {
        listModel.addElement(user);
        clientActiveUsersList.setModel(listModel);
    }

    public void setOnlineList(Set<User> users) {
        listModel.clear();
        users.forEach(u -> listModel.addElement(u));
        clientActiveUsersList.setModel(listModel);

    }

    public void sendMessageLogic(String textAreaMessage, ImageIcon selectedImg) {
        User to;
        if (clientActiveUsersList.getSelectedValue() == null) {
            to = (User) contactJList.getSelectedValue();
        } else {
            to = clientActiveUsersList.getSelectedValue();
        }

        Message message = new Message(textAreaMessage, selectedImg, user, to);
        try {
            clientController.sendMessage(message);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        MessageImage = null;
        clientTypingBoard.setText("");
    }


    class MessageBoxRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvitica", Font.BOLD, 12);

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            Message message = (Message) value;
            JLabel label = (JLabel) super.getListCellRendererComponent(list, message, index, isSelected, cellHasFocus);
            if (message.getImage() != null) {
                Image image = message.getImage().getImage(); // transform it
                Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the
                // smooth way
                ImageIcon icon = new ImageIcon(newimg);
                label.setIcon(icon);
            }
            label.setHorizontalTextPosition(JLabel.LEFT);
            label.setFont(font);
            return label;
        }
    }

    class UserBoxRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvitica", Font.BOLD, 12);

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            User user = (User) value;
            JLabel label = (JLabel) super.getListCellRendererComponent(list, user.getUsername(), index, isSelected,
                    cellHasFocus);
            Image image = user.getProfilePicture().getImage(); // transform it
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); // scale it the
            // smooth way
            ImageIcon icon = new ImageIcon(newimg);
            label.setIcon(icon);
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }


}

