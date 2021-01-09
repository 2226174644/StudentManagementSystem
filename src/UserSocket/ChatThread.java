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
import 客户端界面.ChatUI;
import 客户端界面.FriendsUI;
import 客户端界面.NoteUI;

import Util.ChatUIList;
import Util.CommandTranser;
import Util.VoteList;


public class ChatThread extends Thread{
	private Client client;
	private boolean isOnline = true; //没用 ------待删
	private User user; //如果同意好友请求， 则刷新好友列表
	private FriendsUI friendsUI; //刷新好友列表用
	private String username; //如果创建新的聊天窗口（chatUI)那么必须将username传进去 用来发送消息
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
	//这里没用---------------------------------------
	public void setOnline(boolean isOnline) {
		 this.isOnline = true; 
	}
	public String getUserName() {
		return username;
	}
	
	//run()方法是不需要用户来调用的，当通过start方法启动一个线程之后，当线程获得了CPU执行时间，
	//便进入run方法体去执行具体的任务。注意，继承Thread类必须重写run方法，在run方法中定义具体要执行的任务
	@Override
	public void run() {
		if(!isOnline) {
			JOptionPane.showMessageDialog(null,  "unbelievable ！！！");
			return;
		}
		while(isOnline) {
			
			CommandTranser cmd = client.getData();
			//与服务器端相同处理接收到的消息(命令)
			//这里处理来自服务器的消息(命令)
			if(cmd != null) {
				
			 try {
				execute(cmd);
			} catch (BadLocationException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			 //System.out.println(cmd.getCmd());	
			}
		}
	}
	

	//处理消息(命令)
	private void execute(CommandTranser cmd) throws BadLocationException {
		//登录、忘记密码、注册消息未在此处处理
		System.out.println(cmd.getCmd()+"  78");
		
		//聊天消息请求 
		if("message".equals(cmd.getCmd())) {
			if(cmd.isFlag() == false) {
				JOptionPane.showMessageDialog(null, cmd.getResult()); 
				return;
			}
			//查询是否有与该好友的窗口该窗口
			String friendname = cmd.getSender();
			ChatUI chatUI = ChatUIList.getChatUI(friendname);
			if(chatUI == null) {
				chatUI = new ChatUI(username, friendname, username, client,ChatThread.this,user);
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName(friendname);
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); //如果以前创建过仅被别的窗口掩盖了 就重新显示
			}
			Message ms=(Message) cmd.getData();
			System.out.println("messageType  "+ms.getType());
			if(ms.getType()==0) {
				chatUI.getChatWin().insertString(ms.getLength(),ms.getMessage(),ms.getAttrSet());//追加文本消息
			}else {
				byte[] bt=ms.getPhoto();
				String path="E:/迅雷下载/icon/"+ms.getFileName();
				try {
					RandomAccessFile raf1 = new RandomAccessFile(path, "rw");
					raf1.write(bt);
				} catch (FileNotFoundException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				chatUI.getChatWin().insertString(chatUI.getChatWin().getLength(),ms.getMessage(),null);
				chatUI.getJTextPane().setCaretPosition(chatUI.getChatWin().getLength()); // 设置插入位置
				chatUI.getJTextPane().insertIcon(new ImageIcon(path)); // 插入图片
			}
			try {
				@SuppressWarnings("deprecation")
				AudioClip ac=Applet.newAudioClip((new File("sounds/tip.wav")).toURL());
				ac.play();
			} catch (MalformedURLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return;
		}
		
		if("WorldChat".equals(cmd.getCmd())) {
//			if(cmd.isFlag() == false) {
//				JOptionPane.showMessageDialog(null, cmd.getResult()); 
//				return;
//			}
			//查询是否有与该好友的窗口该窗口
			String friendname = cmd.getSender();
			ChatUI chatUI = ChatUIList.getChatUI("WorldChat");
			if(chatUI == null) {
				System.out.println("chatthread 当前用户为"+user.getUserName());
				chatUI = new ChatUI("WorldChat", "WorldChat", user.getUserName(), client,ChatThread.this,user);//
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName("WorldChat");
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); //如果以前创建过仅被别的窗口掩盖了 就重新显示
			}
			Message ms=(Message) cmd.getData();
			if(ms.getType()==0) {
				chatUI.getChatWin().insertString(ms.getLength(),ms.getMessage(),ms.getAttrSet());//追加文本消息
			}else {
				byte[] bt=ms.getPhoto();
				String path="E:/迅雷下载/icon/"+ms.getFileName();
				try {
					RandomAccessFile raf1 = new RandomAccessFile(path, "rw");
					raf1.write(bt);
				} catch (FileNotFoundException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				chatUI.getChatWin().insertString(chatUI.getChatWin().getLength(),ms.getMessage(),null);
				chatUI.getJTextPane().setCaretPosition(chatUI.getChatWin().getLength()); // 设置插入位置
				chatUI.getJTextPane().insertIcon(new ImageIcon(path)); // 插入图片
			}
			try {
				@SuppressWarnings("deprecation")
				AudioClip ac=Applet.newAudioClip((new File("sounds/tip.wav")).toURL());
				ac.play();
			} catch (MalformedURLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}		
						
			return;
		}
		
		//读取公告
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
			System.out.println("chatthread 公告内容 "+constent);
			noteUI.getSenderWin().setText(sender);
			return;
		}
		//查文件
		if("check_file".equals(cmd.getCmd())) {
			System.out.println("cmd是 "+cmd.getResult());
			 files=(Files)cmd.getData();
			 System.out.println("file的数量 "+files.getFileNum()+"内容"+files.getPath().get(0));
			return;}
		//查投票列表
		if("check_vote".equals(cmd.getCmd())) {
			System.out.println("cmd是 "+cmd.getResult());
			 votelist=(VoteList)cmd.getData();
			 System.out.println("votelist的数量 "+votelist.getVoteNum()+"内容"+votelist.getVoteslist().get(0));
			return;}
		//获取投票
		if("get_vote".equals(cmd.getCmd())) {
			System.out.println("cmd是 "+cmd.getResult());
			 vote=(Vote)cmd.getData();
			 System.out.println("chatthread  vote "+vote.getVoteName()+"内容 "+vote.getConstent());
			return;}
		//上传文件
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
				chatUI.show(); //如果以前创建过仅被别的窗口掩盖了 就重新显示
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
			int flag = JOptionPane.showConfirmDialog(null, "是否同意" + sendername + "的好友请求", "好友请求", JOptionPane.YES_NO_OPTION);
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
			System.out.println("chatthread——发送公告成功");
			JOptionPane.showMessageDialog(null, cmd.getResult()); 
			return;
		}
		if("send_vote".equals(cmd.getCmd())) {
			System.out.println("chatthread——发送投票成功");
			JOptionPane.showMessageDialog(null, cmd.getResult()); 
			return;
		}
		if("update_vote".equals(cmd.getCmd())) {
			System.out.println("chatthread——投票成功");
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

