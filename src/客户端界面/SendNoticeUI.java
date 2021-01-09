package 客户端界面;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
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
import javax.swing.SwingConstants;

import Entity.Note;
import Entity.NoteEntity;
import Entity.NoticeList;
import Entity.User;
import UserSocket.Client;
import Util.CommandTranser;

public class SendNoticeUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private User owner;
	private Client client;
	private JButton send_bt;
	private JPanel up_jp,center_jp,down_jp;
	private JTextField noticename;
	private JTextArea constent;
	private NoticeUI noticeUI;
	public SendNoticeUI(User owner,Client client,NoticeUI noticeUI) {
		this.owner=owner;
		this.client=client;
		this.noticeUI=noticeUI;
		init();
		
		
	}
	private void init() {
		// TODO 自动生成的方法存根
	    JPanel jpanel = new JPanel();
        jpanel.setLayout(null);
        
        jpanel.setPreferredSize(new Dimension(500, 400));
        //添加按钮
        noticename=new JTextField("输入公告名称");
		noticename.setBounds(0,0,500,50);
		constent=new JTextArea("输入公告内容");
		constent.setBounds(0,50,500,300);
        
         send_bt=new JButton("发布");
		send_bt.setBounds(220,360,60,40);
		send_bt.addActionListener(this);
		
		jpanel.add(send_bt);
		jpanel.add(constent);
		jpanel.add(noticename);
       
        
        
        
        // 设置窗体属性
        JFrame frame = new JFrame("发布公告");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(jpanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		noticename.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
            	if(noticename.getText().trim().equals("输入公告名称"))
                noticename.setText("");
            }
            @Override
            public void focusLost(FocusEvent e)
            {	if(noticename.getText().trim().equals(""))
            	noticename.setText("输入公告名称");
            }
        });
		
		constent.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
            	if(constent.getText().trim().equals("输入公告内容"))
                constent.setText("");
            }
            @Override
            public void focusLost(FocusEvent e)
            {	if(constent.getText().trim().equals(""))
            	constent.setText("输入公告内容");
            }
        });
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==send_bt) {
			System.out.println("send notice root= "+owner.getRoot());
			if(owner.getRoot()==null) {
				JOptionPane.showMessageDialog(null, "你不是管理员，无法发布公告"); 
				}
			else {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss a");
				String name = noticename.getText().trim();
				String txt=constent.getText().trim();
				if("".equals(name) || name == null || "输入公告名称".equals(name)) {
					JOptionPane.showMessageDialog(null, "请输入公告名称！！");
					return;
				}
				System.out.println("sendnoteUI 公告内容 " +txt);
				Note note =new Note(name,owner.getUserName()+"    发布于"+ sdf.format(date),txt);
				CommandTranser cmd=new CommandTranser();
				cmd.setCmd("send_notice");
				cmd.setSender(owner.getUserName());
				cmd.setData(note);
				//给页面添加新公告
				ImageIcon icon=new ImageIcon("image/文件.jpg");
				icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
				JLabel jb=new JLabel(name+" ",icon,JLabel.CENTER);
				jb.setHorizontalAlignment(SwingConstants.LEFT);
				noticeUI.getNoticePanel().add(jb);
				jb.addMouseListener(new MyMouseListener());
				noticeUI.getNoticePanel().validate();
				noticeUI.getNoticePanel().repaint();
				noticeUI.getNoticePanel().setVisible(true);
				
				client.sendData(cmd);
			}
		}
	}
class MyMouseListener extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			if(e.getClickCount() == 2) {
				JLabel label = (JLabel)e.getSource(); //getSource()返回的是Object,
				
				//通过label中的getText获取聊天对象
				String noticename = label.getText().trim();
				//System.out.println(friendname + "*");
				//查看与该好友是否创建过窗口
				NoteUI noteUI = NoticeList.getNoteUI(noticename);
				if(noteUI == null) {
					System.out.println(noticename);
					noteUI = new NoteUI(noticename, client);
					NoteEntity noteEntity = new NoteEntity();
					noteEntity.setName(noticename);
					noteEntity.setNoteUI(noteUI);
					NoticeList.addNoteUI(noteEntity);
				} else {
					noteUI.show(); //如果以前创建过仅被别的窗口掩盖了 就重新显示
				}
				
			}	
		}

		
	}
	}

