package View.client;

import Controller.ClientController;
import Model.Shared.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class SignUpView extends JFrame
{
	private ClientController clientController;

	private JPanel avatarPane;

	private JPanel contentPane;
	private JTextField txtUsername;
	private JButton btnSignUp = new JButton("Connect");
	private JButton btnChooseImage = new JButton("Choose image");

	private ImageIcon icon;
	private String path;


	public SignUpView(ClientController clientController)
	{
		this.clientController = clientController;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setResizable(false);
		txtUsername = new JTextField();
		txtUsername.setToolTipText("Your username");
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtUsername.setBounds(236, 235, 200, 30);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		txtUsername.setEditable(false);


		this.addWindowListener(new WindowAdapter()
		{
			public void windowOpened(WindowEvent e)
			{
				txtUsername.requestFocus();
			}
		});

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(300, 210, 75, 15);
		contentPane.add(lblUsername);

		btnSignUp.setBounds(289, 385, 89, 23);
		btnSignUp.addActionListener(new ButtonListener());
		contentPane.add(btnSignUp);


		avatarPane = new JPanel();
		avatarPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		avatarPane.setBounds(310, 50, 50, 50);
		contentPane.add(avatarPane);

		btnChooseImage.setBounds(275, 135, 125, 23);
		btnChooseImage.addActionListener(new ButtonListener());
		contentPane.add(btnChooseImage);
		setVisible(true);
	}

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{

			if (e.getSource() == btnChooseImage)
			{
				JFileChooser JFC = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
				JFC.setFileFilter(filter);
				int optionpicked = JFC.showOpenDialog(null);
				if (optionpicked == JFileChooser.APPROVE_OPTION)
				{
					File file = JFC.getSelectedFile();
					icon = new ImageIcon(file.getPath());
					Image image = icon.getImage(); // transform it
					Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); // scale it the
					// smooth way
					icon = new ImageIcon(newimg);  // transform it back

					avatarPane.removeAll();
					avatarPane.add(new JLabel(icon));
					avatarPane.updateUI();
					path = file.getAbsolutePath();
					if (path != null)
					{
						txtUsername.setEditable(true);
					}
				}
			}
			if (e.getSource() == btnSignUp)
			{
				if (txtUsername.getText().length() <= 0)
				{
					JOptionPane.showMessageDialog(null, "Please choose a picture and then enter a username", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					String usernameText = txtUsername.getText();
					try
					{

						//Check if the entered username is not Connected to the server
						boolean exists = clientController.CheckIfUserExists(usernameText);
						//Username already exists (Connected)
						if (exists)
						{
							JOptionPane.showMessageDialog(null, "Username already Connected, please login with " +
									"another username!", "ERROR", JOptionPane.ERROR_MESSAGE);
							txtUsername.setText("");
						}
						//Username not Connected to the server ---> Done login
						else
						{
							User user = clientController.createUser(usernameText, icon);
							//loggedInView = new LoggedInView(clientController);
							System.out.println("User:" + usernameText + "are now logged!!");
							user.setLoggedIn(true);
							setVisible(false);
						}
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}

		}
	}
}
