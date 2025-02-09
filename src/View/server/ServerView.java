package View.server;
import Controller.ServerController;
import javax.swing.*;

public class ServerView extends JFrame {
    private JTextArea serverMessageBoard;
    private JList allUserNameList;
    private JList activeClientList;

    public JList getAllUserNameList() {
        return allUserNameList;
    }
    public JList getActiveClientList() {
        return activeClientList;
    }

    private DefaultListModel<String> activeUsers = new DefaultListModel<String>();
    private DefaultListModel<String> allUsers = new DefaultListModel<String>();


    public DefaultListModel<String> getActiveUsers() {
        return activeUsers;
    }
    public DefaultListModel<String> getAllUsers() {
        return allUsers;
    }

    private ServerController controller;

    public ServerView(ServerController controller) {
        this.controller = controller;
        initialize();
    }

    // Creates the frame components
    private void initialize() {

        setBounds(100, 100, 796, 530);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setTitle("Server View");
        setVisible(true);

        serverMessageBoard = new JTextArea();
        serverMessageBoard.setEditable(false);
        serverMessageBoard.setBounds(12, 29, 489, 435);
        getContentPane().add(serverMessageBoard);
        serverMessageBoard.setText("Starting the Server...\n");


        // allUserNameList
        allUserNameList = new JList();
        allUserNameList.setBounds(526, 324, 218, 140);
        getContentPane().add(allUserNameList);

        // activeClientList
        activeClientList = new JList();
        activeClientList.setBounds(526, 78, 218, 156);
        getContentPane().add(activeClientList);


        //All Usernames label
        JLabel all_usernames = new JLabel("All Usernames");
        all_usernames.setHorizontalAlignment(SwingConstants.LEFT);
        all_usernames.setBounds(530, 295, 127, 16);
        getContentPane().add(all_usernames);

        // Active Users label
        JLabel active_users = new JLabel("Active Users");
        active_users.setBounds(526, 53, 98, 23);
        getContentPane().add(active_users);

        //Save button
        JButton saveButton = new JButton("Stop");
        saveButton.addActionListener(e -> controller.closeAll());
        saveButton.setBounds(640, 469, 100, 20);
        getContentPane().add(saveButton);

        JButton showLogs = new JButton("logs");
        showLogs.addActionListener(a -> controller.showLogs());
        showLogs.setBounds(526, 469, 100, 20);
        getContentPane().add(showLogs);

        this.setResizable(false);
    }

    //Add active user to the list.
    public void addUserToList(String userName) {
        controller.getServerView().getActiveUsers().addElement(userName);
        controller.getServerView().getAllUsers().addElement(userName);
        controller.getServerView().getActiveClientList().setModel(controller.getServerView().getActiveUsers());
        controller.getServerView().getAllUserNameList().setModel(controller.getServerView().getAllUsers());
    }

    //Remove active user from the list.
    public void removeUserFromList(String userName) {
        getActiveUsers().removeElement(userName);
        controller.getServerView().getActiveClientList().setModel(controller.getServerView().getActiveUsers());

    }

    //To show Message in ServerMessageBoard
    public void serverMessageBoardAppend(String message) {
        serverMessageBoard.append(message + "\n");
    }
}
