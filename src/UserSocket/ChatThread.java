package UserSocket;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import Entity.ChatUIEntity;
import Entity.Files;
import Entity.Message;
import Entity.Note;
import Entity.NoteEntity;
import Entity.NoticeList;
import Entity.User;
import Entity.Vote;
import �ͻ��˽���.ChatUI;
import �ͻ��˽���.FriendsUI;
import �ͻ��˽���.NoteUI;

import Util.ChatUIList;
import Util.CommandTranser;
import Util.VoteList;


public class ChatThread extends Thread{
	private Client client;
	private boolean isOnline = true; //û�� ------��ɾ
	private User user; //���ͬ��������� ��ˢ�º����б�
	private FriendsUI friendsUI; //ˢ�º����б���
	private String username; //��������µ����촰�ڣ�chatUI)��ô���뽫username����ȥ ����������Ϣ
	private Files files;
	private VoteList votelist;
	private Vote vote;
	
	public ChatThread(Client client, User user, FriendsUI friendsUI) {
		this.client = client;
		this.user = user;
		this.friendsUI = friendsUI;
		this.username = user.getUserName();
		files=new Files();
		votelist=new VoteList();
		vote=new Vote();
		//this.chat_windows = chat_windows;
	}
	
	public boolean isOnline() {
		return isOnline;
	}
	//����û��---------------------------------------
	public void setOnline(boolean isOnline) {
		 this.isOnline = true; 
	}
	public String getUserName() {
		return username;
	}
	
