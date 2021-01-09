package 客户端界面;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Entity.User;
import Entity.Vote;
import UserSocket.ChatThread;
import UserSocket.Client;
import Util.CommandTranser;

public class VoteUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Client client;
	private User owner;
	private String votename;
	private JTextArea text,tipsArea,writeArea;
	private JTextField sender;
	private JButton send_bt;
	private JRadioButton jrb1,jrb2;		//定义单选框组件
	private JScrollPane scrollPane = null;
	private JScrollPane scrollPane2 = null;
	private ButtonGroup bg;				//定义按钮组(作用域）
	private ChatThread thread;
	private Vote vote=new Vote();
	private int chooseflag=0;
	public VoteUI(User owner,Client client,String votename,ChatThread thread) {
		this.client=client;
		this.owner=owner;
		this.votename=votename;
		this.thread=thread;
		vote.setUserName(owner.getUserName());
		vote.setVoteName(votename);
		CommandTranser cmd=new CommandTranser();
		cmd.setCmd("get_vote");
		cmd.setData(vote);
		client.sendData(cmd);
		
		vote=(Vote)thread.getVote();
		while(vote.getSender()==null) {
			vote=(Vote)thread.getVote();
		}
		while(! vote.getVoteName().equals(votename)){
			vote=(Vote)thread.getVote();
		}
		
		init();
		
	}
	
	private void init() {
		
		System.out.println("voteUI 名称"+vote.getVoteName()+"内 容 "+vote.getConstent()+"赞同数量"+vote.getAgreeNum());
		setSize(400,720);		/*设置窗口大小*/
		setResizable(false);				//是否可以改变大小
		setLocationRelativeTo(null);				//设置窗口相对于指定组件的位置。如果组件当前未显示，或者 c 为 null，则此窗口将置于屏幕的中央
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);			/*关闭窗口*/
		setLayout(null);//取消掉流式布局
		setTitle("投票 :"+votename);
		//提示栏1
		JLabel jl1=new JLabel("需投票的文稿：");
		jl1.setBounds(0, 0,400,30);
		//文稿栏
		text=new JTextArea();
		text.setEditable(false); 
		text.append(vote.getConstent());
		scrollPane = new JScrollPane(text);
		scrollPane.setPreferredSize(new Dimension(400, 150));
		scrollPane.setBounds(0,30,400,150);
		//发送者栏
		JLabel jl2=new JLabel("发布者  :");
		sender=new JTextField(20);
		sender.setText(vote.getSender());
		JPanel jp=new JPanel();
		jp.setLayout(new GridLayout(1,2));
		jp.add(jl2);
		jp.add(sender);
		jp.setBounds(0,180,400,30);
		sender.setEditable(false);
		sender.setHorizontalAlignment(SwingConstants.LEFT );
		//提示栏2
		JLabel jl3=new JLabel("现已投票情况和已有建议 :");
		jl3.setBounds(0, 220,400,30);
		//看建议栏 以及显示当前投票人数
		tipsArea=new JTextArea();
		tipsArea.setEditable(false); 
		String temp="当前，赞成人数有"+vote.getAgreeNum()+" 不赞成人数有"+vote.getDisgreeNum()+"\n"+vote.getTips();
		tipsArea.append(temp);
		scrollPane2= new JScrollPane(tipsArea);
		scrollPane2.setPreferredSize(new Dimension(400, 200));
		scrollPane2.setBounds(0,260,400,200);
		//选择栏
		JPanel choose=new JPanel();
		JLabel jpl=new JLabel("你的投票:         ");
		jrb1=new JRadioButton("赞成");			//创建单选框
		jrb2=new JRadioButton("反对");
		bg=new ButtonGroup();					//按钮组
		choose.add(jpl);			//添加面板2的组件	
		bg.add(jrb1);			//必须要把单选框放入按钮组作用域中才能实现单选
		bg.add(jrb2);
		choose.add(jrb1);
		choose.add(jrb2);
		choose.setBounds(0,470,400,40);
		if(jrb1.isSelected()) {
			System.out.println("你选的反对");
			chooseflag=2;
		}else {chooseflag=1;System.out.println("你选的赞成");}
		//提示栏4
		JLabel jl4=new JLabel("写下你的建议 :");
		jl4.setBounds(0, 510,400,30);
		//写建议栏
		writeArea =new JTextArea();
		writeArea.setBounds(0, 540, 400, 100);
		//提交按钮
		send_bt=new JButton("提交");
		send_bt.setBounds(300, 640,80,40);
		send_bt.addActionListener(this);
		//添加
		add(jl1);
		add(scrollPane);
		add(jp);
		add(jl3);
		add(scrollPane2);
		add(choose);
		add(jl4);
		add(writeArea);
		add(send_bt);
		setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==send_bt) {
			if(vote.getFlag()==1) {
				JOptionPane.showMessageDialog(null, "你已经投过这个票了!");
			}else {
				if(chooseflag==0) {
					JOptionPane.showMessageDialog(null, "请选择赞同或反对!");
				}
				String write=writeArea.getText();
				String temp=vote.getTips();
				vote.setTips(temp+"\n"+write);
				System.out.println("voteui 的选择结果"+chooseflag);
				vote.setChooseFlag(chooseflag);
				vote.setUserName(owner.getUserName());
				CommandTranser newcmd=new CommandTranser();
				newcmd.setCmd("update_vote");
				newcmd.setData(vote);
				client.sendData(newcmd);
				
			}
		}
	}
}
