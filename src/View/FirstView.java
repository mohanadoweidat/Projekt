package View;

import Controller.ClientController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class FirstView
{

	//Login
	private JFrame LoginFrame;
	private JPanel jPanel;
	private JTextField usernameFiled;
	private JButton loginButton;
	private JButton signupButton;

	public String getUsernameFiled()
	{
		return usernameFiled.getText();
	}

	//SignUP
	private JFrame signupFrame;
	private JLabel userNameLabel;
	private JTextField userNameFiledSignup;
	private JLabel lblIcon;
	private JButton iconButton;
	private JButton doneButton;
	private JFileChooser fileChooser;
	private String path;

	public String getUserNameFiledSignup()
	{
		return userNameFiledSignup.getText();
	}


	//ClientController
	private ClientController clientController;




	public FirstView(ClientController clientController) throws FileNotFoundException
	{
		this.clientController = clientController;

        showLoginDialog();
		showInfo();
	}

	public FirstView geFirstView(){
		return this;
	}

	// Login and show frame for that
	private void showLoginDialog()
	{

		usernameFiled = new JTextField();
		loginButton = new JButton("Logga In");
		signupButton = new JButton("Skapa ett konto");

		jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		jPanel.add(usernameFiled);
		jPanel.add(loginButton);
		jPanel.add(signupButton);



		//LoginButton
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try
				{
					if (!getUsernameFiled().equalsIgnoreCase("")){
						boolean done = doLogin();
						if (done){

							JOptionPane.showMessageDialog(null,"User found, welcome back:" + getUsernameFiled(),"User found", JOptionPane.INFORMATION_MESSAGE);
							//Open UserFrame
							UserListPane userListPane = new UserListPane(clientController, geFirstView());
							//Close LoginFrame
							LoginFrame.dispose();
						}
						else {
							JOptionPane.showMessageDialog(null,"Wrong username!","Alert",JOptionPane.WARNING_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null,"Empty field, please enter your username!","Alert", JOptionPane.WARNING_MESSAGE);
					}
				}
				catch (IOException exception)
				{
					exception.printStackTrace();
				}
			}
		});
		//SignupButton
		signupButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
                 createAccountDialog();
                 LoginFrame.dispose();
			}
		});

		LoginFrame = new JFrame("Inloggning:");
		LoginFrame.add(jPanel, BorderLayout.CENTER);
		LoginFrame.pack();
		LoginFrame.setSize(new Dimension(400,200));
		LoginFrame.setVisible(true);
		LoginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	//Check if username exist in the file when login
	private boolean doLogin() throws IOException
	{
		boolean found = false;
		String username = getUsernameFiled();
		if (!username.equalsIgnoreCase("") ){
			found = clientController.readFromFile(username);
			if (found){
				found = true;
			}
		}
		return found;
	}


	// Create account and show frame for that
	private void createAccountDialog()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		userNameLabel = new JLabel("Skriv ett användarnamn:");
		userNameFiledSignup = new JTextField();
		lblIcon = new JLabel("Lägg upp en bild:");
		iconButton = new JButton("Klicka här!");
		doneButton = new JButton("Klar!");
		doneButton.setVisible(false);
		panel.add(userNameLabel);
		panel.add(userNameFiledSignup);
		panel.add(lblIcon);
		panel.add(iconButton);
		panel.add(doneButton);

		//UploadPicButton
		iconButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!getUserNameFiledSignup().equalsIgnoreCase("")){
					fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File("user.dir"));

					FileNameExtensionFilter filter = new FileNameExtensionFilter("Alla bilder", "png", "jpg","jpeg,","gif");
					fileChooser.addChoosableFileFilter(filter);

					int a = fileChooser.showSaveDialog(null);
					if (a == JFileChooser.APPROVE_OPTION){
						File f = fileChooser.getSelectedFile();
						path = f.getAbsolutePath();
					}
					if (getUserNameFiledSignup() != "" && path != ""){
						iconButton.setVisible(false);
						doneButton.setVisible(true);
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"Empty field, please enter a username first!","Alert",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		//DoneButton
		doneButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//Check if username exist in the list before adding it again to the hashMap and to the file user.txt
				try
				{
					boolean done = clientController.checkUserExist(getUserNameFiledSignup());
					if (done){
						JOptionPane.showMessageDialog(null,"Username:" + getUserNameFiledSignup() +  " , alredy " +
										"exist" + " please login!","Alert",
								JOptionPane.WARNING_MESSAGE);
					}
					else {
						// Add username and icon to text file and hashMap
						//clientController.getUser().getUserList().put(getUserNameFiledSignup(), new ImageIcon(path));
						try
						{
							//Save the image and the username from the user in the user.txt file
							clientController.writeToFile(getUserNameFiledSignup(),path);
						}
						catch (IOException exception)
						{
							exception.printStackTrace();
						}

					}
				}
				catch (IOException exception)
				{
					exception.printStackTrace();
				}

				//TODO : debug för att skriva ut alla användare med deras bild i hashmap.
				for (String s : clientController.getUser().getUserList().keySet())
				{
					System.out.println("Alla användare(key):" + s);
					System.out.println("Bilden(Value):" + clientController.getUser().getUserList().get(s));
				}
				//Show LoginFrame
				showLoginDialog();
				signupFrame.dispose();

			}
		});


		signupFrame = new JFrame("Welcome to Message Messanger!");
		signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		signupFrame.setSize(500, 500);
		signupFrame.getContentPane().add(panel, BorderLayout.CENTER);
		signupFrame.setVisible(true);
	}



	public ImageIcon ResizeImage(String imagePath)
	{
		ImageIcon imageIcon = new ImageIcon(imagePath);
		return imageIcon;
	}


	//Telling user to enter a username
	public void showInfo(){
		JOptionPane.showMessageDialog(null, "Please enter your username to Login", "Info", JOptionPane.INFORMATION_MESSAGE);
	}
}
