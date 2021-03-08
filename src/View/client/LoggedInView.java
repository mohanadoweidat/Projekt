package View.client;

import Model.Shared.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LoggedInView extends JFrame
{
	private JTextField clientTypingBoard;
	private JTextArea clientMessageBoard;
	private JList clientActiveUsersList;


	private JRadioButton oneToNBtn;
	private JRadioButton broadcastBtn;
	private JButton uploadBtn;
	private JButton clientEndSessionButton;

	private JLabel user_icon;
	private JLabel user_id;


	private DefaultListModel<String> listModel;
	private JFileChooser fileChooser;

	private User user;


	public LoggedInView(User user)
	{
		this.user = user;
		init();
	}

	//TODO: Just for debug --> delete later!
	public static void main(String[] args)
	{
		new LoggedInView(null);
	}


	public void init()
	{
		//Frame
		//setTitle("Client Frame-" + user.getUsername());
		setTitle("Client Frame-");
		setBounds(100, 100, 926, 680);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);


		//Components

		//clientMessageBoard
		clientMessageBoard = new JTextArea();
		clientMessageBoard.setEditable(false);
		clientMessageBoard.setBounds(12, 63, 530, 457);
		getContentPane().add(clientMessageBoard);


		//user_icon
		user_icon = new JLabel();
		//user_icon.setIcon(new ImageIcon((Image) user.getProfilePicture()));
		user_icon.setHorizontalAlignment(SwingConstants.LEFT);
		user_icon.setBounds(12, 5, 60, 50);
		getContentPane().add(user_icon);

		//user_id
		user_id = new JLabel("");
		user_id.setHorizontalAlignment(SwingConstants.LEFT);
		user_id.setFont(new Font("Verdana", Font.PLAIN, 18));
		user_id.setBounds(70, 12, 50, 45);
		getContentPane().add(user_id);

		//clientTypingBoard
		clientTypingBoard = new JTextField();
		clientTypingBoard.setHorizontalAlignment(SwingConstants.LEFT);
		clientTypingBoard.setBounds(12, 533, 470, 70);
		getContentPane().add(clientTypingBoard);
		clientTypingBoard.setColumns(10);


		//Send button
		JButton clientSendMsgBtn = new JButton("Skicka");
		clientSendMsgBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String textAreaMessage = clientTypingBoard.getText();

			}
		});
		clientSendMsgBtn.setBounds(625, 533, 115, 70);
		getContentPane().add(clientSendMsgBtn);


		//clientActiveUsersList
		clientActiveUsersList = new JList();
		listModel = new DefaultListModel<>();
		clientActiveUsersList.setModel(listModel);
		clientActiveUsersList.setToolTipText("Online användare:");
		clientActiveUsersList.setBounds(554, 63, 327, 457);
		getContentPane().add(clientActiveUsersList);

		//Exit button
		clientEndSessionButton = new JButton("Avsluta");
		clientEndSessionButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
               dispose();
			}
		});
		clientEndSessionButton.setBounds(755, 533,  115, 70);
		getContentPane().add(clientEndSessionButton);

		//Upload pic button
		uploadBtn = new JButton("Bild");
		uploadBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				fileChooser = new JFileChooser();
				fileChooser.setAcceptAllFileFilterUsed(false);
				int option = fileChooser.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION)
				{
					File pic = fileChooser.getSelectedFile();
				}
			}
		});
		uploadBtn.setBounds(490, 533, 120 , 70);
		getContentPane().add(uploadBtn);
		JLabel activeUserLabel = new JLabel("Online:");
		activeUserLabel.setHorizontalAlignment(SwingConstants.LEFT);
		activeUserLabel.setBounds(559, 43, 95, 16);
		getContentPane().add(activeUserLabel);

		//oneToNBtn button
		oneToNBtn = new JRadioButton("1 till N");
		oneToNBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				clientActiveUsersList.setEnabled(true);
			}
		});
		oneToNBtn.setSelected(true);
		oneToNBtn.setBounds(682, 24, 72, 25);
		getContentPane().add(oneToNBtn);


		//broadcastBtn
		broadcastBtn = new JRadioButton("Till alla");
		broadcastBtn.setBounds(774, 24, 107, 25);
		getContentPane().add(broadcastBtn);

		ButtonGroup btngrp = new ButtonGroup();
		btngrp.add(oneToNBtn);
		btngrp.add(broadcastBtn);
		setVisible(true);


		//TODO: Delete this shit later!!!!
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.setSize(1000,1000);
		JButton button = new JButton("X");
		button.setBackground(Color.red);
		panel.add(button);
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}
