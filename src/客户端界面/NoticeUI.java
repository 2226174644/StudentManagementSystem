package 客户端界面;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Entity.NoteEntity;
import Entity.NoticeList;
import Entity.User;
import UserSocket.Client;
import Util.CommandTranser;

public class NoticeUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private User owner;// 当前用户
	private Client client;// 客户端
    private JButton send_notice;
    private JPanel notice_pal;
    
    public NoticeUI() {}
    public NoticeUI(User owner,Client client) {
    	this.owner=owner;
    	this.client=client;
    	setTitle("公告");
    	setSize(600,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		init();
    }
	
	

	private void init() {
		// TODO 自动生成的方法存根
		//登陆成功后下部分 修改密码, 添加好友
				final JPanel down_S = new JPanel();
				down_S.setLayout(new BorderLayout());
				add(down_S, BorderLayout.SOUTH);
				
				//发布公告按钮
				final JPanel down_S_W = new JPanel();
//				final FlowLayout flowLayout = new FlowLayout();
//				flowLayout.setAlignment(FlowLayout.RIGHT);//流式布局 •组件方向。从右到左
//				down_S_W.setLayout(flowLayout);
				down_S.add(down_S_W); 
				send_notice = new JButton(); 
				down_S_W.add(send_notice);
				send_notice.setHorizontalTextPosition(SwingConstants.LEFT);
				send_notice.setHorizontalAlignment(SwingConstants.LEFT);
				send_notice.setText("发布公告");
				send_notice.addActionListener(this);
				
				//公告栏
				ImageIcon icon=new ImageIcon("image/文件.jpg");
				icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
				 notice_pal = new JPanel();
				//notice_pal.setLayout(new GridLayout(5, 1));
				int noticenum = owner.getNoticeNum();//d
				System.out.println("70 UI noticenum "+noticenum);
				notice_pal.setLayout(new GridLayout(50, 1, 4, 4));//行数 列数
				final JLabel noticename[];
				noticename=new JLabel[noticenum];
				String insert = new String();
				
				ArrayList<String> noticelist = new ArrayList<String>(owner. getNotice());
				for (int i = 0; i < noticenum; ++i) {
					// 设置icon显示位置在jlabel的左边
					insert = (String)noticelist.get(i);
					System.out.println("NoticeUI 公告名 "+insert);
					while(insert.length() < 38) {
						insert = (String)(insert + " ");
					}
					System.out.println("NoticeUI 11");
					noticename[i] = new JLabel(insert, icon, JLabel.CENTER);
					noticename[i].setHorizontalAlignment(SwingConstants.LEFT);//置于左侧
					noticename[i].addMouseListener(new MyMouseListener());
					notice_pal.add(noticename[i]);//把公告label添加到公告panel中
					System.out.println("NoticeUI 84");
				}
				notice_pal.setBackground(Color.WHITE);
				add(notice_pal,BorderLayout.NORTH);
				setVisible(true);
	}
	public JPanel getNoticePanel() {
		return notice_pal;
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
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==send_notice) {
				new SendNoticeUI(owner,client,this);
		
	}

	}}
	