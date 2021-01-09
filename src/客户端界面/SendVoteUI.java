package 客户端界面;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Entity.User;
import Entity.Vote;
import UserSocket.ChatThread;
import UserSocket.Client;
import Util.CommandTranser;
import 客户端界面.ChatUI.MyMouseListener;

public class SendVoteUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private User owner;
	private Client client;
	private ChatUI chatui;
	private JTextField votename;
	private JTextArea constent;
	private JButton send_bt;
	private JPanel up_jp,center_jp,down_jp;
	private ChatThread thread;
	
	public SendVoteUI(User owner,Client client,ChatUI chatui,ChatThread thread) {
		this.owner=owner;
		this.client=client;
		this.chatui=chatui;
		this.thread=thread;
		init();
	}
	private void init() {
		// TODO 自动生成的方法存根
	    JPanel jpanel = new JPanel();
        jpanel.setLayout(null);
        
        jpanel.setPreferredSize(new Dimension(500, 400));
        //添加按钮
        votename=new JTextField("输入本次投票名称");
		votename.setBounds(0,0,500,50);
		constent=new JTextArea("输入投票内容");
		constent.setBounds(0,50,500,300);
        
         send_bt=new JButton("发布");
		send_bt.setBounds(220,360,60,40);
		send_bt.addActionListener(this);
		
		jpanel.add(send_bt);
		jpanel.add(constent);
		jpanel.add(votename);
       
        
        
        
        // 设置窗体属性
        JFrame frame = new JFrame("发布投票");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(jpanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		votename.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
            	if(votename.getText().trim().equals("输入本次投票名称"))
                votename.setText("");
            }
            @Override
            public void focusLost(FocusEvent e)
            {	if(votename.getText().trim().equals(""))
            	votename.setText("输入本次投票名称");
            }
        });
		
		constent.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
            	if(constent.getText().trim().equals("输入投票内容"))
                constent.setText("");
            }
            @Override
            public void focusLost(FocusEvent e)
            {	if(constent.getText().trim().equals(""))
            	constent.setText("输入投票内容");
            }
        });
	}
		
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==send_bt) {
			System.out.println("send notice root= "+owner.getRoot());
			if(owner.getRoot()==null) {
				JOptionPane.showMessageDialog(null, "你不是管理员，无法发布投票"); 
				}
			else {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss a");
				String name = votename.getText().trim();
				String txt=constent.getText().trim();
				if("".equals(name) || name == null || "输入投票名称".equals(name)) {
					JOptionPane.showMessageDialog(null, "请输入投票名称！！");
					return;
				}
				System.out.println("sendvoteUI 投票内容 " +txt);
				Vote vote =new Vote(name,owner.getUserName(),txt);
				CommandTranser cmd=new CommandTranser();
				cmd.setCmd("send_vote");
				cmd.setSender(owner.getUserName());
				cmd.setData(vote);
				
				//给页面添加新投票
				ImageIcon icon=new ImageIcon("image/投票.jpg");
				icon.setImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
				JPanel temp=new JPanel();
				temp.setLayout(new GridLayout(1,3));
				JLabel jb1=new JLabel(icon);
				JLabel jb2=new JLabel(name);
				JLabel jb3=new JLabel(owner.getUserName());
				temp.add(jb1);
				temp.add(jb2);
				temp.add(jb3);
				temp.addMouseListener(new MyMouseListener());
				chatui.getVotePanel().add(temp);
				chatui.getVotePanel().validate();
				chatui.getVotePanel().repaint();
				chatui.getVotePanel().setVisible(true);
				
				client.sendData(cmd);
				this.dispose();
			}
	}
	}
	 class MyMouseListener extends MouseAdapter{
			
			@Override
			public void mouseClicked(MouseEvent e) {
				//双击我的好友弹出与该好友的聊天框
				if(e.getClickCount() == 2) {
					JPanel panel = (JPanel)e.getSource(); //getSource()返回的是Object,
					
					
					JLabel temp=(JLabel) panel.getComponent(1);
					String name = temp.getText().trim();
					System.out.println(" chat ui votename="+name);
					new VoteUI(owner,client,name,thread);
					
				}	
			}
	}	
}
