package View.server;

import Controller.ServerController;
import Model.server.Logg;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class LoggerView extends JFrame {

    private JPanel contentPane;
    private JTextArea textArea;
    private JButton btnUpdate;
    private JList<Logg> loggListInfo;


//	private DateTimePicker dtpStart;
//	private DateTimePicker dtpEnd;

    private Controller.ServerController ServerController;

    public LoggerView(ServerController ServerController) {
        this.ServerController = ServerController;
        init();
    }

    private void init() {
        setTitle("View logs");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 950, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 613, 539);
        contentPane.add(scrollPane);

        loggListInfo = new JList();
        scrollPane.setViewportView(loggListInfo);

        Font f = new Font("Arial Bold", Font.BOLD, 32);

        JLabel lblStart = new JLabel("From");
        lblStart.setFont(f);
        lblStart.setBounds(750, 50, 100, 32);
        contentPane.add(lblStart);

//		dtpStart = new DateTimePicker();
//		dtpStart.setBounds(640, 100, 275, 32);
//		contentPane.add(dtpStart);

        JLabel lblEnd = new JLabel("To");
        lblEnd.setFont(f);
        lblEnd.setBounds(750, 250, 100, 32);
        contentPane.add(lblEnd);

//		dtpEnd = new DateTimePicker();
//		dtpEnd.setBounds(640, 300, 275, 32);
//		contentPane.add(dtpEnd);

        btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ButtonListener());
        btnUpdate.setBounds(725, 450, 102, 23);
        contentPane.add(btnUpdate);
        this.setResizable(false);

    }

    public void updateLoggList(HashSet<Logg> log) {
        List<Logg> loggs = new ArrayList(log);
        Collections.reverse(loggs);
        Logg[] finishedVersion = new Logg[loggs.size()];
        for (int x = 0; x < loggs.size(); x++) {
            finishedVersion[x] = loggs.get(x);
        }

        loggListInfo.setListData(finishedVersion);
    }

    public void updateTextArea(String str) {
        textArea.setText(str);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
//			if (e.getSource() == btnUpdate)
//			{
//				LocalDate LDFrom = dtpStart.datePicker.getDate();
//				LocalTime LTFrom = dtpStart.timePicker.getTime();
//
//				LocalDate LDTo = dtpEnd.datePicker.getDate();
//				LocalTime LTTo = dtpEnd.timePicker.getTime();
//
//				if (LDFrom == null || LTFrom == null || LDTo == null || LTTo == null)
//				{
//					JOptionPane.showMessageDialog(null, "Invalid date", "ERROR", JOptionPane.ERROR_MESSAGE);
//				}
//				else
//				{
//					//TODO: Fix --> upddatera viewn alltså texten med log information om vald intervall
//				}
//			}
        }
    }
}
