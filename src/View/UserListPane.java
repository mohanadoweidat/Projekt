package View;

import Controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class UserListPane
{
	private ClientController clientController;
	private FirstView firstView;

	private JFrame frame;


	//onlineUsersPanel
	private JPanel onlineUsersPanel;
	private JLabel onlineUsersInfoLbl;



    //friendListPanel
    private JPanel friendListPanel;
	private JLabel friendListInfoLbl;


	//listPanel
	private JPanel listPanel;
	private JList<String> userList;
	private DefaultListModel<String> userListModel;


	public UserListPane(ClientController clientController, FirstView firstView) throws IOException
	{
		this.clientController = clientController;
		this.firstView = firstView;

		onlineUsersPanel();
		onlineUsersListPanel();
		friendListPanel();
		initFrame();
	}

	//Panel to show label with Online users text
	public void onlineUsersPanel() throws IOException
	{
		onlineUsersPanel = new JPanel();
		onlineUsersPanel.setLayout(new BorderLayout());
		onlineUsersPanel.setBackground(new Color(175, 238, 0));


		onlineUsersInfoLbl = new JLabel("Online users:");
		onlineUsersInfoLbl.setSize(new Dimension(50,50));

		onlineUsersPanel.add(onlineUsersInfoLbl);
	}
	//Panel to show online users (name and maybe image?)
	public void onlineUsersListPanel()
	{

		listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		listPanel.setBackground(new Color(175, 238, 0));

		userListModel = new DefaultListModel<>();
		userList = new JList<>(userListModel);
		listPanel.add(new JScrollPane(userList), BorderLayout.CENTER);
	}
	//Panel to show the friend list
	public void friendListPanel()
	{
		friendListPanel = new JPanel();
		friendListInfoLbl = new JLabel("Friend list:");
		friendListInfoLbl.setSize(new Dimension(50,50));
		friendListInfoLbl.setLayout(new BorderLayout());
		friendListInfoLbl.setBackground(new Color(0, 238, 202));
		friendListPanel.setBackground(new Color(186, 164, 101));
		friendListPanel.add(friendListInfoLbl);
	}
	//Creates frame and sho Logged username and icon
	public void initFrame() throws IOException
	{
		ImageIcon imageIcon = (ImageIcon) getIconForLoggedUser(firstView.getUsernameFiled());
		//Scaling Image
		Image image = imageIcon.getImage();
		Image scaled = image.getScaledInstance(onlineUsersInfoLbl.getWidth(), onlineUsersInfoLbl.getHeight(),Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(scaled);

		frame = new JFrame("Inlogged User: " + firstView.getUsernameFiled());
		frame.setIconImage(scaled);

		frame.getContentPane().add(onlineUsersPanel, BorderLayout.NORTH);
		frame.getContentPane().add(listPanel, BorderLayout.CENTER);
	    frame.getContentPane().add(friendListPanel, BorderLayout.EAST);
		frame.pack();
		frame.setSize(new Dimension(400,700));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
	// Get userIcon by Username from txt.file (users)
	public Icon getIconForLoggedUser(String username) throws IOException
	{
		Icon ImagePath = null;
		File file = new File("user.txt");
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String user;
		String icon;
		while (bufferedReader.readLine() != null){
			user = bufferedReader.readLine();
			if (user.equalsIgnoreCase(username)){
				icon = bufferedReader.readLine();
				ImagePath = new ImageIcon(icon);
			}
		}
		return ImagePath;
	}
}
