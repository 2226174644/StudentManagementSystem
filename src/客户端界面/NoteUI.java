package 客户端界面;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Entity.Note;
import UserSocket.Client;
import Util.CommandTranser;

public class NoteUI extends JFrame {
private static final long serialVersionUID = 1L;
private JTextArea notice_windows; 
JTextField sender_window;
private String noticename;
private Client client; 
private Note note = new Note();
JPanel top = new JPanel();
JPanel bottom = new JPanel();
JLabel sender = new JLabel("发送者");

	public NoteUI(String noticename,Client client) {
		this.noticename=noticename;
		this.client=client;
		
		CommandTranser cmd=new CommandTranser();
		cmd.setCmd("check_notice");
		note.setNoticeName(noticename);
		cmd.setData(note);
		client.sendData(cmd);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		init();
		this.dispose();
	}


	private void init() {
		// TODO 自动生成的方法存根
		setSize(500,500);
		setTitle(noticename);
		setLayout(new BorderLayout());
		notice_windows=new JTextArea(50,50);
//		notice_windows.setBounds(0,0,500,400);
		notice_windows.setEditable(false);
		notice_windows.add(new JScrollBar(JScrollBar.VERTICAL));
		sender_window=new JTextField(20);
//		sender_window.setBounds(0,400,500,100);
		JLabel notice = new JLabel("公告");
		top.add(notice);
		add(top,BorderLayout.NORTH);
		bottom.setLayout(new GridLayout(1, 2));
		bottom.add(sender);
		bottom.add(sender_window);
		add(bottom,BorderLayout.SOUTH);
		sender_window.setEditable(false);
		sender_window.setHorizontalAlignment(SwingConstants.RIGHT);
		add(notice_windows,BorderLayout.CENTER);
		setVisible(true);
	}
	public JTextArea getNoteWin() {
		return notice_windows;
	}
	public JTextField getSenderWin() {
		return sender_window;
	}
}