	//run()�����ǲ���Ҫ�û������õģ���ͨ��start��������һ���߳�֮�󣬵��̻߳����CPUִ��ʱ�䣬
	//�����run������ȥִ�о��������ע�⣬�̳�Thread�������дrun��������run�����ж������Ҫִ�е�����
	@Override
	public void run() {
		if(!isOnline) {
			JOptionPane.showMessageDialog(null,  "unbelievable ������");
			return;
		}
		while(isOnline) {
			
			CommandTranser cmd = client.getData();
			//�����������ͬ������յ�����Ϣ(����)
			//���ﴦ�����Է���������Ϣ(����)
			if(cmd != null) {
				
			 try {
				execute(cmd);
			} catch (BadLocationException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			 //System.out.println(cmd.getCmd());	
			}
		}
	}
	

	//������Ϣ(����)
	private void execute(CommandTranser cmd) throws BadLocationException {
		//��¼���������롢ע����Ϣδ�ڴ˴�����
		System.out.println(cmd.getCmd()+"  78");
		
		//������Ϣ���� 
		if("message".equals(cmd.getCmd())) {
			if(cmd.isFlag() == false) {
				JOptionPane.showMessageDialog(null, cmd.getResult()); 
				return;
			}
			//��ѯ�Ƿ�����ú��ѵĴ��ڸô���
			String friendname = cmd.getSender();
			ChatUI chatUI = ChatUIList.getChatUI(friendname);
			if(chatUI == null) {
				chatUI = new ChatUI(username, friendname, username, client,ChatThread.this,user);
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName(friendname);
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); //�����ǰ������������Ĵ����ڸ��� ��������ʾ
			}
			Message ms=(Message) cmd.getData();
			System.out.println("messageType  "+ms.getType());
			if(ms.getType()==0) {
				chatUI.getChatWin().insertString(ms.getLength(),ms.getMessage(),ms.getAttrSet());//׷���ı���Ϣ
			}else {
				byte[] bt=ms.getPhoto();
				String path="E:/Ѹ������/icon/"+ms.getFileName();
				try {
					RandomAccessFile raf1 = new RandomAccessFile(path, "rw");
					raf1.write(bt);
				} catch (FileNotFoundException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				chatUI.getChatWin().insertString(chatUI.getChatWin().getLength(),ms.getMessage(),null);
				chatUI.getJTextPane().setCaretPosition(chatUI.getChatWin().getLength()); // ���ò���λ��
				chatUI.getJTextPane().insertIcon(new ImageIcon(path)); // ����ͼƬ
			}
			try {
				@SuppressWarnings("deprecation")
				AudioClip ac=Applet.newAudioClip((new File("sounds/tip.wav")).toURL());
				ac.play();
			} catch (MalformedURLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			return;
		}
		
		if("WorldChat".equals(cmd.getCmd())) {
//			if(cmd.isFlag() == false) {
//				JOptionPane.showMessageDialog(null, cmd.getResult()); 
//				return;
//			}
			//��ѯ�Ƿ�����ú��ѵĴ��ڸô���
			String friendname = cmd.getSender();
			ChatUI chatUI = ChatUIList.getChatUI("WorldChat");
			if(chatUI == null) {
				System.out.println("chatthread ��ǰ�û�Ϊ"+user.getUserName());
				chatUI = new ChatUI("WorldChat", "WorldChat", user.getUserName(), client,ChatThread.this,user);//
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName("WorldChat");
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); //�����ǰ������������Ĵ����ڸ��� ��������ʾ
			}
			Message ms=(Message) cmd.getData();
			if(ms.getType()==0) {
				chatUI.getChatWin().insertString(ms.getLength(),ms.getMessage(),ms.getAttrSet());//׷���ı���Ϣ
			}else {
				byte[] bt=ms.getPhoto();
				String path="E:/Ѹ������/icon/"+ms.getFileName();
				try {
					RandomAccessFile raf1 = new RandomAccessFile(path, "rw");
					raf1.write(bt);
				} catch (FileNotFoundException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				chatUI.getChatWin().insertString(chatUI.getChatWin().getLength(),ms.getMessage(),null);
				chatUI.getJTextPane().setCaretPosition(chatUI.getChatWin().getLength()); // ���ò���λ��
				chatUI.getJTextPane().insertIcon(new ImageIcon(path)); // ����ͼƬ
			}
			try {
				@SuppressWarnings("deprecation")
				AudioClip ac=Applet.newAudioClip((new File("sounds/tip.wav")).toURL());
				ac.play();
			} catch (MalformedURLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}		
						
			return;
		}
		
		//��ȡ����
		if("check_notice".equals(cmd.getCmd())) {
//			if(cmd.isFlag() == false) {
//				JOptionPane.showMessageDialog(null, cmd.getResult());
//				return;
//			}
			Note note=(Note)cmd.getData();
			String noticename=note.getNoticeName();
			NoteUI noteUI = NoticeList.getNoteUI(noticename);
			if(noteUI==null) {
				noteUI=new NoteUI(noticename,client);
				NoteEntity noteEntity=new NoteEntity();
				noteEntity.setName(noticename);
				noteEntity.setNoteUI(noteUI);
				NoticeList.addNoteUI(noteEntity);
			}else {
				noteUI.show();
			}
			String constent=note.getConstent();
			String sender=note.getSender();
			noteUI.getNoteWin().setText(constent);
			System.out.println("chatthread �������� "+constent);
			noteUI.getSenderWin().setText(sender);
			return;
		}
		//���ļ�
		if("check_file".equals(cmd.getCmd())) {
			System.out.println("cmd�� "+cmd.getResult());
			 files=(Files)cmd.getData();
			 System.out.println("file������ "+files.getFileNum()+"����"+files.getPath().get(0));
			return;}
		//��ͶƱ�б�
		if("check_vote".equals(cmd.getCmd())) {
			System.out.println("cmd�� "+cmd.getResult());
			 votelist=(VoteList)cmd.getData();
			 System.out.println("votelist������ "+votelist.getVoteNum()+"����"+votelist.getVoteslist().get(0));
			return;}
		//��ȡͶƱ
		if("get_vote".equals(cmd.getCmd())) {
			System.out.println("cmd�� "+cmd.getResult());
			 vote=(Vote)cmd.getData();
			 System.out.println("chatthread  vote "+vote.getVoteName()+"���� "+vote.getConstent());
			return;}
		//�ϴ��ļ�
		if("upload".equals(cmd.getCmd())) {
			JOptionPane.showMessageDialog(null, cmd.getResult());
			//
			ChatUI chatUI = ChatUIList.getChatUI("WorldChat");
			if(chatUI == null) {
				chatUI = new ChatUI("WorldChat", "WorldChat", user.getUserName(), client,this,user);
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName("WorldChat");
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); //�����ǰ������������Ĵ����ڸ��� ��������ʾ
			}
//			chatUI.validate();
//			chatUI.repaint();
//			chatUI.setVisible(true);
			//
			return;
		}
		if("requeste_add_friend".equals(cmd.getCmd())) {
			if(cmd.isFlag() == false) {
				JOptionPane.showMessageDialog(null, cmd.getResult()); 
				return;
			}
			String sendername = cmd.getSender();
			int flag = JOptionPane.showConfirmDialog(null, "�Ƿ�ͬ��" + sendername + "�ĺ�������", "��������", JOptionPane.YES_NO_OPTION);
			System.out.println(flag);
			if(flag == 0) {
				cmd.setCmd("accept_add_friend");
			} else {
				cmd.setCmd("refuse_add_friend");			
			}
			cmd.setSender(username);
			cmd.setReceiver(sendername);
			client.sendData(cmd);
			return;
		}
		
//		if("successful".equals(cmd.getCmd())) {
//			JOptionPane.showMessageDialog(null, cmd.getResult()); 
//			return;
//		}
		
		if("accept_add_friend".equals(cmd.getCmd())) {
			JOptionPane.showMessageDialog(null, cmd.getResult());
			
			CommandTranser newcmd = new CommandTranser();
//			newcmd.setCmd("updatefriendlist");
//			newcmd.setReceiver(username);
//			newcmd.setSender(username);
//			newcmd.setData(user);
//			client.sendData(newcmd);//
			
			return;
			
		}
		
		if("updatefriendlist".equals(cmd.getCmd())) {
			if(cmd.isFlag() == false) {
				System.out.println("chatthread 163");
				JOptionPane.showMessageDialog(null, cmd.getResult()); 
				return;
			}
			
			User tmp = (User)cmd.getData();			
			user.setFriendsList(tmp.getFriend());
			friendsUI.dispose();
			friendsUI=new FriendsUI(user, client);			
			friendsUI.repaint();
			friendsUI.setVisible(true);
			
			return;
		}
		
		if("send_notice".equals(cmd.getCmd())) {
			System.out.println("chatthread�������͹���ɹ�");
			JOptionPane.showMessageDialog(null, cmd.getResult()); 
			return;
		}
		if("send_vote".equals(cmd.getCmd())) {
			System.out.println("chatthread��������ͶƱ�ɹ�");
			JOptionPane.showMessageDialog(null, cmd.getResult()); 
			return;
		}
		if("update_vote".equals(cmd.getCmd())) {
			System.out.println("chatthread����ͶƱ�ɹ�");
			JOptionPane.showMessageDialog(null, cmd.getResult()); 
			return;
		}
		if("refuse_to_add".equals(cmd.getCmd())) {
			JOptionPane.showMessageDialog(null, cmd.getResult());
			return;
		}
		
		if("changepwd".equals(cmd.getCmd())) {
			JOptionPane.showMessageDialog(null, cmd.getResult());
			return;
		}
		return;
	}
	public  Files  getFiles() {
		return files;
	}
	public VoteList getVoteList() {
		return votelist;
	}
	public Vote getVote() {
		return vote;
	}
}

