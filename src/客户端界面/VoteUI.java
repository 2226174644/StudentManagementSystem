package �ͻ��˽���;

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
	private JRadioButton jrb1,jrb2;		//���嵥ѡ�����
	private JScrollPane scrollPane = null;
	private JScrollPane scrollPane2 = null;
	private ButtonGroup bg;				//���尴ť��(������
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
		
		System.out.println("voteUI ����"+vote.getVoteName()+"�� �� "+vote.getConstent()+"��ͬ����"+vote.getAgreeNum());
		setSize(400,720);		/*���ô��ڴ�С*/
		setResizable(false);				//�Ƿ���Ըı��С
		setLocationRelativeTo(null);				//���ô��������ָ�������λ�á���������ǰδ��ʾ������ c Ϊ null����˴��ڽ�������Ļ������
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);			/*�رմ���*/
		setLayout(null);//ȡ������ʽ����
		setTitle("ͶƱ :"+votename);
		//��ʾ��1
		JLabel jl1=new JLabel("��ͶƱ���ĸ壺");
		jl1.setBounds(0, 0,400,30);
		//�ĸ���
		text=new JTextArea();
		text.setEditable(false); 
		text.append(vote.getConstent());
		scrollPane = new JScrollPane(text);
		scrollPane.setPreferredSize(new Dimension(400, 150));
		scrollPane.setBounds(0,30,400,150);
		//��������
		JLabel jl2=new JLabel("������  :");
		sender=new JTextField(20);
		sender.setText(vote.getSender());
		JPanel jp=new JPanel();
		jp.setLayout(new GridLayout(1,2));
		jp.add(jl2);
		jp.add(sender);
		jp.setBounds(0,180,400,30);
		sender.setEditable(false);
		sender.setHorizontalAlignment(SwingConstants.LEFT );
		//��ʾ��2
		JLabel jl3=new JLabel("����ͶƱ��������н��� :");
		jl3.setBounds(0, 220,400,30);
		//�������� �Լ���ʾ��ǰͶƱ����
		tipsArea=new JTextArea();
		tipsArea.setEditable(false); 
		String temp="��ǰ���޳�������"+vote.getAgreeNum()+" ���޳�������"+vote.getDisgreeNum()+"\n"+vote.getTips();
		tipsArea.append(temp);
		scrollPane2= new JScrollPane(tipsArea);
		scrollPane2.setPreferredSize(new Dimension(400, 200));
		scrollPane2.setBounds(0,260,400,200);
		//ѡ����
		JPanel choose=new JPanel();
		JLabel jpl=new JLabel("���ͶƱ:         ");
		jrb1=new JRadioButton("�޳�");			//������ѡ��
		jrb2=new JRadioButton("����");
		bg=new ButtonGroup();					//��ť��
		choose.add(jpl);			//������2�����	
		bg.add(jrb1);			//����Ҫ�ѵ�ѡ����밴ť���������в���ʵ�ֵ�ѡ
		bg.add(jrb2);
		choose.add(jrb1);
		choose.add(jrb2);
		choose.setBounds(0,470,400,40);
		if(jrb1.isSelected()) {
			System.out.println("��ѡ�ķ���");
			chooseflag=2;
		}else {chooseflag=1;System.out.println("��ѡ���޳�");}
		//��ʾ��4
		JLabel jl4=new JLabel("д����Ľ��� :");
		jl4.setBounds(0, 510,400,30);
		//д������
		writeArea =new JTextArea();
		writeArea.setBounds(0, 540, 400, 100);
		//�ύ��ť
		send_bt=new JButton("�ύ");
		send_bt.setBounds(300, 640,80,40);
		send_bt.addActionListener(this);
		//���
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
		// TODO �Զ����ɵķ������
		if(e.getSource()==send_bt) {
			if(vote.getFlag()==1) {
				JOptionPane.showMessageDialog(null, "���Ѿ�Ͷ�����Ʊ��!");
			}else {
				if(chooseflag==0) {
					JOptionPane.showMessageDialog(null, "��ѡ����ͬ�򷴶�!");
				}
				String write=writeArea.getText();
				String temp=vote.getTips();
				vote.setTips(temp+"\n"+write);
				System.out.println("voteui ��ѡ����"+chooseflag);
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
